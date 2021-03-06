package tz.co.ubunifusolutions.screens.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.api.ApiConfig;
import tz.co.ubunifusolutions.screens.api.AppConfig;
import tz.co.ubunifusolutions.screens.api.HttpHandler;
import tz.co.ubunifusolutions.screens.api.ServerResponse;
import tz.co.ubunifusolutions.screens.notification.NotificationHelper;
import tz.co.ubunifusolutions.screens.session.IpAddress;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.utils.FileUtils;
import tz.co.ubunifusolutions.screens.utils.Filezip;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

//import static com.google.android.gms.internal.zzahf.runOnUiThread;

public class synchronizeFragment extends Fragment {
    private static final String TAG = "Data Synchronizing";
    private static final String DNAME = "meter_app" ;
    TextView textView, textViewProgress;
    public static TextView textView1,textView6;

    TextView txtResult;
    Button btnSyncLocal, btnSyncOnline, btnSom_WatejaWote, btnAreas;
    private ProgressBar progressBar,progressBar2;
    public static String progresstxt;
    String LocalIP = "";
    String id,bill_area,connection_number,current_reading,
            latitude,longitude,meter_number,meter_status,previous_reading,
            pushed,reading_date,reading_type,route,seq,updated_at,updated_by;


    NotificationHelper notificationHelper;
    DatabaseHelper dataBaseHelper,databaseHelper1;
    public ArrayList<HashMap<String, String>> CustomerList, AreaList,ReadlogBuffer,salsesAssistantList;

    int serverResponseCode = 0;
    ProgressDialog dialog = null;

    String upLoadServerUri = null;
    //final String uploadFilePath = "/mnt/sdcard/";
   // final String uploadFileName = "service_lifecycle.png";

    IpAddress ip;//global variable

    public String getLocalAddress()
    {
        try {

            dataBaseHelper.settingDrop();
            Cursor res = dataBaseHelper.getSettings_local();
            StringBuffer stringBuffer = new StringBuffer();

            if (res != null && res.getCount() > 0) {
                while (res.moveToNext()) {
                    // stringBuffer.append("ID " + res.getString(0) + "\n");
                    LocalIP = LocalIP + res.getString(0);
                    //   stringBuffer.append("Public IP Address " + res.getString(2) + "\n");

                }
                // txtResult.setText(.toString());
                Toast.makeText(getActivity(), "Data Retrieved Successfully" + LocalIP, Toast.LENGTH_SHORT).show();

            } else {
                // Toast.makeText(sync.this, "Data Not Retrieved Successfully", Toast.LENGTH_SHORT).show();
            }
        }
        catch (final Exception e) {

    Log.e(TAG, "getLocalAddress: Error Has Occoured" + e.getMessage() );

            Toast.makeText(getActivity(), "Error Has Occoured" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return LocalIP;
    }
    int pp=0;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.synchronize_fragment, container,false);

        Context cntx = getActivity();
        ip = new IpAddress(cntx); //in oncreate

        dataBaseHelper = new DatabaseHelper(getActivity());
        notificationHelper = new NotificationHelper(getActivity());

        CustomerList = new ArrayList<>();
        AreaList = new ArrayList<>();
        ReadlogBuffer = new ArrayList<>();

        textView = view.findViewById(R.id.textView);
        textView6 = view.findViewById(R.id.textView6);

        btnSyncLocal = (Button) view.findViewById(R.id.btnSyncLocal);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.INVISIBLE);

        /**Mwanzo wa Local Sync*/
        btnSyncLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // progressBar.setIndeterminate(true);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(1);
                progressBar2.setVisibility(View.VISIBLE);
                progressBar2.setProgress(1);

                LocalIP="";
                LocalIP = getLocalAddress();
                // Toast.makeText(sync.this, LocalIP, Toast.LENGTH_SHORT).show();
                textView.setText("Please Wait \n Downloading Data ....");
                // progressBar.setProgress(15);
                new getAllConnections().execute();
                new getAllAreas().execute();
                new getAllCustomers().execute();

                // progressBar.setVisibility(View.INVISIBLE);
     }

        });

/**
 * Mwisho wa local Sync
 *
 *
 * Mwanzo wa Sync Online*/
        btnSyncOnline = (Button)view.findViewById(R.id.btnSyncOnline);

        btnSyncOnline.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Toast.makeText(cntx, "KUbumka ku-update flags ya upload", Toast.LENGTH_SHORT).show();
                new UploadfileTask().execute();


            }
        });

        /**
         * Mwisho wa Online Sync*/

        return view;
    }

    /*
     * This is the method responsible for image upload
     * We need the full image path and the name for the image in this method
     * */
    public void uploadMultipart(File file)
    {
        //getting name for the image
       // String name = editText.getText().toString().trim();

        String name = file.getName();

        String LocalIPAddress = null,PublicIPAddress = null;

        // dataBaseHelper.settingDrop();
        Cursor res = dataBaseHelper.getSettings_All();
        StringBuffer stringBuffer = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append("Local_IP_Address" + res.getString(res.getColumnIndex("ip_local")) + "\n");
                LocalIPAddress = res.getString(res.getColumnIndex("ip_local"));
                stringBuffer.append("Public_IP_Address" + res.getString(res.getColumnIndex("ip_public")) + "\n");
                PublicIPAddress = res.getString(res.getColumnIndex("ip_public"));
            }
            // txtResult.setText(stringBuffer.toString());
        }

        String aa = ip.getLocalIP();
        String BASE_URL = "http://"+LocalIPAddress+"/upload/upload.php/";
        Log.e(TAG, "uploadFile: "+ LocalIPAddress );

       String path = file.getAbsolutePath();

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
           // dialog = ProgressDialog.show(getActivity(), "Sending Data", "Uploading file...", true);
            new MultipartUploadRequest(getActivity(), uploadId, BASE_URL)
                    .addFileToUpload(path, "file") //Adding file
                   // .addParameter("name", name) //Adding text parameter to the request
                   // .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

  } catch (Exception exc) {
            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
   }

    public boolean isNetworkIpo()
    {
        ConnectivityManager connectivityM = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityM.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

    public int uploadSpeed(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        NetworkCapabilities nc = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
        }
        int downSpeed = nc.getLinkDownstreamBandwidthKbps();
        int upSpeed = nc.getLinkUpstreamBandwidthKbps();
        return upSpeed;
    }

    public void uploadFile(File file)
    {
        String fileName = file.getName();
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        if (!file.isFile())
        {
            dialog.dismiss();
            Log.e("uploadFile", "Source File not exist :") ;
            Toast.makeText(getActivity(), "Source File not exist :", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String LocalIPAddress = null,PublicIPAddress = null;

                // dataBaseHelper.settingDrop();
                Cursor res = dataBaseHelper.getSettings_All();
                StringBuffer stringBuffer = new StringBuffer();
                if (res != null && res.getCount() > 0) {
                    while (res.moveToNext()) {
                        stringBuffer.append("Local_IP_Address" + res.getString(res.getColumnIndex("ip_local")) + "\n");
                        LocalIPAddress = res.getString(res.getColumnIndex("ip_local"));
                        stringBuffer.append("Public_IP_Address" + res.getString(res.getColumnIndex("ip_public")) + "\n");
                        PublicIPAddress = res.getString(res.getColumnIndex("ip_public"));
                    }
                   // txtResult.setText(stringBuffer.toString());
                }
            res.close();
            String aa = ip.getLocalIP();
            String BASE_URL = "http://"+LocalIPAddress+"/upload/upload.php/";
            Log.i(TAG, "uploadFile: "+ LocalIPAddress );

           // Log.e(TAG, "uploadFile: "+ getLocalAddress() );
            Log.e(TAG, "uploadFile: BaseURL - " + BASE_URL );
            HttpHandler httpHandler = new HttpHandler();
            HttpHandler.makeServiceCall_Upload(BASE_URL,file);

        }


        dialog.dismiss();
    }

    public void uploadSingleFile(File file)
    {
        //Loop if uploading many files

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("application/json"), file.getName());

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        retrofit2.Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename);
        call.enqueue(new retrofit2.Callback<ServerResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Sync_Fragment", "onResponse: "+ serverResponse.getMessage());
                    } else {
                        Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Sync_Fragment", "onResponse: "+ serverResponse.getMessage());
                    }
                }
//                else {
//                    assert serverResponse != null;
//                   // Log.v("Response", ""+serverResponse);
//                    Log.d("Main Activity", "onResponse: "+ serverResponse.message);
//                }
                // progressDialog.dismiss();
            }

            @Override
            public void onFailure(retrofit2.Call<ServerResponse> call, Throwable t) {
                Log.v("Response", ""+t.getMessage());
            }
        });
    }

    public String OKHTTP_Upload(File file){
        String LocalIPAddress = null;
        String PublicIPAddress = null;
        final String[] jibu = { null };
        Cursor res = dataBaseHelper.getSettings_All();
        StringBuffer stringBuffer = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append("Local_IP_Address" + res.getString(res.getColumnIndex("ip_local")) + "\n");
                LocalIPAddress = res.getString(res.getColumnIndex("ip_local"));
                stringBuffer.append("Public_IP_Address" + res.getString(res.getColumnIndex("ip_public")) + "\n");
                PublicIPAddress = res.getString(res.getColumnIndex("ip_public"));
            }
            // txtResult.setText(stringBuffer.toString());
        }
        String aa = ip.getLocalIP();
        String BASE_URL = "http://"+LocalIPAddress+"/upload/upload.php/";
        Log.e(TAG, "uploadFile: "+ LocalIPAddress );

        Thread upload = new Thread(new Runnable() {
            @Override
            public void run() {


                Uri uri = Uri.fromFile(file);
                ContentResolver cR = getContext().getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String mimeType = cR.getType(Uri.parse(String.valueOf(new File(file.getPath()))));
                String file_path = file.getAbsolutePath();
                //String content_type = getMimeType(uri);
               // String content_type= mime.getExtensionFromMimeType(cR.getType(uri));
                Log.d(TAG, "run: Absolute path "+ file.getAbsolutePath());
                Log.d(TAG, "run: Canonicals PAth "+ file.getPath());
                Log.d(TAG, "run: filename " +file_path.substring(file_path.lastIndexOf(".")+1));
                Log.d(TAG, "run: File type " + mimeType);

                OkHttpClient client = new OkHttpClient();
                RequestBody file_body = RequestBody.create(MediaType.parse("*/*"), file);
               // RequestBody file_body = RequestBody.create(MediaType.parse(content_type),file.getPath());
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                       // .addFormDataPart("type",mimeType)
                        .addFormDataPart("file",file_path.substring(file_path.lastIndexOf("/")+1),file_body)
                        .build();
                Request request = new Request.Builder()
                        .url(BASE_URL)
                        .post(requestBody)

                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if(response.isSuccessful() == true){
                        jibu[0] = "Success";
                        response.body().close();
                    }
                    else
                    {
                        jibu[0] = "Failure";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        upload.start();

        return jibu[0];
    }

    private String getMimeType(String path) {
//        String extension = MimeTypeMap.getFileExtensionFromUrl(String.valueOf(path));
//        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;

    }

    private class getAllConnections extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity(), "Json Data is downloading -- All Connections", Toast.LENGTH_LONG).show();

        }

        String connection_number;
        //   Toast.makeText(sync.this,"Json Data Background",Toast.LENGTH_LONG).show();


        @Override
        protected Void doInBackground(Void... arg0)
        {
            progressBar.animate();
            progressBar.setProgress(10);
            progressBar2.setProgress(0);
            String jsonStr="";
            try {
                HttpHandler sh = new HttpHandler();

                // Making a request to url and getting response
                /*
                 * All Customers comes from connection Table
                 * */

                String url = " http://" + LocalIP + "/api_sql_run1/public/allcustomers";
                jsonStr = sh.makeServiceCall(url);
            } catch (Exception e) {
                Log.e(TAG, "Response from url: " + jsonStr);
                Log.e(TAG,""+ e);
            }

            if (jsonStr != null)
            {
                dataBaseHelper = new DatabaseHelper(getActivity());
                dataBaseHelper.ondoaConnection();
                try
                {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray customersJArray = jsonObj.getJSONArray("customers");
                    int le = customersJArray.length();

                    Log.e(TAG, "doInBackground: "+"Urefu wote" + le );
                    progressBar2.setMax(le);
                    // looping through All Customers
                    for (int i = 0; i < customersJArray.length(); i++) {



                        //  ProgressBarAnimation anim = new ProgressBarAnimation(progressBar2, 0, i);
                        progressBar2.setProgress(i);
                        // anim.setDuration(100);
                        //  progressBar2.startAnimation(anim);

                        JSONObject c = customersJArray.getJSONObject(i);
                        connection_number = c.getString("connection_number");
                        String connection_name = c.getString("connection_name");
                        String meter_number = c.getString("meter_number");
                        String meter_status = c.getString("meter_status");
                        String previous_date = c.getString("previous_date");
                        String reading_date = c.getString("reading_date");
                        String current_reading = c.getString("current_reading");
                        String previous_reading = c.getString("previous_reading");
                        String reading_type_id = c.getString("reading_type_id");//reading_type_id
                        String current_consumption = c.getString("current_consumption");
                        String previous_consumption = c.getString("previous_consumption");
                        String daily_average = c.getString("daily_average");
                        String no_days = c.getString("no_days");
                        String average_consumption = c.getString("average_consumption");
                        String month = c.getString("month");
                        String zone = c.getString("zone");
                        String route = c.getString("route");
                        String seq = c.getString("seq");
                        String done = c.getString("done");
                        String metered = c.getString("metered");
                        // String pushed = c.getString("metered");
                        //  String updated_at = c.getString("updated_at");//haipo
                        String updated_by = c.getString("updated_by");
                        String date_updated = c.getString("date_updated");
                        String sales_assistant_id3 = c.getString("sales_assistant_id3");
                        String gpsx = c.getString("gpsx");
                        String gpsy = c.getString("gpsy");//ongeza elevation
                        String gpsh = c.getString("gpsh");
                        String disconnected = c.getString("disconnected");

                        // tmp hash map for single contact
                        HashMap<String, String> customerList = new HashMap<>();
/**Hash Map haina kazi punguza lines of code*/
                        // adding each child node to HashMap key => value
                        customerList.put("connection_number", connection_number);
                        customerList.put("meter_number", meter_number);
                        customerList.put("meter_status", meter_status);
                        customerList.put("previous_date", previous_date);
                        customerList.put("reading_date", reading_date);
                        customerList.put("current_reading", current_reading);
                        customerList.put("previous_reading", previous_reading);
                        customerList.put("current_consumption", current_consumption);
                        customerList.put("previous_consumption", previous_consumption);
                        customerList.put("reading_type_id", reading_type_id);
                        customerList.put("daily_average", daily_average);
                        customerList.put("no_days", no_days);
                        customerList.put("average_consumption", average_consumption);
                        customerList.put("month", month);
                        customerList.put("zone", zone);
                        customerList.put("route", route);
                        customerList.put("seq", seq);
                        customerList.put("done", done);
                        customerList.put("metered", metered);
                        //  customerList.put("pushed", pushed);
                        //  customerList.put("updated_at", updated_at);
                        customerList.put("updated_by", updated_by);
                        customerList.put("date_updated", date_updated);
                        customerList.put("sales_assistant_id3", sales_assistant_id3);
                        customerList.put("gpsx", gpsx);latitude = gpsx;
                        customerList.put("gpsy", gpsy);latitude = gpsy;
                        customerList.put("gpsh", gpsh);
                        customerList.put("disconnected", disconnected);

                        // adding contact to contact list
                        CustomerList.add(customerList);



                        if (dataBaseHelper.insertTblConnection(connection_number,connection_name, meter_number, meter_status, previous_date, reading_date,
                                current_reading, previous_reading, reading_type_id, current_consumption,
                                previous_consumption, daily_average, no_days, average_consumption, month, zone, route,
                                seq, done, metered, updated_by, date_updated, sales_assistant_id3,
                                longitude, latitude, disconnected)) {

                            Log.i(TAG, "Customer meter number  " + connection_number + "Synced Successful, Total " + i);


                        } else {
                            Log.e(TAG, "All Customers: Error at " + i);
                        }
                    }

                } catch (final Exception e) {
                    Log.e(TAG, "All Customers: Error at: " + e);
                }

            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
                String msg = "Couldn't get json from server.";

                notificationHelper.createNotification("Data Synchronizing Error","Couldn't get Data from Server, Please Check if server can be reached");

                cancel(true);
            }
            Log.i(TAG, "Done Loading Customers.");
            progressBar.setProgress(35);
            dataBaseHelper.close();
/**
 * End All Connection Table
 * */

            return null;
        }



        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            textView.setText("Please Wait \n Downloading Data \n Customer Connections Done ....");

            // String msg = "Data Synchronizing Completed Successful";
            //  notificationHelper.createNotification("Data Synchronizing","Data Synchronizing Completed Successful");
        }


        /*
         * Read
         * */
    }

    private class getAllAreas extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity(), "Json Area Data is downloading", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Json Area Data Downloading -- All Areas");
            progressBar.setProgress(40);
            textView.setText("Please Wait \n Downloading Data \n Customer Connections Done ....\n Area Data Downloading -- All Areas");
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

/**
 * * All Areas
 * */
            HttpHandler sh_Area = new HttpHandler();

            /*
             * All Area from Areas Table
             * */

            // String All_allArea_URL = "http://192.168.43.116/server/public/allareas";
            String All_allArea_URL = " http://" + LocalIP + "/api_sql_run1/public/allareas";

            String jsonStr_Area = sh_Area.makeServiceCall(All_allArea_URL);
            Log.e(TAG, "Response from url: " + jsonStr_Area);
            //  Toast.makeText(sync.this, "Response from url: " + jsonStr, Toast.LENGTH_SHORT).show();
            if (jsonStr_Area != null) {
                dataBaseHelper = new DatabaseHelper(getActivity());
                dataBaseHelper.ondoaArea();
                progressBar2.setProgress(0);

                try {
                    JSONObject jsonObj_Area = new JSONObject(jsonStr_Area);

                    // Getting JSON Array node
                    JSONArray areaJArray = jsonObj_Area.getJSONArray("areas");

                    int le =areaJArray.length();
                    progressBar2.setMax(le);
                    for (int i = 0; i < areaJArray.length(); i++) {
                        progressBar2.setProgress(i);
                        JSONObject c = areaJArray.getJSONObject(i);
                        String area_id = c.getString("area_id");
                        String area_code = c.getString("area_code");
                        String Area = c.getString("Area");
                        String zone_id = c.getString("zone_id");
                        String created_by = c.getString("created_by");
                        String date_created = c.getString("date_created");
                        String time_created = c.getString("time_created");
                        String date_updated = c.getString("date_updated");
                        String time_updated = c.getString("time_updated");
                        String updated_by = c.getString("updated_by");

                        // tmp hash map for single area
                        HashMap<String, String> areaListHash = new HashMap<>();

                        // adding each child node to HashMap key => value
                        areaListHash.put("area_id", area_id);
                        areaListHash.put("area_code", area_code);
                        areaListHash.put("Area", Area);
                        areaListHash.put("zone_id", zone_id);
                        areaListHash.put("created_by", created_by);
                        areaListHash.put("date_created", date_created);
                        areaListHash.put("time_created", time_created);
                        areaListHash.put("date_updated", date_updated);
                        areaListHash.put("time_updated", time_updated);
                        areaListHash.put("updated_by", updated_by);
                        AreaList.add(areaListHash);
                        dataBaseHelper = new DatabaseHelper(getActivity());
                        if (dataBaseHelper.insertAreas(area_id, area_code, Area, zone_id, created_by, date_created,time_created, date_updated, time_updated, updated_by)) {

                            Log.i(TAG, "Area Code   " + area_code + " Synced Successful, Total " + i);

                        } else {
                            Log.e(TAG, "All Area Error at " + i);

                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    String msg = "Json parsing error: " + e.getMessage();
                    notificationHelper.createNotification("Data Synchronizing Error", "Json parsing error: " + e.getMessage());
                    cancel(true);
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                String msg = "Couldn't get json from server.";
                notificationHelper.createNotification("Data Synchronizing Error","Couldn't get Data from Server, Please Check if server can be reached");
                cancel(true);
            }
            progressBar.setProgress(70);
            Log.i(TAG, "Done Loading Area.");
            dataBaseHelper.close();
            /**
             * All Areas mwisho hapa*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            textView.setText("Please Wait \n Downloading Data \n Customer Connections Done ....\n Area Data Downloading -- All Areas\n Details Done .... ");


        }
    }

    private class getAllSalesAsstant extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity(), "Json Sales Assistant Data is downloading", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Json Sales Assistant Data Downloading -- All Sales Assistant");
            progressBar.setProgress(40);
            textView.setText("Please Wait \n Downloading Data \n Customer Connections Done ....\n Area Data Downloading \n Sales Assistant Data Downloading-- All Areas");
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {

/**
 * * All Sales Assistant
 * */
            HttpHandler sh_SalesAssistant = new HttpHandler();

            /*
             * All Area from Areas Table
             * */

            // String All_allArea_URL = "http://192.168.43.116/server/public/allareas";
            // http://localhost:8080/api_sql_run1/public/salesassist
            String All_allSales_URL = " http://" + LocalIP + "/api_sql_run1/public/salesassist";

            String jsonStr_SalesAssistant = sh_SalesAssistant.makeServiceCall(All_allSales_URL);
            Log.e(TAG, "Response from url: " + jsonStr_SalesAssistant);
            //  Toast.makeText(sync.this, "Response from url: " + jsonStr, Toast.LENGTH_SHORT).show();
            if (jsonStr_SalesAssistant != null) {
                dataBaseHelper = new DatabaseHelper(getActivity());
                dataBaseHelper.ondoaArea();
                progressBar2.setProgress(0);

                try {
                    JSONObject jsonObj_Area = new JSONObject(jsonStr_SalesAssistant);

                    // Getting JSON Array node
                    ///
                    JSONArray SalesAssistantJArray = jsonObj_Area.getJSONArray("salesAssistant");

                    int le =SalesAssistantJArray.length();
                    progressBar2.setMax(le);
                    for (int i = 0; i < SalesAssistantJArray.length(); i++) {
                        progressBar2.setProgress(i);
                        JSONObject c = SalesAssistantJArray.getJSONObject(i);
                        String sales_assistant_id = c.getString("sales_assistant_id");
                        String sales_assistant_name = c.getString("sales_assistant_name");
                        String sales_assistant_ids = c.getString("sales_assistant_ids");
                        String technician = c.getString("technician");
                        String engineer = c.getString("engineer");
                        String sales_assistant = c.getString("sales_assistant");
                        String regular = c.getString("regular");


                        // tmp hash map for single area
                        HashMap<String, String> salesAssistantListHash = new HashMap<>();

                        // adding each child node to HashMap key => value
                        salesAssistantListHash.put("sales_assistant_id", sales_assistant_id);
                        salesAssistantListHash.put("sales_assistant_name", sales_assistant_name);
                        salesAssistantListHash.put("sales_assistant_ids", sales_assistant_ids);
                        salesAssistantListHash.put("technician", technician);
                        salesAssistantListHash.put("engineer", engineer);
                        salesAssistantListHash.put("sales_assistant", sales_assistant);
                        salesAssistantListHash.put("regular", regular);
                        salsesAssistantList.add(salesAssistantListHash);
                        dataBaseHelper = new DatabaseHelper(getActivity());
                        if (dataBaseHelper.insertSalesAssistant(sales_assistant_id, sales_assistant_name, sales_assistant_ids, technician, engineer, sales_assistant,regular)) {

                            Log.i(TAG, "Sales Assistant  " + sales_assistant_name + " Synced Successful, Total " + i);

                        } else {
                            Log.e(TAG, "All Sales Assistant Error at " + i);

                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    String msg = "Json parsing error: " + e.getMessage();
                    notificationHelper.createNotification("Data Synchronizing Error", "Json parsing error: " + e.getMessage());
                    cancel(true);
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                String msg = "Couldn't get json from server.";
                notificationHelper.createNotification("Data Synchronizing Error","Couldn't get Data from Server, Please Check if server can be reached");
                cancel(true);
            }
            progressBar.setProgress(70);
            Log.i(TAG, "Done Loading Area.");
            dataBaseHelper.close();
            /**
             * All Areas mwisho hapa*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            textView.setText("Please Wait \n Downloading Data \n Customer Connections Done ....\n Area Data Downloading -- All Areas\n Details Done .... ");


        }
    }


    private class getAllCustomers extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity(), "Json Data is downloading -- Customer ", Toast.LENGTH_LONG).show();
            textView.setText("Please Wait \n Downloading Data \n Customer Connections Done ....\n " +
                    "Area Data Downloading -- All Areas\n Details Done .... ");

        }

        @Override
        protected Void doInBackground(Void... voids) {
/**All Customers from customers table
 * */
            progressBar2.setProgress(0);
            HttpHandler sh_Customers = new HttpHandler();

            // Making a request to url and getting response
            // All Customers comes from connection Table
            String url_Customer = " http://" + LocalIP + "/api_sql_run1/public/customerdetails";
            String jsonStr_Customers = sh_Customers.makeServiceCall(url_Customer);
            Log.e(TAG, "Response from url: " + jsonStr_Customers);
            if (jsonStr_Customers != null)
            {
                dataBaseHelper = new DatabaseHelper(getActivity());
                dataBaseHelper.ondoaCustomers();

                try
                {
                    JSONObject jsonObj = new JSONObject(jsonStr_Customers);

                    // Getting JSON Array node
                    JSONArray customers_JArray = jsonObj.getJSONArray("customers_Details");
                    int le = customers_JArray.length();
                    progressBar2.setMax(le);
                    // looping through All Customers
                    for (int i = 0; i < customers_JArray.length(); i++) {
                        progressBar2.setProgress(i);
                        JSONObject c = customers_JArray.getJSONObject(i);
                        String customer_number = c.getString("customer_number");
                        String first_name = c.getString("first_name");
                        String middle_name = c.getString("middle_name");
                        String last_name = c.getString("last_name");
                        String mobile_number = c.getString("mobile_number");
                        String telephone_number = c.getString("telephone_number");
                        String work_phone = c.getString("work_phone_number");
                        String billing_area = c.getString("billing_area");
                        String updated_by = c.getString("updated_by");
                        String date_created = c.getString("date_created");
                        String date_updated = c.getString("date_updated");

                        // tmp hash map for single contact
                        HashMap<String, String> customerList = new HashMap<>();

                        // adding each child node to HashMap key => value
                        customerList.put("customer_connection", customer_number);
                        customerList.put("first_name", first_name);
                        customerList.put("middle_name", middle_name);
                        customerList.put("last_name", last_name);
                        customerList.put("mobile_number", mobile_number);
                        customerList.put("telephone_number", telephone_number);
                        customerList.put("work_phone_number", work_phone);
                        customerList.put("bill_area", billing_area);
                        customerList.put("updated_by", updated_by);
                        customerList.put("date_created", date_created);
                        customerList.put("date_updated", date_updated);

                        // adding Customer Details to details list
                        CustomerList.add(customerList);

                        if (dataBaseHelper.insertCustomerDetails(customer_number, first_name, middle_name, last_name, mobile_number, telephone_number, work_phone, billing_area, updated_by, date_created, date_updated)) {

                            Log.i(TAG, "Customer Details Successful " + first_name + "Synced Successful, Total " + i + "Customer Number "+customer_number);
                        } else {
                            Log.e(TAG, "All Customer Error at " + i);
                        }
                    }
                }
                catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    String msg= "Json parsing error: " + e.getMessage();
                    // notifyMe_Error(msg);
                    cancel(true);
                }

            }
            else
            {
                Log.e(TAG, "Couldn't get json from server.");
                String msg = "Couldn't get json from server.";
                //  notifyMe_Error(msg);
                cancel(true);
            }

            Log.i(TAG, "Done Loading Customers Details.");


/**
 * End All Customers
 * */

            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            String msg = "Data Synchronizing Completed Successful";

            textView.setText("Please Wait \n Downloading Data \n Customer Connections Done \n Area Details Done \n Done Getting All Customers Details \n Data Synchronizing Completed Successful ");

            textView.setText("Please Wait \n Downloading Data \n Customer Connections Done \n Area Details Done \n Done Getting All Customers Details \n Data Synchronizing Completed Successful  \n Attempting to delete Images");

            dataBaseHelper.ondoaPicha();
            // delete na picha za kwenye file also
            File imagesFolder = new File(Environment.getExternalStorageDirectory(), DNAME + "/Images");
            Log.e(TAG, "onPostExecute: Path ya picha zote"+imagesFolder );
            File file_path = new File(Environment.getExternalStorageDirectory(), DNAME);
            Log.e(TAG, "onPostExecute: Path ya Json zote"+file_path );
//            imagesFolder.mkdirs();
           deleteRecursive(imagesFolder);
           deleteRecursive(file_path);

            try {
                if (!file_path.exists()) {
                    file_path.mkdirs();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            dataBaseHelper.ondoaUsomaji();

            progressBar.setVisibility(View.GONE);

            textView6.setVisibility(View.GONE);
            notificationHelper.createNotification("Data Synchronizing","Data Synchronizing Completed Successful");

            // notificationHelper.createNotification("Data Synchronizing","Data Synchronizing Completed Successful");

        }

    }

    @SuppressLint("StaticFieldLeak")
    private class UploadfileTask extends AsyncTask<Void, Integer, Void> {

        private ProgressDialog nDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Uploading");
            nDialog.setIndeterminate(true);

            //nDialog.setCancelable(true);
//            nDialog.show();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        protected void onProgressUpdate(Integer... progress) {
            nDialog.setMessage("Uploading Image: " + progress[0]);
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected Void doInBackground(Void... voids) {
//            textView.setText("");
            /*
             * Get Data From Database read kwa ku-upload*/
            if (isNetworkIpo()) {

                JSONArray resultSet = dataBaseHelper.readJSon();
                JSONObject myobj = new JSONObject();
                try {
                    myobj.put("Read_log", resultSet);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    /*
                    Mwanzo wa kutuma data
                     * Generating File--JSON DATA
                     */

                long unixTime = System.currentTimeMillis() / 1000L;
                String filename = "json_data-" + unixTime + ".json";
                File file_path = new File(Environment.getExternalStorageDirectory(), DNAME);
                try {
                    if (!file_path.exists()) {
                        file_path.mkdirs();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FileReader fileReader = null;
                FileWriter fileWriter = null;
                BufferedReader bufferedReader = null;
                BufferedWriter bufferedWriter = null;
                final File file = new File(file_path, filename);
                if (!file.exists()) {

                    try {
                        file.createNewFile();
                        fileWriter = new FileWriter(file.getAbsoluteFile());
                        bufferedWriter = new BufferedWriter(fileWriter);
//                            bufferedWriter.append(myobj.toString());
                        bufferedWriter.append(resultSet.toString());
                        // bufferedWriter.write(resultSet.toString());
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Log.e(TAG, "getreadlog_data: Json Data - Result set"+resultSet);

                    Log.e(TAG, "getreadlog_data: Location Of JSON - " + file.getAbsoluteFile());

                }
                // get List of files to upload
                // hii ni kwa ajili ya images

                String path = Environment.getExternalStorageDirectory().toString() + "/" + DNAME;
                String path_images = Environment.getExternalStorageDirectory().toString() + "/" + DNAME + "/Images";
                Log.d("Files", "Path: " + path);
                File directory = new File(path);
                File directory_images = new File(path_images);
                File[] files = directory.listFiles();
                final File[] files_images = directory_images.listFiles();
                Log.d("Files", "Size: " + files.length);


                Filezip filezip = new Filezip();
                String source = path + "/Images";

                final int[] jj = {0};
                try {

                    String jibu_upload = null;//= OKHTTP_Upload(file);
                    jibu_upload = "Success";
                    if(jibu_upload == "Success")
                    {
                        for (int i = 0; i < files_images.length; i++) {

                            jj[0] = (int) (jj[0] + files_images[i].length());
                        }

                        jj[0] = (int) (jj[0] + file.length());

                        final int kk = files_images.length;

                        for (int i = 1; i <= files_images.length; i++) {
                              Log.d("Files", "FileName:" + files_images[i].getName());
                            //  uploadSingleFile(files_images[i]);
                            //   uploadFile(files_images[i]);
                           // nDialog.setMessage("Uploading Image: " + i + " of " + files_images.length);
                            notificationHelper.uploadnotifiaction("File Uploading","Upload in Progress",i,kk);
                            //  notificationHelper.createNotification("File Uploading","Upload in Progress");
                            Thread.sleep(15000);
                            String jibu_upload1 = OKHTTP_Upload(files_images[i]);
                           // uploadMultipart(files_images[i]);
                            Log.i(TAG, "run: File Upload: File - " + i + " of " + files_images.length);

                            if(jibu_upload1 == "Failure"){

                                notificationHelper.createNotification("Upload Failure","Failed to upload file");
                               // isCancelled();
                            }

                        }
                        notificationHelper.createNotification("Success","Upload Completed");
                    }
                    else{
                       // isCancelled();
                        notificationHelper.createNotification("Upload Failure","Failed to upload file");
                    }

                   // uploadMultipart(file); // using library
//                    nDialog.setMessage("Uploading Data .  ");
//                    Thread.sleep(2500);


//                    for (int i = 0; i < files_images.length; i++) {
//
//                        jj[0] = (int) (jj[0] + files_images[i].length());
//                    }

//                    jj[0] = (int) (jj[0] + file.length());

//                   final int kk = files_images.length;

//                    for (int i = 1; i <= files_images.length; i++) {
//                        //  Log.d("Files", "FileName:" + files_images[i].getName());
//                        //  uploadSingleFile(files_images[i]);
//                        //   uploadFile(files_images[i]);
//                        nDialog.setMessage("Uploading Image: " + i + " of " + files_images.length);
//                        notificationHelper.uploadnotifiaction("File Uploading","Upload in Progress",i,kk);
//                      //  notificationHelper.createNotification("File Uploading","Upload in Progress");
//                        Thread.sleep(30000);
//                        uploadMultipart(files_images[i]);
//                        Log.i(TAG, "run: File Upload: File - " + i + " of " + files_images.length);
//
//
//                    }
                } catch (Exception e) {
                    //customDialog("Upload Failed"," "+ e,
                    //       "cancelMethod1","okMethod1");
                    notificationHelper.createNotification("Upload Failed", "Please Try again");
                    nDialog.dismiss();

                }
                int uploadtime, netspeed, filesize = 0;
                int kilobytes = (jj[0] / 1024);
                int megabytes = (kilobytes / 1024);
              //  netspeed = uploadSpeed();
                // uploadtime = megabytes/netspeed;
                //   Log.d(TAG, " Upload time :::"  + uploadtime);
                Log.d(TAG, " Megabyte :::" + megabytes);
             //   Log.d(TAG, " netspeed :::" + netspeed);
//                nDialog.setMessage("Uploading Complete: Total Files " + (files_images.length +1) + " Of " +megabytes + " MB");

                notificationHelper.createNotification("Success","Upload Completed");


                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            else
            {
                //customDialog("Network Error","Please check Your Network Settings \n or Allow Data Connection",
                //       "cancelMethod1","okMethod1");
                notificationHelper.createNotification("Network Error","Please check Your Network Settings \n or Allow Data Connection");

                nDialog.dismiss();

               // dialog.dismiss();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {

 try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            nDialog.dismiss();

        }
    }

    public void customDialog(String title,String message,final String cancelMethod,final String okMethod)
    {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
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

//        Intent intent = new Intent(LoginActivity.this, settings.class);
//        intent.putExtra("from","Login");
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        startActivity(intent);


        return;
    }
    public void deleteRecursive(File fileOrDirectory) {

        try {

            if (fileOrDirectory.isDirectory()) {
                for (File child : fileOrDirectory.listFiles()) {
                    deleteRecursive(child);
                }
            }

            //Hii inafuta na directory

            fileOrDirectory.delete();
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Please Check All App Permissions and try Again", Toast.LENGTH_LONG).show();
        }
    }


}
