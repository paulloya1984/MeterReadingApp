package tz.co.ubunifusolutions.screens.activities.extras;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper_node;
import tz.co.ubunifusolutions.screens.uploads.Constants;

public class tatizo extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private static final String TAG = "Tatizo Activity";
    private static final int CAMERA_REQUEST = 123;
    Spinner spinerseverity;
    Button btnClear, btnSave, btnTakepicha,btntakefromgalary;
    TextView txtlat_fault, txtlong_fault, txtmtaa;
    EditText txtWhereee, txtSeverityy, txtCommentt;
    ImageView imageView;
    EditText txtImagepath;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private Uri filePath;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    /***storage permission code*/
    private static final int STORAGE_PERMISSION_CODE = 123;
    /**Location*/
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;

    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 150000;  /* 10 secs */
    private long FASTEST_INTERVAL = 200000; /* 2 sec */
    private LocationManager locationManager;

    /***Storage*/
    DatabaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tatizo);

        //Requesting storage permission
        requestStoragePermission();

        spinerseverity = (Spinner)findViewById(R.id.spinner_Severity);
        btnClear = (Button) findViewById(R.id.btnClear_tatizo);
        btnSave = (Button) findViewById(R.id.btnSave_Tatizo);
        btnTakepicha = (Button) findViewById(R.id.btnTakepichaa);
        txtImagepath = (EditText)findViewById(R.id.txtImagepath);
        txtlat_fault = (TextView) findViewById(R.id.txtLatitude_fault);
        txtlong_fault = (TextView) findViewById(R.id.txtLongitude_fault);
        btntakefromgalary = (Button) findViewById(R.id.btnfromGalary);
        txtmtaa = findViewById(R.id.txtMtaa);

        /**
         * Location Service Init
         */

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        checkLocation(); //check whether location service is enable or not in your  phone


        btntakefromgalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        txtCommentt = (EditText) findViewById(R.id.txtComment);
        txtWhereee = (EditText) findViewById(R.id.txtWhere);

        imageView  = (ImageView) findViewById(R.id.imageView_new_Reading);

        btnTakepicha.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
             dispatchTakePictureIntent();
        }
        catch (Exception e){
            Log.e(TAG,"An Error occured "+e.getMessage());
        }
            }
});
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder adb = new AlertDialog.Builder(tatizo.this);
                adb.setMessage("You are about to Cancel The Reading \n Are You Sure ?");
                adb.setTitle("Warning");
                adb.setIcon(R.drawable.ic_warning_black_24dp);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        txtWhereee.setText("");
                        txtCommentt.setText("");
                        spinerseverity.setSelection(0);
                        txtImagepath.setText("");
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                adb.show();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            saveData();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            imageView.setImageBitmap(photo);
        }


        //handling the image chooser activity result
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                String path = String.valueOf(filePath);
                path = path.substring(path.length() - 15);
              txtImagepath.setText(path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void saveData()
    {
        String where_ = txtWhereee.getText().toString().trim();
        String severity = spinerseverity.getItemAtPosition(spinerseverity.getSelectedItemPosition()).toString();
        String comment = txtCommentt.getText().toString().trim();
        //String areaz = spinnerArea.getItemAtPosition(spinnerArea.getSelectedItemPosition()).toString();

        if(where_.isEmpty()){
            txtWhereee.setError("General Area Of Fault is Required");
            txtWhereee.requestFocus();
            return;
        }
        else if(severity=="Select One"){
            Toast.makeText(tatizo.this, "Please Select How bad Is The Fault", Toast.LENGTH_LONG).show();
            spinerseverity.setSelection(0);
            spinerseverity.requestFocus();
            return;
        }else if(comment.isEmpty()){
            txtCommentt.setError("Add a comment");
            txtCommentt.requestFocus();
            return;

        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        final String date_created = sdf.format(cal.getTime());
        String reported_by = "Paul";
        String latitude = txtlat_fault.getText().toString().trim();
        String longitude = txtlat_fault.getText().toString().trim();
        String photo_url = String.valueOf(filePath);

        dataBaseHelper = new DatabaseHelper(tatizo.this);
Log.i(TAG,where_+severity+comment+photo_url+reported_by+date_created+latitude+longitude);
        if (dataBaseHelper.insertFault( where_,  severity,  comment,  photo_url,  reported_by,  date_created, latitude,  longitude))
        {
            Toast.makeText(tatizo.this, "Fault Saved Successfully  ", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(tatizo.this, "An Error Has Occurred", Toast.LENGTH_LONG).show();

        }
    }
    private File createImageFile() throws IOException
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    //method to show file chooser
    private void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

 public void uploadMultipart()
    {

        // Save kwanza sqlite details then upload baadae on sync
        /*  name,path na upload url na file(get file from )
        * */

        //getting name for the image
        String name = txtImagepath.getText().toString().trim();

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

        //method to get the file path from uri
    public String getPath(Uri uri)
    {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

        Toast.makeText(tatizo.this, ""+path, Toast.LENGTH_LONG).show();
        cursor.close();

        return path;

    }

    //Requesting permission
    private void requestStoragePermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

        //camera Permision

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO);

        //Location Permision

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, STORAGE_PERMISSION_CODE);


    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
       // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, CAMERA_REQUEST);
        startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(tatizo.this, "An Error Has Occoured while creating a file (Picture)", Toast.LENGTH_LONG).show();
            return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "tz.co.ubunifusolutions.screens.pictures",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                String ilipo = String.valueOf(photoURI);
                txtImagepath.setText(ilipo);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
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

            txtlat_fault.setText(String.valueOf(mLocation.getLatitude()));
            txtlong_fault.setText(String.valueOf(mLocation.getLongitude()));
            pataMtaa(mLocation.getLatitude(),mLocation.getLongitude());
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates()
    {
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
    public void onLocationChanged(Location location)
    {
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        txtlat_fault.setText(String.valueOf(location.getLatitude()));
        txtlong_fault.setText(String.valueOf(location.getLongitude() ));

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        pataMtaa(location.getLatitude(),location.getLongitude());
    }

    private boolean checkLocation()
    {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert()
    {
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

    private boolean isLocationEnabled()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }

    public void pataMtaa( Double lat,Double longi )
    {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
           // List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(txtlat_fault.getText().toString()), Double.parseDouble(txtlong_fault.getText().toString()), 1);
            List<Address> addresses = geocoder.getFromLocation(lat,longi, 1);

            if (addresses.size() >0) {
//                Address returnedAddress = addresses.get(0);
////                StringBuilder strReturnedAddress = new StringBuilder();
////                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
////                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("");
////                }
//                txtmtaa.setText(strReturnedAddress.toString());

               // String cityName = addresses.get(0).getAddressLine(0);
                String cityName = addresses.get(0).getLocality();
              //  String stateName = addresses.get(0).getAddressLine(1);
                String stateName = addresses.get(0).getPostalCode();
                String countryName = addresses.get(0).getCountryName();
                String mtaa = cityName + ", " + stateName + ", " + countryName ;
                 txtmtaa.setText(mtaa);
               // Toast.makeText(this, mtaa, Toast.LENGTH_SHORT).show();
            }
            else {
               txtmtaa.setText("No Address returned!");
            }
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            txtmtaa.setText("Can not get Local Address!" + e.getMessage());
        }

    }



    }

