import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import IconButton from '@material-ui/core/IconButton';
import {KeyboardArrowLeft as LeftIcon, KeyboardArrowRight as RightIcon} from '@material-ui/icons';
import Card from './components/cards/Card';
import {ArrowBack} from '@material-ui/icons';
import {Link} from "react-router-dom";
import {BackgroundTheme} from './themes';
import PreparationImg1 from './assets/img1.png';
import {format} from 'util';

const styles = {
  page: {
    Width: '100%',
    height: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'flex-start' ,
    backgroundPosition: '50% 50%',
    backgroundSize:'cover',
    overflow: 'auto'
  },
  rules: {
    fontSize: 50,
    marginTop: 15,
    textAlign: 'center'
  },
  container: {
    width: '50%',
    minWidth: '320px',
    paddingBottom: '24px'
  },
  title: {
    fontSize: '40px',
    textAlign: 'center'
  },
  block: {
    marginTop: '24px',
    h2:{
      marginButtom:'16px',
      fontSize: '40px'
    }
  },
  text: {
    fontWeight: 450,
    textAlign: 'center',
    fontSize: '20px',
    margin: '4px 0'
  },
  space: {
    paddingTop: 10
  },
  footer: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    background: 'white',
    borderBottomLeftRadius: 4,
    borderBottomRightRadius: 4,
    padding: 24
  },
  left: {
    justifySelf: 'flex-start'
  },
  right: {
    justifySelf: 'flex-end'
  },
  cards: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginRight: -20,
    marginTop: 24
  },
  component: {
    background: 'white',
    borderTopLeftRadius: 4,
    borderTopRightRadius: 4,
    padding: 24
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
  grow: {
    flexGrow: 1
  },
  cardsInfoContainer: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between'
  },
  red: {
    color: 'red'
  },
  cardsInfo: {
    fontSize: 20,
    fontWeight: 450,
    textAlign: 'center',
    flexGrow: 1,
    maxWidth: 200
  },
  cardsSolo: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    marginTop: 24,
    marginRight: -20
  },
  img: {
    display: 'block',
    maxHeight: 284,
    maxWidth: '100%',
    margin: '8px auto'
  }
};

function One({classes, settings: {lang}}) {
  const cards = [{type: 'V'}, {type: 'J'}, {type: 'O'}, {type: 'R'}, {type: 'S'}];
  
  return (
    <div className={classes.component}>
      <h2 className={classes.title}>{lang.rules.title1}</h2>
      <div className={classes.text}>{lang.rules.block1.a}</div>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block1.b}</div>
      <div className={classes.text}>{lang.rules.block1.c}</div>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block1.d}</div>
      <div className={classes.cards}>
        {cards.map(c =>
          <Card card={c} value={c.value} type={c.type}/>)}
      </div>
    </div>
  );
}

function Two({classes, settings: {lang}}) {
  return (
    <div className={classes.component}>
      <h2 className={classes.title}>{lang.rules.title2}</h2>
      <div className={classes.text}>{lang.rules.block2.a}</div>
      <img className={classes.img} src={PreparationImg1}/>
      <div className={classes.text}>{lang.rules.block2.b}</div>
    </div>
  );
}

function Three({classes, settings: {lang}}) {
  const cards = [
    {type: 'V', from: 1, to: 15},
    {type: 'J', from: 2, to: 14},
    {type: 'O', from: 3, to: 13},
    {type: 'R', from: 7, to: 9}
  ];

  return (
    <div className={classes.component}>
      <h2 className={classes.title}>{lang.rules.title3}</h2>
      <div className={classes.text}>{lang.rules.block3.a}</div>
      <div className={classes.cards}>
        {cards.map(c =>
          <Card card={c} value={c.value} type={c.type}/>)}
      </div>
      <div className={classes.cardsInfoContainer}>
        {cards.map(c =>
      <div className={classes.cardsInfo}>{format(lang.misc.fromTo, c.from, c.to)}</div>)}
      </div>
      <div className={classes.text}>{lang.rules.block3.b}</div>
      <div className={classes.cardsSolo}>
        <Card card={{type: 'S'}} type={'S'}/>
      </div>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block3.c}</div>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block3.d}</div>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block3.e}</div>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block3.f}</div>
      <div className={`${classes.text} ${classes.red}  ${classes.space}`}>{lang.rules.block3.g}</div>
    </div>
  );
}

function Four({classes, settings: {lang}}) {
  return (
    <div className={classes.component}>
      <h2 className={classes.title}>{lang.rules.title4}</h2>
      <div className={classes.text}>{lang.rules.block4.a}</div>
      <img className={classes.img} src={PreparationImg1}/>
      <div className={classes.text}>{lang.rules.block4.b}</div>
      <div className={classes.text}>{lang.rules.block4.c}</div>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block4.d}</div>
    </div>
  );
}

function Five({classes, settings: {lang}}) {
  return (
    <div className={classes.component}>
      <h2 className={classes.title}>{lang.rules.title5}</h2>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block5.a}</div>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block5.b}</div>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block5.c}</div>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block5.d}</div>
      <div className={`${classes.text} ${classes.space}`}>{lang.rules.block4.e}</div>
    </div>
  );
}

const components = [withStyles(styles)(One), withStyles(styles)(Two), withStyles(styles)(Three), withStyles(styles)(Four), withStyles(styles)(Five)];


class Rules extends Component {
  constructor(props) {
    super(props);

    this.state = {
      index: 0
    };
  }

  handleRight = () => {
    const {index} = this.state;

    this.setState({index: Math.min(4, index + 1)});
  }

  handleLeft = () => {
    const {index} = this.state;

    this.setState({index: Math.max(index - 1, 0)});
  }

  render() {
    const {index} = this.state;
    const {classes, settings} = this.props;
    const {lang} = settings;
    const Component = components[index];

    return (
      <BackgroundTheme settings={settings} className={classes.page}>
        <div className={classes.header}>
          <IconButton className={classes.back} aria-label="Menu" component={Link} to='/'>
            <ArrowBack />
          </IconButton>
        </div>
        <div className={classes.container}>
          <h1 className={classes.rules}>{lang.rules.title}</h1>
          <Component settings={settings}/>
          <div className={classes.footer}>
            {index > 0 ? <IconButton color="inherit" aria-label="LeftRight" className={classes.left} onClick={this.handleLeft}>
              <LeftIcon />
            </IconButton> : <div className={classes.grow}></div>}
            {index < components.length - 1 && <IconButton color="inherit" aria-label="Menu" className={classes.right} onClick={this.handleRight}>
              <RightIcon />
            </IconButton>}
          </div>
        </div>
      </BackgroundTheme>
    );
  }
}

Rules.propTypes = {
  classes: PropTypes.object.isRequired,
  settings: PropTypes.object.isRequired
};

export default withStyles(styles)(Rules);