package at.kaindorf.schnapsen.bl;

import at.kaindorf.schnapsen.Card;
import at.kaindorf.schnapsen.bl.Deck;

public class Spieler {

    private Deck deck = new Deck();


    public void startingMove(Card playCard){
        //Ausgewählte Karte wird in die Mitte gelegt
    }

    public void replyMove(Card playCard){
        //Karte in der mitte angreifen
        //Färben muss beachtet werden
    }

    public void switchTrump(Card playCard){
        Card helper = deck.getTrump();
        deck.setTrump(playCard);
        playCard = helper;
    }

    public void drawCard(){
        deck.handOut(deck.getMyCards(),1);
    }

    public static void main(String[] args) {
        }



}
