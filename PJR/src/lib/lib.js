import messageRouter from './messageRouter';
import EventEmitter from './EventEmitter';

const BRIDGE_IP   = window.location.hostname || '127.0.0.1';
const BRIDGE_PORT = 7777;






let bridgeConnection;

(() => {
  const RETRY_DELAY = 5;
  let emitter = new EventEmitter();

  class Connection extends WebSocket {
    constructor(url) {
      super(url);
      this.onopen    = (...args) => emitter.emit('open', ...args);
      this.onmessage = (...args) => emitter.emit('message', ...args);
      this.onerror   = (...args) => emitter.emit('error', ...args);
      this.onclose   = (...args) => emitter.emit('close', ...args);
      
      this.on = (...args) => {emitter.on(...args)}
      this.off = (...args) => {emitter.off(...args)}
      this.send = (...args) => {
        if(this.bufferedAmount === 0)
          super.send(...args);
        else
          setTimeout(() => this.send(...args), 100);
      }
    }
  }

  function connect() {
    bridgeConnection = new Connection(`ws://${BRIDGE_IP}:${BRIDGE_PORT}`);
  }
  connect();

  bridgeConnection.on('close', () => {
    console.log(`Reconnecting in ${RETRY_DELAY} seconds...`);
    setTimeout(connect, RETRY_DELAY*1000);
  });

})();






/**
 * Handles joinable servers
 */
export default class ServerManager extends EventEmitter {
  /**
   * @constructor
   */
  constructor(gameService) {
    super();
    /** @private */ this.finderInterface = new FinderNetworkInterface(this);
    /** @private */ this.servers = [];
    /** @private */ this.gameService = gameService;
  }

  /**
   * Add or update a server.
   * If the game associated to the server is canceled, the server will be removed from the list
   * Likewise, if the game if finished, it will be removed after a time
   * 
   * @param {Server} server Server to add / update
   */
  updateServer(server) {
    if(!(server instanceof Server)) return;
    let existingServer = this.servers.find(s => s.ip === server.ip && s.port === server.port);
    if(existingServer && server.isCanceled())
      this.servers.splice(this.servers.indexOf(existingServer), 1);
    else if(existingServer && !server.isCanceled())
      existingServer.setProperties(server);
    else if(!server.isCanceled())
      this.servers.push(server);
    else
      return;
    if(server.isFinished())
      setTimeout(() => {
        this.servers.splice(this.servers.indexOf(existingServer || server), 1);
        this.emit('update', [...this.servers]);
      }, 10000);
    this.emit('update', [...this.servers]);
  }

  /**
   * Requests a server update to the game finder
   * 
   * @param {string} type Type of requested servers
   * @param {number} maxPlayers Max players of requested servers
   */
  refreshServers(type, maxPlayers) {
    this.finderInterface.refreshServers(type, maxPlayers);
  }

  /**
   * Creates an anonymous server
   * 
   * @param {string} ip Ip of the anonymous server
   * @param {nomber} port Port of the anonymous server
   */
  getAnonymousServer(ip, port) {
    return new Server(ip, parseInt(port), '', 0, 0, 0, 0, 'ATTENTE');
  }

  /**
   * Asks the server to join it
   * 
   * @param {Server} server Server to join
   * @param {string} pseudo Pseudonym of the joining player
   */
  join(server, pseudo) {
    if(server instanceof Server && server.isWaiting())
      return this.gameService.join(server, pseudo);
  }
}






/**
 * Game finder interface communicating with game devices
 */
class FinderNetworkInterface {
  /**
   * @constructor
   * @param {ServerManager} serverManager Server manager communicating with
   */
  constructor(serverManager) {
    /** @private */ this.serverManager = serverManager;
    /** @private */ this.connection = bridgeConnection;

    this.connection.on('message', this.router.bind(this));
    this.connection.on('open', () => {
      this.refreshServers('MIXTE', 5);
      this.refreshServers('JRU', 5);
      this.refreshServers('JVU', 5);
    });
  }

  /**
   * Handles a message reception
   * 
   * @param {MessageEvent} message Message received
   */
  router(message) {
    messageRouter(message)
      .route('AP-<ip:ip>-<port:int>-<name:string>-<nbjm:int>-<nbjrm:int>-<nbjvm:int>-<nbjrc:int>-<nbjvc:int>-<status:string>',
        this.updateServer.bind(this));
  }

  ///// Received message /////

  /**
   * Updates a server information
   * 
   * @param {Object} info Information of the server
   */
  updateServer(info) {
    let server = new Server(info.ip, info.port, info.name, info.nbjrm, info.nbjvm, info.nbjrc, info.nbjvc, info.status);
    this.serverManager.updateServer(server);
  }

  ///// Sent message /////

  /**
   * Ask for game servers refresh
   * 
   * @param {string} type Type of players (JRU: real player only, JVU: virtual player only or MIXTE: both player types)
   * @param {number} maxPlayers Maximum amout of players allowed in the server
   */
  refreshServers(type, maxPlayers) {
    const ALLOWED_TYPES = ['JRU', 'JVU', 'MIXTE', 'MIXSR', 'MIXSV'];
    if (!ALLOWED_TYPES.includes(type)) return;
    if (typeof maxPlayers != "number" || maxPlayers < 0 || maxPlayers > 5) return;
    if(this.connection.readyState === this.connection.OPEN)
      this.connection.send(`RP-${type}-${maxPlayers}`);
  }
}






/**
 * Represents a server
 */
class Server {
  /**
   * @constructor
   * @param {string} ip Ip of the server
   * @param {number} port Port of the server
   * @param {string} name Name of the server
   * @param {number} maxRP Max real players of the server
   * @param {number} maxVP Max virtual players of the server
   * @param {number} connectedRP Amout of real players in the server
   * @param {number} connectedVP Amout of virtual players in the server
   * @param {string} status Current state of the server
   */
  constructor(ip, port, name, maxRP, maxVP, connectedRP, connectedVP, status) {
    if (arguments.length !== 8)
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

  /**
   * Get the total amount of player allowed in the server (i.e. maxRP + maxVP)
   */
  get maxP() {
    return +this.maxRP + +this.maxVP;
  }

  /**
   * Copy the given server's properties
   * 
   * @param {Server} server Server to be copied
   */
  setProperties(server) {
    this.ip = server.ip;
    this.port = server.port;
    this.name = server.name;
    this.maxRP = server.maxRP;
    this.maxVP = server.maxVP;
    this.connectedRP = server.connectedRP;
    this.connectedVP = server.connectedVP;
    this.status = server.status;
  }

  /**
   * Returns whether the server is in waiting state
   */
  isWaiting() {
    return this.status === 'ATTENTE';
  }

  /**
   * Returns whether the server is in complete state
   */
  isComplete() {
    return this.status === 'COMPLETE';
  }

  /**
   * Returns whether the server is in cancel state
   */
  isCanceled() {
    return this.status === 'ANNULEE';
  }

  /**
   * Returns whether the server is in finished state
   */
  isFinished() {
    return this.status === 'TERMINEE';
  }
}






export class GameService {
  /**
   * @constructor
   */
  constructor() {
    /** @private */ this.game = null;
  }

  /**
   * Creates a game instance
   * 
   * @param {Server} server Server to join
   * @param {string} pseudo Pseudonym of the joining player
   */
  join(server, pseudo) {
    if(server instanceof Server) {
      this.destroy();
      this.game = new Game(server, pseudo);
      return this.game;
    }
  }

  /**
   * Returns the current game
   */
  getCurrentGame() {
    return this.game;
  }

  /**
   * Destroys a game
   */
  destroy() {
    if(this.game instanceof Game)
      this.game.destroy();
    this.game = null;
  }
}






/**
 * Playable game
 */
class Game extends EventEmitter {
  constructor(server, pseudo) {
    super();
    if(!(server instanceof Server))
      throw new TypeError('Server must be of a Server');
    pseudo = pseudo.replace(/-/g, '').trim();

    /** @private */ this.server = server;
    /** @private */ this.pseudo = pseudo;
    /** @private */ this.gameInterface = new GameNetworkInterface(server.ip, server.port, pseudo);

    /** @private */ this.players = [];       // Player list
    /** @private */ this.herd = [];          // Current herd
    /** @private */ this.currentPlayerIndex = 0; // Current player index (from 0 to 4)
    /** @private */ this.direction = 1;      // Direction of the game (1 for croi, -1 for decroi)
    /** @private */ this.hand = [];          // Cards in the player hand
    /** @private */ this.stackSize = 48;     // Size of the stack

    /** @private */ this.gameId = null;      // Current game ID
    /** @private */ this.roundId = null;     // Current round ID
    /** @private */ this.turnId = null;      // Current turn ID

    /** @private */ this.herdPickedUp = false;    // Herd picked up by the player on his turn
    /** @private */ this.lastCardPlayed = null;   // Last card played

    this.gameInterface.on('start', this.startGame.bind(this));
    this.gameInterface.on('newRound', this.newRound.bind(this));
    this.gameInterface.on('collectHand', this.collectHand.bind(this));
    this.gameInterface.on('newTurn', this.newTurn.bind(this));
    this.gameInterface.on('drawCard', this.drawCard.bind(this));
    this.gameInterface.on('endTurn', this.endTurn.bind(this));
    this.gameInterface.on('endRound', this.endRound.bind(this));
    this.gameInterface.on('disconnection', this.disconnection.bind(this));
    this.gameInterface.on('end', this.endGame.bind(this));
    this.gameInterface.on('stop', this.stopGame.bind(this));
    this.gameInterface.on('replay', this.replayGame.bind(this));

    this.gameInterface.relay(this, ['accept', 'reject', 'error', 'close', 'start']);
  }

  /**
   * Returns player
   */
  getPlayer() {
    return this.players.find(player => player.getName() === this.pseudo);
  }

  /**
   * Returns the currently playing player
   */
  get currentPlayer() {
    if(this.currentPlayerIndex >= 0)
      return this.players[this.currentPlayerIndex];
  }

  /**
   * Whether the current turn allows the player to play
   */
  isAllowedToPlay() {
    if(this.currentPlayer)
      return this.currentPlayer.getName() === this.pseudo;
  }

  /**
   * Returns whether the herd is empty
   */
  isHerdEmpty() {
    return Array.isArray(this.herd) && this.herd.length === 0;
  }

  /**
   * Add a card to the herd
   * 
   * @param {Card} card Card to add to the herd
   */
  addToHerd(card) {
    // The herd is empty
    if(this.isHerdEmpty()) {
      this.herd.push(card);
      return;
    }
    // The card is a number
    if(!card.isSpecial() || card.value === '0' || card.value === '16') {
      if(parseInt(card.value) < parseInt(this.herd[0].value))
        this.herd.unshift(card);
      else if(parseInt(card.value) > parseInt(this.herd[0].value))
        this.herd.push(card);
      return;
    }
    let index = -1;
    if(card.isAcrobat()) {
    // The card is an acrobat
      index = this.herd
        .findIndex(herdCard =>
          herdCard.value === card.value.slice(0, card.value.length-1));
      if(index >= 0)
          index++;
    }else if(card.isGatecrasher()) {
    // The card is a gatecrasher
      let prevCard = null;
      this.herd
        .every((herdCard, i) => {
          if(herdCard.isSpecial())
            prevCard = null;
          else if(prevCard && parseInt(herdCard.value) - parseInt(prevCard.value) > 1) {
            index = i;
            return false;
          }else
            prevCard = herdCard;
          return true;
        })
    }
    if(index >= 0)
      this.herd.splice(index, 0, card)
  }

  /**
   * Returns playable cards from the player's hand
   */
  getPlayableCards() {
    return this.hand.filter(card => {
      // The herd is empty
      if(this.isHerdEmpty()) {
        if(card.isSpecial() && card.value !== '0' && card.value !== '16')
          return false;
        return true;
      }
      // The card is a number
      if(!card.isSpecial() || card.value === '0' || card.value === '16') {
        return parseInt(card.value) < parseInt(this.herd[0].value)
            || parseInt(card.value) > parseInt(this.herd[this.herd.length-1].value);
      }
      // The card is an acrobat
      if(card.isAcrobat()) {
        return this.herd.find(herdCard =>
          !herdCard.isSpecial() && parseInt(card.value) === parseInt(herdCard.value));
      }
      // The card is a gatecrasher
      if(card.isGatecrasher()) {
        let prevCard = null;
        return !this.herd
          .every(herdCard => {
            if(herdCard.isGatecrasher())
              prevCard = null;
            else if(prevCard && parseInt(herdCard.value) - parseInt(prevCard.value) > 1)
              return false;
            else
              prevCard = herdCard;
            return true;
          });
      }
    });
  }

  /**
   * Verify if the provided IDs match the current ID information
   * 
   * @param {number} gameId Verify if the game ID is correct
   * @param {number} [roundId] If specified, verify if the round ID is correct too
   * @param {number} [turnId] If specified, verify if the turn ID is correct too
   * @return {boolean} True if the provided ID(s) match with the current ID information, false otherwise
   */
  verify(gameId, roundId, turnId) {
    return this.gameId === gameId
      && (!roundId || this.roundId === roundId)
      && (!turnId || this.turnId === turnId);
  }

  /**
   * Initialize the start of the game
   * 
   * @param {Player[]} players List of player
   * @param {number} newGameId New game ID
   */
  startGame(players, newGameId) {
    this.players = players;
    this.currentPlayerIndex = 0;
    this.direction = 1;
    this.hand = [];
    this.stackSize = 48;
    this.gameId = newGameId;
    this.roundId = null;
    this.turnId = null;
  }

  /**
   * Initialize a new round
   * 
   * @param {string} direction Defines the way in which the players will play
   * @param {number} gameId Current game ID
   * @param {number} newRoundId New round ID
   */
  newRound(direction, gameId, newRoundId) {
    if(this.verify(gameId)) {
      this.direction = direction === 'DECROI' ? -1 : 1;
      this.roundId = newRoundId;
      this.emit('newRound', this.getPlayer().getScore());
    }
  }

  /**
   * Collects a new set of cards at the begining of a round
   * 
   * @param {Card[]} cards Comma separated list of cards
   * @param {number} gameId Current game ID
   * @param {number} roundId Current round ID
   */
  collectHand(cards, gameId, roundId) {
    if(this.verify(gameId, roundId)) {
      this.hand = cards;
      this.emit('handUpdate', [...this.hand]);
    }
  }

  /**
   * Initialize a new turn
   * 
   * @param {number} firstPlayer Identifier of the first player to play in the turn
   * @param {number} stackSize Amout of cards remaining in the stack
   * @param {number} gameId Current game ID
   * @param {number} roundId Current round ID
   * @param {number} newTurnId New turn ID
   */
  newTurn(firstPlayer, stackSize, gameId, roundId, newTurnId) {
    if(this.verify(gameId, roundId)) {
      if(this.turnId != null)
        this.currentPlayerIndex += this.direction;
      if(this.currentPlayerIndex < 0) this.currentPlayerIndex = this.players.length-1;
      if(this.currentPlayerIndex > this.players.length-1) this.currentPlayerIndex = 0;
      this.turnId = newTurnId;
      this.stackSize = stackSize;
      this.emit('newTurn', this.currentPlayer, this.getPlayableCards());
    }
  }

  /**
   * Play a card from the hand
   * 
   * @param {Card} card Card to play
   * @param {boolean} [invertDir=false] Whether the game direction is inverted if a special card is played
   */
  play(card, invertDir=false) {
    if(!this.isAllowedToPlay()) return;
    if(this.hand.includes(card)) {
      if(!this.herdPickedUp)
        this.pickupHerd(false);
      this.herdPickedUp = false;
      this.gameInterface.play(card, this.gameId, this.roundId, this.turnId);
      if(card.isSpecial())
        this.gameInterface.invertDirection(invertDir, this.gameId, this.roundId, this.turnId);
      this.lastCardPlayed = card;
    }
  }

  /**
   * Pick up the herd or not
   * 
   * @param {boolean} [isPicked=true] Whether the herd is picked up
   */
  pickupHerd(isPicked=true) {
    if(!this.isAllowedToPlay()) return;
    if(isPicked)
      this.herd = [];
    this.herdPickedUp = isPicked;
    this.gameInterface.pickupHerd(isPicked, this.gameId, this.roundId, this.turnId);
  }

  /**
   * Collects a card after playing
   * 
   * @param {?Card} card Drawn card or null is the stack is empty
   * @param {number} gameId Current game ID
   * @param {number} roundId Current round ID
   * @param {number} turnId Current turn ID
   */
  drawCard(card, gameId, roundId, turnId) {
    if(this.verify(gameId, roundId, turnId)) {
      if(this.lastCardPlayed)
        this.hand.splice(this.hand.indexOf(this.lastCardPlayed), 1);
      if(card)
        this.hand.push(card);
      this.emit('handUpdate', [...this.hand]);
    }
  }

  /**
  * Error due to a previously sent message
  * 
  * @param {string} action Action in cause in the error
  * @param {number} gameId Current game ID
  * @param {number} roundId Current round ID
  * @param {number} turnId Current turn ID
  */
 errorManagement(action, gameId, roundId, turnId) {
   if(this.verify(gameId, roundId, turnId)) {
     // TODO - Handle errors
   }
 }

  /**
   * Retrieve opponent player action
   * 
   * @param {boolean} herdPickedUp Whether the herd have been picked up
   * @param {?Card} card The card played by the player
   * @param {boolean} directionChanged Whether the direction have been changed
   * @param {number} gameId Current game ID
   * @param {number} roundId Current round ID
   * @param {number} turnId Current turn ID
   */
  endTurn(herdPickedUp, card, directionChanged, gameId, roundId, turnId) {
    if(this.verify(gameId, roundId, turnId)) {
      if(herdPickedUp)
        this.herd = [];
      if(card) {
        if(directionChanged)
          this.direction *= -1;
        this.addToHerd(card);
      }
    }
  }

  /**
   * Represents the end of a round
   * 
   * @param {number[]} score Scores of the ended round
   * @param {number[]} totalScore Total scores of the game
   * @param {number} gameId Current game ID
   * @param {number} roundId Current round ID
   */
  endRound(score, totalScore, gameId, roundId) {
    if(this.verify(gameId, roundId)) {
      this.players.map((player, i) =>
        player.addScore(score[i]));
      this.turnId = null;
      this.herd = [];
    }
  }

  /**
   * Triggered when a player has disconnected the game
   * 
   * @param {number} gameId Current game ID
   * @param {number} roundId Current round ID
   * @param {number} turnId Current turn ID
   */
  disconnection(gameId, roundId, turnId) {
    if(this.verify(gameId, roundId, turnId)) {
      this.emit('disconnection');
    }
  }

  /**
   * Represents the end of the game
   * 
   * @param {string} winnerName Name of the player who won the game
   * @param {number} gameId Current game ID
   */
  endGame(winnerName, gameId) {
    if(this.verify(gameId)) {
      this.players.sort((a, b) =>
        a.getScore() - b.getScore());
      this.emit('end', winnerName, this.players);
    }
  }

  /**
   * Stops a game, so it won't be restarted
   * 
   * @param {int} gameId Current game ID
   */
  stopGame(gameId) {
    if(this.verify(gameId)) {
      this.destroy();
    }
  }

  /**
   * Restart a new game with the same players
   * 
   * @param {int} newGameId New game ID
   * @param {int} gameId Current game ID
   */
  replayGame(newGameId, gameId) {
    if(this.verify(gameId)) {
      let newPlayers = this.players.map(player => new Player(player.name));
      this.startGame(newPlayers, newGameId);
    }
  }

  /**
   * Destroys a game
   */
  destroy() {
    this.gameInterface.destroy();
  }
}






/**
 * Game interface communicating with a game device
 */
class GameNetworkInterface extends EventEmitter {
  /**
   * @constructor
   * @param {string} ip Ip of the game device
   * @param {number} port Port of the game device
   * @param {string} pseudo Pseudonym of the player joining the game device
   */
  constructor(ip, port, pseudo) {
    super();
    /** @private */ this.connection = bridgeConnection;
    /** @private */ this.id = Math.floor(Math.random() * 9999);
    /** @private */ this.readyState = GameNetworkInterface.CONNECTING;

    if(this.connection.readyState === this.connection.CONNECTING)
      this.connection.on('open', this.connect.bind(this, ip, port, pseudo, this.id));
    else
      this.connect(ip, port, pseudo, this.id);
    this.connection.on('message', this.router.bind(this));
    
    this.on('accept', () => this.readyState = GameNetworkInterface.CONNECTED);
    this.on('reject', () => this.readyState = GameNetworkInterface.CLOSED);
    this.on('close',  () => this.readyState = GameNetworkInterface.CLOSED);

    setTimeout(() => {
      if(this.readyState === GameNetworkInterface.CONNECTING)
        this.triggerError(1);
    }, 6000);
  }

  /**
   * Handles a message reception
   * 
   * @param {MessageEvent} message Message received
   */
  router(message) {
    if(this.readyState === GameNetworkInterface.CONNECTING)
      messageRouter(message)
        .route('AC-<id:int>', info => {
            if(parseInt(info.id) === this.id)
              this.emit('accept');
            else
              this.triggerError(2);
          })
        .route('RC-<id:int>', info => {
            if(parseInt(info.id) === this.id)
              this.emit('reject');
            else
              this.triggerError(2);
          })
    else if(this.readyState === GameNetworkInterface.CONNECTED)
      messageRouter(message)
        .addType('playerList', '(<string>,){2,4}<string>')
        .addType('cardList', '(<card>,){4}<card>')
        .addType('intList', '(<int>,){2,4}<int>')
        .addType('draw', '(<card>|PV)')
        .route('DJ-<game id:int>-<round id:int>-<turn id:int>', this.disconnection.bind(this))
        .route('IP-<players:playerList>-<game id:int>', this.startGame.bind(this))
        .route('IM-<direction:direction>-<game id:int>-<round id:int>', this.newRound.bind(this))
        .route('DM-<hand:cardList>-<game id:int>-<round id:int>', this.collectHand.bind(this))
        .route('IT-<first player:int>-<stack size:int>-<game id:int>-<round id:int>-<turn id:int>', this.newTurn.bind(this))
        .route('FC-<draw:draw>-<game id:int>-<round id:int>-<turn id:int>', this.drawCard.bind(this))
        .route('IE-<action:string>-<game id:int>-<round id:int>-<turn id:int>', this.errorManagement.bind(this))
        .route('IAT-[herd picked up:TR/?][<card:card>][direction changed:/SJI]-<game id:int>-<round id:int>-<turn id:int>', this.endTurn.bind(this))
        .route('FM-<scores:intList>-<total scores:intList>-<game id:int>-<round id:int>', this.endRound.bind(this))
        .route('FP-<winner name:string>-<game id:int>', this.endGame.bind(this))
        .route('TSJ-<game id:int>', this.stopGame.bind(this))
        .route('RNSJ-<game id:int>-<new game id:int>', this.replayGame.bind(this))
        .route('DISCONNECT', this.destroy.bind(this))
  }

  /**
   * Triggers an error event
   * 
   * @param {number} code Error code
   * @param {string} message Message of the error
   */
  triggerError(code) {
    const ERRORS = {
      1: 'No server response',
      2: 'Connection id doesn\'t match'
    }
    this.emit('error', code, ERRORS[code]);
    this.emit('close');
  }

  ///// Received message /////

  /**
   * Init the game start
   * 
   * @param {Object} info Information concerning the game start
   */
  startGame(info) {
    let players = info.players.split(',')
      .map(playerName => new Player(playerName))
    this.emit('start', players,
      parseInt(info.gameId));
  }

  /**
   * Collect cards given by the game device
   * 
   * @param {Object} info Information concerning the card delivery
   */
  collectHand(info) {
    let hand = info.hand.split(',')
      .map(cardStr => {
        let {[0]: type, [1]: value} = cardStr.split('.');
        return new Card(type, value);
      })
    this.emit('collectHand', hand,
      parseInt(info.gameId), parseInt(info.roundId));
  }

  /**
   * New game round
   * 
   * @param {Object} info Information concerning the new round
   */
  newRound(info) {
    this.emit('newRound', info.direction,
      parseInt(info.gameId), parseInt(info.roundId));
  }

  /**
   * New game turn
   * 
   * @param {Object} info Information concerning the new turn
   */
  newTurn(info) {
    this.emit('newTurn', parseInt(info.firstPlayer), parseInt(info.stackSize),
      parseInt(info.gameId), parseInt(info.roundId), parseInt(info.turnId));
  }

  /**
   * Collect the card given by the game device after playing
   * 
   * @param {Object} info Information concerning the drawn card
   */
  drawCard(info) {
    let card = null;
    if(info.draw !== 'PV') {
      let {[0]: type, [1]: value} = info.draw.split('.');
      card = new Card(type, value);
    }
    this.emit('drawCard', card,
      parseInt(info.gameId), parseInt(info.roundId), parseInt(info.turnId));
  }

  /**
   * Catches an error from a prevously sent message
   * 
   * @param {Object} info Information concerning the error
   */
  errorManagement(info) {
    this.emit('internalError', info.action,
      parseInt(info.gameId), parseInt(info.roundId), parseInt(info.turnId));
  }

  /**
   * Informs an opponent player action
   * 
   * @param {Object} info Information concerning the player action
   */
  endTurn(info) {
    let {[0]: type, [1]: value} = info.card.split('.');
    this.emit('endTurn', info.herdPickedUp, new Card(type, value), info.directionChanged,
      parseInt(info.gameId), parseInt(info.roundId), parseInt(info.turnId));
  }

  /**
   * References a round ending
   * 
   * @param {Object} info Information concerning the end of the round
   */
  endRound(info) {
    let scores = info.scores.split(',').map(score => parseInt(score)),
      totalScores = info.totalScores.split(',').map(score => parseInt(score));
    this.emit('endRound', scores, totalScores,
      parseInt(info.gameId), parseInt(info.roundId));
  }

  /**
   * References a player disconnection
   * 
   * @param {Object} info Information concerning the disconnection
   */
  disconnection(info) {
    this.emit('disconnection',
      parseInt(info.gameId), parseInt(info.roundId), parseInt(info.turnId));
  }

  /**
   * End of the game
   * 
   * @param {Object} info Information concerning the end of the game
   */
  endGame(info) {
    this.emit('end', info.winnerName,
      parseInt(info.gameId));
  }

  /**
   * Stops a game
   * 
   * @param {Object} info Information concerning the stop of the game
   */
  stopGame(info) {
    this.emit('stop',
      parseInt(info.gameId));
  }

  /**
   * Restart an ended game
   * 
   * @param {Object} info Information concerning the restarted game
   */
  replayGame(info) {
    this.emit('replay', parseInt(info.newGameId),
      parseInt(info.gameId));
  }

  ///// Sent message /////

  /**
   * Initial connection with the game device
   * 
   * @param {string} ip Ip to connect to
   * @param {number} port Port to connect to
   * @param {string} pseudo Pseudo of the player
   * @param {number} id Id of the connection
   */
  connect(ip, port, pseudo, id) {
    if(this.connection.readyState === this.connection.OPEN)
      this.connection.send(`CP-${ip}-${port}-${pseudo}-JR-${id}`);
  }

  /**
   * Tell the game device whether the herd is picked up
   * 
   * @param {boolean} isPicked Whether the herd is picked up
   * @param {number} gameId Current game ID
   * @param {number} roundId Current round ID
   * @param {number} turnId Current turn ID
   */
  pickupHerd(isPicked, gameId, roundId, turnId) {
    if(this.connection.readyState === this.connection.OPEN)
      this.connection.send(`RT-${isPicked ? 'OUI' : 'NON'}-${gameId}-${roundId}-${turnId}`);
  }

  /**
   * Play a card
   * 
   * @param {Card} card Card to be played
   * @param {number} gameId Current game ID
   * @param {number} roundId Current round ID
   * @param {number} turnId Current turn ID
   */
  play(card, gameId, roundId, turnId) {
    if(card instanceof Card && this.connection.readyState === this.connection.OPEN)
      this.connection.send(`JC-${card.toString()}-${gameId}-${roundId}-${turnId}`);
  }

  /**
   * Tell the game device if the direction of the game is inverted
   * 
   * @param {boolean} invertDir Whether the direction of the game is inverted
   * @param {number} gameId Current game ID
   * @param {number} roundId Current round ID
   * @param {number} turnId Current turn ID
   */
  invertDirection(invertDir, gameId, roundId, turnId) {
    if(this.connection.readyState === this.connection.OPEN)
      this.connection.send(`ISJ-${invertDir ? 'OUI' : 'NON'}-${gameId}-${roundId}-${turnId}`);
  }

  /**
   * Closes properly the connection with the game device
   */
  destroy() {
    this.connection.send('CLOSE');
    this.emit('close');
  }
}
GameNetworkInterface.CONNECTING = 0;
GameNetworkInterface.CONNECTED = 1;
GameNetworkInterface.CLOSED = 3;






/**
 * Player of a game
 */
class Player {
  /**
   * @constructor
   * @param {string} name Name of the player
   */
  constructor(name) {
    /** @private */ this.name = name;
    /** @private */ this.score = 0;
  }

  /**
   * Add a positive score to the player's score
   * 
   * @param {number} score Score to add
   */
  addScore(score) {
    if(typeof score === 'number' && score > 0)
      this.score += score;
  }

  /**
   * Returns player's score
   */
  getScore() {
    return this.score;
  }

  /**
   * Returns the name of the player
   */
  getName() {
    return this.name;
  }
}






/**
 * Card of the game
 */
class Card {
  /**
   * @constructor
   * @param {string} type Type of the card
   * @param {string} value Value of the card
   */
  constructor(type, value) {
    /** @private */ this.type = type;
    /** @private */ this.value = value;
  }

  /**
   * Returns the fly count of the card
   */
  get flyCount() {
    return Card.FLYCOUNT[this.type]
  }

  /**
   * Returns whether the card is special
   */
  isSpecial() {
    return this.type === Card.SPECIAL;
  }

  /**
   * Returns whether the card is special and especialy an accrobat
   */
  isAcrobat() {
    return this.isSpecial() && this.value.endsWith('A');
  }

  /**
   * Returns whether the card is special and especialy a gatecrasher
   */
  isGatecrasher() {
    return this.isSpecial() && this.value.startsWith('R');
  }

  /**
   * Return the card as a string formated like : type.value
   * e.g. V.6 or S.R1
   */
  toString() {
    return this.type+'.'+this.value;
  }
}

Card.GREEN = 'V';
Card.YELLOW = 'J';
Card.ORANGE = 'O';
Card.RED = 'R';
Card.SPECIAL = 'S';

Card.FLYCOUNT = {
  [Card.GREEN]: 0,
  [Card.YELLOW]: 1,
  [Card.ORANGE]: 2,
  [Card.RED]: 3,
  [Card.SPECIAL]: 5
}