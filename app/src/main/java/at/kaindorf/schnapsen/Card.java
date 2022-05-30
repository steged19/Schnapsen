package at.kaindorf.schnapsen;

import java.util.Objects;

public class Card {

    //The class card consists of: the value of the card, the name and a boolean parameter if it is the "Trumpf".

    private int value;
    private String type;

    public Card(int value, String type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && Objects.equals(type, card.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", type='" + type + '\'' +
                '}';
    }
}