package tz.co.ubunifusolutions.screens.api;

import android.database.Cursor;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper_node;

public class RetrofitClient {

    private static final String AUTH = "Basic "+ Base64.encodeToString(("bawasa_meter_app_API:123456").getBytes(),Base64.NO_WRAP);

    String LocalIP ;
    DatabaseHelper dataBaseHelper;

    public String getLocalAddress()
    {
        try {

            dataBaseHelper.settingDrop();
            Cursor res = dataBaseHelper.getSettings_local();
            StringBuffer stringBuffer = new StringBuffer();

            if (res != null && res.getCount() > 0) {
                while (res.moveToNext()) {
                    LocalIP = LocalIP + res.getString(0);

                }

            } else {

            }
        } catch (Exception e) {
        }

        return LocalIP;
    }
// kwenye login

   public static final String BASE_URL="http://192.168.43.33:8080/api_sql_run1/public/";
   // public static final String BASE_URL="http://192.168.0.137/server/public/";

    // public  String BASE_URL;
 private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){

        //BASE_URL = "http://" + LocalIP + "/server/public/";
       //  Log.i("retrofit",BASE_URL);

//         OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(
//                 new Interceptor() {
//                     @Override
//                     public Response intercept(Chain chain) throws IOException {
//                         Request original = chain.request();
//
//                         Request.Builder requestBuilder = original.newBuilder()
//                                 .addHeader("Authorization",AUTH)
//                                 .method(original.method(),original.body());
//                         Request request = requestBuilder.build();
//                         return  chain.proceed(request);
//                     }
//                 }
//         ).build();


        retrofit =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
                .build();
    }

public static synchronized RetrofitClient getInstance(){
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
}

public Api getApi(){
        return retrofit.create(Api.class);
}


}

