package at.kaindorf.schnapsen;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
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

        imageView = (ImageView) findViewById(R.id.imageView);   //       <---- ImageView for the Logo "Schnapsen"

        /////////////Animation bei Start//////////////
        ObjectAnimator yAnimation = ObjectAnimator.ofFloat(imageView,"y",230f);
        ObjectAnimator xAnimation = ObjectAnimator.ofFloat(imageView,"x",70f);
        yAnimation.setDuration(1500);
        xAnimation.setDuration(1500);
        Set<Animator> hashSet = new HashSet<>();
        hashSet.add(yAnimation);
        hashSet.add(xAnimation);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(hashSet);
        animatorSet.start();
        /////////////////////////////////////////////



        ///////////-----Move to the right Animation-----////////////////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator toRight = ObjectAnimator.ofFloat(imageView,"x",2000f);
                toRight.setDuration(700);
                toRight.start();
            }
        }, 1000);
        /////////////////////////////////////////////////////////////////

        ////////////-----Animatioon für TextView Items ( Klassisch, Blitzschnapsen, ... ) werden eingeblendet.

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.INVISIBLE);        // <---- Make "Schnapsen" logo invisible.
                //ANIMATION
            }
        }, 2000);

        ////////////////////////////////////////////////////////////////7


        //////////-----SOUND-----///////////////////

        ImageButton sound = findViewById(R.id.sound);

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sound.setActivated(soundCheck);

                soundCheck = !soundCheck;

                if(soundCheck == true){
                    //SOUND AN
                    AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
                }
                else{
                    //SOUND AUS
                    AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
                    amanager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
                }
            }
        });

        ///////////////////////////////////////////




    }
}