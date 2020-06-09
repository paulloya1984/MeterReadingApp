package tz.co.ubunifusolutions.screens.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.about.about_Bawasa;
import tz.co.ubunifusolutions.screens.fragments.reading.readingSearchFragment;
import tz.co.ubunifusolutions.screens.map.MapsActivityCurrentPlace;

public class Nyumbani extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
TextView nametxt;
Button btnCustomers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nyumbani);

       /* LinearLayout contentii = (LinearLayout) findViewById(R.id.Mkubwamama);
        btnCustomers = contentii.findViewById(R.id.btnCustomers);
        btnCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
customerList m = new customerList();
m.onCreate(null);
            }
        });*/

        Intent i = getIntent();
        String name= i.getStringExtra("username");
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
       // nametxt = findViewById(R.id.txtNameinNavHeader);
      //  nametxt.setText(name);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nyumbani, menu);
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
//            Toast.makeText(this, "Action Menu hapa", Toast.LENGTH_SHORT).show();
//        }
//        else
            if(id == R.id.about_Ubunifu){
            Toast.makeText(this, "About Ubunifu  hapa", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.about_App){
            Intent intent = new Intent(Nyumbani.this,about_Bawasa.class);


            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_readlist) {
            // Handle the camera action
            fragment = new readingSearchFragment();
           // fragment = new mapFragment();
        } else if (id == R.id.nav_pendinglist) {


        } else if(id == R.id.nav_readMap){
           // Toast.makeText(this, "Before", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MapsActivityCurrentPlace.class);
            startActivity(intent);
           // Toast.makeText(this, "After", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_synchronise) {
           // fragment = new synchronizeFragment();
            Intent intent = new Intent(this, sync.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {
            Toast.makeText(this, "Help Munu", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_signout) {
            customDialog("WARNING","You are about to Sign Out \n Are You Sure ?","cancelMethod1","okMethod1");
 }

        if(fragment !=null){
            displayFragment(fragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void cancelMethod1(){
        Toast.makeText(Nyumbani.this, "Cancel Method 1", Toast.LENGTH_SHORT).show();

    }
    public  void okMethod1(){

        Toast.makeText(Nyumbani.this, "Signed off", Toast.LENGTH_SHORT).show();
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
    private void displayFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.Mkubwamama, fragment)

                .commit();

    }


}
