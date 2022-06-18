package at.kaindorf.schnapsen;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
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

        assignImages(myCards.get(0), card1);
        card1.setTag(myCards.get(0));
        assignImages(myCards.get(1), card2);
        card1.setTag(myCards.get(1));
        assignImages(myCards.get(2), card3);
        card1.setTag(myCards.get(2));
        assignImages(myCards.get(3), card4);
        card1.setTag(myCards.get(3));
        assignImages(myCards.get(4), card5);
        card1.setTag(myCards.get(4));



        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardAnimation(card1, 0);
                    }
                });
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardAnimation(card2, -320);
                    }
                });
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardAnimation(card3, -630);
                    }
                });
            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardAnimation(card4, -938);
                    }
                });
            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardAnimation(card5, -1247);
                    }
                });
            }
        });

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
                    case 1: imageView.setImageResource(R.drawable.herzbube); break;
                    case 2: imageView.setImageResource(R.drawable.herzdame); break;
                    case 3: imageView.setImageResource(R.drawable.herzkoenig); break;
                    case 4: imageView.setImageResource(R.drawable.herzzehner); break;
                    case 5: imageView.setImageResource(R.drawable.herzass); break;
                }
                break;
            case "Acorn" :
                switch (card.getValue()){
                    case 1: imageView.setImageResource(R.drawable.kreuzbube); break;
                    case 2: imageView.setImageResource(R.drawable.kreuzdame); break;
                    case 3: imageView.setImageResource(R.drawable.kreuzkoenig); break;
                    case 4: imageView.setImageResource(R.drawable.kreuzzehner); break;
                    case 5: imageView.setImageResource(R.drawable.kreuzass); break;
                }
                break;
            case "Leave" :
                switch (card.getValue()){
                    case 1: imageView.setImageResource(R.drawable.picbube); break;
                    case 2: imageView.setImageResource(R.drawable.picdame); break;
                    case 3: imageView.setImageResource(R.drawable.pickoenig); break;
                    case 4: imageView.setImageResource(R.drawable.piczehner); break;
                    case 5: imageView.setImageResource(R.drawable.picass); break;
                }
                break;
            case "Bell" :
                switch (card.getValue()){
                    case 1: imageView.setImageResource(R.drawable.karobube); break;
                    case 2: imageView.setImageResource(R.drawable.karodame); break;
                    case 3: imageView.setImageResource(R.drawable.karokoenig); break;
                    case 4: imageView.setImageResource(R.drawable.karozehner); break;
                    case 5: imageView.setImageResource(R.drawable.karoass); break;
                }
                break;
            default: break;
        }

    }

    public void cardAnimation(View view, int number){
        ObjectAnimator yAnimation = ObjectAnimator.ofFloat(view,"translationY",-517f);
        ObjectAnimator xAnimation = ObjectAnimator.ofFloat(view,"translationX",1070f + number);

        yAnimation.setDuration(1500);
        xAnimation.setDuration(1500);

        Set<Animator> xySet = new HashSet<>();
        xySet.add(yAnimation);
        xySet.add(xAnimation);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(xySet);
        animatorSet.start();
    }

}













