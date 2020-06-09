package tz.co.ubunifusolutions.screens.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String TAG = "Database Manager" ;
    private static final  String DATABASE_NAME = "BawasaAppDatabase";
   // private static final  String DATABASE_NAME = "mydatabase";
    private static final  int DATABASE_VERSION= 1;

    // TABLE EMPLOYEE
    private static final  String TABLE_NAME = "employees";
    private static final  String COLUMN_ID = "id";
    private static final  String COLUMN_NAME = "name";
    private static final  String COLUMN_DEPT = "department";
    private static final  String COLUMN_JOIN_DATE = "joiningdate";
    private static final  String COLUMN_SALARY = "salary";

    // TABLE LOGIN
    private static final  String TABLE_LOGIN = "login";
    private static final  String COLUMN_LOGIN_ID = "id";
    private static final  String COLUMN_LOGIN_NAME = "username";
    private static final  String COLUMN_LOGIN_TIME = "timestamp";
    private static final  String COLUMN_LOGIN_STATUS = "status";

    //TABLE CONNECTION
    private static final  String TABLE_NAME_CONNECTION = "tbl_connection";
    private static final String KEY_ID = "id";
    private static final  String COLUMN_CONNECTION_NUMBER = "connection_number";
    private static final  String COLUMN_METER_NUMBER = "meter_number";
    private static final  String COLUMN_METER_STATUS = "meter_status";
    private static final  String COLUMN_PREVIOUS_DATE = "previous_date";
    private static final  String COLUMN_READING_DATE = "reading_date";
    private static final  String COLUMN_CURRENT_READING = "current_reading";
    private static final  String COLUMN_PREVIOUS_READING = "previous_reading";
    private static final  String COLUMN_READING_TYPE = "reading_type";
    private static final  String COLUMN_CURRENT_CONSUMPTION = "current_consumption";
    private static final  String COLUMN_PREVIOUS_CONSUMPTION = "previous_consumption";
    private static final  String COLUMN_DAILY_AVERAGE = "daily_average";
    private static final  String COLUMN_NO_DAYS = "no_days";
    private static final  String COLUMN_AVARAGE_CONSYMPTION = "average_consumption";
    private static final  String COLUMN_MONTH = "month";
    private static final  String COLUMN_ZONE = "zone";
    private static final  String COLUMN_ROUTE = "route";
    private static final  String COLUMN_SEQ = "seq";
    private static final  String COLUMN_DONE = "done";
    private static final  String COLUMN_PUSHED = "pushed";
    private static final  String COLUMN_METERED = "metered ";
    private static final  String COLUMN_UPDATED_AT = "updated_at";
    private static final  String COLUMN_UPDATED_BY = "updated_by";
    private static final  String COLUMN_DATE_UPDATED = "date_updated";
    private static final  String COLUMN_SALES_ASSISTANT_ID3 = "sales_assistant_id3";
    private static final  String COLUMN_LONGITUDE= "longitude";
    private static final  String COLUMN_LATITUDE = "latitude";
    private static final  String COLUMN_DISCONNECTED = "disconnected";

    // TABLE CUSTOMERS
    private static final  String TABLE_NAME_CUSTOMERS = "tbl_customer";
    private static final  String COLUMN_CUSTOMER_CONNECTION = "customer_connection";
    private static final  String COLUMN_FIRST_NAME = "first_name";
    private static final  String COLUMN_MIDDLE_NAME = "middle_name";
    private static final  String COLUMN_LAST_NAME = "last_name";
    private static final  String COLUMN_MOBILE_NUMBER = "mobile_number";
    private static final  String COLUMN_TELEPHONE_NUMBER = "telephone_number";
    private static final  String COLUMN_WORK_PHONE = "work_phone";
    private static final  String COLUMN_BILL_AREA = "bill_area";
    private static final  String COLUMN_UPDATED_BY_C = "updated_by";
    private static final  String COLUMN_DATE_CREATED = "date_created";
    private static final  String COLUMN_DATE_UPDATED_C = "date_updated";

    // TABLE AREAS
    private static final  String TABLE_NAME_AREAS = "tbl_areas";
    private static final  String COLUMN_AREA_ID= "area_id";
    private static final  String COLUMN_AREA_CODE= "area_code";
    private static final  String COLUMN_AREA = "Area";
    private static final  String COLUMN_ZONE_ID = "zone_id";
    private static final  String COLUMN_CREATED_BY = "created_by";
    private static final  String COLUMN_DATE_CREATED_A = "date_created";
    private static final  String COLUMN_DATE_UPDATED_A = "date_updated";
    private static final  String COLUMN_UPDATED_AT_A = "updated_at";
    private static final  String COLUMN_UPDATED_BY_A = "updated_by";

// TABLE READ LOG

    private static final  String TABLE_NAME_READ_LOG = "tbl_reading_log";
    private static final  String COLUMN_ID_R = "id";
    private static final  String COLUMN_BILL_AREA_R = "bill_area";
    private static final  String COLUMN_CONNECTION_NUMBER_R = "connection_number";
    private static final  String COLUMN_CURRENT_READING_R = "current_reading";
    private static final  String COLUMN_LATITUDE_R = "latitude";
    private static final  String COLUMN_LONGITUDE_R= "longitude";
    private static final  String COLUMN_METER_NUMBER_R = "meter_number";
    private static final  String COLUMN_METER_STATUS_R= "meter_status";
    private static final  String COLUMN_PREVIOUS_READING_R = "previous_reading";
    private static final  String COLUMN_PUSHED_R = "pushed";
    private static final  String COLUMN_READING_DATE_R= "reading_date";
    private static final  String COLUMN_READING_TYPE_R = "reading_type";
    private static final  String COLUMN_ROUTE_R = "route";
    private static final  String COLUMN_SEQ_R = "seq";
    private static final  String COLUMN_UPDATED_AT_R = "updated_at";
    private static final  String COLUMN_UPDATED_BY_R = "updated_by";


// TABLE READING
    private static final  String TABLE_NAME_NEW_READING = "tbl_new_reading";




    public DatabaseManager(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {




//CREATE EMPLOYEES
        String sql_createEmployee =  "CREATE TABLE "+ TABLE_NAME +" (\n" +
                "    "+ COLUMN_ID +"  INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    "+ COLUMN_NAME +" varchar(200) NOT NULL,\n" +
                "    "+ COLUMN_DEPT +" varchar(200) NOT NULL,\n" +
                "    "+ COLUMN_JOIN_DATE +" datetime NOT NULL,\n" +
                "    "+ COLUMN_SALARY +" double NOT NULL\n" +
                ");";

        //CREATE LOGIN
        String sql_createLogin =  "CREATE TABLE "+ TABLE_LOGIN +" (\n" +
                "    "+ COLUMN_LOGIN_ID +"  INTEGER NOT NULL CONSTRAINT login_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    "+ COLUMN_LOGIN_NAME +" varchar(200) ,\n" +
                "    "+ COLUMN_LOGIN_STATUS +" varchar(200)  ,\n" +
                "    "+ COLUMN_LOGIN_TIME +" varchar(200)  \n" +
               ");";

        //CREATE CONNECTION TABLE
        String sql_create_tbl_connection = "CREATE TABLE "+TABLE_NAME_CONNECTION+" (\n" +
                "  "+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  "+ COLUMN_CONNECTION_NUMBER +" varchar(200) ,\n" +
                "  "+ COLUMN_METER_NUMBER +" varchar(200),\n" +
                "  "+ COLUMN_METER_STATUS +"  varchar(200) ,\n" +
                "  "+ COLUMN_PREVIOUS_DATE +" varchar(255) ,\n" +
                "  "+ COLUMN_READING_DATE +" varchar(255),\n" +
                "  "+ COLUMN_CURRENT_READING +" varchar(200) ,\n" +
                "  "+ COLUMN_PREVIOUS_READING +"  varchar(200) ,\n" +
                "  "+ COLUMN_READING_TYPE +"  varchar(200) ,\n" +
                "  "+ COLUMN_CURRENT_CONSUMPTION +"  varchar(200) ,\n" +
                "  "+ COLUMN_PREVIOUS_CONSUMPTION +"  varchar(200) ,\n" +
                "  "+ COLUMN_DAILY_AVERAGE +"  varchar(200) ,\n" +
                "  "+ COLUMN_NO_DAYS +"  varchar(200),\n" +
                "  "+ COLUMN_AVARAGE_CONSYMPTION +"  varchar(200),\n" +
                "  "+ COLUMN_MONTH +" varchar(200) ,\n" +
                "  "+ COLUMN_ZONE +" varchar(200)  ,\n" +
                "  "+ COLUMN_ROUTE +" varchar(200),\n" +
                "  "+ COLUMN_SEQ +"  varchar(200) ,\n" +
                "  "+ COLUMN_DONE +" varchar(200) ,\n" +
                "  "+ COLUMN_METERED +" varchar(200) ,\n" +
                "  "+ COLUMN_PUSHED +"  varchar(200) ,\n" +
                "  "+ COLUMN_UPDATED_AT +"  varchar(200) ,\n" +
                "  "+ COLUMN_UPDATED_BY +"  varchar(200) ,\n" +
                "  "+ COLUMN_DATE_UPDATED +" varchar(255) ,\n" +
                "  "+ COLUMN_SALES_ASSISTANT_ID3 +"  varchar(200)  ,\n" +
                "  "+ COLUMN_LONGITUDE +" double   ,\n" +
                "  "+ COLUMN_LATITUDE +" double  ,\n" +
                "  "+ COLUMN_DISCONNECTED +" double   \n" +
                ");";



        // CREATE CUSTOMERS TABLE
        String sql_create_tbl_customer = "CREATE TABLE "+TABLE_NAME_CUSTOMERS+" (\n" +
                "  "+ COLUMN_CUSTOMER_CONNECTION +" varchar(255) ,\n" +
                "  "+ COLUMN_FIRST_NAME +" varchar(255) ,\n" +
                "  "+ COLUMN_MIDDLE_NAME +" varchar(255) ,\n" +
                "  "+ COLUMN_LAST_NAME +" varchar(255) ,\n" +
                "  "+ COLUMN_MOBILE_NUMBER +" varchar(255) ,\n" +
                "  "+ COLUMN_TELEPHONE_NUMBER +" varchar(255) ,\n" +
                "  "+ COLUMN_WORK_PHONE +" varchar(255) ,\n" +
                "  "+ COLUMN_BILL_AREA +" varchar(255) ,\n" +
                "  "+ COLUMN_UPDATED_BY_C +" varchar(255) ,\n" +
                "  "+ COLUMN_DATE_CREATED +" varchar(255)   ,\n" +
                "  "+ COLUMN_DATE_UPDATED_C +" varchar(255)   \n" +
                ") ;";

        //CREATE AREA TABLE

        String sql_create_tbl_areas = "CREATE TABLE "+TABLE_NAME_AREAS+" (\n" +
                "  "+COLUMN_AREA_ID+" double  ,\n" +
                "  "+COLUMN_AREA_CODE+" varchar(255) ,\n" +
                "  "+COLUMN_AREA+" varchar(255) ,\n" +
                "  "+ COLUMN_ZONE_ID +" varchar(255) ,\n" +
                "  "+ COLUMN_CREATED_BY +" varchar(255) ,\n" +
                "  "+ COLUMN_DATE_CREATED_A +" varchar(255)  ,\n" +
                "  "+ COLUMN_DATE_UPDATED_A +" varchar(255)   ,\n" +
                "  "+ COLUMN_UPDATED_AT_A +" double  ,\n" +
                "  "+ COLUMN_UPDATED_BY_A +" varchar(255) \n" +
                ");";

//CREATE READ LOG

        String sql_create_tbl_read_log = "CREATE TABLE "+ TABLE_NAME_READ_LOG +" (\n" +
                "  "+ COLUMN_ID_R +" double  ,\n" +
                "  "+ COLUMN_BILL_AREA_R +" longtext ,\n" +
                "  "+ COLUMN_CONNECTION_NUMBER_R +" longtext ,\n" +
                "  "+ COLUMN_CURRENT_READING_R +" double  ,\n" +
                "  "+ COLUMN_LATITUDE_R +" double  ,\n" +
                "  "+ COLUMN_LONGITUDE_R +" double  ,\n" +
                "  "+ COLUMN_METER_NUMBER_R +" longtext,\n" +
                "  "+ COLUMN_METER_STATUS_R +" double  ,\n" +
                "  "+ COLUMN_PREVIOUS_READING_R +" double  ,\n" +
                "  "+ COLUMN_PUSHED_R +" double  ,\n" +
                "  "+ COLUMN_READING_DATE_R +" varchar(255)   ,\n" +
                "  "+ COLUMN_READING_TYPE_R +" double  ,\n" +
                "  "+ COLUMN_ROUTE_R +" double  ,\n" +
                "  "+ COLUMN_SEQ_R +" double  ,\n" +
                "  "+ COLUMN_UPDATED_AT_R +" double  ,\n" +
                "  "+ COLUMN_UPDATED_BY_R +" double  \n" +
                ") ";

        //CREATE NEW READING

        String sql_create_tbl_new_reading = "CREATE TABLE "+ TABLE_NAME_NEW_READING +" (\n" +
                "  "+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  "+ COLUMN_METER_NUMBER +"  varchar(200)  ,\n" +
                "  "+ COLUMN_CURRENT_READING +" varchar(200)  ,\n" +
                "  "+ COLUMN_AREA +"  varchar(200) ,\n" +
                "  "+ COLUMN_CONNECTION_NUMBER +"  varchar(200),\n" +
                "  "+ COLUMN_LATITUDE +"  varchar(200) ,\n" +
                "  "+ COLUMN_LONGITUDE +"  varchar(200),\n" +
                "  "+ COLUMN_READING_DATE+"  varchar(200) \n" +
                ") ";

        sqLiteDatabase.execSQL(sql_createEmployee);

        sqLiteDatabase.execSQL(sql_create_tbl_connection);

        sqLiteDatabase.execSQL(sql_create_tbl_customer);

        sqLiteDatabase.execSQL(sql_create_tbl_areas);

        sqLiteDatabase.execSQL(sql_create_tbl_read_log);

        sqLiteDatabase.execSQL(sql_create_tbl_new_reading);

        sqLiteDatabase.execSQL(sql_createLogin);


        Log.e(TAG, "onCreate: Table creation Was Successful"+ TABLE_NAME_NEW_READING +" ,"+ TABLE_LOGIN +" ,"+ TABLE_NAME_CONNECTION +" ,"+ TABLE_NAME_CUSTOMERS +" ,"+ TABLE_NAME_AREAS +" ,"+ TABLE_NAME_READ_LOG +" ,");

    }

    public boolean addNewReading(String Meter_Number,String CurrentReading, String Area, String ConnectionNumber, String latitude, String longitude){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String readingDate = sdf.format(cal.getTime());
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_METER_NUMBER ,Meter_Number);
        cv.put(COLUMN_CURRENT_READING ,CurrentReading);
        cv.put(COLUMN_AREA ,Area);
        cv.put(COLUMN_CONNECTION_NUMBER ,ConnectionNumber);
        cv.put(COLUMN_LATITUDE ,latitude);
        cv.put(COLUMN_LONGITUDE ,longitude);
        cv.put(COLUMN_READING_DATE,readingDate);
        Log.e(TAG, "addNewReading: New Reading Added");
        return sqLiteDatabase.insert(TABLE_NAME_NEW_READING,null,cv) != 1;
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

        return sqLiteDatabase.insert(TABLE_NAME_NEW_READING,null,cv) != 1;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

      //  TABLE_LOGIN,TABLE_NAME_NEW_READING,TABLE_NAME_READ_LOG,TABLE_NAME_AREAS,TABLE_NAME_CUSTOMERS,TABLE_NAME_CONNECTION,TABLE_NAME
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_LOGIN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_NEW_READING);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_READ_LOG);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_AREAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CUSTOMERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CONNECTION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

        onCreate(sqLiteDatabase);


    }


    boolean addEmployee(String name, String dept, String joiningdate, double salary){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME ,name);
        cv.put(COLUMN_DEPT ,dept);
        cv.put(COLUMN_JOIN_DATE ,joiningdate);
        cv.put(COLUMN_SALARY ,salary);

        //Return -1 if no column is inserted
        return sqLiteDatabase.insert(TABLE_NAME,null,cv) != 1;

    }


public Cursor getallCustomerData(){
    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    Cursor res = sqLiteDatabase.rawQuery("Select * from "+ TABLE_NAME_CONNECTION,null);
        return res;
}

   public boolean addCustomers(String connection_number,String meter_number, String previous_date, String reading_date,
                         String current_reading,String previous_reading,String reading_type, String current_consumption,
                         String previous_consumption,String daily_average,String no_days,String average_consumption,String month,String zone,String route,
                               String seq,String done,String metered,String pushed,String updated_at,String updated_by,String date_updated,String sales_assistant_id3,
                         String longitude,String latitude,String disconnected )
    {
SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CONNECTION_NUMBER ,connection_number);
        cv.put(COLUMN_METER_NUMBER ,meter_number);
        cv.put(COLUMN_PREVIOUS_DATE,previous_date);
        cv.put(COLUMN_READING_DATE ,reading_date);
        cv.put(COLUMN_CURRENT_READING ,current_reading);
        cv.put(COLUMN_PREVIOUS_READING,previous_reading);
        cv.put(COLUMN_READING_TYPE,reading_type);
        cv.put(COLUMN_CURRENT_CONSUMPTION ,current_consumption);
        cv.put(COLUMN_PREVIOUS_CONSUMPTION,previous_consumption);
        cv.put(COLUMN_DAILY_AVERAGE ,daily_average);
        cv.put(COLUMN_NO_DAYS ,no_days);
        cv.put(COLUMN_AVARAGE_CONSYMPTION ,average_consumption);
        cv.put(COLUMN_MONTH ,month);
        cv.put(COLUMN_ZONE ,zone);
        cv.put(COLUMN_ROUTE ,route);
        cv.put(COLUMN_SEQ ,seq);
        cv.put(COLUMN_DONE ,done);
        cv.put(COLUMN_METERED ,metered);
        cv.put(COLUMN_PUSHED ,pushed);
        cv.put(COLUMN_UPDATED_AT ,updated_at);
        cv.put(COLUMN_UPDATED_BY ,updated_by);
        cv.put(COLUMN_DATE_UPDATED ,date_updated);
        cv.put(COLUMN_SALES_ASSISTANT_ID3 ,sales_assistant_id3);
        cv.put(COLUMN_LONGITUDE ,longitude);
        cv.put(COLUMN_LATITUDE ,latitude);
        cv.put(COLUMN_DISCONNECTED ,disconnected);
//sqLiteDatabase.beginTransaction();

        String sql= "Insert or Replace INTO "+ TABLE_NAME_CONNECTION +" ("
                + COLUMN_CONNECTION_NUMBER +","+ COLUMN_METER_NUMBER +","+ COLUMN_PREVIOUS_DATE +","+ COLUMN_READING_DATE +","
                + COLUMN_CURRENT_READING +","+ COLUMN_PREVIOUS_READING +","+ COLUMN_READING_TYPE +","+ COLUMN_CURRENT_CONSUMPTION +","+ COLUMN_PREVIOUS_CONSUMPTION +","+ COLUMN_DAILY_AVERAGE +","
                + COLUMN_NO_DAYS +","+ COLUMN_AVARAGE_CONSYMPTION +","+ COLUMN_MONTH +","+ COLUMN_ZONE +","+ COLUMN_ROUTE +","+ COLUMN_SEQ +","
                + COLUMN_DONE +","+ COLUMN_METERED +","+ COLUMN_PUSHED +","+ COLUMN_UPDATED_AT +","+ COLUMN_UPDATED_BY +","+ COLUMN_DATE_UPDATED +","
                + COLUMN_SALES_ASSISTANT_ID3 +","+ COLUMN_LONGITUDE +","+ COLUMN_LATITUDE +","+ COLUMN_DISCONNECTED +
                ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement insert = sqLiteDatabase.compileStatement(sql);



//Return -1 if no column is inserted
     // return sqLiteDatabase.insert(TABLE_NAME_CONNECTION,null,cv) != 1;
    long flag =   sqLiteDatabase.insert(TABLE_NAME_CONNECTION,null,cv);
    Log.i(TAG,"Add Customers was success full");
    return flag!=-1;

    }

    Cursor getAllEmployees(){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        // sqLiteDatabase.rawQuery("SELECT * FROM EMPLOYEE WHERE id = ?",0011);
        return sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME, null);

    }

    Cursor getAllCustomers(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return  sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_CONNECTION,null);
    }

    Cursor getCustomerByID(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return  sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME_CONNECTION,null);
    }

    boolean updateEmployee(int id, String name, String dept, double salary){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME ,name);
        cv.put(COLUMN_DEPT ,dept);
        cv.put(COLUMN_SALARY ,salary);

        return  sqLiteDatabase.update(TABLE_NAME, cv,COLUMN_ID + "=?",new String[] {String.valueOf(id)}) >0 ;

    }

    boolean deleteEmployee(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.delete(TABLE_NAME,COLUMN_ID + "=?",new String[] {String.valueOf(id)}) >0;
    }


}
