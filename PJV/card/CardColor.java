package card;

public enum CardColor {
    V ("V",0),
    J ("J",1),
    O ("O",2),
    R ("R",3),
    S ("S",5);

    private String color="";
    private int flyCount;

    CardColor(String color, int flyCount) {
        this.color=color;
        this.flyCount = flyCount;
    }

    public int getFlyCount() {
        return flyCount;
    }

    public String getColor() {
        return color;
    }
}
