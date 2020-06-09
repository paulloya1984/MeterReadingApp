package tz.co.ubunifusolutions.screens.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.about.about_Bawasa;
import tz.co.ubunifusolutions.screens.activities.extras.tatizo;
import tz.co.ubunifusolutions.screens.activities.wateja.List;
import tz.co.ubunifusolutions.screens.activities.wateja.Routeee;
import tz.co.ubunifusolutions.screens.map.MapsActivityCurrentPlace;
import tz.co.ubunifusolutions.screens.storage.SharesPrefManager;


public class Activity_Main2 extends AppCompatActivity
{

    FloatingActionButton newReadFloating;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent i = getIntent();
        String name= i.getStringExtra("username");
        String time= i.getStringExtra("time");
      //  Toast.makeText(this, User, Toast.LENGTH_SHORT).show();
       // Snackbar.make(findViewById(android.R.id.content), "Logged in As \n"+name+"\n At\n "+time, Snackbar.LENGTH_LONG).show();
        Toast.makeText(this, "Logged in As \n"+name+"\n At\n "+time, Toast.LENGTH_LONG).show();

        CardView cardView1,cardView2,location,settings,synchronise,fault,customers;

        cardView1 = findViewById(R.id.bankcardId);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main2.this, New_Reading_Tab.class);//New_Reading_Tab.class  read_Search.class
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                Toast.makeText(Activity_Main2.this, "Swap na Tab view", Toast.LENGTH_SHORT).show();
            }
        });
        cardView2 = findViewById(R.id.cardpending);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main2.this, List.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        location = findViewById(R.id.cardLocation);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main2.this, MapsActivityCurrentPlace.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        settings = findViewById(R.id.cardsettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main2.this, settings.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        synchronise =  findViewById(R.id.cardsynchronise);
        synchronise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main2.this, sync.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        fault = findViewById(R.id.cardFault);
        fault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main2.this, tatizo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        customers = findViewById(R.id.cardsCustomers);
        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main2.this, Routeee.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        newReadFloating = findViewById(R.id.float_new_Read);
        newReadFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Main2.this, read_Search.class);//New_Reading_Tab.class
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nyumbani, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Toast.makeText(Activity_Main2.this, "Action Menu hapa", Toast.LENGTH_SHORT).show();
//        }
//        else
            if(id == R.id.about_Ubunifu){
            Toast.makeText(Activity_Main2.this, "About Ubunifu  hapa", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.about_App){
            Intent intent = new Intent(Activity_Main2.this, about_Bawasa.class);
            startActivity(intent);

        } else if (id == R.id.logoff_mnu){
            customDialog("WARNING","You are about to Sign Out \n Are You Sure ?","cancelMethod1","okMethod1");

        }

        return super.onOptionsItemSelected(item);
    }


    public  void cancelMethod1(){
       // Toast.makeText(Activity_Main2.this, "Cancel Method 1", Toast.LENGTH_SHORT).show();
        return;

    }
    public  void okMethod1(){

        Snackbar.make(findViewById(android.R.id.content),"Signed off Successfull" , Snackbar.LENGTH_LONG).show();

      //  Toast.makeText(Activity_Main2.this, "Signed off Successfull", Toast.LENGTH_SHORT).show();
        SharesPrefManager.getmInstance(this).clear();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        // Toast.makeText(Nyumbani.this, "", Toast.LENGTH_SHORT).show();
    }
    public void customDialog(String title,String message,final String cancelMethod,final String okMethod){
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle(title);
        builderSingle.setIcon(R.drawable.ic_warning_black_24dp);
        builderSingle.setMessage(message);
        builderSingle.setNegativeButton(
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(cancelMethod.equals("cancelMethod1")){
                            cancelMethod1();
                        }

                    }
                }
        );
        builderSingle.setPositiveButton(
                "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(okMethod.equals("okMethod1")){
                            okMethod1();
                        }
                    }
                }
        );
        builderSingle.show();
    }


//
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        String isLoginedIN = sharedPref.getString("name","DefaultName");

      //  if (SharesPrefManager.getmInstance(this).isLoggedIn()) {
        if(isLoginedIN.equals("LOGGED IN")){
            Intent intent = new Intent(this, Activity_Main2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


}
