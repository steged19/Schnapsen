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
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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

    private List<Card> deckCards = new ArrayList<>();
    private List<Card> myCards = new ArrayList<>();
    private List<Card> opponentCards = new ArrayList<>();
    private Card trump;

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


        assignImages(myCards.get(0), card1);
        card1.setTag(myCards.get(0));
        assignImages(myCards.get(1), card2);
        card2.setTag(myCards.get(1));
        assignImages(myCards.get(2), card3);
        card3.setTag(myCards.get(2));
        assignImages(myCards.get(3), card4);
        card4.setTag(myCards.get(3));
        assignImages(myCards.get(4), card5);
        card5.setTag(myCards.get(4));

        ////////////////////////AUSTEIL ANIMATION///////////////////////////

        handOutAnimation(card1, -2100, 600, 0);
        handOutAnimation(card2, -1775, 600, 200);
        handOutAnimation(card3, -1450, 600, 400);

        //WARTE EINE SEKUNDE

        handOutAnimation(opCard1,-2100, -500, 1000);
        handOutAnimation(opCard2,-1775, -500, 1200);
        handOutAnimation(opCard3,-1450, -500, 1400);

        //WARTE EINE SEKUNDE

        handOutAnimation(trumpCard, -200, 30, 2000);

        trumpCard.animate().rotation(90f).setDuration(1000).setStartDelay(2000).start();

        //WARTE EINE SEKUNDE

        handOutAnimation(card4, -1125, 600, 3500);
        handOutAnimation(card5, -800, 600, 3700);

        //WARTE EINE SEKUNDE

        handOutAnimation(opCard4,-1125, -500, 4500);
        handOutAnimation(opCard5,-800, -500, 4700);

        ////////////////////////////////////////////////////////////////////

        card1.setOnClickListener(view -> cardClicked(card1));

        card2.setOnClickListener(view -> cardClicked(card2));

        card3.setOnClickListener(view -> cardClicked(card3));

        card4.setOnClickListener(view -> cardClicked(card4));

        card5.setOnClickListener(view -> cardClicked(card5));

    }

    public void cardClicked(ImageView card){

        card1.setClickable(false);
        card2.setClickable(false);
        card3.setClickable(false);
        card4.setClickable(false);
        card5.setClickable(false);


        Card cardValue = (Card) card.getTag();
        System.out.println(cardValue);
        handOutAnimation(card, -1000, 60, 0);

        if(cardValue.getValue() < 10){
            handOutAnimation(card, 100, 300, 2500);
        }

        if(cardValue.getValue() > 10){
            handOutAnimation(card, 800, -500, 2500);
        }


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
                    case 4: imageView.setImageResource(R.drawable.herzkoenig); break;
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
}













