package tz.co.ubunifusolutions.screens.activities.wateja;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.notification.myDialog_currentReading;

public class customer_Details extends AppCompatActivity {

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        Intent i = getIntent();
        name= i.getStringExtra("item").trim();
        Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();

       // myDialog_currentReading mydialog = new myDialog_currentReading();

      //  mydialog.show(getSupportFragmentManager(),"Current Reading For: "+name);

    }


}
