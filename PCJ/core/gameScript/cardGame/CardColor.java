package core.gameScript.cardGame;

public enum CardColor {
    V ("V",0,"green"),
    J ("J",1,"yellow"),
    O ("O",2,"orange"),
    R ("R",3,"red"),
    S ("S",5,"spe");

    private String color="";
    private String cardColor="";
    private int flyCount;

    CardColor(String color, int flyCount,String cardColor) {
        this.color=color;
        this.flyCount = flyCount;
        this.cardColor=cardColor;
    }

    public int getFlyCount() {
        return flyCount;
    }

    public String getColor() {
        return color;
    }

    public String getCardColor() {
        return cardColor;
    }
}
