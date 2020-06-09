package tz.co.ubunifusolutions.screens.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusername(String username) {
        prefs. edit().remove("localIP").apply();
        prefs.edit().putString("username", username).commit();
    }
    public void setpassword(String password) {
        prefs. edit().remove("localIP").apply();
        prefs.edit().putString("password", password).commit();
    }
    public void setSession(int session) {
        prefs. edit().remove("session").apply();
        prefs.edit().putInt("session", session).commit();
    }

    public String getusername() {
        String username = prefs.getString("username","");
        return username;
    }
    public String getpassword() {
        String password = prefs.getString("password","");
        return password;
    }





// adding data
//    private Session session;//global variable
//    session = new Session(cntx); //in oncreate
////and now we set sharedpreference then use this like
//
//session.setusename("USERNAME");

    // getting data
    //session.getusename();

}
