package tz.co.ubunifusolutions.screens.uploads.API;
import android.database.Cursor;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;

public class ApiClient {
    private static final String TAG = "Upload Client API" ;
    DatabaseHelper dataBaseHelper;
    String LocalIP = "";

    public static final String BASE_URL = "http://localhost";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public  String getLocalAddress()
    {
        try {

            dataBaseHelper.settingDrop();
            Cursor res = dataBaseHelper.getSettings_local();
            StringBuffer stringBuffer = new StringBuffer();

            if (res != null && res.getCount() > 0) {
                while (res.moveToNext()) {
                    // stringBuffer.append("ID " + res.getString(0) + "\n");
                    LocalIP = LocalIP + res.getString(0);
                  }
                } else {
                           }
        }
        catch (final Exception e) {

            Log.e(TAG, "getLocalAddress: Error Has Occoured" + e.getMessage() );
 }
   return LocalIP;
    }
}
