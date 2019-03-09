import React, { Component } from 'react';
import Menu from './Menu';
import {withStyles} from '@material-ui/core/styles';
import { BrowserRouter as Router, Route } from "react-router-dom";
import GamesPanel from './GamesPanel';
import Settings from './Settings';
import Rules from './Rules';
import Game from './Game';
import ApiService, {GameService} from './lib/lib';
import ApiSettings from './lib/Settings';

const styles = {
  root: {
    width: '100%',
    height: '100%',
    position: 'absolute',
    top: 0,
    bottom: 0,
    left: 0,
    right: 0

  }
};

class App extends Component {
  constructor(props) {
    super(props);
    const name = localStorage.getItem('name');
    const nameAlreadySetStringified = sessionStorage.getItem('nameAlreadySet');
    const nameAlreadySet = nameAlreadySetStringified && JSON.parse(nameAlreadySetStringified);
    const gameService = new GameService();

    this.state = {
      name: name || null,
      nameAlreadySet,
      gameService,
      apiService: new ApiService(gameService),
      apiSettings: new ApiSettings()
    };
  }

  handleNameChange = (newName) => {
    newName = newName.replace(/-/g, '');
    localStorage.setItem('name', newName);
    sessionStorage.setItem('nameAlreadySet', true);
    this.setState({name: newName, nameAlreadySet: true});
  }

  render() {
    const {classes} = this.props;
    const {name, apiService, apiSettings, nameAlreadySet, gameService} = this.state;

    return (
      <Router>
        <div className={classes.root}>
          <Route path="/" exact component={() =>
              <Menu
                settings={apiSettings}/>
            } />
          <Route path="/join" exact component={() =>
              <GamesPanel
                name={name}
                openNameModal={!nameAlreadySet}
                onNameChange={this.handleNameChange}
                manager={apiService}
                settings={apiSettings}/>
            }/>
          <Route path="/settings" component={() =>
              <Settings
                settings={apiSettings}/>
            }/>
          <Route path="/rules" component={() =>
              <Rules
                settings={apiSettings}/>
            } />
          <Route path="/game" component={() =>
              <Game
                manager={gameService}
                settings={apiSettings}/>
            } />
        </div>
      </Router>
    );
  }
}

export default withStyles(styles)(App);
