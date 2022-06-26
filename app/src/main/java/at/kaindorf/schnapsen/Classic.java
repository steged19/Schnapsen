package at.kaindorf.schnapsen;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Classic extends AppCompatActivity {

    private final ImageView[] ivMyCards = new ImageView[5];
    private final ImageView[] ivOpCards = new ImageView[5];

    private ImageView trumpCard;

    private ImageView myCardStack;
    private ImageView opCardStack;

    private final List<Card> deckCards = new ArrayList<>();
    private final List<Card> myCards = new ArrayList<>();
    private final List<Card> opponentCards = new ArrayList<>();
    private Card trump;
    private Card helperCard;
    private ImageView ivHelperCard;

    private final Random random = new Random();

    private TextView myPoints;
    private TextView opPoints;

    private int myPointsCNT = 0;
    private int opPointsCNT = 0;
    private int verschiebungMyCard;
    private int verschiebungOppCard;
    private boolean deckNotEmpty = true;

    private ImageView stackCard1;
    private ImageView stackCard2;
    private ImageView stackCard3;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic);

        ///Deck wird erstellt
        //Deck deck = new Deck();
        String type = "";
        int v;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    type = "Heart";
                    break;
                case 1:
                    type = "Bell";
                    break;
                case 2:
                    type = "Acorn";
                    break;
                case 3:
                    type = "Leave";
                    break;
            }
            for (int j = 0; j < 5; j++) {
                v = j + 1;
                switch (j) {
                    case 0:
                        v = 2;
                        break;
                    case 1:
                        v = 3;
                        break;
                    case 2:
                        v = 4;
                        break;
                    case 3:
                        v = 10;
                        break;
                    case 4:
                        v = 11;
                        break;
                }

                deckCards.add(new Card(v, type));
            }
        }
        //////Karten werden gemischt und an beide Spieler ausgeteilt///////

        Collections.shuffle(deckCards);
        handOut(myCards, 5);        //Spieler bekommt Karten
        handOut(opponentCards, 5);   //Gegner bekommt Karten
        trump = handTrump();               //Trumpf wird bestimmt
        System.out.println(trump);

        //Alles wird zugewiesen
        ivMyCards[0] = findViewById(R.id.myCard1);
        ivMyCards[1] = findViewById(R.id.myCard2);
        ivMyCards[2] = findViewById(R.id.myCard3);
        ivMyCards[3] = findViewById(R.id.myCard4);
        ivMyCards[4] = findViewById(R.id.myCard5);

        stackCard1 = findViewById(R.id.stackCard1);
        stackCard2 = findViewById(R.id.stackCard2);
        stackCard3 = findViewById(R.id.stackCard3);

        stackCard1.setZ(12f);
        stackCard2.setZ(12f);
        stackCard3.setZ(12f);

        trumpCard = findViewById(R.id.trumpCard);

        ivOpCards[0] = findViewById(R.id.oponnentCard1);
        ivOpCards[1] = findViewById(R.id.oponnentCard2);
        ivOpCards[2] = findViewById(R.id.oponnentCard3);
        ivOpCards[3] = findViewById(R.id.oponnentCard4);
        ivOpCards[4] = findViewById(R.id.oponnentCard5);

        myCardStack = findViewById(R.id.myCardStack);
        myCardStack.setClickable(false);
        opCardStack = findViewById(R.id.opCardStack);
        opCardStack.setClickable(false);

        myPoints = findViewById(R.id.myPoints);
        opPoints = findViewById(R.id.opPoints);
        ///////
        //Karte Bild zuweisen;

        for (int i = 0; i < 5; i++) {
            assignImages(myCards.get(i), ivMyCards[i]);
            ivMyCards[i].setTag(myCards.get(i));
            ivMyCards[i].setZ(11f);

            ivOpCards[i].setTag(opponentCards.get(i));
        }
        assignImages(trump, trumpCard);
        trumpCard.setTag(trump);

        opCardStack.setZ(10f);
        myCardStack.setZ(10f);

        //Gegnerdeck ausgeben
        System.out.println("Gegnerkarten:");
        for (Card card : opponentCards) {
            System.out.println(card);
        }

        ////////////////////////AUSTEIL ANIMATION///////////////////////////

        handOutAnimation(ivMyCards[0], -2320, 560, 0);
        handOutAnimation(ivMyCards[1], -1995, 560, 200);
        handOutAnimation(ivMyCards[2], -1670, 560, 400);

        //WARTE EINE SEKUNDE

        handOutAnimation(ivOpCards[0], -2320, -530, 1000);
        handOutAnimation(ivOpCards[1], -1995, -530, 1200);
        handOutAnimation(ivOpCards[2], -1670, -530, 1400);


        //WARTE EINE SEKUNDE

        handOutAnimation(trumpCard, -200, 30, 2000);

        trumpCard.animate().rotation(90f).setDuration(1000).setStartDelay(2000).start();

        //WARTE EINE SEKUNDE

        handOutAnimation(ivMyCards[3], -1345, 560, 3500);
        handOutAnimation(ivMyCards[4], -1020, 560, 3700);

        //WARTE EINE SEKUNDE

        handOutAnimation(ivOpCards[3], -1345, -530, 4500);
        handOutAnimation(ivOpCards[4], -1020, -530, 4700);

        ////////////////////////////////////////////////////////////////////


        for (int i = 0; i < 5; i++) {
            int finalI = i;
            ivMyCards[i].setOnClickListener(view -> cardClicked(ivMyCards[finalI], finalI));
        }
        trumpCard.setOnClickListener(view -> trumpSwap());
    }


    private void trumpSwap() {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            if (myCards.get(i).getType() == trump.getType() && myCards.get(i).getValue() == 2){
                assignImages(myCards.get(i), trumpCard);
                assignImages(trump, ivMyCards[i]);
                Card helper = myCards.get(i);
                myCards.set(i, trump);
                trump = helper;
                ivMyCards[i].setTag(myCards.get(i));
                trumpCard.setTag(trump);

                trumpCard.animate().rotation(630).setDuration(1000).setStartDelay(0).start();
                ivMyCards[i].animate().rotation(720).setDuration(1000).setStartDelay(0).start();

            }
        }
    }

    public void cardClicked(ImageView card, int cardNumber) {

        setCardsClickable(false, 0);

        Card cardValue = (Card) card.getTag();
        ImageView oppCard = randOppCard(cardValue.getType());
        Card oppCardValue = (Card) oppCard.getTag();

        int oppCardNumber = (int) oppCard.getZ();

        handOutAnimation(card, -1000, 20, 0);

        if (cardValue.getType() == oppCardValue.getType()) {
            if (cardValue.getValue() < oppCardValue.getValue()) {
                roundLossAnimation(card, oppCard);
                updateOppPoints(cardValue, oppCardValue);
            } else {
                roundWonAnimation(card, oppCard);
                updateMyPoints(cardValue, oppCardValue);
            }
        } else {
            if (oppCardValue.getType() == trump.getType()) {
                roundLossAnimation(card, oppCard);
                updateOppPoints(cardValue, oppCardValue);
            } else {
                roundWonAnimation(card, oppCard);
                updateMyPoints(cardValue, oppCardValue);
            }
        }

        if (checkIfWin() == false) {
            handoutNewCard(card, oppCard, cardNumber, oppCardNumber);
        }

        opponentCards.remove(oppCardValue);
        myCards.remove(cardValue);

        setCardsClickable(true, 6500);
    }


    public void handOut(List<Card> cards, int number) {
        for (int i = 0; i < number; i++) {
            cards.add(deckCards.get(0));
            deckCards.remove(0);
        }
    }

    public Card handTrump() {
        Card trump = deckCards.get(0);
        deckCards.remove(0);
        return trump;
    }

    public void assignImages(Card card, ImageView imageView) {
        switch (card.getType()) {

            case "Heart":
                switch (card.getValue()) {
                    case 2:
                        imageView.setImageResource(R.drawable.herzbube);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.herzdame);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.herzkoenig);
                        break;
                    case 10:
                        imageView.setImageResource(R.drawable.herzzehner);
                        break;
                    case 11:
                        imageView.setImageResource(R.drawable.herzass);
                        break;
                }
                break;
            case "Acorn":
                switch (card.getValue()) {
                    case 2:
                        imageView.setImageResource(R.drawable.kreuzbube);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.kreuzdame);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.kreuzkoenig);
                        break;
                    case 10:
                        imageView.setImageResource(R.drawable.kreuzzehner);
                        break;
                    case 11:
                        imageView.setImageResource(R.drawable.kreuzass);
                        break;
                }
                break;
            case "Leave":
                switch (card.getValue()) {
                    case 2:
                        imageView.setImageResource(R.drawable.picbube);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.picdame);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.pickoenig);
                        break;
                    case 10:
                        imageView.setImageResource(R.drawable.piczehner);
                        break;
                    case 11:
                        imageView.setImageResource(R.drawable.picass);
                        break;
                }
                break;
            case "Bell":
                switch (card.getValue()) {
                    case 2:
                        imageView.setImageResource(R.drawable.karobube);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.karodame);
                        break;
                    case 4:
                        imageView.setImageResource(R.drawable.karokoenig);
                        break;
                    case 10:
                        imageView.setImageResource(R.drawable.karozehner);
                        break;
                    case 11:
                        imageView.setImageResource(R.drawable.karoass);
                        break;
                }
                break;
            default:
                break;
        }
    }

    //FUNKTION FÜR ANIMATION FÜRS AUSTEILEN

    public void handOutAnimation(View view, int numberX, int numberY, int delay) {

        ObjectAnimator xAnimation = ObjectAnimator.ofFloat(view, "translationX", numberX);
        ObjectAnimator yAnimation = ObjectAnimator.ofFloat(view, "translationY", numberY);

        yAnimation.setDuration(1500).setStartDelay(delay);
        xAnimation.setDuration(1500).setStartDelay(delay);

        Set<Animator> xySet = new HashSet<>();
        xySet.add(yAnimation);
        xySet.add(xAnimation);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(xySet);

        animatorSet.start();
    }

    public ImageView randOppCard(String type) {
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
        if (cardValue == (Card) ivOpCards[0].getTag()) {
            ivOpCards[0].postDelayed(new Runnable() {
                @Override
                public void run() {
                    handOutAnimation(ivOpCards[0], -1350, -0, 0);
                    assignImages(helperCard, ivOpCards[0]);
                }
            }, 1500);
            opCard = ivOpCards[0];
            opCard.setZ(0);
        }
        if (cardValue == (Card) ivOpCards[1].getTag()) {
            ivOpCards[1].postDelayed(new Runnable() {
                @Override
                public void run() {
                    handOutAnimation(ivOpCards[1], -1350, -0, 0);
                    assignImages(helperCard, ivOpCards[1]);
                }
            }, 1500);
            opCard = ivOpCards[1];
            opCard.setZ(1);
        }
        if (cardValue == (Card) ivOpCards[2].getTag()) {
            ivOpCards[2].postDelayed(new Runnable() {
                @Override
                public void run() {
                    handOutAnimation(ivOpCards[2], -1350, -0, 0);
                    assignImages(helperCard, ivOpCards[2]);
                }
            }, 1500);
            opCard = ivOpCards[2];
            opCard.setZ(2);
        }
        if (cardValue == (Card) ivOpCards[3].getTag()) {
            ivOpCards[3].postDelayed(new Runnable() {
                @Override
                public void run() {
                    handOutAnimation(ivOpCards[3], -1350, -0, 0);
                    assignImages(helperCard, ivOpCards[3]);
                }
            }, 1500);
            opCard = ivOpCards[3];
            opCard.setZ(3);
        }
        if (cardValue == (Card) ivOpCards[4].getTag()) {
            ivOpCards[4].postDelayed(new Runnable() {
                @Override
                public void run() {
                    handOutAnimation(ivOpCards[4], -1350, -0, 0);
                    assignImages(helperCard, ivOpCards[4]);
                }
            }, 1500);
            opCard = ivOpCards[4];
            opCard.setZ(4);
        }
        return opCard;
    }

    public boolean checkIfWin() {
        handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            if (myPointsCNT >= 66) {
                System.out.println("Ich gewinne");
                openWinScreen();
            }
            if (opPointsCNT >= 66) {
                System.out.println("Gegner gewinnt");
                openLossScreen();
            }
        }
    }, 5000);
        return false;
    }

    private void setCardsClickable(boolean clickable, int delay) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    ivMyCards[i].setClickable(clickable);
                }
            }
        }, 0 + delay);
    }

    private void roundLossAnimation(ImageView card, ImageView oppCard) {
        handOutAnimation(card, -450, -500, 3500);
        card.animate().rotation(210f).setDuration(1500).setStartDelay(3500).start();
        handOutAnimation(oppCard, -450, -500, 3500);
        oppCard.animate().rotation(210f).setDuration(1500).setStartDelay(3500).start();

        opCardStack.postDelayed(new Runnable() {
            @Override
            public void run() {
                opCardStack.setVisibility(View.VISIBLE);
                assignImages((Card) card.getTag(), opCardStack);
            }
        }, 5000);
    }

    private void roundWonAnimation(ImageView card, ImageView oppCard) {
        handOutAnimation(card, -400, 500, 3500);
        card.animate().rotation(150f).setDuration(1500).setStartDelay(3500).start();
        handOutAnimation(oppCard, -400, 500, 3500);
        oppCard.animate().rotation(150f).setDuration(1500).setStartDelay(3500).start();

        myCardStack.postDelayed(new Runnable() {
            @Override
            public void run() {
                myCardStack.setVisibility(View.VISIBLE);
                assignImages((Card) card.getTag(), myCardStack);
            }
        }, 5000);
    }


    private void updateOppPoints(Card cardValue, Card oppCardValue) {
        opPointsCNT += cardValue.getValue() + oppCardValue.getValue();
        opPoints.postDelayed(new Runnable() {
            @Override
            public void run() {
                opPoints.setText(opPointsCNT + "");
            }
        }, 3000);
    }

    private void updateMyPoints(Card cardValue, Card oppCardValue) {
        myPointsCNT += cardValue.getValue() + oppCardValue.getValue();
        myPoints.postDelayed(new Runnable() {
            @Override
            public void run() {
                myPoints.setText(myPointsCNT + "");
            }
        }, 3000);
    }

    public void handoutNewCard(ImageView myCard, ImageView opCard, int cardNumber, int oppCardNumber){

        //BACK TO STACK - > UNSICHTBAR
        handOutAnimation(myCard, 0, 0, 5000);
        handOutAnimation(opCard, 0, 0, 5000);

        myCard.postDelayed(new Runnable() {
            @Override
            public void run() {
                myCard.setVisibility(View.INVISIBLE);
            }
        }, 5000);

        opCard.postDelayed(new Runnable() {
            @Override
            public void run() {
                opCard.setVisibility(View.INVISIBLE);
            }
        }, 5000);
        if (deckNotEmpty) backToPosition(myCard, opCard, cardNumber, oppCardNumber);

    }


    public void backToPosition(ImageView myCard, ImageView opCard, int myCardNumber, int oppCardNumber){

         verschiebungMyCard = 0;
         verschiebungOppCard = 0;

        switch(myCardNumber){
            case 0: verschiebungMyCard = -2320; break;
            case 1: verschiebungMyCard = -1995; break;
            case 2: verschiebungMyCard = -1670; break;
            case 3: verschiebungMyCard = -1345; break;
            case 4: verschiebungMyCard = -1020; break;
        }

        switch(oppCardNumber){
            case 0: verschiebungOppCard = -2320; break;
            case 1: verschiebungOppCard = -1995; break;
            case 2: verschiebungOppCard = -1670; break;
            case 3: verschiebungOppCard = -1345; break;
            case 4: verschiebungOppCard = -1020; break;
        }

            myCard.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (deckCards.size() != 0) {
                    System.out.println(deckCards.size());
                    myCard.setVisibility(View.VISIBLE);
                    handOutAnimation(myCard, verschiebungMyCard, 560, 0);
                    myCard.animate().rotation(0).setDuration(1000).setStartDelay(0).start();

                    Card nextCard = deckCards.get(0);
                    handOut(myCards, 1);
                    assignImages(nextCard, ivMyCards[myCardNumber]);
                    ivMyCards[myCardNumber].setTag(nextCard);
                    }
                }
            }, 6000);
            opCard.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (deckCards.size() != 0) {
                        System.out.println(deckCards.size());
                        opCard.setVisibility(View.VISIBLE);
                        opCard.animate().rotation(0).setDuration(1000).setStartDelay(0).start();
                        handOutAnimation(opCard, verschiebungOppCard, -536, 0);
                        Card nextCard = deckCards.get(0);
                        handOut(opponentCards, 1);
                        ivOpCards[oppCardNumber].setImageResource(R.drawable.back);
                        //assignImages(nextCard, ivOpCards[oppCardNumber]);
                        ivOpCards[oppCardNumber].setTag(nextCard);
                    }
                    else{
                        trumpCard.setClickable(false);
                        deckNotEmpty = false;
                        stackCard1.setVisibility(View.INVISIBLE);
                        stackCard2.setVisibility(View.INVISIBLE);
                        stackCard3.setVisibility(View.INVISIBLE);

                        assignImages(trump ,opCard);

                        handOutAnimation(opCard, -223, 30, 0);

                        opCard.animate().rotation(720f).setDuration(2000).setStartDelay(000).start();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                opCard.setVisibility(View.VISIBLE);
                                trumpCard.setVisibility(View.INVISIBLE);
                                handOutAnimation(opCard, verschiebungOppCard, -536, 0);;
                            }
                        }, 1500);
                    }
                }
            }, 6000);
    }

    public void openWinScreen(){
        Intent intent = new Intent(this, winScreen.class);
        startActivity(intent);
    }

    public void openLossScreen(){
        Intent intent = new Intent(this, lossActivity.class);
        startActivity(intent);
    }
}