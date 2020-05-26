package com.example.wartimmunotherapypredict;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable loadAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView potImageView = (ImageView)findViewById(R.id.potImageView);
        loadAnim = (AnimationDrawable)potImageView.getDrawable();
        loadApp();
    }

    public void loadApp()
    {
        loadAnim.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(".SecondActivity");
                startActivity(intent);
            }
        }, 3000);
    }

}
