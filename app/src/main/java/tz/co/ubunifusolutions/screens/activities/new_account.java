package tz.co.ubunifusolutions.screens.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.models.DefaultResponse;
import tz.co.ubunifusolutions.screens.api.RetrofitClient;
import tz.co.ubunifusolutions.screens.storage.SharesPrefManager;


public class new_account extends AppCompatActivity implements View.OnClickListener {
Button btnCancel,btnOk;
EditText txtfirstname,txtlastname,txtpassword,txtpassword2,txtusername;
private static String TAG = "New Account";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        txtfirstname = findViewById(R.id.txtfirstname);
        txtlastname = findViewById(R.id.txtlastname);
        txtusername = findViewById(R.id.txtusername1);
        txtpassword = findViewById(R.id.txtpassword1);
        txtpassword2 = findViewById(R.id.txtpassword2);
        btnOk = findViewById(R.id.btnok);
        btnOk.setOnClickListener(this);
        btnCancel= findViewById(R.id.btncancel);
        btnCancel.setOnClickListener(this);


    }
    @Override
    protected void onStart() {
        super.onStart();
        if (SharesPrefManager.getmInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


    private void usersignup(){
        String username=txtusername.getText().toString().trim();
        String password=txtpassword.getText().toString().trim();
        String first_name=txtfirstname.getText().toString().trim();
        String last_name=txtlastname.getText().toString().trim();
        String password2=txtpassword2.getText().toString().trim();

/*
* Validate the data za mtu*/

        if(first_name.isEmpty()) {
        txtfirstname.setError("First Name is Required");
        txtfirstname.requestFocus();
        return;
        }
        if(last_name.isEmpty()){

            txtlastname.setError("Last Name is Required");
            txtlastname.requestFocus();
            return;
        }

        if(username.isEmpty()){
            txtusername.setError("Username is required");
            txtusername.requestFocus();
            return;
        }
        if(password.isEmpty() || password2.isEmpty()){
            txtpassword.setError("Password is required");
            txtpassword.requestFocus();
            return;
        }
        if(!(password.length()== password2.length())){
            txtpassword.setError("Password Should Match");
            txtpassword.requestFocus();
            return;
        }
        if(password.length() < 4){
            txtpassword.setError("Password Should be 6 Characters long");
            txtpassword.requestFocus();
            return;
        }


/*        *//*
        * Do User Reg using API Call*/

       // Call <DefaultResponse> call = RetrofitClient.getInstance().getApi().createUser(username,password,first_name,last_name);
 Call<DefaultResponse> call =RetrofitClient.getInstance().getApi().createUser(username,password,first_name,last_name);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                DefaultResponse dr = response.body();
                if(response.code()==201){

                    Toast.makeText(new_account.this,dr.getMsg(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }
                else if (response.code()==422){
                    Toast.makeText(new_account.this,"Some Error Occurred", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    //Toast.makeText(new_account.this,"Somethung Happened: "+dr.getMsg(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(new_account.this, "Mhh kuna tatizo", Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"Mhh kuna tatizo: "+response.message());
                }

                /*else{
String s= response.errorBody().string();

                }*/

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(new_account.this, "Mhh kuna tatizo: \n "+ t, Toast.LENGTH_SHORT).show();

            }
        });





        /*call.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        String s = null;
        try {
            if (response.code() == 201) {
                s = response.body().string();
                Toast.makeText(new_account.this,"Request sent Successfull \n Contact Your System Administrator\n To Activate your Account",Toast.LENGTH_LONG).show();
            } else {
                s = response.errorBody().string();
                JSONObject json = new JSONObject(s);
                Toast.makeText(new_account.this, json.getString("message"), Toast.LENGTH_LONG).show();

                // Toast.makeText(new_account.this, s, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();

       if(s != null){
           try {
               JSONObject json = new JSONObject(s);
               Toast.makeText(new_account.this, json.getString("message"), Toast.LENGTH_LONG).show();

           } catch (JSONException e1) {
               e1.printStackTrace();
           }
       }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(new_account.this,t.getMessage(),Toast.LENGTH_LONG).show();
    }
});*/

    }

 @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.btnok:
        usersignup();

     break;
    case R.id.btncancel:
      //  Toast.makeText(getApplicationContext(), "Put Action to Clear All", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent1);
        break;


}
    }
}
