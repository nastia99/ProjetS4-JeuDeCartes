import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import {withStyles} from "@material-ui/core";
import Divider from '@material-ui/core/Divider';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';

const styles = {
  root: {
    position: 'fixed',
    bottom: 0,
    right: 0,
    left: 0
  },
  wrapper: {
    textAlign: 'center',
    color: ' #333',
    background: '#f0f0f0',
    userSelect: 'none'
  },
  header: {
    display: 'flex',
    justifyContent: 'flex-end',
    alignItems: 'center',
    width: '100%',
    fontSize: '16px',
    fontStyle: 'italic',
    textTransform: 'none',
    padding: '8px 16px',
    borderRadius: 0,
    cursor: 'pointer',
    '&:hover': {
      background: '#e0e0e0'
    }
  },
  title: {
    margin: 'auto'
  },
  content: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    flexWrap: 'wrap',
    maxHeight: '100px',
    padding: '12px 0',
    overflow: 'hidden',
    transition: 'all 250ms',
    '&> *': {
        margin: '0 15px'
    }
  },
  closed: {
    maxHeight: 0,
    padding: 0
  },
  chevron: {
    transition: 'all 250ms'
  },
  chevronTop: {
    transform: 'rotateZ(90deg)'
  },
  chevronBottom: {
    transform: 'rotateZ(-90deg)'
  }
};

class ManualJoin extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      manualIp: null,
      manualPort: null,
      open: false
    };
  }

  handleManualJoin = (event) => {
    const {manager, gamesPanel} = this.props;
    const {manualIp, manualPort} = this.state;

    if(event.keyCode && event.keyCode !== 13) return
    if(manualIp && manualPort) {
      gamesPanel.setState({selectedServer: manager.getAnonymousServer(manualIp, manualPort)});
      setTimeout(gamesPanel.handleJoinServer, 0);
    }
  }

  handleManualIpChange = (event) => {
    this.setState({manualIp: event.target.value || null});
  }

  handleManualPortChange = (event) => {
    let value = event.target.value;
    if(!value || (value > 0 && value <= 65535)) {
        this.setState({manualPort: value});
    }
  }

  toggleOpen = () => {
    this.setState({open: !this.state.open});
  }

  render() {
    const {classes, settings: {lang}} = this.props;
    const {open, manualIp, manualPort} = this.state;

    return (
      <div className={classes.root}>
        <Divider />
        <div className={classes.wrapper}>
          <Button className={classes.header} onClick={this.toggleOpen}>
            <span className={classes.title}>{lang.gamesPanel.unfoundGame}</span>
            <ChevronLeftIcon className={`${classes.chevron} ${open ? classes.chevronTop : classes.chevronBottom}`} />
          </Button>
          <div className={`${classes.content} ${!open ? classes.closed : ''}`}>
            <TextField
              value={manualIp || ''}
              label={lang.gamesPanel.ipAddress}
              placeholder="192.168.1.15"
              onKeyDown={this.handleManualJoin}
              onChange={this.handleManualIpChange}/>
            <TextField
              type="number"
              value={manualPort || ''}
              label={lang.gamesPanel.port}
              placeholder="5678"
              inputProps={{min: 0, max: 65535}}
              onKeyDown={this.handleManualJoin}
              onChange={this.handleManualPortChange}/>
            <Button variant="contained" onClick={this.handleManualJoin}>
              {lang.gamesPanel.connection}
            </Button>
          </div>
        </div>
      </div>
    )
  }
}

ManualJoin.propTypes = {
  classes: PropTypes.object.isRequired,
  gamesPanel: PropTypes.object.isRequired,
  manager: PropTypes.object.isRequired,
  settings: PropTypes.object.isRequired,
  open: PropTypes.bool
};

export default withStyles(styles)(ManualJoin);