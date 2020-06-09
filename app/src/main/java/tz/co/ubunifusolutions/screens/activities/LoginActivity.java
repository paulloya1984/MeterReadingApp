package tz.co.ubunifusolutions.screens.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.api.RetrofitClient;
import tz.co.ubunifusolutions.screens.models.LoginResponse;
import tz.co.ubunifusolutions.screens.notification.NotificationHelper;
import tz.co.ubunifusolutions.screens.session.Session;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper_node;


public class LoginActivity extends AppCompatActivity{
    private static final String TAG = "Activity Login";
  //  DatabaseManager db;
    EditText txtmtu,txtpass;

    Button btnIngia; TextView btn_Mpya,btnForgotPassword;
    String LocalIP = "";
    String name = "" ;
    String loginTime = "";
    NotificationHelper notificationHelper;
    DatabaseHelper dataBaseHelper;

    Session session;

    public String getLocalAddress()
    {
        try {

          //  dataBaseHelper.settingDrop();

            Cursor res = dataBaseHelper.getSettings_local();
            StringBuffer stringBuffer = new StringBuffer();

            if (res != null && res.getCount() > 0) {
                while (res.moveToNext()) {
                    LocalIP = LocalIP + res.getString(0);
                }
                // txtResult.setText(.toString());
                //Toast.makeText(LoginActivity.this, "Data Retrieved Successfully" + LocalIP, Toast.LENGTH_SHORT).show();
                Log.i(TAG,"Data Retrieved Successfully" + LocalIP);

            } else {
                //Toast.makeText(LoginActivity.this, "Data Not Retrieved Successfully", Toast.LENGTH_SHORT).show();
                Log.i(TAG,"Data Not Retrieved Successfully -- NO IP Found");
            }
        } catch (Exception e) {
           // Toast.makeText(LoginActivity.this, "Error Has Occoured" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG,"Error Has Occoured" + e.getMessage());
        }

        return LocalIP;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);

       dataBaseHelper = new DatabaseHelper(LoginActivity.this);
       LocalIP = getLocalAddress();
        btn_Mpya= findViewById(R.id.mpya);
        btn_Mpya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),new_account.class);
                startActivity(intent);
            }
        });

        txtmtu = (EditText) findViewById(R.id.mtumiaji);
        txtpass = (EditText) findViewById(R.id.nenolasiri);
        btnIngia=  findViewById(R.id.ingia);
        btnIngia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
               // Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();

 }
        });

        btnForgotPassword = findViewById(R.id.forgot_password);
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);

                //inflate view
                View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
                ((TextView) custom_view.findViewById(R.id.message)).setText("Tafadhali wasiliana na Idara Ya ICT & GIS");
                ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_error_outline);
                ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.blue_500));

                toast.setView(custom_view);
                toast.show();
            }
        });

          }
/*
    @Override
    protected void onStart() {
        super.onStart();
       if (SharesPrefManager.getmInstance(this).isLoggedIn())
       {
           dataBaseHelper = new DatabaseHelper(LoginActivity.this);
           Cursor cursor = dataBaseHelper.getLoginLog();
           if(cursor.moveToFirst())
           {
               do{
                   //meterList.add(res.getString(2));

                   String connum=cursor.getString(cursor.getColumnIndex("connection_number"));
                   String prevread=cursor.getString(cursor.getColumnIndex( "previous_reading" ));
                   //   Toast.makeText(this, connum, Toast.LENGTH_LONG).show();

                   txtConnectionNumber.setText(connum);
                   txtPreviousRead.setText(prevread);
//                mLatitudeTextView.setText(cursor.getString(cursor.getColumnIndex("latitude")));
//                mLongitudeTextView.setText(cursor.getString(cursor.getColumnIndex("longitude")));

               }while (cursor.moveToNext());
           }



            Intent intent = new Intent(this, Activity_Main2.class);
           intent.putExtra("username", name);
           intent.putExtra("time", loginTime);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
     */
        private void userLogin() {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            final String loginTime = sdf.format(cal.getTime());

       // final String Timein= SharesPrefManager.getmInstance(this).gettime();
        final String jina = txtmtu.getText().toString().trim();
        String password = txtpass.getText().toString().trim();
        if (jina.isEmpty()) {
            txtmtu.setError("Username is required");
            txtmtu.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            txtpass.setError("Password is required");
            txtpass.requestFocus();
            return;
        }
        if (password.length() < 4) {
            txtpass.setError("Password Should be 4 Characters long");
            txtpass.requestFocus();
            return;
        }
/** Back Door kumbuka kuotoa hii**/
if (jina.equals(777) && password.equals(1234567)){

    String status_OK = "LOGGED_IN";

    Toast toast = new Toast(getApplicationContext());
    toast.setDuration(Toast.LENGTH_LONG);

    //inflate view
    View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
    ((TextView) custom_view.findViewById(R.id.message)).setText("Welcome! " + jina);
    ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_done);
    ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.green_500));

    toast.setView(custom_view);
    toast.show();

    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    intent.putExtra("username", jina);
    intent.putExtra("time", loginTime);
    //Snackbar.make(findViewById(android.R.id.content), loginTime, Snackbar.LENGTH_SHORT).show();

    /**    Save Login
     * */
    if (dataBaseHelper.Login_Log(name, status_OK)) {

/*Kuweka Loging session*/


        Context cntx = getApplicationContext();
        session = new Session(cntx); //in oncreate

////and now we set sharedpreference then use this like
//
       session.setusername(jina);

        Calendar calll = Calendar.getInstance(); // creates calendar
        calll.setTime(new Date()); // sets calendar time/date
        calll.add(Calendar.HOUR_OF_DAY, 20); // adds one hour
        calll.getTime(); // returns new date object, one hour in the future
        long unixTime = System.currentTimeMillis() / 1000L;


        SharedPreferences sharedPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("name", status_OK);
        editor.putBoolean(status_OK, true);
        editor.commit();

/*Mwisho wa Logining Session*/
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
/*Mwisho wa back Door*/
        Call<LoginResponse> ita= RetrofitClient
                .getInstance()
                .getApi()
                .userLogin(jina,password);

        ita.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
             try {
                 LoginResponse loginResponse = response.body();

                 if (!loginResponse.isError()) {
                     String name = loginResponse.getUser();
                     String status_OK = "LOGGED_IN";

                     Toast toast = new Toast(getApplicationContext());
                     toast.setDuration(Toast.LENGTH_LONG);

                     //inflate view
                     View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
                     ((TextView) custom_view.findViewById(R.id.message)).setText("Welcome! " + jina);
                     ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_done);
                     ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.green_500));

                     toast.setView(custom_view);
                     toast.show();

                   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                     intent.putExtra("username", name);
                     intent.putExtra("time", loginTime);
                     //Snackbar.make(findViewById(android.R.id.content), loginTime, Snackbar.LENGTH_SHORT).show();

                     /**    Save Login
                      * */
                     if (dataBaseHelper.Login_Log(name, status_OK)) {


                     SharedPreferences sharedPref = getSharedPreferences("login_pref", Context.MODE_PRIVATE);
                     SharedPreferences.Editor editor=sharedPref.edit();
                     editor.putString("name",status_OK);
                     editor.putBoolean(status_OK,true);
                     editor.commit();


                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         startActivity(intent);
                     }
                     else{
                         //Toast.makeText(LoginActivity.this, "Couldn't Add Login Log", Toast.LENGTH_SHORT).show();
//                         Toast toast = new Toast(getApplicationContext());
                         toast.setDuration(Toast.LENGTH_LONG);

                         //inflate view
                          custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
                         ((TextView) custom_view.findViewById(R.id.message)).setText("Couldn't Add Login Log");
                         ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
                         ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.red_600));

                         toast.setView(custom_view);
                         toast.show();

                     }
                 }
              else
                {
                   //Toast.makeText(LoginActivity.this, "MMM hapa kuna shida"+loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);

                    //inflate view
                    View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
                    ((TextView) custom_view.findViewById(R.id.message)).setText("MMM hapa kuna shida \n"+loginResponse.getMessage());
                    ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
                    ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.red_600));

                    toast.setView(custom_view);
                    toast.show();

                    return;
                }
             }catch (Exception e)
             {

               //  Toast.makeText(LoginActivity.this, "Login Error: \n " +e.getMessage(), Toast.LENGTH_SHORT).show();

                 Toast toast = new Toast(getApplicationContext());
                 toast.setDuration(Toast.LENGTH_LONG);

                 //inflate view
                 View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
                 ((TextView) custom_view.findViewById(R.id.message)).setText("Login Error: \n " +e.getMessage());
                 ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
                 ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.red_600));

                 toast.setView(custom_view);
                 toast.show();


             }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
        //   Toast.makeText(LoginActivity.this,"Login: onFailure \n"+t.getMessage(), Toast.LENGTH_SHORT).show();
                customDialog("Error","Login Failure: Couldn't Reach Server \n Click OK To check Server Setup or Cancel to Try Again",
                        "cancelMethod1","okMethod1");



            }
        });

    }

    public void customDialog(String title,String message,final String cancelMethod,final String okMethod)
    {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(LoginActivity.this);
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
                        } else if(okMethod.equals("okMethod2")){
                            okMethod1();
                        }
                    }
                }
        );
        builderSingle.show();
    }
    public  void cancelMethod1(){
        return;

    }
    public  void okMethod1(){

        Intent intent = new Intent(LoginActivity.this, settings.class);
        intent.putExtra("from","Login");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


}
