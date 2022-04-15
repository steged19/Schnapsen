package at.kaindorf.schnapsen;

public class Card {

    //The class card consists of: the value of the card, the name and a boolean parameter if it is the "Trumpf".

    private int value;
    private String name;
    private boolean isTrumpf;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTrumpf() {
        return isTrumpf;
    }

    public void setTrumpf(boolean trumpf) {
        isTrumpf = trumpf;
    }
}