package tz.co.ubunifusolutions.screens.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class IpAddress {
    private SharedPreferences prefs;

    public IpAddress(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setLocalIP(String localIP)
    {
        prefs. edit().remove("localIP").commit();
        prefs.edit().putString("localIP", localIP).apply();
    }

    public void setPublicIP(String PublicIP) {
        prefs. edit().remove("PublicIP").commit();
        prefs.edit().putString("PublicIP", PublicIP).apply();
    }

    public String getLocalIP() {
        String LocalIP = prefs.getString("LocalIP","");
        return LocalIP;
    }

    public String getPublicIP() {
        String PublicIP = prefs.getString("PublicIP","");
        return PublicIP;
    }
}
