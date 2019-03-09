import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Slider from '@material-ui/lab/Slider';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
import {VolumeOff, VolumeUp} from '@material-ui/icons';
import IconButton from '@material-ui/core/IconButton';
import {ArrowBack} from '@material-ui/icons';
import {Link} from "react-router-dom";
import {getThemes} from './themes';
import {BackgroundTheme} from './themes';

const styles = {
  page: {
    display:'flex',
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'center',
    Width: '100%',
    height: '100%',
    backgroundPosition: '50% 50%',
    backgroundSize:'cover',
    overflow: 'auto'
  },
  title: {
    fontSize: 50,
    marginTop: 15
  },
  content: {
    background: 'white',
    width: '50%',
    minWidth: '320px',
    padding: '24px',
    marginBottom: 24
  },
  h2: {
    marginTop: 0
  },
  volumeContent: {
    overflow: 'hidden'
  },
  volumeContainer: {
    marginTop: 16,
    minWidth: '300px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between'
  },
  volumeSlider: {
    flexGrow: '1',
    width: 250
  },
  volumeIcon: {
    cursor: 'pointer',
    marginRight: '16px'
  },
  header: {
    display: 'flex',
    width: '100%',
    height: 42,
    alignItems: 'center',
    position: 'absolute'
  },
  back: {
    color: '#eee'
  },
  themes: {
    display: 'flex',
    flexWrap: 'wrap',
    justifyContent: 'center'
  },
  themeBorder: {
    margin: '8px',
    border: '2px solid gray',
    borderRadius: '4px',
    cursor: 'pointer'
  },
  themeActive: {
    border: '2px solid #00abd1!important'
  },
  themeBlock: {
    border: '10px solid white',
    borderRadius: '4px',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    minWidth: '120px',
    userSelect: 'none',
    width: '120px',
    overflow: 'hidden'
  },
  themeTitle: {
    textAlign: 'center',
    marginTop: 0
  },
  themeImg: {
    minHeight: '120px',
    height: '120px',
    margin: '0 8px',
    pointerEvents: 'none'
  }
};

class Settings extends Component {
  constructor(props) {
    super(props);
    this.state = {
      theme: props.settings.theme,
      locale: props.settings.locale,
      effectVolume: props.settings.effectVolume,
      vibrations: props.settings.vibrations
    };
  }

  handleSettingChange = setting => (event, value) => {
    const {settings} = this.props;

    if(typeof value !== 'boolean')
    value = setting === 'locale' ? event.target.value : value || event.target.value;

    settings[setting] = value;
    this.setState({[setting]: value});
  }

  handleMute = () => {
    const {settings} = this.props;
    const {effectVolume} = this.state;

    settings.effectVolume = Math.max(0, -effectVolume);
    this.setState({effectVolume: -effectVolume});
  }

  handleSetTheme = theme => () => {
    const {settings} = this.props;

    settings.theme = theme;
    this.setState({theme});
  }

  render() {
    const {classes, settings} = this.props;
    const {locale, effectVolume, theme, vibrations} = this.state;
    const {lang, availableLocales, areVibrationsAvailable} = settings;
    const themes = getThemes(settings.locale);

    return (
      <BackgroundTheme settings={settings} className={classes.page}>
        <div className={classes.header}>
          <IconButton className={classes.back} aria-label="Menu" component={Link} to='/'>
            <ArrowBack />
          </IconButton>
        </div>
        <h1 className={classes.title}>{lang.settings.title}</h1>
        <div className={classes.content}>
          <h2 className={classes.h2}>{lang.settings.locale}</h2>
          <FormControl className={classes.formControl}>
            <Select
              value={locale}
              onChange={this.handleSettingChange('locale')}
              inputProps={{
                name: lang.settings.local,
                id: 'local',
              }}
            >
              {availableLocales.map((locale) => (
                <MenuItem key={locale.code} value={locale.code}>{locale.name}</MenuItem>
              ))}
            </Select>
          </FormControl>
          <h2>{lang.settings.effectVolume}</h2>
          <FormControl className={classes.formControl}>
            <div className={classes.volumeContainer}>
            {effectVolume <= 0 ? <VolumeOff className={classes.volumeIcon} onClick={this.handleMute}/> : <VolumeUp className={classes.volumeIcon} onClick={this.handleMute}/>}
              <div className={classes.volumeSlider}>
                <Slider
                  min={0}
                  max={1}
                  step={.01}
                  onChange={this.handleSettingChange('effectVolume')}
                  value={effectVolume} />
              </div>
            </div>
          </FormControl>
          <h2>{lang.settings.vibrations}</h2>
          <FormControl>
            <FormControlLabel
              disabled={!areVibrationsAvailable}
              control={
                <Switch
                  checked={areVibrationsAvailable && !!vibrations}
                  onChange={this.handleSettingChange('vibrations')} />}
              label={areVibrationsAvailable ? (vibrations ? lang.settings.vibrationsOn : lang.settings.vibrationsOff) : lang.settings.vibrationsUnavailable} />
          </FormControl>
          <h2>{lang.settings.theme}</h2>
          <div className={classes.themes}>
            {themes.map((t, i) =>{
              return (
                <div key={i} className={`${classes.themeBorder} ${theme === t.key ? classes.themeActive : ''}`} onClick={this.handleSetTheme(t.key)}>
                  <div className={classes.themeBlock}>
                    <h3 className={classes.themeTitle}>{t.title}</h3>
                    <img className={classes.themeImg} src={t.src}/>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </BackgroundTheme>
    );
  }
}

Settings.propTypes = {
  classes: PropTypes.object.isRequired,
  settings: PropTypes.object.isRequired
};

export default withStyles(styles)(Settings);