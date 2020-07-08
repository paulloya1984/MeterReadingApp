package tz.co.ubunifusolutions.screens.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.about.about_Bawasa;
import tz.co.ubunifusolutions.screens.activities.extras.tatizo;
import tz.co.ubunifusolutions.screens.notification.myDialog_readingPhoto;
import tz.co.ubunifusolutions.screens.session.IpAddress;
import tz.co.ubunifusolutions.screens.session.area_sessioin;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper_node;

public class new_Reading extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, TextWatcher {
    private static final String TAG = "New Reading ";
    private static final int CAMERA_REQUEST = 123;
    static final int REQUEST_TAKE_PHOTO = 1;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath, filePath1;
    private static final String DNAME = "meter_app";
    ImageView imageView;
    String mCurrentPhotoPath;
    //   DatabaseManager db;
    DatabaseHelper dataBaseHelper;
    GoogleMap mMap;
    MapView mMapView;
    TextView txtMeterNumber, txtConnectionNumber, txtArea, txtPreviousRead, txtCurrentReading, txtCurrConsumptin, txtnoofDays, txtJinalamteja, txtRoute, txtSeq;
    // TextInputEditText txtCurrentReading;
    // txtCurrConsumptin,txtnoofDays;
    Button btnOk, btnCancel, btnTakePhoto;
    ImageButton btnZoom;
    Spinner spinnerStutus;
    // For getting Are Name
    String bill_area = "";
    /*
     * Shared Prefrenc*/
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Preference-Global";

    area_sessioin area_sessioin;//global variable
    /*
     * Mwisho wa shared*/
    //GPSTracker class
    public Location mLastKnownLocation;

    private TextView mLatitudeTextView;
    private TextView mAltitudeTextView;
    private TextView mLongitudeTextView;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;

    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 150000;  /* 10 secs */
    private long FASTEST_INTERVAL = 200000; /* 2 sec */
    private LocationManager locationManager;

    String meternumber;
    String currentreading;
    String connectionnumber;
    String previous_read;
    String areaz;
    String status;
    String lat;
    String longi;
    String route, seq;


    String name, area_code;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__reading);

// getting data from previos intnent

        Intent i = getIntent();
        name = i.getStringExtra("item").trim();

       // sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //  Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        Context cntx = this;
        area_sessioin = new area_sessioin(cntx); //in oncreate

        /**
         * Location Service Init
         */

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        checkLocation(); //check whether location service is enable or not in your  phone

        /**
         * Anzisha Inputs
         * */
        txtConnectionNumber = findViewById(R.id.txtConnectionNumber);
        txtConnectionNumber.setEnabled(false);
        txtMeterNumber = findViewById(R.id.txtMeterNumber_newread);
        txtMeterNumber.setEnabled(false);
        txtArea = findViewById(R.id.txtArea);
        txtArea.setEnabled(false);
       // txtArea.setVisibility(View.INVISIBLE);
        txtPreviousRead = findViewById(R.id.txtPreviousRead_newread);
        txtPreviousRead.setEnabled(false);
        mLatitudeTextView = findViewById(R.id.mLatitudeTextView);
        mLatitudeTextView.setEnabled(false);
        mLongitudeTextView = findViewById(R.id.mLongitudeTextView);
        mLongitudeTextView.setEnabled(false);
        mAltitudeTextView = findViewById(R.id.mAltitudeTextView);
        mAltitudeTextView.setEnabled(false);
        txtCurrConsumptin = findViewById(R.id.txtCurrConsumption);
        txtCurrConsumptin.setEnabled(false);
        txtCurrConsumptin.setVisibility(View.INVISIBLE);
        txtnoofDays = findViewById(R.id.txtno0fDays);
        txtJinalamteja = findViewById(R.id.txtName);
        txtJinalamteja.setEnabled(false);
        txtRoute = findViewById(R.id.txtRouteN);
        txtRoute.setEnabled(false);
        txtSeq = findViewById(R.id.txtSeqN);
        txtSeq.setEnabled(false);

        imageView = (ImageView) findViewById(R.id.imageView_new_Reading);
        //imageView.setVisibility(View.INVISIBLE);
        btnZoom = findViewById(R.id.btnZoom);
        //btnZoom.setVisibility(View.INVISIBLE);


        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        txtCurrentReading = findViewById(R.id.txtCurrentReading);
//        spinnerArea = findViewById(R.id.spinnerArea);
        spinnerStutus = findViewById(R.id.spinnerReadingStutus);
        spinnerStutus.setSelection(7);

        txtCurrentReading.addTextChangedListener(this);

        btnCancel = findViewById(R.id.btnClear);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customDialog("WARNING", "You are about to Cancel The Reading \n Are You Sure ?", "cancelMethod1", "okMethod1");
            }
        });


        btnOk = findViewById(R.id.btnOKK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                meternumber = "" + txtMeterNumber.getText();
                currentreading = "" + txtCurrentReading.getText();
                connectionnumber = "" + txtConnectionNumber.getText();
                previous_read = "" + txtPreviousRead.getText();
                // areaz = spinnerArea.getItemAtPosition(spinnerArea.getSelectedItemPosition()).toString();
                areaz = "" + txtArea.getText();
                status = spinnerStutus.getItemAtPosition(spinnerStutus.getSelectedItemPosition()).toString();
                lat = "" + mLatitudeTextView.getText();
                longi = "" + mLongitudeTextView.getText();
                route = "" + txtRoute.getText();
                seq = "" + txtSeq.getText();

                if (currentreading.length() == 0) {
                    txtCurrentReading.setError("Current Reading is Required");
                    txtCurrentReading.requestFocus();
                    return;
                }
/*
 String cur = "" + txtCurrentReading.getText();
        if (cur.length() == 0) {
            txtCurrConsumptin.setText("");
            return;
        } else {
            double currreading = Double.parseDouble(cur);
            String pre = "" + txtPreviousRead.getText();
            double previous = Double.parseDouble(pre);
            double cons = currreading - previous;
            String qqq = String.valueOf(cons);
            txtCurrConsumptin.setText(qqq
* */
               Double curreading = Double.parseDouble("" + txtCurrentReading.getText());
               Double prrevreading = Double.parseDouble("" + txtPreviousRead.getText());

                // String tmp = (String) txtCurrConsumptin.getText();

                if ((curreading - prrevreading) == 0) {
                    customDialog("Hamna Matumizi", "Usomaji Una Matumizi Sifuri (0)  \n Endelea ?", "cancelMethod1", "okMethod_Zero");
                }
               else if ((curreading - prrevreading) < 0) {
                    customDialog("Usomaji Umekosewa", "Usomaji Sio Sahihi - Umerudi Nyuma \n Hakiki ", "cancelMethod1", "okMethod2");
                }

               else if ((curreading - prrevreading) > 0){
//pick from shared pref ya login
                    // Kumbuka from Ok method zero

                    try {

                        dispatchTakePictureIntent();



                    } catch (Exception e) {
                        Log.e(TAG, "An Error occured " + e.getMessage());
                        Toast.makeText(new_Reading.this, "An Error occured " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

        /**
         * Pull Meter Details
         * */
        getCustomerDetails(name);


        btnTakePhoto = findViewById(R.id.btnTakepichaa2);
        //btnTakePhoto.setVisibility(View.INVISIBLE);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    dispatchTakePictureIntent();
                } catch (Exception e) {
                    Log.e(TAG, "An Error occured " + e.getMessage());
                }
            }

        });
        btnZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showphoto_Dialog();
            }
        });
    }

    // Kupiga Picha

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//new File(Environment.get);
            // Uri uriSavedImage=Uri.fromFile(new File(Environment.getExternalStorageDirectory(), DNAME));
            //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            //Log.e(TAG, "dispatchTakePictureIntent: Location of image"+uriSavedImage );
            Calendar cal = Calendar.getInstance();
            // System.out.println(new SimpleDateFormat("MMM").format(cal.getTime()));
            String timeStamp = txtConnectionNumber.getText() + "-" + new SimpleDateFormat("MMM").format(cal.getTime());

//folder stuff
            File imagesFolder = new File(Environment.getExternalStorageDirectory(), DNAME + "/Images");
            imagesFolder.mkdirs();

            File image = new File(imagesFolder, timeStamp + ".png");
            Uri imageUri = FileProvider.getUriForFile(
                    new_Reading.this,
                    "tz.co.ubunifusolutions.screens.pictures", //(use your app signature + ".provider" )
                    image);
            Uri uriSavedImage = Uri.fromFile(image);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
           // imageView.setImageResource(uriSavedImage);
        }
    }

    String currentPhotoPath;

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        galleryAddPic();
    }

    private File createImageFile() throws IOException {

       long unixTime = System.currentTimeMillis() / 1000L;
        String imageFileName = "JPEG_" + unixTime + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), DNAME);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void getCustomerDetails(String MeterNumber)
    {

        String mNumber = MeterNumber;
        // Toast.makeText(this, mNumber, Toast.LENGTH_SHORT).show();
        txtMeterNumber.setText(mNumber);
        dataBaseHelper = new DatabaseHelper(new_Reading.this);
        Cursor cursor = dataBaseHelper.getDetils(mNumber);
        String connum;
        if (cursor.moveToFirst()) {
            do {

                connum = cursor.getString(cursor.getColumnIndex("connection_number"));
                String prevread = cursor.getString(cursor.getColumnIndex("previous_reading"));
                String route = cursor.getString(cursor.getColumnIndex("route"));
                String seq = cursor.getString(cursor.getColumnIndex("seq"));


                txtConnectionNumber.setText(connum);
                txtPreviousRead.setText(prevread);
                txtSeq.setText(seq);
                txtRoute.setText(route);
                // txtArea.setText("Weka Eneo Hapa");
                mLatitudeTextView.setText(cursor.getString(cursor.getColumnIndex("latitude")));
                mLongitudeTextView.setText(cursor.getString(cursor.getColumnIndex("longitude")));

            } while (cursor.moveToNext());
            cursor.close();
            getCustomerName(connum);
            getAreaDetails(connum);

        }

        // getAreaDetails(connum);
    }

    public void getAreaDetails(String connectionnumber) {
        bill_area = "";
        Log.e(TAG, "getAreaDetails: cursor ya kwanza --- passed conn " + connectionnumber);
        dataBaseHelper = new DatabaseHelper(new_Reading.this);
        Cursor cursor2 = dataBaseHelper.getArea_Name(connectionnumber);
        if (cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                try {
                    String con = cursor2.getString(cursor2.getColumnIndexOrThrow("bill_area"));
                    bill_area = "" + con;
                    Toast.makeText(this, bill_area, Toast.LENGTH_SHORT).show();

             } catch (IllegalArgumentException e) {
                    Log.e(TAG, "getAreaDetails: " + e.getMessage());
                }
            }
            Log.e(TAG, "getAreaDetails: Bill " + bill_area);
            txtArea.setText(bill_area);
         //   getAreaNameHalisi(bill_area);
        } else {
            Log.e(TAG, "getAreaDetails: Bill - Error");
        }


    }


    public String getAreaNameHalisi(String Areacode) {
        String key = Areacode;
        dataBaseHelper = new DatabaseHelper(new_Reading.this);
        Cursor cursor1 = dataBaseHelper.getArea_Namehalisi(key);
        Log.e(TAG, "getAreaDetails: cursor init --- " + key);
        String Area_name = null;
        if (cursor1 != null && cursor1.getCount() > 0) {
            while (cursor1.moveToNext()) {
                Area_name = cursor1.getString(cursor1.getColumnIndex("Area"));
                //  cursor1.getString(cursor1.getColumnIndex("first_name"));
              //  Toast.makeText(this, "Area name" + Area_name, Toast.LENGTH_SHORT).show();
                txtArea.setText(Area_name);
                Log.e(TAG, "getAreaNameHalisi: Area name" + Area_name );
            }
        }
        else{

            Area_name = cursor1.getString(cursor1.getColumnIndex("Area"));
            //  cursor1.getString(cursor1.getColumnIndex("first_name"));
            //  Toast.makeText(this, "Area name" + Area_name, Toast.LENGTH_SHORT).show();
            txtArea.setText(Area_name);
            Log.e(TAG, "getAreaNameHalisi: Area name" + Area_name );
            Log.e(TAG, "getAreaNameHalisi: Error part" );
        }
//        if (cursor1.moveToFirst()) {
//            do {
//                Area_name = cursor1.getString(cursor1.getColumnIndex("Area"));
//              //  cursor1.getString(cursor1.getColumnIndex("first_name"));
//              //  Toast.makeText(this, "Area name" + Area_name, Toast.LENGTH_SHORT).show();
//                txtArea.setText(Area_name);
//
//            } while (cursor1.moveToNext());
//        }

//        if (true) {
//
//            while (cursor1.moveToNext()) {
//              Area_name = cursor1.getString(cursor1.getColumnIndex("Area"));
//              txtArea.setText(Area_name);
//
//            }
//
//        } else {
//            Log.e(TAG, "getAreaNameHalisi: else part cursor is null");
//        }
        cursor1.close();
        return Area_name;
    }

    String Key_Duplicate;

    public boolean checkDublicate(String connectionNumber) {
        Key_Duplicate = connectionNumber;
        dataBaseHelper = new DatabaseHelper(new_Reading.this);
        Cursor cursor = dataBaseHelper.checkDuplicates(Key_Duplicate);
        if (cursor.moveToFirst())
            return true;
        else
            return false;
    }

    public void getCustomerName(String connection_number) {

        String mNumber = connection_number;

        dataBaseHelper = new DatabaseHelper(new_Reading.this);
        Cursor cursor = dataBaseHelper.getAllCustomers_ByConnectionNumber(mNumber);
        if (cursor.moveToFirst()) {
            do {
                //meterList.add(res.getString(2));

                String jinalakwanza = cursor.getString(cursor.getColumnIndex("first_name"));
                String jinalakati = cursor.getString(cursor.getColumnIndex("middle_name"));
                String jinalamwisho = cursor.getString(cursor.getColumnIndex("last_name"));
                //  String area = cursor.getString(cursor.getColumnIndex("bill_area"));

                String Name = jinalakwanza + " " + jinalakati + " " + jinalamwisho;
                txtJinalamteja.setText(Name);
                // Toast.makeText(this, "Area new read"+area, Toast.LENGTH_LONG).show();
                // txtArea.setText(area);

                /**
                 * weka spinner selection hapa ili Area ioneka during load
                 * check
                 * dynamic spinner load*/


            } while (cursor.moveToNext());
        }


    }

    public void cancelMethod1() {
        return;

    }

    public void cancelMethod_Dublicate() {
        //  Toast.makeText(this, "From Cancel dublicate method \n work on it", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(new_Reading.this, read_Search.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        // return;
    }

    public void okMethod_Dublicate() {

        //  Toast.makeText(this, "From OK dublicate method  \n(Remove dublicate dbhelper) work on it", Toast.LENGTH_SHORT).show();
        dataBaseHelper = new DatabaseHelper(new_Reading.this);
        dataBaseHelper.OndoaDublicates(Key_Duplicate);
        String updated_by = String.valueOf(1);

        dataBaseHelper = new DatabaseHelper((new_Reading.this));

        if (dataBaseHelper.insertNewReading(areaz, connectionnumber, currentreading, lat, longi, meternumber, previous_read, updated_by, status, route, seq)) {
            Toast.makeText(new_Reading.this, "Reading Saved Successfully \n For Meter Number " + meternumber, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(new_Reading.this, read_Search.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else {
            Toast.makeText(new_Reading.this, "An Error Has Occoured", Toast.LENGTH_LONG).show();

        }


        return;
    }

    public void okMethod2() {

         new_Reading.super.onBackPressed();

    }
    public void okMethod_negative() {

     new_Reading.super.onBackPressed();

    }
    public void okMethod_Zero()
    {
        try {

            dispatchTakePictureIntent();

 //           String updated_by = String.valueOf(1);

//            dataBaseHelper = new DatabaseHelper((new_Reading.this));
//
//            if (dataBaseHelper.insertNewReading(areaz, connectionnumber, currentreading, lat, longi, meternumber, previous_read, updated_by, status, route, seq))
//            {
//                Toast.makeText(new_Reading.this, "Reading Saved Successfully \n For Meter Number " + meternumber, Toast.LENGTH_SHORT).show();
//
//                new_Reading.super.onBackPressed();
//
//            } else {
//                Toast.makeText(new_Reading.this, "An Error Has Occoured", Toast.LENGTH_LONG).show();

        //    }

        } catch (Exception e) {
            Log.e(TAG, "An Error occured " + e.getMessage());
            Toast.makeText(new_Reading.this, "An Error occured " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    public void okMethod1() {

        //  Toast.makeText(Nyumbani.this, "Signed off", Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();*/
        // Toast.makeText(Nyumbani.this, "", Toast.LENGTH_SHORT).show();
        txtCurrentReading.setText("");
        txtMeterNumber.setText("");
        txtConnectionNumber.setText("");
        // txtArea.setText("");


    }

    public void customDialog(String title, String message, final String cancelMethod, final String okMethod) {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(new_Reading.this);
        builderSingle.setTitle(title);
        builderSingle.setIcon(R.drawable.ic_warning_black_24dp);
        builderSingle.setMessage(message);
        builderSingle.setNegativeButton(
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cancelMethod.equals("cancelMethod1")) {
                            cancelMethod1();
                        } else if (cancelMethod.equals("cancelMethod_Dublicate")) {
                            cancelMethod_Dublicate();
                        }

                    }
                }
        );
        builderSingle.setPositiveButton(
                "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (okMethod.equals("okMethod1")) {
                            okMethod1();
                        } else if (okMethod.equals("okMethod_Dublicate")) {
                            okMethod_Dublicate();
                        }
                        else if (okMethod.equals("okMethod_Zero")){
                            okMethod_Zero();
                        }
                        else if (okMethod.equals("okMethod_Zero")){
                            okMethod_Zero();
                        }
                    }
                }
        );
        builderSingle.show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation == null) {
            startLocationUpdates();
        }
        if (mLocation != null) {

            mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
            mAltitudeTextView.setText(String.valueOf(mLocation.getAltitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }


    @Override
    public void onLocationChanged(Location location) {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
        mLongitudeTextView.setText(String.valueOf(location.getLongitude()));
        //   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nyumbani, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Toast.makeText(new_Reading.this, "Action Menu hapa", Toast.LENGTH_SHORT).show();
//        }
//        else
        if (id == R.id.about_Ubunifu) {
            Toast.makeText(new_Reading.this, "About Ubunifu  hapa", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.about_App) {
            Intent intent = new Intent(new_Reading.this, about_Bawasa.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String cur = "" + txtCurrentReading.getText();
        if (cur.length() == 0) {
            txtCurrConsumptin.setText("");
            return;
        } else {
            double currreading = Double.parseDouble(cur);
            String pre = "" + txtPreviousRead.getText();
            double previous = Double.parseDouble(pre);
            double cons = currreading - previous;
            String qqq = String.valueOf(cons);
            txtCurrConsumptin.setText(qqq);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
          //  Bundle extras = data.getExtras();
          //  Bitmap photo = (Bitmap) data.getExtras().get("data");

           // filePath1 = data.getData();
          //  Log.e(TAG, "onActivityResult: Path ya image" + filePath1 );
           //  imageView.setImageBitmap(photo);


            String updated_by = String.valueOf(1);

            dataBaseHelper = new DatabaseHelper((new_Reading.this));

            if (dataBaseHelper.insertNewReading(areaz, connectionnumber, currentreading, lat, longi, meternumber, previous_read, updated_by, status, route, seq))
            {
               // Toast.makeText(new_Reading.this, "Reading Saved Successfully \n For Meter Number " + meternumber, Toast.LENGTH_SHORT).show();

                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);

                //inflate view
                View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
                ((TextView) custom_view.findViewById(R.id.message)).setText("Reading Saved \n For Meter Number \n " + meternumber);
                ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_done);
                ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.green_500));

                toast.setView(custom_view);
                toast.show();

                new_Reading.super.onBackPressed();

            } else {
               // Toast.makeText(new_Reading.this, "An Error Has Occoured", Toast.LENGTH_LONG).show();

                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);

                //inflate view
                View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
                ((TextView) custom_view.findViewById(R.id.message)).setText("An Error Has Occoured");
                ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
                ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.red_600));

                toast.setView(custom_view);
                toast.show();

            }



        }


        //handling the image chooser activity result
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                String path = String.valueOf(filePath);
                path = path.substring(path.length() - 15);
                // txtImagepath.setText(path);//hamna nafasi

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void showphoto_Dialog() {
        Bundle args = new Bundle();
        String pathyapicha = String.valueOf(filePath);
//                args.putString("conn_number", adapter.getItem(position));
//                args.putString("data", readingmoja);
        args.putString("path", pathyapicha);
        myDialog_readingPhoto mydialog = new myDialog_readingPhoto();

        mydialog.setArguments(args);
        mydialog.show(getSupportFragmentManager(), "Current Reading Photo");
    }


}
