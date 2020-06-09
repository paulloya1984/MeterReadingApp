package tz.co.ubunifusolutions.screens.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.about.About;
import tz.co.ubunifusolutions.screens.about.about_Bawasa;
import tz.co.ubunifusolutions.screens.activities.extras.tatizo;
import tz.co.ubunifusolutions.screens.fragments.dashboardFragment;
import tz.co.ubunifusolutions.screens.fragments.reading.readingSearchFragment;
import tz.co.ubunifusolutions.screens.fragments.settingsFrag;
import tz.co.ubunifusolutions.screens.fragments.synchronizeFragment;
import tz.co.ubunifusolutions.screens.fragments.walisomewaFrag;
import tz.co.ubunifusolutions.screens.map.MapsActivityCurrentPlace;
import tz.co.ubunifusolutions.screens.storage.SharesPrefManager;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
           Fragment fragment;
            switch (item.getItemId())
            {
                case R.id.navigation_dashboard:
                  fragment = new dashboardFragment();
                    if(fragment !=null){
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frag_container, fragment)
                                .commit();
                    }

                    break;

                case R.id.navigation_waliosomewa:
                   fragment = new walisomewaFrag();
                    if(fragment !=null){
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frag_container, fragment)
                                .commit();
                    }

                    break;

                case R.id.navigation_add:
//                   fragment = new readingSearchFragment();
//                    if(fragment !=null){
//                        getSupportFragmentManager()
//                                .beginTransaction()
//                                .replace(R.id.frag_container, fragment)
//                                .commit();
//                    }
                    Intent intent = new Intent(MainActivity.this,read_Search.class);
                    startActivity(intent);
                    break;

                case R.id.navigation_synchronise:
                    fragment = new synchronizeFragment();
                    if(fragment !=null){
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frag_container, fragment)
                                .commit();
                    }

                    break;
                case R.id.navigation_settings:
                    fragment = new settingsFrag();
                    if(fragment !=null){
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frag_container, fragment)
                                .commit();
                    }



                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return false;

        }
    };
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.nyumbani, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {

        case R.id.about_App:
             intent = new Intent(this, About.class);
            startActivity(intent);
           // finish();
            return(true);
        case R.id.about_Ubunifu:
             intent = new Intent(this, about_Bawasa.class);
            startActivity(intent);
//            finish();
            return(true);
        case R.id.logoff_mnu:
            customDialog("WARNING","You are about to Sign Out \n Are You Sure ?","cancelMethod1","okMethod1");

            return(true);

            case R.id.fault_kubwa:
               Intent intent2 = new Intent(MainActivity.this, tatizo.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent2);
                return(true);


            case R.id.location:
            Intent intent_ = new Intent(MainActivity.this, MapsActivityCurrentPlace.class);
            intent_.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent_);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nyumbani, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Anza juu
        //Navigation Drawer na toolbar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//       // setSupportActionBar(toolbar);
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//       // navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
//

        //Maliza na chini
        //Bottom Nav
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        displayFragment(new dashboardFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        boolean loggedin = sharedPref.getBoolean("LOGGED_IN",false);
        //Toast.makeText(this, "IS Logged in " +loggedin , Toast.LENGTH_SHORT).show();
        Intent i = getIntent();
        String name= i.getStringExtra("username");
        String time= i.getStringExtra("time");
        //  Toast.makeText(this, User, Toast.LENGTH_SHORT).show();
        // Snackbar.make(findViewById(android.R.id.content), "Logged in As \n"+name+"\n At\n "+time, Snackbar.LENGTH_LONG).show();
       // Toast.makeText(this, "IS Logged in " +loggedin +"\n Logged in As \n"+name+"\n At\n "+time, Toast.LENGTH_LONG).show();

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);

        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText("IS Logged in " +loggedin +"\n Logged in As \n"+name+"\n At\n "+time);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_done);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.green_500));

        toast.setView(custom_view);
        toast.show();

        // if (SharesPrefManager.getmInstance(this).isLoggedIn()) {
        if (loggedin){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

 private void displayFragment(Fragment fragment){
     getSupportFragmentManager()
             .beginTransaction()
             .replace(R.id.frag_container, fragment)
             .commit();

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

}
