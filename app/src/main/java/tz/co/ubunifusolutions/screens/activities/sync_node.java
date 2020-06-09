package tz.co.ubunifusolutions.screens.activities;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.api.HttpHandler;
import tz.co.ubunifusolutions.screens.api.RetrofitClient;
import tz.co.ubunifusolutions.screens.models.DefaultResponse;
import tz.co.ubunifusolutions.screens.notification.NotificationHelper;
import tz.co.ubunifusolutions.screens.storage.*;

public class sync_node extends AppCompatActivity {
    private static final String TAG = "Data Synchronizing";
    TextView textView, textViewProgress;
    public static TextView textView1;

    TextView txtResult;
    Button btnSyncLocal, btnSyncOnline, btnSom_WatejaWote, btnAreas;
    private ProgressBar progressBar;
    public static String progresstxt;
    String LocalIP = "";
    String id,bill_area,connection_number,current_reading,
            latitude,longitude,meter_number,meter_status,previous_reading,
            pushed,reading_date,reading_type,route,seq,updated_at,updated_by;


    NotificationHelper notificationHelper;
    DatabaseHelper dataBaseHelper;

    public ArrayList<HashMap<String, String>> CustomerList, AreaList;

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
                Toast.makeText(sync_node.this, "Data Retrieved Successfully" + LocalIP, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(sync_node.this, "Data Not Retrieved Successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(sync_node.this, "Error Has Occoured" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return LocalIP;
    }
    int pp=0;
    public void  getreadlog_data()
    {
        Log.i(TAG, "Get read log  " );

        try {

            //dataBaseHelper.settingDrop();
            Cursor res = dataBaseHelper.getreadlog();
            StringBuffer stringBuffer = new StringBuffer();

            if (res != null && res.getCount() > 0) {

                while (res.moveToNext()) {
                    // stringBuffer.append("ID " + res.getString(0) + "\n");
                    bill_area = res.getString(res.getColumnIndex("bill_area"));
                    connection_number = res.getString(res.getColumnIndex("connection_number"));
                    current_reading = res.getString(res.getColumnIndex("current_reading"));
                    latitude = res.getString(res.getColumnIndex("latitude"));
                    longitude = res.getString(res.getColumnIndex("longitude"));
                    meter_status = res.getString(res.getColumnIndex("meter_status"));
                    previous_reading = res.getString(res.getColumnIndex("previous_reading"));
                    pushed = res.getString(res.getColumnIndex("pushed"));
                    reading_date = res.getString(res.getColumnIndex("reading_date"));
                    reading_type = res.getString(res.getColumnIndex("reading_type"));
                    route = res.getString(res.getColumnIndex("route"));
                    seq = res.getString(res.getColumnIndex("seq"));
                    updated_at = res.getString(res.getColumnIndex("updated_by"));
                    updated_by = res.getString(res.getColumnIndex("updated_by"));

                    Log.i(TAG, "Log Data: Connection Number \n" + connection_number +
                            " Bill Area\n"+bill_area+
                           " Current Reading\n"+current_reading+
                            " Latitude\n"+latitude+
                            " Longitude\n"+longitude+
                            " Meter Number\n"+meter_number+
                            " Meter Status\n"+meter_status+
                            " Previous Reading\n"+previous_reading+
                            " Pushed\n"+pushed+
                            " Reading Date\n"+reading_date+
                            " Reading Type\n"+reading_type+
                            " Route\n"+route+
                            " Sequence\n"+seq+
                            " Updated at\n"+updated_at+
                            " Updated By\n"+updated_by+
                            "  Synced Successful, Total " + pp);

//                    Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().readlog(bill_area,connection_number,current_reading,latitude,longitude,meter_number,meter_status,previous_reading,pushed,reading_date,reading_type,route,seq,updated_at,updated_by);
//                    call.enqueue(new Callback<DefaultResponse>() {
//                        @Override
//                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//
//                            Log.i(TAG, "Read sent Successfully  " + connection_number + "Synced Successful, Total " + pp);
//
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<DefaultResponse> call, Throwable t) {
//                            Log.i(TAG, "Failed to Send Reading  " + connection_number + "Failed, Total "+pp);
//
//
//                        }
//                    });
                }
                // txtResult.setText(.toString());
                Toast.makeText(sync_node.this, "Data Retrieved Successfully" , Toast.LENGTH_SHORT).show();
                pp++;

            } else {
                Toast.makeText(sync_node.this, "Data Not Retrieved Successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(sync_node.this, "Error Has Occoured" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        dataBaseHelper = new DatabaseHelper(sync_node.this);
        notificationHelper = new NotificationHelper(sync_node.this);

        CustomerList = new ArrayList<>();
        AreaList = new ArrayList<>();

        textView = findViewById(R.id.textView);

        btnSyncLocal = (Button) findViewById(R.id.btnSyncLocal);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        /**Mwanzo wa Local Sync*/
        btnSyncLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setIndeterminate(true);
                progressBar.setVisibility(View.VISIBLE);
                LocalIP="";
                LocalIP = getLocalAddress();
               // Toast.makeText(sync.this, LocalIP, Toast.LENGTH_SHORT).show();
                textView.setText("Please Wait \n Downloading Data ....");
               // progressBar.setProgress(15);
                new getAllConnections().execute();
               // new getAllAreas().execute();
               //new getAllCustomers().execute();

               // progressBar.setVisibility(View.INVISIBLE);

            }

        });

/**
 * Mwisho wa local Sync
 *
 *
 * Mwanzo wa Sync Online*/
        btnSyncOnline = (Button)findViewById(R.id.btnSyncOnline);
        btnSyncOnline.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                getreadlog_data();
                /*Part One anza na kuvuta picha from SQLite
                * */
              //  vutaPicha();
//                Toast.makeText(sync.this, "Logic ya Upload", Toast.LENGTH_SHORT).show();
//                Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().readlog(id,bill_area,connection_number,current_reading,latitude,longitude,meter_number,meter_status,previous_reading,pushed,reading_date,reading_type,route,seq,updated_at,updated_by);
//                call.enqueue(new Callback<DefaultResponse>() {
//                    @Override
//                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
//
//                    }
//                });

            }
        });

        /**
         * Mwisho wa Online Sync*/

    }


    public boolean isNetworkIpo()
    {
        ConnectivityManager connectivityM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityM.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

    private void vutaPicha()
    {

        dataBaseHelper = new DatabaseHelper(sync_node.this);
        Cursor res = dataBaseHelper.getAllPichas();
        StringBuffer stringBuffer = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                String id = res.getString(0);
                String Picha_name = res.getString(1);
                String Picha_path = res.getString(2);

            }
            txtResult.setText(stringBuffer.toString());
            Toast.makeText(sync_node.this, "Data Retrieved Successfully", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(sync_node.this, "Data Not Retrieved Successfully", Toast.LENGTH_SHORT).show();
        }
    }


    private class getAllConnections extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(sync_node.this, "Json Data is downloading -- All Connections", Toast.LENGTH_LONG).show();

        }

        String connection_number;
        //   Toast.makeText(sync.this,"Json Data Background",Toast.LENGTH_LONG).show();


        @Override
        protected Void doInBackground(Void... arg0)
        {
             HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            /*
             * All Customers comes from connection Table
             * */

            String url = " http://" + LocalIP + "/server/public/allcustomers";
            String url1 = " http://" + LocalIP + "/readlog";
            String jsonStr = sh.makeServiceCall(url1);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null)
            {
                dataBaseHelper = new DatabaseHelper(sync_node.this);
                dataBaseHelper.ondoaConnection();
                try
                {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     // Getting JSON Array node
                    JSONArray customersJArray = jsonObj.getJSONArray("customers");
                    // looping through All Customers
                    for (int i = 0; i < customersJArray.length(); i++) {
                        JSONObject c = customersJArray.getJSONObject(i);
                        connection_number = c.getString("connection_number");
                        String meter_number = c.getString("meter_number");
                        String meter_status = c.getString("meter_status");
                        String previous_date = c.getString("previous_date");
                        String reading_date = c.getString("reading_date");
                        String current_reading = c.getString("current_reading");
                        String previous_reading = c.getString("previous_reading");
                        String reading_type = c.getString("reading_type");//reading_type_id
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
                        String pushed = c.getString("metered");
                      //  String updated_at = c.getString("updated_at");//haipo
                        String updated_by = c.getString("updated_by");
                        String date_updated = c.getString("date_updated");
                        String sales_assistant_id3 = c.getString("sales_assistant_id3");
                        String longitude = c.getString("longitude");
                        String latitude = c.getString("latitude");
                        String elevation = c.getString("elevation");
                        String disconnected = c.getString("disconnected");
                        String first_name = c.getString("first_name");
                        String middle_name = c.getString("middle_name");
                        String last_name = c.getString("last_name");
                        String mobile_number = c.getString("mobile_number");
                        String telephone_number = c.getString("telephone_number");
                        String work_phone_number = c.getString("work_phone_number");
                        String billing_area = c.getString("billing_area");
                        String u_first_name = c.getString("u_first_name");
                        String u_last_name = c.getString("u_last_name");
                        String username = c.getString("username");

                        /**
                         * Ongeza
                         *  "elevation": null,
                         *  "first_name": "EMANUEL",
                         *         "middle_name": " EZEKIELI",
                         *         "last_name": " MREMA",
                         *         "mobile_number": "",
                         *         "telephone_number": "",
                         *         "work_phone_number": "255689445795",
                         *         "billing_area": "BGR",
                         *         "u_first_name": null,
                         *         "u_last_name": null,
                         *         "username": null
                         * */

                        // tmp hash map for single contact
                        HashMap<String, String> customerList = new HashMap<>();

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
                        customerList.put("daily_average", daily_average);
                        customerList.put("no_days", no_days);
                        customerList.put("average_consumption", average_consumption);
                        customerList.put("month", month);
                        customerList.put("zone", zone);
                        customerList.put("route", route);
                        customerList.put("seq", seq);
                        customerList.put("done", done);
                        customerList.put("metered", metered);
                        customerList.put("pushed", pushed);
                      //  customerList.put("updated_at", updated_at);
                        customerList.put("updated_by", updated_by);
                        customerList.put("date_updated", date_updated);
                        customerList.put("sales_assistant_id3", sales_assistant_id3);
                        customerList.put("longitude", longitude);
                        customerList.put("latitude", latitude);
                        customerList.put("elevation",elevation);
                        customerList.put("disconnected", disconnected);
                        customerList.put("first_name", first_name);
                        customerList.put("middle_name", middle_name);
                        customerList.put("last_name", last_name);
                        customerList.put("mobile_number", mobile_number);
                        customerList.put("telephone_number", telephone_number);
                        customerList.put("work_phone_number", work_phone_number);
                        customerList.put("billing_area", billing_area);
                        customerList.put("u_first_name", u_first_name);
                        customerList.put("u_last_name", u_last_name);
                        customerList.put("username", username);

                       // customerList.put("disconnected", disconnected);

                        /**
                         * Ongeza
                         *  "elevation": null,
                         *  "first_name": "EMANUEL",
                         *         "middle_name": " EZEKIELI",
                         *         "last_name": " MREMA",
                         *         "mobile_number": "",
                         *         "telephone_number": "",
                         *         "work_phone_number": "255689445795",
                         *         "billing_area": "BGR",
                         *         "u_first_name": null,
                         *         "u_last_name": null,
                         *         "username": null
                         * */

                        // adding contact to contact list
                        CustomerList.add(customerList);



//                       if (dataBaseHelper.insertTblConnection(connection_number, meter_number, meter_status, previous_date, reading_date,
//                                current_reading, previous_reading, reading_type, current_consumption,
//                                previous_consumption, daily_average, no_days, average_consumption, month, zone, route,
//                                seq, done, metered, updated_by, date_updated, sales_assistant_id3,
//                                longitude, latitude,elevation, disconnected,first_name,middle_name,last_name,mobile_number,telephone_number,work_phone_number
//                       ,billing_area,u_first_name,u_last_name,username)) {
//
//                            Log.i(TAG, "Customer meter mnumber  " + connection_number + "Synced Successful, Total " + i);
//                        } else {
//                            Log.e(TAG, "All Customes Error at " + i);
//                        }
                    }

                } catch (final JSONException e) {

                }

            }
            else {
                Log.e(TAG, "Couldn't get json from server.");
                String msg = "Couldn't get json from server.";

                notificationHelper.createNotification("Data Synchronizing Error","Couldn't get Data from Server, Please Check if server can be reached");

                cancel(true);
            }
                Log.i(TAG, "Done Loading Customers.");
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
            Toast.makeText(sync_node.this, "Json Area Data is downloading", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Json Area Data Downloading -- All Areas");
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
           // String All_allArea_URL = " http://" + LocalIP + "/server/public/allareas";
            String All_allArea_URL = " http://" + LocalIP + "/areas";

            String jsonStr_Area = sh_Area.makeServiceCall(All_allArea_URL);
            Log.e(TAG, "Response from url: " + jsonStr_Area);
            //  Toast.makeText(sync.this, "Response from url: " + jsonStr, Toast.LENGTH_SHORT).show();
            if (jsonStr_Area != null) {
                dataBaseHelper = new DatabaseHelper(sync_node.this);
                dataBaseHelper.ondoaArea();
                 try {
                    JSONObject jsonObj_Area = new JSONObject(jsonStr_Area);

                    // Getting JSON Array node
                    JSONArray areaJArray = jsonObj_Area.getJSONArray("areas");

                    for (int i = 0; i < areaJArray.length(); i++) {
                        JSONObject c = areaJArray.getJSONObject(i);
                        String area_id = c.getString("area_id");
                        String area_code = c.getString("area_code");
                        String Area = c.getString("Area");
                        String zone_id = c.getString("zone_id");
                        String created_by = c.getString("created_by");
                        String date_created = c.getString("date_created");
                        String date_updated = c.getString("date_updated");
                        String updated_at = c.getString("updated_at");
                        String updated_by = c.getString("updated_by");

                        /*
                        *"area_id": 2,
        "area_code": "MRA",
        "Area": "MRARA",
        "zone_id": "A",
        "created_by": null,
        "date_created": "2014-07-14T00:00:00.000Z",
        "time_created": "1970-01-01T11:37:10.920Z",
        "updated_by": null,
        "date_updated": "2014-07-14T00:00:00.000Z",
        "time_updated": "1970-01-01T11:37:10.920Z"
                        * */
                        // tmp hash map for single contact
                        HashMap<String, String> areaListHash = new HashMap<>();

                        // adding each child node to HashMap key => value
                        areaListHash.put("area_id", area_id);
                        areaListHash.put("area_code", area_code);
                        areaListHash.put("Area", Area);
                        areaListHash.put("zone_id", zone_id);
                        areaListHash.put("created_by", created_by);
                        areaListHash.put("date_created", date_created);
                        areaListHash.put("date_updated", date_updated);
                        areaListHash.put("updated_at", updated_at);
                        areaListHash.put("updated_by", updated_by);
                        AreaList.add(areaListHash);
                        dataBaseHelper = new DatabaseHelper(sync_node.this);
//                        if (dataBaseHelper.insertAreas(area_id, area_code, Area, zone_id, created_by, date_created, date_updated, updated_at, updated_by)) {
//
//                            Log.i(TAG, "Area Code   " + area_code + " Synced Successful, Total " + i);
//
//                        } else {
//                            Log.e(TAG, "All Area Error at " + i);
//
//                        }
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

            Log.i(TAG, "Done Loading Area.");
              dataBaseHelper.close();
              /**
             * All Areas mwisho hapa*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            textView.setText("Please Wait \n Downloading Data \n Customer Connections Done \n Area Details Done .... ");


        }
    }

    private class getAllCustomers extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(sync_node.this, "Json Data is downloading -- Customer ", Toast.LENGTH_LONG).show();
            textView.setText("Please Wait \n Downloading Data \n Customer Connections Done \n Area Details Done .... " +
                    "\n Downloading -- Customer");

        }

        @ Override
        protected Void doInBackground(Void... voids) {
/**All Customers from customers table
 * */
                HttpHandler sh_Customers = new HttpHandler();

                // Making a request to url and getting response
                // All Customers comes from connection Table

                String url_Customer = " http://" + LocalIP + "/server/public/customerdetails";
            String url_Customer1 = " http://" + LocalIP + "/customer";
                String jsonStr_Customers = sh_Customers.makeServiceCall(url_Customer);
                Log.e(TAG, "Response from url: " + jsonStr_Customers);
                if (jsonStr_Customers != null)
                {
                    dataBaseHelper = new DatabaseHelper(sync_node.this);
                    dataBaseHelper.ondoaCustomers();

                    try
                    {
                        JSONObject jsonObj = new JSONObject(jsonStr_Customers);

                        // Getting JSON Array node
                        JSONArray customers_JArray = jsonObj.getJSONArray("customers_Details");
                        // looping through All Customers
                        for (int i = 0; i < customers_JArray.length(); i++) {
                            JSONObject c = customers_JArray.getJSONObject(i);
                            String customer_connection = c.getString("customer_connection");
                            String first_name = c.getString("first_name");
                            String middle_name = c.getString("middle_name");
                            String last_name = c.getString("last_name");
                            String mobile_number = c.getString("mobile_number");
                            String telephone_number = c.getString("telephone_number");
                            String work_phone = c.getString("work_phone");
                            String bill_area = c.getString("bill_area");
                            String updated_by = c.getString("updated_by");
                            String date_created = c.getString("date_created");
                            String date_updated = c.getString("date_updated");

                            // tmp hash map for single contact
                            HashMap<String, String> customerList = new HashMap<>();

                            // adding each child node to HashMap key => value
                            customerList.put("customer_connection", customer_connection);
                            customerList.put("first_name", first_name);
                            customerList.put("middle_name", middle_name);
                            customerList.put("last_name", last_name);
                            customerList.put("mobile_number", mobile_number);
                            customerList.put("telephone_number", telephone_number);
                            customerList.put("work_phone", work_phone);
                            customerList.put("bill_area", bill_area);
                            customerList.put("updated_by", updated_by);
                            customerList.put("date_created", date_created);
                            customerList.put("date_updated", date_updated);

                            // adding Customer Details to details list
                            CustomerList.add(customerList);

                            if (dataBaseHelper.insertCustomerDetails(customer_connection, first_name, middle_name, last_name, mobile_number, telephone_number, work_phone, bill_area, updated_by, date_created, date_updated)) {

                                Log.i(TAG, "Customer Details Successful " + first_name + "Synced Successful, Total " + i);
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

            textView.setText("Please Wait \n Downloading Data \n Customer Connections Done \n Area Details Done \n Done Getting All Customers Details ");

            progressBar.setVisibility(View.GONE);
            notificationHelper.createNotification("Data Synchronizing","Data Synchronizing Completed Successful");

            // notificationHelper.createNotification("Data Synchronizing","Data Synchronizing Completed Successful");

  }

    }

}
