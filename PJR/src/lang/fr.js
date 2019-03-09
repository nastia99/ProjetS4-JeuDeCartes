export default {
  langName: 'Français',
  menu: {
    play: 'Jouer',
    settings: 'Paramètres',
    rules: 'Règles'
  },
  gamesPanel: {
    name: 'Nom',
    players: 'Joueurs réels',
    bots: 'Bots',
    state: 'Etat',
    address: 'Adresse',
    title: 'Rejoindre une partie',
    username: 'en tant que %s',
    filter: {
      title: 'Filtres',
      publicGamesOnly: 'Parties publiques',
      realPlayersOnly: 'Joueurs réels seulement'
    },
    serverList: {
      name: 'Nom',
      realPlayers: 'Joueurs réels',
      virtualPlayers: 'Bots',
      status: 'Etat',
      address: 'Adresse'
    },
    unfoundGame: 'Vous ne trouvez pas votre partie ? Entrer ses coordonnées manuellement',
    ipAddress: 'Adresse ip',
    port: 'Port',
    connection: 'Connexion',
    alreadyConnected: 'Vous êtes connecté en tant que %s',
    inputUsername: 'Veuillez entrer votre pseudo',
    useSameUsername: 'Ou modifiez votre pseudo ci-dessous',
    invalidSyntax: 'Votre pseudo ne doit pas contenir %s',
    joinServer: 'Voulez-vous rejoindre %s ?',
    waitingStart: 'Connection à la partie...',
    joinReject: 'Connexion à la partie rejetée',
    joinError: 'Impossible de rejoindre la partie'
  },
  game: {
    settings: {
      changeLang: 'Changer la langue',
      soundEffects: 'Effets sonores',
      vibrations: 'Vibrations',
      backToGame: 'Retour au jeu'
    },
    waiting: 'En attente du début de la partie...',
    sort: {
      button: 'Trier',
      title: 'Trier les cartes',
      flies: 'par nombre de mouches',
      number: 'par valeur'
    },
    pickupHerd: 'Ramasser le troupeau',
    pickupHerdDone: 'Vous avez ramassé le troupeau !',
    herdEmpty: 'Le troupeau est vide',
    playCard: 'Souhaitez vous jouer cette carte ?',
    invertGameDirection: 'Souhaitez vous changer le sens du jeu ?',
    disconnection: 'Un joueur a été déconnecté',
    gameEnded: 'La partie est terminée',
    ranking: 'Vous êtes arrivé en position #%s avec un score de %s mouches.',
    leaveOrStay: 'Vous pouvez quitter ou attendre que la partie soit relancée',
    closed: 'La partie a été fermée inopinément',
    gameEnded: 'La partie est terminée'
  },
  settings: {
    title: 'Paramètres',
    effectVolume: 'Volume des effets sonores',
    locale: 'Langue',
    vibrations: 'Vibrations',
    vibrationsOn: 'Vibrations activées',
    vibrationsOff: 'Vibrations désactivées',
    vibrationsUnavailable: 'Vibrations indisponibles',
    theme: 'Thème'
  },
  rules: {
    title: 'Règles du jeu',
    title1: 'But du jeu :',
    block1: {
      a: 'Avoir LE MOINS DE MOUCHES dans son étable quand la partie se termine.',
      b: 'Elle se termine quand un joueur obtient au moins 100 mouches au total.',
      c: 'Le score est calculé en fin de chaque manche. Une partie se joue en plusieurs manches.',
      d: 'Chaque carte a un nombre de mouches :'
    },
    title2: 'Préparation :',
    block2: {
      a: 'Chaque fermier reçoit au départ dans sa main 5 cartes vaches, le restant constituant la PIOCHE.',
      b: 'Le jeu commence dans le sens des aiguilles d’une montre mais jouer une carte spéciale permet au joueur s’il le souhaite de changer de sens.'
    },
    title3: 'Les cartes :',
    block3: {
      a: 'Cartes normales',
      b: 'Cartes spéciales',
      c: '0 = la plus petite valeur',
      d: '16 = la plus grande valeur',
      e: '7 et 9 acrobates = à poser sur des cartes de même valeur',
      f: 'Resquilleuse = à placer entre 2 cartes n’ayant pas de numéro consécutifs',
      g: '/!\\ : En revanche, on ne peut pas poser de vaches acrobates par-dessus en prétextant qu’il s’agit du numéro 7 ou 9. Même dans le cas où on intercale clairement la carte entre un 6 et 8, elle ne peut prendre que la valeur 7 mais on ne peut pas jouer de vaches acrobates par-dessus.'
    },
    title4: 'Déroulement du jeu :',
    block4: {
      a: 'Chaque fermier doit poser une carte. Le numéro de sa vache doit être supérieur à la plus grande vache présente dans le troupeau ou inferieur à la plus petite vache du troupeau.',
      b: 'Si un joueur ne peut pas jouer : il ramasse le troupeau et les mouches vont dans son étable.',
      c: '/!\\ Un joueur peut ramasser s’il le souhaite même s’il peut jouer.',
      d: 'Quand un joueur ramasse, c’est à lui de jouer la carte suivante.'
    },
    title5: 'Fin de la partie :',
    block5: {
      a: 'Manche',
      b: 'Chaque partie est découpée en plusieurs manches. Une manche se termine quand il n’y a plus de pioche : quand il n’y a plus de pioche, le tour en cours se termine et lorsqu’un joueur ramasse, c’est la fin de la manche, les cartes restantes dans les mains des joueurs vont automatiquement dans leur étable respective. Chaque mouche est comptée et constitue le score de ma manche.',
      c: 'Partie',
      d: 'Dès qu’un fermier ATTEINT OU DEPASSE 100 mouches au total, la partie prend fin.',
      e: 'Le fermier avec le plus petit nombre de mouches remporte la partie !'
    }
  },
  misc: {
    backToMenu: 'Retour menu',
    cancel: 'Annuler',
    validate: 'Valider',
    play: 'Jouer',
    yes: 'Oui',
    no: 'Non',
    back: 'Retour',
    fromTo: 'De %s à %s',
    quit: 'Quitter'
  }
};