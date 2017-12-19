package com.mattspissell.digdug;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.media.MediaPlayer;

public class MainActivity extends Activity {

    MediaPlayer bkgdmsc;

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        //show everything!
        gameView = new GameView(this, point);
        setContentView(gameView);

        //music!
        bkgdmsc = MediaPlayer.create(MainActivity.this,R.raw.digdugmusic);
        bkgdmsc.setLooping(true);
        bkgdmsc.start();

    }


    @Override
    protected void onPause() {
        super.onPause();
        bkgdmsc.release();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
