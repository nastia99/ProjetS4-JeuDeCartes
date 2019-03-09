export default {
  langName: 'English',
  menu: {
    play: 'Play',
    settings: 'Settings',
    rules: 'Rules'
  },
  gamesPanel: {
    name: 'Name',
    players: 'Players',
    bots: 'Bots',
    state: 'State',
    address: 'Address',
    title: 'Join a game',
    username: 'as %s',
    filter: {
      title: 'Filters',
      publicGamesOnly: 'Public games',
      realPlayersOnly: 'Real players only'
    },
    serverList: {
      name: 'Name',
      realPlayers: 'Real players',
      virtualPlayers: 'Bots',
      status: 'Status',
      address: 'Address'
    },
    unfoundGame: 'You don\'t find your game ? Type its coordonates',
    ipAddress: 'Ip address',
    port: 'Port',
    connection: 'Connection',
    alreadyConnected: 'You are connected as %s',
    inputUsername: 'Please specify your username',
    useSameUsername: 'Or update your username below',
    invalidSyntax: 'Your username must not contain %s',
    joinServer: 'Would you like to join %s ?',
    waitingStart: 'Connecting to the game...',
    joinReject: 'Connection to the game rejected',
    joinError: 'Unable to connect to the game'
  },
  game: {
    settings: {
      changeLang: 'Change language',
      soundEffects: 'Sound effects',
      vibrations: 'Vibrations',
      backToGame: 'Back to the game'
    },
    waiting: 'Waiting for the game to begin...',
    sort: {
      button: 'Sort',
      title: 'Sort cards',
      flies: 'By flies',
      number: 'By values'
    },
    pickupHerd: 'Pickup the herd',
    pickupHerdDone: 'You pickup the herd !',
    herdEmpty: 'The herd is empty',
    playCard: 'Would you like to play this card ?',
    invertGameDirection: 'Would you like to invert the game direction ?',
    disconnection: 'A player has been disconnected',
    gameEnded: 'The game is over',
    ranking: 'You have arrived at the position #%s with a score of %s',
    leaveOrStay: 'You can quit or wait for the game to be restarted',
    closed: 'The game have been closed unexpectedly',
    gameEnded: 'The game is over'
  },
  settings: {
    title: 'Settings',
    effectVolume: 'Sound effects volume',
    locale: 'Language',
    vibrations: 'Vibrations',
    vibrationsOn: 'Vibrations enabled',
    vibrationsOff: 'Vibrations disabled',
    vibrationsUnavailable: 'Vibrations unavailable',
    theme: 'Theme'
  },
  rules: {
    title: 'Rules',
    title1: 'The aim :',
    block1: {
      a: 'Have THE LEAST FLIES.',
      b: 'It ends at more than 100 flies in total.',
      c: 'The score is calculated at the end of each round. A game is played in several rounds.',
      d: 'Each card has a number of flies:'
    },
    title2: 'Preparation :',
    block2: {
      a:'Each farmer initially receives in his hand 5 cows cards, the rest constituting the DECK.',
      b:'The game starts clockwise but playing a special card allows the player to change direction if he wishes.'
    },
    title3: 'The cards :',
    block3: {
      a:'Normal cards',
      b: 'Special cards',
      c: '0 = the smallest value',
      d: '16 = the greatest value',
      e: '7 and 9 acrobats = to place on cards of the same value',
      f: 'Gatecrashers = to place between 2 cards having no consecutive number',
      g: '/!\\ : On the other hand, you can not put acrobatic cows on top of it, pretending that it is number 7 or 9. Even if you insert the card clearly between a 6 and 8, it can not take that\'s worth 7 but you can not play acrobatic cows over it.'
    },
    title4: 'Course of the game :',
    block4: {
      a:'Every farmer must play a card. The number of the cow must be greater than the largest cow present in the herd or lower than the smallest cow in the herd.',
      b: 'If a player can not play: he picks up the herd and the flies go to his barn.',
      c: '/!\\ A player can pick it up if he wants, even if he can play.',
      d: 'When a player picks the herd up, it\'s up to him to play the next card.'
    },
    title5: 'End of the game :',
    block5: {
      a:'Round',
      b: 'Each game is divided into several rounds. A round ends when the stack is empty: when there is no draw, the current round ends and when a player picks up the herd, it is the end of the round, the remaining cards in the players\' hand automatically go to their respective barn. Each fly is counted and is the score of the round.',
      c: 'Game',
      d: 'As soon as a farmer REACHES or EXCEED 100 flies in total, the game ends.',
      e: 'The farmer with the smallest number of flies wins the game!'
    }
  },
  misc: {
    backToMenu: 'Back to menu',
    cancel: 'Cancel',
    validate: 'Validate',
    play: 'Play',
    yes: 'Yes',
    no: 'No',
    back: 'Back',
    fromTo: 'From %s to %s',
    quit: 'Quit'
  }
};