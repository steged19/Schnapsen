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

import java.util.HashSet;
import java.util.Set;

public class Classic extends AppCompatActivity {

    private ImageView card1;
    private ImageView card2;
    private ImageView card3;
    private ImageView card4;
    private ImageView card5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic);

        card1 = findViewById(R.id.myCard1);
        card2 = findViewById(R.id.myCard2);
        card3 = findViewById(R.id.myCard3);
        card4 = findViewById(R.id.myCard4);
        card5 = findViewById(R.id.myCard5);

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