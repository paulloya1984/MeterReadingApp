package tz.co.ubunifusolutions.screens.about;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.LoginActivity;
import tz.co.ubunifusolutions.screens.activities.Nyumbani;

public class about_Bawasa extends AppCompatActivity {
Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__bawasa);

//        btnBack = (Button) findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // Intent intent = new Intent(about_Bawasa.this,Nyumbani.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//               // startActivity(intent);
//               // about_Bawasa.super.onBackPressed();
//                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.bawasa.or.tz/index.php/about-us"));
//            }
//        });
    }
}
