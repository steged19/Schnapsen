package at.kaindorf.schnapsen;

import java.util.*;


public class Game {

    private List<Card> deckCards = new ArrayList<>();
    private List<Card> myCards = new ArrayList<>();
    private List<Card> opponentCards = new ArrayList<>();
    private Card trump;

    public Game(){
        ////////////Kartendeck wird erstellt//////////////
        String type="";
        int v;
        for (int i = 0; i<4; i++){
            switch (i){
                case 0: type = "Heart"; break;
                case 1: type = "Bell"; break;
                case 2: type = "Acorn"; break;
                case 3: type = "Leave"; break;
            }
            for (int j = 0; j < 5; j++) {
                v = j+1;
                deckCards.add(new Card(v,type));
            }
        }
        //////Karten werden gemischt und an beide Spieler ausgeteilt///////

        Collections.shuffle(deckCards);
        handOut(myCards, 3);        //Spieler bekommt Karten
        handOut(opponentCards,3);   //Gegner bekommt Karten
        //Trumpf wird bestimmt
        trump = handTrump();
        System.out.println(trump);
        handOut(myCards, 2);
        handOut(opponentCards, 2);
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
        Iterator itr = cards.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    public static void main(String[] args) {
        Game game = new Game();

    }

}