import Green from '../../assets/green.png';
import Orange from '../../assets/orange.png';
import Spe from '../../assets/spe.png';
import Red from '../../assets/red.png';
import Yellow from '../../assets/yellow.png';
import Zero from '../../assets/0.png';
import One from '../../assets/1.png';
import Two from '../../assets/2.png';
import Three from '../../assets/3.png';
import Four from '../../assets/4.png';
import Five from '../../assets/5.png';
import Six from '../../assets/6.png';
import Seven from '../../assets/7.png';
import Eight from '../../assets/8.png';
import Nine from '../../assets/9.png';
import Ten from '../../assets/10.png';
import Eleven from '../../assets/11.png';
import Twelve from '../../assets/12.png';
import Thirteen from '../../assets/13.png';
import Fourteen from '../../assets/14.png';
import Fifteen from '../../assets/15.png';
import Sixteen from '../../assets/16.png';
import Gatecrasher from '../../assets/R.png';

export const types = [
  {type: 'V', background: Green},
  {type: 'J', background: Yellow},
  {type:'O', background: Orange},
  {type: 'R', background: Red},
  {type: 'S', background: Spe}
];
export const values = [
  {type: '0', background: Zero},
  {type: '1', background: One},
  {type: '2', background: Two},
  {type: '3', background: Three},
  {type: '4', background: Four},
  {type: '5', background: Five},
  {type: '6', background: Six},
  {type: '7', background: Seven},
  {type: '8', background: Eight},
  {type: '9', background: Nine},
  {type: '10', background: Ten},
  {type: '11', background: Eleven},
  {type: '12', background: Twelve},
  {type: '13', background: Thirteen},
  {type: '14', background: Fourteen},
  {type: '15', background: Fifteen},
  {type: '16', background: Sixteen},
  {type: '9A', background: Nine},
  {type: '7A', background: Seven},
  {type: 'R1', background: Gatecrasher},
  {type: 'R2', background: Gatecrasher}
];

export function computeStyle() {
  return types.concat(values).reduce((acc, t) => {
    acc[t.type] = {
      backgroundImage: `url('${t.background}')`,
      backgroundSize: '100% 100%'
    }
    return acc;
  }, {})
}

