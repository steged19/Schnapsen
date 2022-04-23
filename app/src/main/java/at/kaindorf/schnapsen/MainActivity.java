package at.kaindorf.schnapsen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ImageButton;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ImageView ivTitle;
    private boolean soundCheck = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //////////////----Hide Notification Bar----///////////////
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ///////////////////////////////////////////////
        setContentView(R.layout.activity_main);


        //////////////----BACKGROUND----///////////////
        RelativeLayout relativeLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        ///////////////////////////////////////////////

        ivTitle = (ImageView) findViewById(R.id.imageView);   //       <---- ImageView for the Logo "Schnapsen"

        /////////////Animation bei Start//////////////
        ivTitle.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottomup));



/*        ObjectAnimator yAnimation = ObjectAnimator.ofFloat(imageView,"y",230f);
        ObjectAnimator xAnimation = ObjectAnimator.ofFloat(imageView,"x",70f);
        yAnimation.setDuration(1500);
        xAnimation.setDuration(1500);
        Set<Animator> hashSet = new HashSet<>();
        hashSet.add(yAnimation);
        hashSet.add(xAnimation);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(hashSet);
        animatorSet.start();*/
        /////////////////////////////////////////////



        ///////////-----Move to Left-----////////////////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivTitle.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoomout));
                ObjectAnimator yAnimation = ObjectAnimator.ofFloat(ivTitle,"translationY",-600f);
                ObjectAnimator xAnimation = ObjectAnimator.ofFloat(ivTitle,"translationX",-1000f);
                yAnimation.setDuration(1500);
                xAnimation.setDuration(1500);
                Set<Animator> xySet = new HashSet<>();
                xySet.add(yAnimation);
                xySet.add(xAnimation);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(xySet);
                animatorSet.start();

            }
        }, 4000);
        /////////////////////////////////////////////////////////////////



        ////////////-----Animatioon für TextView Items ( Klassisch, Blitzschnapsen, ... ) werden eingeblendet.


        ImageButton sound = findViewById(R.id.sound);

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound.setActivated(soundCheck);

                soundCheck = !soundCheck;

                /*if(soundCheck == true){
                    AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                }
                else{
                    AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                }*/

            }
        });
    }
}