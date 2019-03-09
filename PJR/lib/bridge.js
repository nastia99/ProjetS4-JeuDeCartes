const MCAST_IP = "224.7.7.7";
const MCAST_PORT = 7777;
const WS_PORT = 7777;

// Multicast dependency
let dgram = require('dgram');
let dgramServer = dgram.createSocket({type: 'udp4', reuseAddr: true});

// Socket dependency
let net = require('net');

// WebSocket dependency
let server = require('ws').Server;
let ws = new server({port: WS_PORT});

let {messageRouter} = require('./lib');

const INCOM_MCAST_PROT = ['RP'];

// All PJR websockets
let clients = [];


let interfaces = [];

//---------- NETWORK INTERFACES ----------\\

let os = require('os');
let ifaces = os.networkInterfaces();

Object.keys(ifaces).map(ifname => {
  ifaces[ifname].map(iface => {
    if (iface.family !== 'IPv4' || iface.internal !== false) return;
    interfaces.push(iface.address);
  });
});


//---------- MULTICAST ----------\\

dgramServer.on('listening', () => {
  interfaces.map(interface =>
    dgramServer.addMembership(MCAST_IP, interface));
  //dgramServer.setMulticastLoopback(false);
  console.log('Multicast opérationnel');
});

// On message received from PCJs
// MCAST -> PJRs
dgramServer.on('message', mes => {
  while([0x0,0x1].includes(mes[0]))
    mes = mes.slice(1);
  mes = mes.toString('utf8')
  console.log(' <mcast', mes);
  clients.forEach(conn => {
    if(conn.readyState == conn.OPEN)
      conn.send(mes);
  });
});
// On multicast disconnect
dgramServer.on('close', () => {
  console.log('multicast disconnect');
});

// On multicast error
dgramServer.on('error', () => {
  console.log(' /!\\ multicast error');
});

dgramServer.bind(MCAST_PORT);


//---------- SOCKET ----------\\

// Accept PJR connection
ws.on('connection', conn => {
  console.log(' + new client');
  clients.push(conn);

  let socketConn;

  // On message received from the PJR
  conn.on('message', mes => {
    // PJR -> MCAST
    if(INCOM_MCAST_PROT.includes(mes.split('-')[0])) {
      console.log(" client/mcast>", mes);
      if(conn.readyState == conn.OPEN)
        dgramServer.send(mes, MCAST_PORT, MCAST_IP);
    }
    // PJR -> PCJ
    else {
      console.log(" client/socket>", mes);
      messageRouter(mes)
        .addType('type', 'JR|JV')
        .route('CP-<ip:string>-<port:int>-<pseudo:string>-<type:type>-<id:int>', info => {
            socketConn = net.connect(info.port, info.ip);
            socketConn.setEncoding('utf8');
            if(socketConn && !socketConn.destroyed)
                socketConn.write(`CP-${info.pseudo}-${info.type}-${info.id}\n`);
            socketConn.on('data', mes => {
              // PCJ -> PJR
              mes = mes.toString('utf8');
              console.log(" <socket", mes);
              messageRouter(mes)
                .route('RC-<id:int>', info => {
                  if(socketConn)
                    socketConn.end();
                });
              if(conn.readyState == conn.OPEN)
                conn.send(mes);
            });
            socketConn.on('close', () => {
              if(conn.readyState == conn.OPEN)
                conn.send('DISCONNECT');
            });
            socketConn.on('error', () => {});
          })
        .route('CLOSE', info => {
          if(socketConn)
            socketConn.end()
        })
        .default(mes => {
          if(socketConn && !socketConn.destroyed)
            socketConn.write(mes+'\n')
        });
    }
  });
  
  // On PJR disconnect
  conn.on('close', () => {
    console.log(' - client disconnect');
    clients.splice(clients.indexOf(conn), 1);
    if(socketConn)
      socketConn.end();
  });

  // On PJR error
  conn.on('error', () => {
    console.log(' /!\\ client error');
  });
});