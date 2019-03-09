package core.gameScript.cardGame;

import core.gameScript.cardGame.classicalCard.OneFlieClassical;
import core.gameScript.cardGame.classicalCard.ThreeFlieClassical;
import core.gameScript.cardGame.classicalCard.TwoFlieClassical;
import core.gameScript.cardGame.classicalCard.ZeroFlieClassical;
import core.gameScript.cardGame.specialCard.Acrobat;
import core.gameScript.cardGame.specialCard.Border;
import core.gameScript.cardGame.specialCard.NoValue;

public class CardFactory {

    public CardFactory(){}

    public Card createCard(String type, int value){
        switch (type){
            case "ZeroFlieClassical" : {
                return new ZeroFlieClassical(value);
            }
            case "OneFlieClassical" : {
                return new OneFlieClassical(value);
            }
            case "TwoFlieClassical" : {
                return new TwoFlieClassical(value);
            }
            case "ThreeFlieClassical" : {
                return new ThreeFlieClassical(value);
            }
            case "Acrobat" : {
                return new Acrobat(value);
            }
            case "Border" : {
                return new Border(value);
            }
            case "NoValue" : {
                return new NoValue(value);
            }
            default : {
                return null;
            }
        }
    }
}
