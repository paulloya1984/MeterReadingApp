package tz.co.ubunifusolutions.screens.api;

import android.util.Log;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import tz.co.ubunifusolutions.screens.models.DefaultResponse;
import tz.co.ubunifusolutions.screens.models.LoginResponse;
import tz.co.ubunifusolutions.screens.models.SyncResponse;

public interface Api {
    public static final String TAG = "Api";

    @FormUrlEncoded
    @POST("createuser")
    Call<DefaultResponse> createUser(
            @Field("username") String username,
            @Field("password") String password,
            @Field("first_name") String first_name,
           @Field("last_name") String last_name
    );
/*
*   $log['id'] = $id;
     $log['bill_area']=$bill_area;
     $log['connection_number'] = $connection_number;
     $log['current_reading'] = $current_reading;
     $log['latitude'] = $latitude;
     $log['longitude'] = $longitude;
     $log['meter_number'] = $meter_number;
     $log['meter_status'] = $meter_status;
     $log['previous_reading'] = $previous_reading;
     $log['pushed'] = $pushed;
     $log['reading_date'] = $reading_date;
     $log['reading_type'] = $reading_type;
     $log['route'] = $route;
     $log['seq'] = $seq;
     $log['updated_at'] = $updated_at;
     $log['updated_by'] = $updated_by;
     * bill_area, connection_number, current_reading, latitude, longitude, meter_number,
     * meter_status, previous_reading, pushed, readingDate, read_type, route, seq, updated_at, updated_by are missing or empty"
}*/


    @FormUrlEncoded
    @POST("createread")

    Call<SyncResponse> readlog(

   // Call<ResponseBody> readlog(
           // @Field("id") String id,
            @Field("bill_area") String bill_area,
            @Field("connection_number") String connection_number,
            @Field("current_reading") String current_reading,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("meter_number") String meter_number,
            @Field("meter_status") String meter_status,
            @Field("previous_reading") String previous_reading,
            @Field("pushed") String pushed,
            @Field("reading_date") String reading_date,
            @Field("reading_type") String reading_type,
            @Field("route") String route,
            @Field("seq") String seq,
          //  @Field("updated_at") String updated_at,
           @Field("updated_by") String updated_by
    );
/**
 *  bill_area, connection_number, current_reading, latitude, longitude, meter_number, meter_status,
 *  previous_reading, pushed, readingDate, read_type, route, seq*/
    @FormUrlEncoded
    @POST("userlogin")
Call<LoginResponse> userLogin(
        @Field("username") String username,
        @Field("password") String password
);

    /**
     * Uploading Json File
     * haifanyi kazi*/
    @Multipart
    @POST("upload_data")
    Call<ResponseBody>uploadFile(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );

}
