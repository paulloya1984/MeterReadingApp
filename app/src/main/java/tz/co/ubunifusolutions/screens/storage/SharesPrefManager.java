package tz.co.ubunifusolutions.screens.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import tz.co.ubunifusolutions.screens.models.User;

public class SharesPrefManager {

 private static final String SHARED_PREF_NAME= "my_shared_pref";

private static SharesPrefManager mInstance;
private Context mCtx;

private  SharesPrefManager(Context mCtx){
    this.mCtx = mCtx;
}

public static synchronized SharesPrefManager getmInstance(Context mCtx){
if(mInstance== null){
    mInstance = new SharesPrefManager(mCtx);
}
return mInstance;
}
    public void saveUser(User user) {


        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String loginTime = sdf.format(cal.getTime());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", user.getUser());
        editor.putString("time",loginTime);

        editor.apply();

    }

    public boolean isLoggedIn() {
SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
  //return sharedPreferences.getInt("id", -1) != -1;
return  sharedPreferences.getString("username",null)!=null;

    }
    public User getUser() {
 SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
   return new User(
 sharedPreferences.getString("username",null)

        );

    }

    public String gettime(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
       return sharedPreferences.getString("time",null);

    }
    public void clear() {
 SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
 SharedPreferences.Editor editor = sharedPreferences.edit();
editor.clear();
 editor.apply();
 }


}
