package tz.co.ubunifusolutions.screens.api;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tz.co.ubunifusolutions.screens.session.IpAddress;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;


public class AppConfig extends Activity {
   static DatabaseHelper dataBaseHelper,databaseHelper1;
 static   String LocalIP = "";
    IpAddress ip;//global variable

    private static final String TAG = "AppConfig";

    public AppConfig() {
          IpAddress ipAddress = new IpAddress(getApplicationContext());
           String aa = ipAddress.getLocalIP();
    }

//    SharedPreferences sharedpreferences;
//    final static String MyPREFERENCES = "Preference-Global" ;
//    static SharedPreferences sh =getSharedPreferences(MyPREFERENCES, Context.MODE_APPEND);
//
//   String local_ip_address = sh.getString("Local_IP_Address", "");;
//    public static String BASE_URL= "http://"+local_ip_address+"";



    // dataBaseHelper.settingDrop();


   // int a = sh.getInt("age", 0);

    //private static String BASE_URL = "http://192.168.43.33:8080/upload/upload.php/";
    static String ipp = getLocalAddress();

    private static String BASE_URL = "http://"+ipp+"/upload/upload.php/";

    public static Retrofit getRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static String getLocalAddress()
    {
        try {

           // dataBaseHelper.settingDrop();
            Cursor res = dataBaseHelper.getSettings_local();
            StringBuffer stringBuffer = new StringBuffer();

            if (res != null && res.getCount() > 0) {
                while (res.moveToNext()) {
                    // stringBuffer.append("ID " + res.getString(0) + "\n");
                    LocalIP = LocalIP + res.getString(0);
                    //   stringBuffer.append("Public IP Address " + res.getString(2) + "\n");

                }
                // txtResult.setText(.toString());
               // Toast.makeText(getActivity(), "Data Retrieved Successfully" + LocalIP, Toast.LENGTH_SHORT).show();

            } else {
                // Toast.makeText(sync.this, "Data Not Retrieved Successfully", Toast.LENGTH_SHORT).show();
            }
        }
        catch (final Exception e) {

            Log.e("Network Error", "getLocalAddress: Error Has Occoured" + e.getMessage() );

           // Toast.makeText(getActivity(), "Error Has Occoured" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return LocalIP;
    }
}
