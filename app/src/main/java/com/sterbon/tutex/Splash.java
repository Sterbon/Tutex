package com.sterbon.tutex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Utsav on 7/30/2017.
 */

public class Splash extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timer = new Thread() {

            public void run() {
                try {
                    sleep(2* 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                } finally {
                    Intent i = new Intent(Splash.this,Main.class);
                    startActivity(i);
                }
            }

        };

        timer.start();
    }
}
