import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Card from './Card';

const styles = theme => ({
  container: {
    display:'flex',
    width: '100%',
    height: '100%',
    maxHeight: '300px',
    paddingBottom: '24px',
    justifyContent: 'center'
  }
});


class Cards extends PureComponent {
  constructor(props){
    super(props);
  }

  render() {
    const {classes, cards, playableCards, onSelect} = this.props;

    return (
      <div className={classes.container}>
        {cards && cards.map((card, index) => {
          return <Card
            key={`${card.type}${card.value}${index}`}
            hover={true}
            disabled={!playableCards || !playableCards.includes(card)}
            onSelect={onSelect}
            card={card}
            value={card.value}
            isFirst={index === 0}
            isLast={index === (cards.length - 1)}
            type={card.type}/>
        })}
      </div>
    );
   }
}

Cards.propTypes = {
  classes: PropTypes.object.isRequired,
  cards: PropTypes.array,
  playableCards: PropTypes.array,
  onSelect: PropTypes.func.isRequired
};

export default withStyles(styles)(Cards);