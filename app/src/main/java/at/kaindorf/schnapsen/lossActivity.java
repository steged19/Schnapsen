package at.kaindorf.schnapsen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class lossActivity extends AppCompatActivity {

    private ImageView lossIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final MediaPlayer gameOverPlayer = MediaPlayer.create(this, R.raw.gameover);

        lossIV = findViewById(R.id.lossIV);

        gameOverPlayer.start();

        lossIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity();
            }
        });
    }

    public void openNewActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}