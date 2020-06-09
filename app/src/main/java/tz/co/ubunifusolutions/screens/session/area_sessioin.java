package tz.co.ubunifusolutions.screens.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class area_sessioin {
    private SharedPreferences prefs;

    public area_sessioin(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }
    public void setArea(String area)
    {
        prefs. edit().remove("areaName").commit();
        prefs.edit().putString("areaName", area).apply();
    }
    public void setAreaCode(String areaCode)
    {
        prefs. edit().remove("areaNameCode").commit();
        prefs.edit().putString("areaNameCode", areaCode).apply();
    }

    public String getArea() {
        String areaName = prefs.getString("areaName","");
        return areaName;
    }
}
