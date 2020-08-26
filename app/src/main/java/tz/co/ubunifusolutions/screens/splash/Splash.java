package tz.co.ubunifusolutions.screens.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.MainActivity;
import tz.co.ubunifusolutions.screens.activities.new_Reading;
import tz.co.ubunifusolutions.screens.activities.read_Search;

public class Splash extends AppCompatActivity {

    Animation topAnimation,bottomAnimation;
    ImageView  image;
    TextView logo,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo = findViewById(R.id.logo);
        slogan = findViewById(R.id.slogan);
        image = findViewById(R.id.imageView3);
        image.setAnimation(topAnimation);
        logo.setAnimation(bottomAnimation);
        slogan.setAnimation(bottomAnimation);

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent = new Intent(Splash.this, MainActivity.class);
               //  intent.putExtra("item", adapter.getItem(position));
               // intent.putExtra("from", "new_Read");
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
               startActivity(intent);
               finish();
           }
       },2000);


    }
}