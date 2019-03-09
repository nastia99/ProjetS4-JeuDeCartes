import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {computeStyle} from './consts';

const wrapperStyle = {
  position: 'absolute',
  width: '100%',
  height: '100%',
  display:'flex',
  flexDirection: 'column',
  maxWidth: '200px',
  maxHeight: '300px',
  background: 'transparent',
  zIndex: 2,
  transition: 'transform 500ms',
  cursor: 'pointer'
}

const styles = theme => ({
  container: {
    display:'flex',
    flexDirection: 'column',
    maxWidth: '200px',
    maxHeight: '300px',
    width: 'calc(100%/5)',
    paddingBottom: 'calc((100% / 5) + (100% / 15))',
    marginLeft: '-20px',
    position: 'relative',
    zIndex: 1
  },
  wrapper: {
    ...wrapperStyle,
    '&:hover': {
      transform: 'translate(0px, -50px)'
    }
  },
  wrapperWithoutHover: {
    ...wrapperStyle
  },
  wrapper2: {
    position: 'absolute',
    width: '100%',
    height: '100%',
    display:'flex',
    flexDirection: 'column',
    maxWidth: '200px',
    maxHeight: '300px',
    zIndex: 1
  },
  card: {
    border: '1px solid black',
    borderRadius: '10%',
    background: 'rgba(0,0,0,.2)',
    cursor: 'pointer',
    transition: 'opacity 100ms'
  },
  first: {
    transform: 'rotate(-5deg)  translate(0px, 10px)'
  },
  last: {
    transform: 'rotate(5deg) translate(0px, 10px)'
  },
  firstHover: {
    '&:hover': {
      transform: 'rotate(-5deg)  translate(0px, -50px)'
    }
  },
  lastHover: {
    '&:hover': {
      transform: 'rotate(5deg) translate(0px, -50px)'
    }
  },
  disabled: {
    opacity: .25,
    cursor: 'default !important'
  },
  ...computeStyle()
});


class Card extends PureComponent {

  handleSelect = () => {
    if (this.props.onSelect) {
      this.props.onSelect(this.props.card)
    }
  }

  render() {
    const {classes, value, type, isFirst, isLast, hover, disabled} = this.props;

    return (
      <div className={`${classes.container} ${isFirst ? classes.first : isLast ? classes.last : ''}`}>
        <div onClick={!disabled ? this.handleSelect : () => null} className={`${!disabled && hover ? classes.wrapper : classes.wrapperWithoutHover} ${!disabled && isFirst ? classes.firstHover : !disabled && isLast ? classes.lastHover : ''} ${disabled ? classes.disabled : ''} ${classes[type]} ${classes.card}`}>
          <div className={`${classes.wrapper2} ${classes[value]}`}>
          </div>
        </div>
      </div>
    );
  }
}

Card.propTypes = {
  classes: PropTypes.object.isRequired,
  value: PropTypes.string.isRequired,
  card: PropTypes.object.isRequired,
  onSelect: PropTypes.func,
  type: PropTypes.string.isRequired,
  fly: PropTypes.number,
  isFirst: PropTypes.bool,
  isLast: PropTypes.bool,
  hover: PropTypes.bool,
  disabled: PropTypes.bool
};

export default withStyles(styles)(Card);