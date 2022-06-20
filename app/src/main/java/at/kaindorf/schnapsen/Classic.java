package at.kaindorf.schnapsen;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Classic extends AppCompatActivity {

    private ImageView card1;
    private ImageView card2;
    private ImageView card3;
    private ImageView card4;
    private ImageView card5;

    private ImageView trumpCard;

    private ImageView opCard1;
    private ImageView opCard2;
    private ImageView opCard3;
    private ImageView opCard4;
    private ImageView opCard5;

    private ImageView overlapCard1;
    private ImageView overlapCard2;
    private ImageView overlapCard3;
    private ImageView overlapCard4;
    private ImageView overlapCard5;

    private ImageView myCardStack;
    private ImageView opCardStack;

    private List<Card> deckCards = new ArrayList<>();
    private List<Card> myCards = new ArrayList<>();
    private List<Card> opponentCards = new ArrayList<>();
    private Card trump;
    private Card helperCard;

    private Random random = new Random();

    private TextView myPoints;
    private TextView opPoints;

    private int myPointsCNT = 0;
    private int opPointsCNT = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic);

        ///Deck wird erstellt
        //Deck deck = new Deck();
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
                switch (j){
                case 0: v=2; break;
                case 1: v=3; break;
                case 2: v=4; break;
                case 3: v=10; break;
                case 4: v=11; break;
                }

                deckCards.add(new Card(v,type));
            }
        }
        //////Karten werden gemischt und an beide Spieler ausgeteilt///////

        Collections.shuffle(deckCards);
        handOut(myCards, 5);        //Spieler bekommt Karten
        handOut(opponentCards,5);   //Gegner bekommt Karten
        trump = handTrump();               //Trumpf wird bestimmt
        System.out.println(trump);

        card1 = findViewById(R.id.myCard1);
        card2 = findViewById(R.id.myCard2);
        card3 = findViewById(R.id.myCard3);
        card4 = findViewById(R.id.myCard4);
        card5 = findViewById(R.id.myCard5);

        trumpCard = findViewById(R.id.trumpCard);

        opCard1 = findViewById(R.id.oponnentCard1);
        opCard2 = findViewById(R.id.oponnentCard2);
        opCard3 = findViewById(R.id.oponnentCard3);
        opCard4 = findViewById(R.id.oponnentCard4);
        opCard5 = findViewById(R.id.oponnentCard5);

        myCardStack = findViewById(R.id.myCardStack);
        myCardStack.setClickable(false);
        opCardStack = findViewById(R.id.opCardStack);
        opCardStack.setClickable(false);

        myPoints = findViewById(R.id.myPoints);
        opPoints = findViewById(R.id.opPoints);

        //Karte Bild zuweisen
        assignImages(myCards.get(0), card1);
        assignImages(myCards.get(1), card2);
        assignImages(myCards.get(2), card3);
        assignImages(myCards.get(3), card4);
        assignImages(myCards.get(4), card5);

        assignImages(trump, trumpCard);

        //Tag setten
        card1.setTag(myCards.get(0));
        card2.setTag(myCards.get(1));
        card3.setTag(myCards.get(2));
        card4.setTag(myCards.get(3));
        card5.setTag(myCards.get(4));

        card1.setZ(9);
        card2.setZ(9);
        card3.setZ(9);
        card4.setZ(9);
        card5.setZ(9);

        trumpCard.setTag(trump);

        opCard1.setTag(opponentCards.get(0));
        opCard2.setTag(opponentCards.get(1));
        opCard3.setTag(opponentCards.get(2));
        opCard4.setTag(opponentCards.get(3));
        opCard5.setTag(opponentCards.get(4));

        //Gegnerdeck ausgeben
        System.out.println("Gegnerkarten:");
        for (Card card: opponentCards){
            System.out.println(card);
        }
        ////////////////////////AUSTEIL ANIMATION///////////////////////////

        handOutAnimation(card1, -2320, 560, 0);
        handOutAnimation(card2, -1995, 560, 200);
        handOutAnimation(card3, -1670, 560, 400);

        //WARTE EINE SEKUNDE

        handOutAnimation(opCard1,-2320, -530, 1000);
        handOutAnimation(overlapCard1, -2320, -530, 1000);
        handOutAnimation(opCard2,-1995, -530, 1200);
        handOutAnimation(overlapCard2,-1995, -530, 1200);
        handOutAnimation(opCard3,-1670, -530, 1400);
        handOutAnimation(overlapCard3,-1670, -530, 1400);


        //WARTE EINE SEKUNDE

        handOutAnimation(trumpCard, -200, 30, 2000);

        trumpCard.animate().rotation(90f).setDuration(1000).setStartDelay(2000).start();

        //WARTE EINE SEKUNDE

        handOutAnimation(card4, -1345, 560, 3500);
        handOutAnimation(card5, -1020, 560, 3700);

        //WARTE EINE SEKUNDE

        handOutAnimation(opCard4,-1345, -530, 4500);
        handOutAnimation(overlapCard4,-1345, -530, 4500);
        handOutAnimation(opCard5,-1020, -530, 4700);
        handOutAnimation(overlapCard5,-1020, -530, 4700);

        ////////////////////////////////////////////////////////////////////

        card1.setOnClickListener(view -> cardClicked(card1));

        card2.setOnClickListener(view -> cardClicked(card2));

        card3.setOnClickListener(view -> cardClicked(card3));

        card4.setOnClickListener(view -> cardClicked(card4));

        card5.setOnClickListener(view -> cardClicked(card5));

    }

    public void cardClicked(ImageView card){

//        card1.setClickable(false);
//        card2.setClickable(false);
//        card3.setClickable(false);
//        card4.setClickable(false);
//        card5.setClickable(false);

        Card cardValue = (Card) card.getTag();
        System.out.println(cardValue);
        handOutAnimation(card, -1000, 20, 0);

        ImageView oppCard =  randOppCard(cardValue.getType());
        Card oppCardValue = (Card) oppCard.getTag();

        //WENN KARTE KLEINER ALS OPONENT CARD
                if(cardValue.getValue() < oppCardValue.getValue() && cardValue.getType() == oppCardValue.getType()){
                    handOutAnimation(card, -450, -500, 3500);
                    card.animate().rotation(210f).setDuration(1500).setStartDelay(3500).start();
                    handOutAnimation(oppCard, -450, -500, 3500);
                    oppCard.animate().rotation(210f).setDuration(1500).setStartDelay(3500).start();

                    opCardStack.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            opCardStack.setVisibility(View.VISIBLE);
                            assignImages((Card) card.getTag(), opCardStack);
                            opCardStack.setZ(100);
                        }
                    }, 5000);

                    opPointsCNT += cardValue.getValue() + oppCardValue.getValue();
                    opPoints.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            opPoints.setText(opPointsCNT +"");
                        }
                    }, 3000);
            }
            //WENN KARTE GRÖßER ALS OPONENT CARD
            else{
                    handOutAnimation(card, -400, 500, 3500);
                    card.animate().rotation(150f).setDuration(1500).setStartDelay(3500).start();
                    handOutAnimation(oppCard, -400, 500, 3500);
                    oppCard.animate().rotation(150f).setDuration(1500).setStartDelay(3500).start();

                    myCardStack.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myCardStack.setVisibility(View.VISIBLE);
                            assignImages((Card) card.getTag(), myCardStack);
                            opCardStack.setZ(100);
                        }
                    }, 5000);

                    myPointsCNT += cardValue.getValue() + oppCardValue.getValue();
                    myPoints.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myPoints.setText(myPointsCNT +"");
                        }
                    }, 3000);
            }

            if(checkIfWin() == false ){
                handoutNewCard();
            }
            else{
                checkIfWin();
            }

            opponentCards.remove(oppCardValue);
            myCards.remove(cardValue);
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

    public void assignImages(Card card, ImageView imageView){
        switch (card.getType()){

            case "Heart" :
                switch (card.getValue()){
                    case 2: imageView.setImageResource(R.drawable.herzbube); break;
                    case 3: imageView.setImageResource(R.drawable.herzdame); break;
                    case 4: imageView.setImageResource(R.drawable.herzbube); break;
                    case 10: imageView.setImageResource(R.drawable.herzzehner); break;
                    case 11: imageView.setImageResource(R.drawable.herzass); break;
                }
                break;
            case "Acorn" :
                switch (card.getValue()){
                    case 2: imageView.setImageResource(R.drawable.kreuzbube); break;
                    case 3: imageView.setImageResource(R.drawable.kreuzdame); break;
                    case 4: imageView.setImageResource(R.drawable.kreuzkoenig); break;
                    case 10: imageView.setImageResource(R.drawable.kreuzzehner); break;
                    case 11: imageView.setImageResource(R.drawable.kreuzass); break;
                }
                break;
            case "Leave" :
                switch (card.getValue()){
                    case 2: imageView.setImageResource(R.drawable.picbube); break;
                    case 3: imageView.setImageResource(R.drawable.picdame); break;
                    case 4: imageView.setImageResource(R.drawable.pickoenig); break;
                    case 10: imageView.setImageResource(R.drawable.piczehner); break;
                    case 11: imageView.setImageResource(R.drawable.picass); break;
                }
                break;
            case "Bell" :
                switch (card.getValue()){
                    case 2: imageView.setImageResource(R.drawable.karobube); break;
                    case 3: imageView.setImageResource(R.drawable.karodame); break;
                    case 4: imageView.setImageResource(R.drawable.karokoenig); break;
                    case 10: imageView.setImageResource(R.drawable.karozehner); break;
                    case 11: imageView.setImageResource(R.drawable.karoass); break;
                }
                break;
            default: break;
        }
    }

    //FUNKTION FÜR ANIMATION FÜRS AUSTEILEN

    public void handOutAnimation(View view, int numberX, int numberY, int delay){

        ObjectAnimator xAnimation = ObjectAnimator.ofFloat(view,"translationX", numberX);
        ObjectAnimator yAnimation = ObjectAnimator.ofFloat(view,"translationY", numberY);

        yAnimation.setDuration(1500).setStartDelay(delay);
        xAnimation.setDuration(1500).setStartDelay(delay);

        Set<Animator> xySet = new HashSet<>();
        xySet.add(yAnimation);
        xySet.add(xAnimation);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(xySet);

        animatorSet.start();
    }

    public ImageView randOppCard(String type){


                List<Card> opponentCardsSameType = new ArrayList<>();
                for (Card card:opponentCards) {
                    if (card.getType() == type){
                        opponentCardsSameType.add(card);
                    }
                }
                Card cardValue = null;
                if (opponentCardsSameType.size() != 0){
                    int cardNumber = random.nextInt(opponentCardsSameType.size());
                    cardValue = opponentCardsSameType.get(cardNumber);
                }
                else {
                    int cardNumber = random.nextInt(opponentCards.size());
                    cardValue =  opponentCards.get(cardNumber);
                }
                ImageView opCard = null;
                helperCard = cardValue;
                if (cardValue == (Card) opCard1.getTag()) {
                    opCard1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handOutAnimation(opCard1, -1350, -0, 0);
                            assignImages(helperCard, opCard1);
                        }
                    }, 1500);
                    opCard = opCard1;
                }
                if (cardValue == (Card) opCard2.getTag()) {
                    opCard2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handOutAnimation(opCard2, -1350, -0, 0);
                            assignImages(helperCard, opCard2);
                        }
                    }, 1500);
                    opCard = opCard2;
                }
                if (cardValue == (Card) opCard3.getTag()) {
                    opCard3.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handOutAnimation(opCard3, -1350, -0, 0);
                            assignImages(helperCard, opCard3);
                        }
                    }, 1500);
                    opCard = opCard3;
                }
                if (cardValue == (Card) opCard4.getTag()) {
                    opCard4.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handOutAnimation(opCard4, -1350, -0, 0);
                            assignImages(helperCard, opCard4);
                        }
                    }, 1500);
                    opCard = opCard4;
                }
                if (cardValue == (Card) opCard5.getTag()) {
                    opCard5.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handOutAnimation(opCard5, -1350, -0, 0);
                            assignImages(helperCard, opCard5);
                        }
                    }, 1500);
                    opCard = opCard5;
                }
                return opCard;
    }

    public boolean checkIfWin(){
        if(myPointsCNT >= 66){
            System.out.println("Ich gewinne");
            return true;
        }
        if(opPointsCNT >= 66){
            System.out.println("Gegner gewinnt");
            return true;
        }
        return false;
    }

    public void handoutNewCard(){

    }

}