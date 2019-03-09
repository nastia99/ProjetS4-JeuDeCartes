import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import { Link } from "react-router-dom";
import {BackgroundTheme} from './themes';

const styles = {
  page: {
    display:'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    Width: '100%',
    height: '100%',
    backgroundPosition: '50% 50%',
    backgroundSize:'cover'
  },
  menu: {
    display:'flex',
    flexDirection: 'column'
  },
  button: {
    margin: '4px 0px',
    background: 'white',
    fontWeight: 500
  },
  title: {
    fontSize: '60px'
  }
};


class Menu extends Component {
  render() {
    const {classes, settings} = this.props;
    const {lang} = settings;

    return (
      <BackgroundTheme settings={settings} className={classes.page}>
        <h1 className={classes.title}>MOW</h1>
        <div className={classes.menu}>
          <Button variant="contained" size="large" className={classes.button} component={Link} to='/join'>{lang.menu.play}</Button>
          <Button variant="contained" size="large" className={classes.button} component={Link} to='/settings'>{lang.menu.settings}</Button>
          <Button variant="contained" size="large" className={classes.button} component={Link} to='/rules'>{lang.menu.rules}</Button>
        </div>
      </BackgroundTheme>
    );
  }
}

Menu.propTypes = {
  classes: PropTypes.object.isRequired,
  settings: PropTypes.object.isRequired
};

export default withStyles(styles)(Menu);