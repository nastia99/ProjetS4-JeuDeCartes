/**
 * Joinable servers manager
 * 
 * @constructor
 */
class ServerManager {
  constructor() {
    /** @private */ this.gameFinder = new FinderNetworkInterface(this);
    /** @private */ this.servers = [];
  }

  /**
   * Add or update a server
   * 
   * @param {Server} server Server to add / update
   */
  updateServer(server) {
    if(!(server instanceof Server)) return;
    let existingServer = this.servers.find(s => s.ip == s.ip && s.port == server.port);
    if (existingServer)
      this.servers[this.servers.indexOf(existingServer)] = server;
    else
      this.servers.push(server);
  }

  /**
   * Requests a server update to the game finder
   * 
   * @param {String} type Type of requested servers
   * @param {number} maxPlayers Max players of requested servers
   */
  refreshServers(type, maxPlayers) {
    this.gameFinder.refreshServers(type, maxPlayers);
  }

  /**
   * Asks the server to join it
   * 
   * @param {Server} server Server to join
   */
  join(server) {
    // TODO - ServerManager join
    if(!(server instanceof Server)) return;
    new GameNetworkInterface(server.ip, server.port);
  }
}





/**
 * Game finder interface communicating with game devices
 * 
 * @constructor
 */
class FinderNetworkInterface {
  constructor(serverManager) {
    const MCAST_ADDR = location.hostname || '127.0.0.1';
    const MCAST_PORT = "7777"; 

    this.serverManager = serverManager;
    this.connection = new WebSocket(`ws://${MCAST_ADDR}:${MCAST_PORT}`);
    this.connection.onmessage = (...args) =>
      this.receive.call(this, ...args);
    // TODO - Handle error case
    this.connection.onerror = () => {};
  }

  /**
   * Handles a message reception
   * 
   * @param {MessageEvent} message Message received
   */
  receive(message) {
    execIfMatch.call(this,
      /^AP-(?<ip>.{1,3}(?:\..{1,3}){3})-(?<port>[0-9]{1,5})-(?<name>[^\-]+)-(?<nbjm>\d+)-(?<nbjrm>\d+)-(?<nbjvm>\d+)-(?<nbjrc>\d+)-(?<nbjvc>\d+)-(?<status>[a-zA-Z]+)$/,
      message.data, this.updateServer);
  }

  /**
   * Ask for game servers refresh
   * 
   * @param {String} type Type of players (JRU: real player only, JVU: virtual player only or MIXTE: both player types)
   * @param {number} maxPlayers 
   */
  refreshServers(type, maxPlayers) {
    const allowedTypes = ['JRU', 'JVU', 'MIXTE'];
    if (!allowedTypes.includes(type)) return;
    if (typeof maxPlayers != "number" || maxPlayers < 0 || maxPlayers > 5) return;
    this.serverManager
      .send(`RP-${type}-${maxPlayers}`);
  }

  updateServer(ap) {
    let server = FinderNetworkInterface.APToServer(ap.groups);
    this.serverManager.updateServer(server);
  }

  static APToServer(info) {
    return new Server(info.ip, info.port, info.name, info.nbjrm, info.nbjvm, info.nbjrc, info.nbjvc, info.status);
  }
}






class GameNetworkInterface {
  constructor(ip, port) {
    this.connection = new WebSocket('ws://'+(location.hostname || '127.0.0.1')+':7778');
    this.connection.onopen = () =>
      this.connection.send(`CONN-${ip}-${port}`);
    // TODO - Handle error case
    this.connection.onerror = () => {};
  }

  join(pseudo, type) {
    // TODO - join
    connection.send(`CP-${pseudo}-JR`);
  }
}






class Server {
  constructor(ip, port, name, maxRP, maxVP, connectedRP, connectedVP, status) {
    if (arguments.length != 8)
      throw new TypeError(`Failed to construct '${this.constructor.name}': 8 argument required, but only ${arguments.length} present.`);

    this.ip = ip;
    this.port = port;
    this.name = name;
    this.maxRP = maxRP;
    this.maxVP = maxVP;
    this.connectedRP = connectedRP;
    this.connectedVP = connectedVP;
    this.status = status;
  }

  get maxP() {
    return +this.maxRP + +this.maxVP;
  }
}






function execIfMatch(regexp, value, callback) {
  if (regexp instanceof RegExp)
    return regexp.test(value) ? callback.call(this, regexp.exec(value)) : false;
  throw new TypeError("argument 0 must be of type RegExp");
}

// Get the next message received from a connection
// withing the abort time if it's given
function nextMessage(connection, abortTime) {
  return new Promise((res, rej) => {
    let abortTimeout,
      callbackSave = connection.onmessage;
    if(abortTime)
      abortTimeout = setTimeout(() => {
        connection.onmessage = callbackSave;
        rej();
      }, abortTime);
    connection.onmessage = mes => {
      connection.onmessage = callbackSave;
      clearTimeout(abortTimeout);
      res(mes);
    };
  });
}