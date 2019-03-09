import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Button from '@material-ui/core/Button';
import {Link} from "react-router-dom";
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import {ArrowBack, Settings} from '@material-ui/icons';
import DialogContentText from '@material-ui/core/DialogContentText';
import CircularProgress from '@material-ui/core/CircularProgress';
import GameList from './components/game-list';
import ManualJoin from './components/manual-join';
import { Redirect } from 'react-router-dom';
import {format} from 'util';

const styles = theme => ({
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    background: '#098e2b'
  },
  page: {
    display:'flex',
    flexDirection: 'column',
    width: '100%',
    height: '100%'
  },
  title: {
    display:'flex',
    alignItems:'center',
    justifyContent:'space-between',
    width: '100%'
  },
  waitMessage: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%'
  },
  bold: {
    fontWeight: 'bold'
  }
});


class GamesPanel extends PureComponent {

  constructor(props){
    super(props);
    this.state = {
      pseudo: props.name || null,
      parties: [],
      redirect: false
    };
    this.invalidPseudoChars = '-,';
  }

  componentDidMount() {
    const {manager} = this.props;

    manager.refreshServers('MIXTE', 5);
    manager.refreshServers('JRU', 5);
    manager.refreshServers('JVU', 5);
    manager.on('update', ((servers) => {
      const {name, openNameModal} = this.props;

      if (name && !openNameModal) {
        this.setState({parties: servers});
      }
    }).bind(this));
  }

  isPseudoSyntaxValid = () => {
    const {pseudo} = this.state;

    return !pseudo || !this.invalidPseudoChars.split('')
      .reduce((acc, char) => acc || pseudo.includes(char), false);
  }

  handleNameChange = () => {
    const {pseudo} = this.state;

    this.props.onNameChange(pseudo);
  }

  handlePseudoChange = (event) => {
    this.setState({pseudo: event.target.value || null});
  }

  handleSelectServer = (server) => {
    this.setState({selectedServer: server, state: 'join'});
  }

  handleJoinServer = () => {
    const {manager} = this.props;
    const {selectedServer, pseudo} = this.state;

    const currentGame = manager.join(selectedServer, pseudo);
    if(!currentGame) return;
    this.setState({state: 'wait'});

    currentGame.on('accept', () => this.setState({redirect: true, currentGame}));
    currentGame.on('reject', () => this.setState({state: 'reject'}));
    currentGame.on('error', () => this.setState({state: 'error'}));
  }

  handleCancelGame = () => {
    this.setState({selectedServer: null, state: null});
  }

  render() {
    const {classes, name, openNameModal, manager, settings} = this.props;
    const {redirect, pseudo, parties, selectedServer, state} = this.state;
    const {lang} = settings;

    if (redirect) {
      return <Redirect to='/game' />
    }

    return (
      <div className={classes.page}>
        <AppBar position="fixed" className={classes.appBar}>
          <Toolbar>
            <div className={classes.title}>
              <IconButton className={classes.menuButton} color="inherit" aria-label="Menu" component={Link} to='/'>
                <ArrowBack />
              </IconButton>
              <Typography align="center" variant="h6" color="inherit" className={classes.grow}>
                {lang.gamesPanel.title} {name && format(lang.gamesPanel.username, name)}
              </Typography>
              <IconButton className={classes.menuButton} color="inherit" aria-label="Menu" component={Link} to='/settings'>
                <Settings />
              </IconButton>
            </div>
          </Toolbar>
        </AppBar>

        <GameList
          parties={parties}
          onSelect={this.handleSelectServer}
          settings={settings}/>

        <ManualJoin
          gamesPanel={this}
          manager={manager}
          settings={settings}/>

        <Dialog
          open={!name || openNameModal}
        >
          <DialogTitle id="form-dialog-title">
            {openNameModal && name
              ? format(lang.gamesPanel.alreadyConnected, name)
              : lang.gamesPanel.inputUsername}
          </DialogTitle>
          <DialogContent>
            {
              openNameModal && name ?
                <DialogContentText>
                  {lang.gamesPanel.useSameUsername}
                </DialogContentText> : null
            }
            <TextField
              value={pseudo || ''}
              error={!this.isPseudoSyntaxValid()}
              helperText={!this.isPseudoSyntaxValid() && format(lang.gamesPanel.invalidSyntax, '"'+this.invalidPseudoChars+'"')}
              autoFocus
              margin="dense"
              id="name"
              onChange={this.handlePseudoChange}
              type="text"
              fullWidth/>
          </DialogContent>
          <DialogActions>
            <Button component={Link} to='/' >
              {lang.misc.backToMenu}
            </Button>
            <Button disabled={!pseudo || !this.isPseudoSyntaxValid()} onClick={this.handleNameChange} color="primary">
              {lang.misc.validate}
            </Button>
          </DialogActions>
        </Dialog>
        <Dialog
          open={!!state && !!selectedServer}
        >
          <DialogTitle id="form-dialog-title">
            {state === 'join' && (
              <div>
                <span>{lang.gamesPanel.joinServer.split('%s')[0]}</span>
                <span className={classes.bold}>{selectedServer.name}</span>
                <span>{lang.gamesPanel.joinServer.split('%s').length > 1
                  && lang.gamesPanel.joinServer.split('%s')[1]}</span>
              </div>
            )}
            {state === 'wait'   && lang.gamesPanel.waitingStart}
            {state === 'reject' && lang.gamesPanel.joinReject}
            {state === 'error'  && lang.gamesPanel.joinError}
          </DialogTitle>
          <DialogContent>
            <div className={classes.waitMessage}>
              {state === 'wait' &&
                <CircularProgress className={classes.progress} />}
            </div>
          </DialogContent>
          <DialogActions>
            <Button onClick={this.handleCancelGame}>
            {lang.misc.cancel}
            </Button>
            {state === 'join' &&
              (<Button onClick={this.handleJoinServer} color="primary">
                {lang.misc.validate}
              </Button>)}
          </DialogActions>
        </Dialog>
      </div>
    );
  }
}

GamesPanel.propTypes = {
  classes: PropTypes.object.isRequired,
  onNameChange: PropTypes.func.isRequired,
  name: PropTypes.string,
  openNameModal: PropTypes.bool,
  manager: PropTypes.object.isRequired,
  settings: PropTypes.object.isRequired
};

export default withStyles(styles)(GamesPanel);