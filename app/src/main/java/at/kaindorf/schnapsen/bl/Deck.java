package at.kaindorf.schnapsen.bl;

import android.widget.ImageView;

import java.util.*;

import at.kaindorf.schnapsen.Card;
import at.kaindorf.schnapsen.R;

public class Deck {

    private List<Card> deckCards = new ArrayList<>();
    private List<Card> myCards = new ArrayList<>();
    private List<Card> opponentCards = new ArrayList<>();
    private Card trump;

    public List<Card> getDeckCards() {
        return deckCards;
    }

    public void setDeckCards(List<Card> deckCards) {
        this.deckCards = deckCards;
    }

    public List<Card> getMyCards() {
        return myCards;
    }

    public void setMyCards(List<Card> myCards) {
        this.myCards = myCards;
    }

    public List<Card> getOpponentCards() {
        return opponentCards;
    }

    public void setOpponentCards(List<Card> opponentCards) {
        this.opponentCards = opponentCards;
    }

    public Card getTrump() {
        return trump;
    }

    public void setTrump(Card trump) {
        this.trump = trump;
    }


    public void handOut(List<Card> cards, int number){
        for (int i = 0; i < number; i++) {
            cards.add(deckCards.get(0));
            deckCards.remove(0);
        }
    }

    public Card handTrump(){
        Card trump = deckCards.get(0);
        deckCards.remove(0);
        return trump;
    }

    public void printCards(List<Card> cards){
        System.out.println(cards.size());
        for (Card card : cards) {
            System.out.println(card);
        }
    }

}