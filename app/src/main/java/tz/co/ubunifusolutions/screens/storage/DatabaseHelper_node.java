package tz.co.ubunifusolutions.screens.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DatabaseHelper_node extends SQLiteOpenHelper {
    private static final String TAG = "Database Manager";
    private static final String DATABASE_NAME = "BawasaAppDatabase";
    // private static final  String DATABASE_NAME = "mydatabase";
    private static final int DATABASE_VERSION = 3;


    //TABLE CONNECTION
    private static final String TABLE_NAME_CONNECTION = "tbl_connection";
    private static final String KEY_ID = "id";
    private static final String COLUMN_CONNECTION_NUMBER = "connection_number";
    private static final String COLUMN_METER_NUMBER = "meter_number";
    private static final String COLUMN_METER_STATUS = "meter_status";
    private static final String COLUMN_PREVIOUS_DATE = "previous_date";
    private static final String COLUMN_READING_DATE = "reading_date";
    private static final String COLUMN_CURRENT_READING = "current_reading";
    private static final String COLUMN_PREVIOUS_READING = "previous_reading";
    private static final String COLUMN_READING_TYPE = "reading_type";
    private static final String COLUMN_CURRENT_CONSUMPTION = "current_consumption";
    private static final String COLUMN_PREVIOUS_CONSUMPTION = "previous_consumption";
    private static final String COLUMN_DAILY_AVERAGE = "daily_average";
    private static final String COLUMN_NO_DAYS = "no_days";
    private static final String COLUMN_AVARAGE_CONSYMPTION = "average_consumption";
    private static final String COLUMN_MONTH = "month";
    private static final String COLUMN_ZONE = "zone";
    private static final String COLUMN_ROUTE = "route";
    private static final String COLUMN_SEQ = "seq";
    private static final String COLUMN_DONE = "done";
    private static final String COLUMN_PUSHED = "pushed"; // not used in conn table
    private static final String COLUMN_METERED = "metered ";
   private static final String COLUMN_UPDATED_AT = "updated_at";// not used in con table
    private static final String COLUMN_UPDATED_BY = "updated_by";
    private static final String COLUMN_DATE_UPDATED = "date_updated";
    private static final String COLUMN_SALES_ASSISTANT_ID3 = "sales_assistant_id3";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_ELEVATION = "elevation";
    private static final String COLUMN_DISCONNECTED = "disconnected";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_MIDDLE_NAME = "middle_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_MOBILE_NUMBER = "mobile_number";
    private static final String COLUMN_TELEPHONE_NUMBER = "telephone_number";
    private static final String COLUMN_WORK_PHONE_NUMBER = "work_phone_number";
    private static final String COLUMN_BILLING_AREA = "billing_area";
    private static final String COLUMN_U_FIRST_NAME = "u_first_name";
    private static final String COLUMN_U_LAST_NAME = "u_last_name";
    private static final String COLUMN_USERNAME = "username";



/**
 *
 * "area_id": 2,
 *         "area_code": "MRA",
 *         "Area": "MRARA",
 *         "zone_id": "A",
 *         "created_by": null,
 *         "date_created": "2014-07-14T00:00:00.000Z",
 *         "time_created": "1970-01-01T11:37:10.920Z",
 *         "updated_by": null,
 *         "date_updated": "2014-07-14T00:00:00.000Z",
 *         "time_updated": "1970-01-01T11:37:10.920Z"*/
    // TABLE AREAS
    private static final String KEY_ID_A = "id";
    private static final String TABLE_NAME_AREAS = "tbl_areas";
    private static final String COLUMN_AREA_ID = "area_id";
    private static final String COLUMN_AREA_CODE = "area_code";
    private static final String COLUMN_AREA = "Area";
    private static final String COLUMN_ZONE_ID = "zone_id";
    private static final String COLUMN_CREATED_BY = "created_by";
    private static final String COLUMN_DATE_CREATED_A = "date_created";
    private static final String COLUMN_TIME_CREATED_A = "time_created";
    private static final String COLUMN_DATE_UPDATED_A = "date_updated";
    private static final String COLUMN_UPDATED_AT_A = "updated_at";
    private static final String COLUMN_UPDATED_BY_A = "updated_by";
    private static final String COLUMN_TIME_UPDATED_A = "time_updated";

    // TABLE ROUTE
    private static final String KEY_ID_RT = "id";
    private static final String TABLE_NAME_ROUTE = "tbl_route";
    private static final String COLUMN_METERNUMBER_RT = "meter_number";
    private static final String COLUMN_NAME_RT = "name";
    private static final String COLUMN_AREA_RT = "area";
    private static final String COLUMN_ZONE_RT = "zone_id";
    private static final String COLUMN_CREATED_BY_RT = "created_by";
    private static final String COLUMN_SEQ_RT = "seq";
    private static final String COLUMN_ROUTE_RT = "route";
    private static final String COLUMN_DATE_CREATED_RT = "updated_at";
//    private static final String COLUMN_UPDATED_BY_RT = "updated_by";


    // TABLE READ LOG

    private static final String TABLE_NAME_READ_LOG = "tbl_reading_log1";
    private static final String COLUMN_ID_R = "id";
    private static final String COLUMN_BILL_AREA_R = "bill_area";
    private static final String COLUMN_CONNECTION_NUMBER_R = "connection_number";
    private static final String COLUMN_CURRENT_READING_R = "current_reading";
    private static final String COLUMN_LATITUDE_R = "latitude";
    private static final String COLUMN_LONGITUDE_R = "longitude";
    private static final String COLUMN_METER_NUMBER_R = "meter_number";
    private static final String COLUMN_METER_STATUS_R = "meter_status";
    private static final String COLUMN_PREVIOUS_READING_R = "previous_reading";
    private static final String COLUMN_PUSHED_R = "pushed";
    private static final String COLUMN_READING_DATE_R = "reading_date";
    private static final String COLUMN_READING_TYPE_R = "reading_type";
    private static final String COLUMN_ROUTE_R = "route";
    private static final String COLUMN_SEQ_R = "seq";
    private static final String COLUMN_UPDATED_AT_R = "updated_at";
    private static final String COLUMN_UPDATED_BY_R = "updated_by";

    // TABLE CUSTOMERS
    //customer_connection, first_name, middle_name, last_name, mobile_number, telephone_number, work_phone, bill_area,
    // updated_by, date_created, date_updated FROM tbl_customer
    private static final String TABLE_NAME_CUSTOMER = "tbl_customer";
    private static final String COLUMN_ID_C = "id";
    private static final String COLUMN_CUSTOMER_CONNECTION = "customer_connection";
   // private static final String COLUMN_FIRST_NAME = "first_name";  COLUMNS ALREADY DEFINED IN CONNECTIONS
   // private static final String COLUMN_MIDDLE_NAME = "middle_name";
  //  private static final String COLUMN_LAST_NAME = "last_name";
  //  private static final String COLUMN_MOBILE_NUMBER = "mobile_number";
  //  private static final String COLUMN_TELEPHONE_NUMBER = "telephone_number";
    private static final String COLUMN_WORK_NUMBER = "work_phone";
    private static final String COLUMN_BILL_AREA = "bill_area";
    private static final String COLUMN_UPDATED_BY_C = "updated_by";
    private static final String COLUMN_DATE_CREATED_C = "date_created";
    private static final String COLUMN_DATE_UPDATED_C = "date_updated";


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


    // TABLE FAULT

    private static final String TABLE_NAME_FAULT = "tbl_fault";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SEVERITY = "severity";
    private static final String COLUMN_COMMENT = "comment";
    private static final String COLUMN_PHOTO_URL = "photo_url";
    private static final String COLUMN_REPORTED_BY = "reported_by";
    private static final String COLUMN_DATE_CREATED = "date_created";
    private static final String COLUMN_RECIEVED_ON = "recieved_on";
    private static final String COLUMN_FOLLOWED_ON = "followed_on";
    private static final String COLUMN_FOLLOWED_BY = "followed_by";
  //  private static final String COLUMN_LATITUDE = "latitude";
  //  private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_WHERE = "where_R";
  //  private static final String COLUMN_ROUTE_R = "route";
 //   private static final String COLUMN_SEQ_R = "seq";



    // TABLE READING
    private static final String TABLE_NAME_NEW_READING = "tbl_new_reading";


    //CREATE CONNECTION TABLE
    String sql_create_tbl_connection = "CREATE TABLE " + TABLE_NAME_CONNECTION + " (\n" +
            "  " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  " + COLUMN_CONNECTION_NUMBER + " varchar(200) ,\n" +
            "  " + COLUMN_METER_NUMBER + " varchar(200),\n" +
            "  " + COLUMN_METER_STATUS + "  varchar(200) ,\n" +
            "  " + COLUMN_PREVIOUS_DATE + " varchar(255) ,\n" +
            "  " + COLUMN_READING_DATE + " varchar(255),\n" +
            "  " + COLUMN_CURRENT_READING + " varchar(200) ,\n" +
            "  " + COLUMN_PREVIOUS_READING + "  varchar(200) ,\n" +
            "  " + COLUMN_READING_TYPE + "  varchar(200) ,\n" +
            "  " + COLUMN_CURRENT_CONSUMPTION + "  varchar(200) ,\n" +
            "  " + COLUMN_PREVIOUS_CONSUMPTION + "  varchar(200) ,\n" +
            "  " + COLUMN_DAILY_AVERAGE + "  varchar(200) ,\n" +
            "  " + COLUMN_NO_DAYS + "  varchar(200),\n" +
            "  " + COLUMN_AVARAGE_CONSYMPTION + "  varchar(200),\n" +
            "  " + COLUMN_MONTH + " varchar(200) ,\n" +
            "  " + COLUMN_ZONE + " varchar(200)  ,\n" +
            "  " + COLUMN_ROUTE + " varchar(200),\n" +
            "  " + COLUMN_SEQ + "  varchar(200) ,\n" +
            "  " + COLUMN_DONE + " varchar(200) ,\n" +
            "  " + COLUMN_METERED + " varchar(200) ,\n" +
         //   "  " + COLUMN_PUSHED + "  varchar(200) ,\n" +
         //   "  " + COLUMN_UPDATED_AT + "  varchar(200) ,\n" +
            "  " + COLUMN_UPDATED_BY + "  varchar(200) ,\n" +
            "  " + COLUMN_DATE_UPDATED + " varchar(255) ,\n" +
            "  " + COLUMN_SALES_ASSISTANT_ID3 + "  varchar(200)  ,\n" +
            "  " + COLUMN_LONGITUDE + " double   ,\n" +
            "  " + COLUMN_LATITUDE + " double  ,\n" +
            "  " + COLUMN_ELEVATION + " double  ,\n" +
            "  " + COLUMN_DISCONNECTED + " double ,  \n" +
            "  " + COLUMN_FIRST_NAME + "  varchar(200)  , \n" +
            "  " + COLUMN_MIDDLE_NAME + "  varchar(200) ,  \n" +
            "  " + COLUMN_LAST_NAME + "  varchar(200) ,  \n" +
            "  " + COLUMN_MOBILE_NUMBER + "  varchar(200),   \n" +
            "  " + COLUMN_TELEPHONE_NUMBER + "  varchar(200) ,  \n" +
            "  " + COLUMN_WORK_PHONE_NUMBER + "  varchar(200) ,  \n" +
            "  " + COLUMN_BILLING_AREA + "  varchar(200) ,  \n" +
            "  " + COLUMN_U_FIRST_NAME + "  varchar(200) ,  \n" +
            "  " + COLUMN_U_LAST_NAME + "  varchar(200) ,  \n" +
            "  " + COLUMN_USERNAME + "  varchar(200)   \n" +
            ");";

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
    //CREATE CUSTOMERS TABLE
   String sql_create_tbl_customer = "CREATE TABLE " + TABLE_NAME_CUSTOMER + " (\n" +
            "  " + COLUMN_ID_C + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  " + COLUMN_CUSTOMER_CONNECTION + " varchar(200) ,\n" +
            "  " + COLUMN_FIRST_NAME + " varchar(200) ,\n" +
            "  " + COLUMN_MIDDLE_NAME + " varchar(200) ,\n" +
            "  " + COLUMN_LAST_NAME + " varchar(200) ,\n" +
            "  " + COLUMN_MOBILE_NUMBER + " varchar(200) ,\n" +
            "  " + COLUMN_TELEPHONE_NUMBER + " varchar(200)  ,\n" +
            "  " + COLUMN_WORK_NUMBER + " varchar(200)   ,\n" +
            "  " + COLUMN_BILL_AREA + " varchar(200)  ,\n" +
            "  " + COLUMN_UPDATED_BY_C + " varchar(200)  ,\n" +
            "  " + COLUMN_DATE_CREATED_C + " varchar(200)  ,\n" +
            "  " + COLUMN_DATE_UPDATED_C + " varchar(200) \n" +
            ");";

    /**
     *
     * "area_id": 2,
     *         "area_code": "MRA",
     *         "Area": "MRARA",
     *         "zone_id": "A",
     *         "created_by": null,
     *         "date_created": "2014-07-14T00:00:00.000Z",
     *         "time_created": "1970-01-01T11:37:10.920Z",
     *         "updated_by": null,
     *         "date_updated": "2014-07-14T00:00:00.000Z",
     *         "time_updated": "1970-01-01T11:37:10.920Z"*/

    //CREATE AREA TABLE

    String sql_create_tbl_areas = "CREATE TABLE " + TABLE_NAME_AREAS + " (\n" +
            "  " + KEY_ID_A + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  " + COLUMN_AREA_ID + " varchar(200) ,\n" +
            "  " + COLUMN_AREA_CODE + " varchar(200) ,\n" +
            "  " + COLUMN_AREA + " varchar(200) ,\n" +
            "  " + COLUMN_ZONE_ID + " varchar(200) ,\n" +
            "  " + COLUMN_CREATED_BY + " varchar(200) ,\n" +
            "  " + COLUMN_DATE_CREATED_A + " varchar(200)  ,\n" +
            "  " + COLUMN_DATE_UPDATED_A + " varchar(200)   ,\n" +
            "  " + COLUMN_UPDATED_AT_A + " varchar(200)  ,\n" +
            "  " + COLUMN_UPDATED_BY_A + " varchar(200) \n" +
            ");";


    // TABLE PICHA
    private static final String KEY_ID_P = "id";
    private static final String TABLE_NAME_PICHA = "tbl_images";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PATH = "path";

    //CREATE PICHA TABLE

    String sql_create_tbl_images = "CREATE TABLE " + TABLE_NAME_PICHA + " (\n" +
            "  " + KEY_ID_P + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  " + COLUMN_NAME + " varchar(200) ,\n" +
            "  " + COLUMN_PATH + " varchar(200) \n" +
            ");";

//CREATE READ LOG

    String sql_create_tbl_read_log = "CREATE TABLE " + TABLE_NAME_READ_LOG + " (\n" +
            "  " + COLUMN_ID_R + " INTEGER PRIMARY KEY AUTOINCREMENT  ,\n" +
            "  " + COLUMN_BILL_AREA_R + " varchar(200) ,\n" +
            "  " + COLUMN_CONNECTION_NUMBER_R + " varchar(200) ,\n" +
            "  " + COLUMN_CURRENT_READING_R + " varchar(200)  ,\n" +
            "  " + COLUMN_LATITUDE_R + " varchar(200)  ,\n" +
            "  " + COLUMN_LONGITUDE_R + " varchar(200)  ,\n" +
            "  " + COLUMN_METER_NUMBER_R + " varchar(200),\n" +
            "  " + COLUMN_METER_STATUS_R + " varchar(200)  ,\n" +
            "  " + COLUMN_PREVIOUS_READING_R + " varchar(200)  ,\n" +
            "  " + COLUMN_PUSHED_R + " varchar(200)  ,\n" +
            "  " + COLUMN_READING_DATE_R + " varchar(200)   ,\n" +
            "  " + COLUMN_READING_TYPE_R + " varchar(200)  ,\n" +
            "  " + COLUMN_ROUTE_R + " varchar(200)  ,\n" +
            "  " + COLUMN_SEQ_R + " varchar(200)  ,\n" +
            "  " + COLUMN_UPDATED_AT_R + " varchar(200)  ,\n" +
            "  " + COLUMN_UPDATED_BY_R + " varchar(200)  \n" +
            ") ";

/**
    private static final String TABLE_NAME_FAULT = "tbl_fault";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SEVERITY = "severity";
    private static final String COLUMN_COMMENT = "comment";
    private static final String COLUMN_PHOTO_URL = "photo_url";
    private static final String COLUMN_REPORTED_BY = "reported_by";
    private static final String COLUMN_DATE_CREATED = "date_created";
    private static final String COLUMN_RECIEVED_ON = "recieved_on";
    private static final String COLUMN_FOLLOWED_ON = "followed_on";
    private static final String COLUMN_FOLLOWED_BY = "followed_by";
    //  private static final String COLUMN_LATITUDE = "latitude";
    //  private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_WHERE = "where";
    //  private static final String COLUMN_ROUTE_R = "route";
    //   private static final String COLUMN_SEQ_R = "seq";
*/
    //CREATE FAULT

    String sql_create_tbl_fault = "CREATE TABLE " + TABLE_NAME_FAULT + " (\n" +
            "  " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT  ,\n" +
            "  " + COLUMN_SEVERITY + " varchar(200) ,\n" +
            "  " + COLUMN_COMMENT + " varchar(200) ,\n" +
            "  " + COLUMN_PHOTO_URL + " varchar(200)  ,\n" +
            "  " + COLUMN_REPORTED_BY + " varchar(200)  ,\n" +
            "  " + COLUMN_DATE_CREATED + " varchar(200)  ,\n" +
            "  " + COLUMN_RECIEVED_ON + " varchar(200),\n" +
            "  " + COLUMN_FOLLOWED_ON + " varchar(200)  ,\n" +
            "  " + COLUMN_FOLLOWED_BY + " varchar(200)  ,\n" +
            "  " + COLUMN_LATITUDE + " varchar(200)  ,\n" +
            "  " + COLUMN_LONGITUDE + " varchar(200)   ,\n" +
            "  " + COLUMN_WHERE + " varchar(200)  ,\n" +
            "  " + COLUMN_ROUTE + " varchar(200)  ,\n" +
            "  " + COLUMN_SEQ + " varchar(200)  \n" +
            ") ";

    //CREATE NEW READING

    String sql_create_tbl_new_reading = "CREATE TABLE " + TABLE_NAME_NEW_READING + " (\n" +
            "  " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  " + COLUMN_METER_NUMBER + "  varchar(200)  ,\n" +
            "  " + COLUMN_CURRENT_READING + " varchar(200)  ,\n" +
            "  " + COLUMN_AREA + "  varchar(200) ,\n" +
            "  " + COLUMN_CONNECTION_NUMBER + "  varchar(200),\n" +
            "  " + COLUMN_LATITUDE + "  varchar(200) ,\n" +
            "  " + COLUMN_LONGITUDE + "  varchar(200),\n" +
            "  " + COLUMN_READING_DATE + "  varchar(200) \n" +
            ") ";


    // TABLE SETTINGS
    private static final String KEY_ID_S = "id";
    private static final String TABLE_NAME_SETTINGS = "tbl_settings";
    private static final String TABLE_NAME_SETTINGS_TMP = "tbl_settings_tmp";
    private static final String COLUMN_IP_LOCAL = "ip_local";
    private static final String COLUMN_IP_PUBLIC = "ip_public";

    // TABLE LOGIN
    private static final  String TABLE_LOGIN = "login";
    private static final  String COLUMN_LOGIN_ID = "id";
    private static final  String COLUMN_LOGIN_NAME = "username";
    private static final  String COLUMN_LOGIN_TIME = "timestamp";
    private static final  String COLUMN_LOGIN_STATUS = "status";

    //CREATE PICHA TABLE

    String sql_create_tbl_settings = "CREATE TABLE " + TABLE_NAME_SETTINGS + " (\n" +
            "  " + KEY_ID_S + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  " + COLUMN_IP_LOCAL + " varchar(200) ,\n" +
            "  " + COLUMN_IP_PUBLIC + " varchar(200) \n" +
            ");";

    //CREATE LOGIN
    String sql_createLogin =  "CREATE TABLE "+ TABLE_LOGIN +" (\n" +
            "    "+ COLUMN_LOGIN_ID +"  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    "+ COLUMN_LOGIN_NAME +" varchar(200) ,\n" +
            "    "+ COLUMN_LOGIN_STATUS +" varchar(200)  ,\n" +
            "    "+ COLUMN_LOGIN_TIME +" varchar(200)  \n" +
            ");";

    String sql_create_tbl_settings_tmp = "CREATE TABLE " + TABLE_NAME_SETTINGS_TMP + " (\n" +
            "  " + KEY_ID_S + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  " + COLUMN_IP_LOCAL + " varchar(200) ,\n" +
            "  " + COLUMN_IP_PUBLIC + " varchar(200) \n" +
            ");";


    public DatabaseHelper_node(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(sql_create_tbl_connection);
        Log.i(TAG, "Tables Created" + TABLE_NAME_CONNECTION);
        db.execSQL(sql_create_tbl_areas);
        Log.i(TAG, "Tables Created" + TABLE_NAME_AREAS);
        db.execSQL(sql_create_tbl_read_log);
        Log.i(TAG, "Tables Created" + TABLE_NAME_READ_LOG);
        db.execSQL(sql_create_tbl_new_reading);
        Log.i(TAG, "Tables Created" + TABLE_NAME_NEW_READING);
        db.execSQL(sql_create_tbl_images);
        Log.i(TAG, "Tables Created" + TABLE_NAME_PICHA);
        db.execSQL(sql_create_tbl_settings);
        Log.i(TAG, "Tables Created" + TABLE_NAME_SETTINGS);
        db.execSQL(sql_create_tbl_customer);
        Log.i(TAG, "Tables Created" + TABLE_NAME_CUSTOMER);
        db.execSQL(sql_create_tbl_fault);
        Log.i(TAG,"Tables Created" + TABLE_NAME_FAULT);
        db.execSQL(sql_createLogin);
        Log.i(TAG,"Tables Created "+TABLE_LOGIN);

        Log.i(TAG, "Tables Created");
        // System.exit(0);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CONNECTION);
        //  TABLE_LOGIN,TABLE_NAME_NEW_READING,TABLE_NAME_READ_LOG,TABLE_NAME_AREAS,TABLE_NAME_CUSTOMERS,TABLE_NAME_CONNECTION,TABLE_NAME
        //  db.execSQL("DROP TABLE IF EXISTS "+TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NEW_READING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_READ_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_AREAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CONNECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PICHA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CUSTOMER);

        // db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CUSTOMERS);
        //  db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        Log.i(TAG, "Upgrade Successful");

    }
/**
 * For Bulk Insert during sync
 * */


    public boolean insertTblConnection(String connection_number, String meter_number, String meter_status, String previous_date, String reading_date,
                                       String current_reading, String previous_reading, String reading_type, String current_consumption,
                                       String previous_consumption, String daily_average, String no_days, String average_consumption, String month, String zone, String route,
                                       String seq, String done, String metered, String updated_by, String date_updated, String sales_assistant_id3,
                                       String longitude, String latitude,String elevation, String disconnected, String first_name, String middle_name, String last_name
                                     , String mobile_number, String telephone_number, String work_phone_number, String billing_area, String u_first_name, String u_last_name, String username)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long flag = 0;
        try {

            sqLiteDatabase.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CONNECTION_NUMBER, connection_number);
            cv.put(COLUMN_METER_NUMBER, meter_number);
            cv.put(COLUMN_METER_STATUS, meter_status);
            cv.put(COLUMN_PREVIOUS_DATE, previous_date);
            cv.put(COLUMN_READING_DATE, reading_date);
            cv.put(COLUMN_CURRENT_READING, current_reading);
            cv.put(COLUMN_PREVIOUS_READING, previous_reading);
            cv.put(COLUMN_READING_TYPE, reading_type);
            cv.put(COLUMN_CURRENT_CONSUMPTION, current_consumption);
            cv.put(COLUMN_PREVIOUS_CONSUMPTION, previous_consumption);
            cv.put(COLUMN_DAILY_AVERAGE, daily_average);
            cv.put(COLUMN_NO_DAYS, no_days);
            cv.put(COLUMN_AVARAGE_CONSYMPTION, average_consumption);
            cv.put(COLUMN_MONTH, month);
            cv.put(COLUMN_ZONE, zone);
            cv.put(COLUMN_ROUTE, route);
            cv.put(COLUMN_SEQ, seq);
            cv.put(COLUMN_DONE, done);
            cv.put(COLUMN_METERED, metered);
           // cv.put(COLUMN_PUSHED, pushed);
          //  cv.put(COLUMN_UPDATED_AT, updated_at);
            cv.put(COLUMN_UPDATED_BY, updated_by);
            cv.put(COLUMN_DATE_UPDATED, date_updated);
            cv.put(COLUMN_SALES_ASSISTANT_ID3, sales_assistant_id3);
            cv.put(COLUMN_LONGITUDE, longitude);
            cv.put(COLUMN_LATITUDE, latitude);
            cv.put(COLUMN_ELEVATION, elevation);
            cv.put(COLUMN_DISCONNECTED, disconnected);
            cv.put(COLUMN_FIRST_NAME, first_name);
            cv.put(COLUMN_MIDDLE_NAME, middle_name);
            cv.put(COLUMN_LAST_NAME, last_name);
            cv.put(COLUMN_MOBILE_NUMBER, mobile_number);
            cv.put(COLUMN_TELEPHONE_NUMBER, telephone_number);
            cv.put(COLUMN_WORK_PHONE_NUMBER, work_phone_number);
            cv.put(COLUMN_BILLING_AREA, billing_area);
            cv.put(COLUMN_U_FIRST_NAME, u_first_name);
            cv.put(COLUMN_U_LAST_NAME, u_last_name);
            cv.put(COLUMN_USERNAME, username);



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
            flag = sqLiteDatabase.insert(TABLE_NAME_CONNECTION, null, cv);

            sqLiteDatabase.setTransactionSuccessful();
            Log.i(TAG, "Done");
        } catch (Exception e) {
            Log.e(TAG, "An Error Has Occoured" + e.getMessage());
       }
// finally {
//
//            // sqLiteDatabase.close();
//        }
        sqLiteDatabase.endTransaction();
        if (flag == -1) {
            Log.i(TAG, "Failed to Add Customers");
            return false;
        } else {
            Log.i(TAG, "Add Customers was success full");
            return true;
        }
    }
//
//   public void add_connections(ArrayList<HashMap<String, String>> CustomerList) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        String sql = "INSERT INTO " + TABLE_NAME_CONNECTION + " VALUES(?, ?)";
//        SQLiteStatement statement = database.compileStatement(sql);
//        database.beginTransaction();
//        try {
//            for (CustomerList c : list) {
//                statement.clearBindings();
//                statement.bindLong(1, c.getCityId());
//                statement.bindLong(2, c.getCityName());
//                statement.execute();
//            }
//            database.setTransactionSuccessful();
//        } finally {
//            database.endTransaction();
//        }
//    }


    public boolean insertTblReadlog(String bill_area, String connection_number, String current_reading, String latitude, String longitude,
                                    String meter_number, String meter_status, String previous_reading, String pushed,
                                    String reading_date, String reading_type, String route, String seq, String updated_at, String updated_by)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long flag2 = 0;
        try {
            sqLiteDatabase.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_BILL_AREA_R, bill_area);
            cv.put(COLUMN_CONNECTION_NUMBER_R, connection_number);
            cv.put(COLUMN_CURRENT_READING_R, current_reading);
            cv.put(COLUMN_LATITUDE_R, latitude);
            cv.put(COLUMN_LONGITUDE_R, longitude);
            cv.put(COLUMN_METER_NUMBER_R, meter_number);
            cv.put(COLUMN_METER_STATUS_R, meter_status);
            cv.put(COLUMN_PREVIOUS_READING_R, previous_reading);
            cv.put(COLUMN_PUSHED_R, pushed);
            cv.put(COLUMN_READING_DATE_R, reading_date);
            cv.put(COLUMN_READING_TYPE_R, reading_type);
            cv.put(COLUMN_ROUTE_R, route);
            cv.put(COLUMN_SEQ_R, seq);
            cv.put(COLUMN_UPDATED_AT_R, updated_at);
            cv.put(COLUMN_UPDATED_BY_R, updated_by);

            flag2 = sqLiteDatabase.insert(TABLE_NAME_READ_LOG, null, cv);

            sqLiteDatabase.setTransactionSuccessful();
            Log.i(TAG, "Done" + TABLE_NAME_READ_LOG);
        } catch (Exception e) {
            Log.e(TAG, "An Error Has Occurred" + e.getMessage());
        } finally {
            sqLiteDatabase.endTransaction();
        }
        if (flag2 == -1) {
            Log.i(TAG, "Failed to Add ReadLog");
            return false;
        } else {
            Log.i(TAG, "Add ReadLog was successful");
            return true;
        }

    }


    public boolean insertAreas(String area_id, String area_code, String Area, String zone_id, String created_by, String date_created, String date_updated, String updated_at, String updated_by)
    {
        SQLiteDatabase sqLiteDatabase_Area = getWritableDatabase();
        long flag1 = 0;
        try {
            /**
             * "area_id": 2,
             *         "area_code": "MRA",
             *         "Area": "MRARA",
             *         "zone_id": "A",
             *         "created_by": null,
             *         "date_created": "2014-07-14T00:00:00.000Z",
             *         "time_created": "1970-01-01T11:37:10.920Z",
             *         "updated_by": null,
             *         "date_updated": "2014-07-14T00:00:00.000Z",
             *         "time_updated": "1970-01-01T11:37:10.920Z"
             * */
            sqLiteDatabase_Area.beginTransaction();
            ContentValues cv_Area = new ContentValues();
            cv_Area.put(COLUMN_AREA_ID, area_id);
            cv_Area.put(COLUMN_AREA_CODE, area_code);
            cv_Area.put(COLUMN_AREA, Area);
            cv_Area.put(COLUMN_ZONE_ID, zone_id);
            cv_Area.put(COLUMN_CREATED_BY, created_by);
            cv_Area.put(COLUMN_DATE_CREATED_A, date_created);
            cv_Area.put(COLUMN_DATE_UPDATED_A, date_updated);
            cv_Area.put(COLUMN_UPDATED_AT_A, updated_at);
            cv_Area.put(COLUMN_UPDATED_BY_A, updated_by);
            // cv.put(COLUMN_PUSHED_R, pushed);

            flag1 = sqLiteDatabase_Area.insert(TABLE_NAME_AREAS, null, cv_Area);

            sqLiteDatabase_Area.setTransactionSuccessful();
            Log.i(TAG, "Done" + TABLE_NAME_AREAS);
            // sqLiteDatabase_Area.close();
        } catch (Exception e) {
            Log.e(TAG, "An Error Has Occurred" + e.getMessage());
        } finally {
            sqLiteDatabase_Area.endTransaction();
        }
        if (flag1 == -1) {
            Log.i(TAG, "Failed to Add Areas");
            return false;
        } else {
            Log.i(TAG, "Add Areas was successful");
            return true;
        }

    }

    public boolean insertCustomerDetails(String customer_connection, String first_name, String middle_name, String last_name, String mobile_number, String telephone_number, String work_phone, String bill_area, String updated_by, String date_created, String date_updated)
    {
        SQLiteDatabase sqLiteDatabase_CustomerDetails = getWritableDatabase();
        long flag1 = 0;
        try {
            sqLiteDatabase_CustomerDetails.beginTransaction();
            ContentValues cv_CustomersDetails = new ContentValues();
            cv_CustomersDetails.put(COLUMN_CUSTOMER_CONNECTION, customer_connection);
            cv_CustomersDetails.put(COLUMN_FIRST_NAME, first_name);
            cv_CustomersDetails.put(COLUMN_MIDDLE_NAME, middle_name);
            cv_CustomersDetails.put(COLUMN_LAST_NAME, last_name);
            cv_CustomersDetails.put(COLUMN_MOBILE_NUMBER, mobile_number);
            cv_CustomersDetails.put(COLUMN_TELEPHONE_NUMBER, telephone_number);
            cv_CustomersDetails.put(COLUMN_WORK_NUMBER, work_phone);
            cv_CustomersDetails.put(COLUMN_BILL_AREA, bill_area);
            cv_CustomersDetails.put(COLUMN_UPDATED_BY_C, updated_by);
            cv_CustomersDetails.put(COLUMN_DATE_CREATED_C, date_created);
            cv_CustomersDetails.put(COLUMN_DATE_UPDATED_C, date_updated);
            // cv.put(COLUMN_PUSHED_R, pushed);

            flag1 = sqLiteDatabase_CustomerDetails.insert(TABLE_NAME_CUSTOMER, null, cv_CustomersDetails);

            sqLiteDatabase_CustomerDetails.setTransactionSuccessful();
            Log.i(TAG, "Done" + TABLE_NAME_CUSTOMER);
            // sqLiteDatabase_Area.close();
        } catch (Exception e) {
            Log.e(TAG, "An Error Has Occurred" + e.getMessage());
        } finally {
            sqLiteDatabase_CustomerDetails.endTransaction();
        }
        if (flag1 == -1) {
            Log.i(TAG, "Failed to Add Customer Details");
            return false;
        } else {
            Log.i(TAG, "Add Customer Details was successful");
            return true;
        }

    }


    public boolean insertNewReading(String bill_area, String connection_number, String current_reading, String latitude, String longitude, String meter_number, String previous_reading, String updated_by)
    {
        SQLiteDatabase sqLiteDatabase_readLog = getWritableDatabase();
        long flag1 = 0;
        try {
            sqLiteDatabase_readLog.beginTransaction();
            ContentValues cv_readLog = new ContentValues();
            cv_readLog.put(COLUMN_BILL_AREA_R, bill_area);
            cv_readLog.put(COLUMN_CONNECTION_NUMBER_R, connection_number);
            cv_readLog.put(COLUMN_CURRENT_READING_R, current_reading);
            cv_readLog.put(COLUMN_LATITUDE_R, latitude);
            cv_readLog.put(COLUMN_LONGITUDE_R, longitude);
            cv_readLog.put(COLUMN_METER_NUMBER_R, meter_number);

           String status = String.valueOf(7);

            cv_readLog.put(COLUMN_METER_STATUS_R, status);
            cv_readLog.put(COLUMN_PREVIOUS_READING_R, previous_reading);

            String pushed = String.valueOf(10);

            cv_readLog.put(COLUMN_PUSHED_R, pushed);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            String readingDate = sdf.format(cal.getTime());

            cv_readLog.put(COLUMN_READING_DATE_R, readingDate);

            String read_type = String.valueOf(2);
            cv_readLog.put(COLUMN_READING_TYPE_R, read_type);

          //  cv_readLog.put(COLUMN_ROUTE_R, route);
          //  cv_readLog.put(COLUMN_SEQ_R, seq);

            long updated_at = System.currentTimeMillis();
            cv_readLog.put(COLUMN_UPDATED_AT_A, updated_at);

            cv_readLog.put(COLUMN_UPDATED_BY_A, updated_by);
            // cv.put(COLUMN_PUSHED_R, pushed);

            flag1 = sqLiteDatabase_readLog.insert(TABLE_NAME_READ_LOG, null, cv_readLog);

            sqLiteDatabase_readLog.setTransactionSuccessful();
            Log.i(TAG, "Done" + TABLE_NAME_READ_LOG);
            // sqLiteDatabase_Area.close();
        } catch (Exception e) {
            Log.e(TAG, "An Error Has Occurred" + e.getMessage());
        } finally {
            sqLiteDatabase_readLog.endTransaction();
        }
        if (flag1 == -1) {
            Log.i(TAG, "Failed to Add Areas");
            return false;
        } else {
            Log.i(TAG, "Add Areas was successful");
            return true;
        }

    }

    public boolean insertFault(String where_, String severity, String comment, String photo_url, String reported_by, String date_created,String latitude, String longitude)
    {
        SQLiteDatabase sqLiteDatabase_fault = getWritableDatabase();
        long flag1 = 0;
        try {
            sqLiteDatabase_fault.beginTransaction();
            ContentValues cv_fault = new ContentValues();
            cv_fault.put(COLUMN_WHERE, where_);
            cv_fault.put(COLUMN_SEVERITY, severity);
            cv_fault.put(COLUMN_COMMENT, comment);
            cv_fault.put(COLUMN_PHOTO_URL, photo_url);
            cv_fault.put(COLUMN_REPORTED_BY,reported_by );
            cv_fault.put(COLUMN_DATE_CREATED, date_created);
//            cv_fault.put(COLUMN_RECIEVED_ON, recieved_on);
//            cv_fault.put(COLUMN_FOLLOWED_ON, followed_on);
//            cv_fault.put(COLUMN_FOLLOWED_BY, followed_by);
            cv_fault.put(COLUMN_LATITUDE, latitude);
            cv_fault.put(COLUMN_LONGITUDE, longitude);


            flag1 = sqLiteDatabase_fault.insert(TABLE_NAME_FAULT, null, cv_fault);

            sqLiteDatabase_fault.setTransactionSuccessful();
            Log.i(TAG, "Done" + TABLE_NAME_FAULT);
            // sqLiteDatabase_Area.close();
        } catch (Exception e) {
            Log.e(TAG, "An Error Has Occurred" + e.getMessage());
        } finally {
            sqLiteDatabase_fault.endTransaction();
        }
        if (flag1 == -1) {
            Log.i(TAG, "Failed to Add Areas");
            return false;
        } else {
            Log.i(TAG, "Add Areas was successful");
            return true;
        }

    }


    public boolean Login_Log(String username,String status){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String loginTime = sdf.format(cal.getTime());
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_LOGIN_NAME ,username);
        cv.put(COLUMN_LOGIN_TIME,loginTime);
        cv.put(COLUMN_LOGIN_STATUS ,status);
        Log.e(TAG, "Login Log: New Login "+username);

        return sqLiteDatabase.insert(TABLE_LOGIN,null,cv) != 1;
    }

    public boolean insertRouteModify(String name,String meternumber, String area, String zone, String sequence, String route){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String routeDate = sdf.format(cal.getTime());
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_RT ,name);
        cv.put(COLUMN_METERNUMBER_RT,meternumber);
        cv.put(COLUMN_AREA_RT ,area);
        cv.put(COLUMN_ZONE_RT ,zone);
        cv.put(COLUMN_SEQ_RT ,sequence);
        cv.put(COLUMN_ROUTE_R ,route);
        String created_byyy = "IT Admin";
        cv.put(COLUMN_CREATED_BY_RT ,created_byyy);
        cv.put(COLUMN_DATE_CREATED_RT ,routeDate);



        Log.e(TAG, "Routeee Modify Request "+ name);

        return sqLiteDatabase.insert(TABLE_NAME_ROUTE,null,cv) != 1;
    }



    public boolean insertPichaKwaSQLite(String Picha_name, String Picha_filePath)
    {
        SQLiteDatabase sqLiteDatabase_Picha = getWritableDatabase();
        long flag_Picha = 0;
        try {
            sqLiteDatabase_Picha.beginTransaction();
            ContentValues cv_Picha = new ContentValues();
            cv_Picha.put(COLUMN_AREA_ID, Picha_name);
            cv_Picha.put(COLUMN_AREA_CODE, Picha_filePath);
//            cv_Picha.put(COLUMN_AREA, Area);
//            cv_Picha.put(COLUMN_ZONE_ID, zone_id);


            flag_Picha = sqLiteDatabase_Picha.insert(TABLE_NAME_AREAS, null, cv_Picha);

            sqLiteDatabase_Picha.setTransactionSuccessful();
            Log.i(TAG, "Done" + TABLE_NAME_AREAS);
            // sqLiteDatabase_Area.close();
        } catch (Exception e) {
            Log.e(TAG, "An Error Has Occurred" + e.getMessage());
        } finally {
            sqLiteDatabase_Picha.endTransaction();
        }
        if (flag_Picha == -1) {
            Log.i(TAG, "Failed to Save Picha on Device Memory");
            return false;
        } else {
            Log.i(TAG, "Add Picha was successful");
            return true;
        }
    }

    public boolean insertSettings(String iplocal, String ippublic)
    {
        SQLiteDatabase sqLiteDatabase_Settings = getWritableDatabase();
        long flag1 = 0;
        try {
            sqLiteDatabase_Settings.beginTransaction();
            ContentValues cv_Settings = new ContentValues();
            cv_Settings.put(COLUMN_IP_LOCAL, iplocal);
            cv_Settings.put(COLUMN_IP_PUBLIC, ippublic);

            flag1 = sqLiteDatabase_Settings.insert(TABLE_NAME_SETTINGS, null, cv_Settings);

            sqLiteDatabase_Settings.setTransactionSuccessful();
            Log.i(TAG, "Done" + TABLE_NAME_SETTINGS);
            // sqLiteDatabase_Area.close();
        } catch (Exception e) {
            Log.e(TAG, "An Error Has Occurred" + e.getMessage());
        } finally {
            sqLiteDatabase_Settings.endTransaction();
        }
        if (flag1 == -1) {
            Log.i(TAG, "Failed to Add Configurations");
            return false;
        } else {
            Log.i(TAG, "Configurations ware successful");
            return true;
        }

    }

    public Cursor getLoginLog()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select  * FROM " + TABLE_LOGIN +" ORDER BY "+COLUMN_METER_NUMBER, null);
        return res;

    }
    public Cursor getAllPichas()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * FROM " + TABLE_NAME_AREAS, null);
        return res;
    }

    public Cursor getAllCustomers_ByConnectionNumber(String key)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select " +COLUMN_FIRST_NAME+", "+COLUMN_MIDDLE_NAME+", "+COLUMN_LAST_NAME+", "+COLUMN_BILL_AREA+" FROM " + TABLE_NAME_CUSTOMER+" WHERE "+ COLUMN_CUSTOMER_CONNECTION+" =='"+key+"'  ORDER BY "+COLUMN_CUSTOMER_CONNECTION , null);
        return res;
    }
    /*SELECT cu.first_name,cu.middle_name,cu.last_name,cu.work_phone,cu.bill_area,con.meter_number from tbl_customer cu
    LEFT JOIN tbl_connection con ON cu.customer_connection = con.connection_number ORDER BY con.connection_number ASC
    * */
    public Cursor getMeterNumber_RV()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select "+ COLUMN_METER_NUMBER +","+COLUMN_CONNECTION_NUMBER+ " FROM " + TABLE_NAME_CONNECTION +" ORDER BY "+COLUMN_METER_NUMBER, null);
      /*  Cursor res1 = db.rawQuery("Select cu." +COLUMN_FIRST_NAME+", cu."+COLUMN_MIDDLE_NAME+", cu."+COLUMN_LAST_NAME+", cu."+COLUMN_BILL_AREA+
               ", con."+COLUMN_METER_NUMBER+" FROM " + TABLE_NAME_CUSTOMER+" cu LEFT JOIN "+ TABLE_NAME_CONNECTION+" con",null);
        System.out.println("Select cu." +COLUMN_FIRST_NAME+", cu."+COLUMN_MIDDLE_NAME+", cu."+COLUMN_LAST_NAME+", cu."+COLUMN_BILL_AREA+
                *///", con."+COLUMN_METER_NUMBER+" FROM " + TABLE_NAME_CUSTOMER+" cu LEFT JOIN "+ TABLE_NAME_CONNECTION+" con");
        return res;



    }

    public Cursor getName_RV()
    {
        /*
        *   SELECT `first_name`, `middle_name`, `last_name` FROM `tbl_customer` WHERE `customer_connection` = '000010'
        * */
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select "+ COLUMN_FIRST_NAME +","+COLUMN_MIDDLE_NAME+ ","+COLUMN_LAST_NAME+ " FROM " + TABLE_NAME_CUSTOMER, null);
        return res;

    }
    public Cursor getMeterNumber_waliosomewa()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select "+ COLUMN_METER_NUMBER + " FROM " + TABLE_NAME_READ_LOG +" ORDER BY "+COLUMN_METER_NUMBER, null);
        return res;

    }


    public Cursor getMeterNumber_RV_where(String Key)
    {
        String key = Key;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select "+ COLUMN_METER_NUMBER +","+COLUMN_CONNECTION_NUMBER+  " FROM " + TABLE_NAME_CONNECTION+" WHERE "+ COLUMN_METER_NUMBER+" LIKE '%"+key+"%'"+" ORDER BY "+COLUMN_METER_NUMBER , null);
        return res;
    }

    public Cursor getMeterNumber_RVWaliosomewa_where(String Key)
    {
        String key = Key;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select "+ COLUMN_METER_NUMBER +","+COLUMN_CONNECTION_NUMBER+ " FROM " + TABLE_NAME_READ_LOG+" WHERE "+ COLUMN_METER_NUMBER+" LIKE '%"+key+"%'"+" ORDER BY "+COLUMN_METER_NUMBER , null);
        return res;
    }
    public Cursor getLatLang()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select "+ COLUMN_LATITUDE+" ," + COLUMN_LONGITUDE+" ,"+ COLUMN_METER_NUMBER + " FROM " + TABLE_NAME_CONNECTION +" WHERE "+ COLUMN_LATITUDE+" !=0 AND " + COLUMN_LONGITUDE+" !=0", null);
        return res;

    }

    public Cursor route_Details(String MeterNumber)
    {
        String key = MeterNumber;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select "+ COLUMN_METER_NUMBER+" ,"+COLUMN_CONNECTION_NUMBER +" ," + COLUMN_ZONE+" ," + COLUMN_ROUTE+" ," + COLUMN_SEQ+ " FROM " + TABLE_NAME_CONNECTION+" WHERE "+ COLUMN_METER_NUMBER+" =='"+key+"'  ORDER BY "+COLUMN_METER_NUMBER , null);
        return res;
    }

    public Cursor getDetils(String mNumn)
    {
        String key = mNumn;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select "+COLUMN_CONNECTION_NUMBER+" ,"+ COLUMN_PREVIOUS_READING+" ,"+ COLUMN_LATITUDE+" ," + COLUMN_LONGITUDE + " FROM " + TABLE_NAME_CONNECTION +" WHERE "+ COLUMN_METER_NUMBER+" =='"+key+"'", null);
        return res;
    }
    public Cursor getArea(String nNum)
    {
        String key = nNum;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select "+COLUMN_AREA+" ,"+ COLUMN_PREVIOUS_READING+" ,"+ COLUMN_LATITUDE+" ," + COLUMN_LONGITUDE + " FROM " + TABLE_NAME_CONNECTION +" WHERE "+ COLUMN_METER_NUMBER+" =='"+key+"'", null);
        return res;
    }
    public Cursor getArea_forModify(String nNum)
    {
        String key = nNum;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select "+COLUMN_AREA+" ,"+ COLUMN_PREVIOUS_READING+" ,"+ COLUMN_LATITUDE+" ," + COLUMN_LONGITUDE + " FROM " + TABLE_NAME_CONNECTION +" WHERE "+ COLUMN_METER_NUMBER+" =='"+key+"'", null);
        return res;
    }

    public Cursor getAllReadLogs()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * FROM " + TABLE_NAME_READ_LOG, null);
        return res;
    }

    public Cursor getAllArea()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * FROM " + TABLE_NAME_AREAS, null);
        return res;
    }


    public Cursor getSettings_local()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = " SELECT "+COLUMN_IP_LOCAL+" FROM "+TABLE_NAME_SETTINGS;
        Cursor res = db.rawQuery(sql, null);
        return res;

    }
    /*Redundant code*/
    public Cursor getreadlog()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = " SELECT "+COLUMN_ID+" ,"+COLUMN_BILL_AREA+" ,"+COLUMN_CONNECTION_NUMBER+" ,"+COLUMN_CURRENT_READING+" ,"+COLUMN_LATITUDE+" ,"+COLUMN_LONGITUDE+" ,"+COLUMN_METER_NUMBER+" ,"+COLUMN_METER_STATUS+" ,"+COLUMN_PREVIOUS_READING+" ,"+COLUMN_PUSHED+" ,"+COLUMN_READING_DATE+" ,"+COLUMN_READING_TYPE+" ,"+COLUMN_ROUTE+" ,"+COLUMN_SEQ+" ,"+COLUMN_UPDATED_AT+" ,"+COLUMN_UPDATED_BY+" FROM "+TABLE_NAME_READ_LOG;
        Cursor res = db.rawQuery(sql, null);
        return res;

    }
/*redundant code*/
    public Cursor getSettings_public()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select COLUMN_IP_PUBLIC FROM " + TABLE_NAME_SETTINGS, null);
        return res;
    }

    public Cursor getSettings_All()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * FROM " + TABLE_NAME_SETTINGS, null);
        return res;
    }

    public void settingDrop()
    {
       final SQLiteDatabase db = getWritableDatabase();
        final String whereClause = KEY_ID_S  + "!=?";
        final String whereClause1 = KEY_ID_S  + "=?";
        String value = String.valueOf(1);
        final String[] whereArgs = new String[] { value };
        db.delete(TABLE_NAME_SETTINGS, whereClause, whereArgs);
        //db.delete(TABLE_NAME_SETTINGS, whereClause1, whereArgs);
        db.close();

    }
    public  void settingDropAll()
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SETTINGS);
        sqLiteDatabase.execSQL(sql_create_tbl_settings);
    }

    public void ondoaConnection()
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CONNECTION);
        sqLiteDatabase.execSQL(sql_create_tbl_connection);

    }
    public void ondoaArea()
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_AREAS);
        sqLiteDatabase.execSQL(sql_create_tbl_areas);

    }
    public void ondoaCustomers()
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_AREAS);
        sqLiteDatabase.execSQL(sql_create_tbl_areas);

    }

}
