package tz.co.ubunifusolutions.screens.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper_node;

public class route_Details extends AppCompatActivity {

    String meternumber,area,zone,seq,workphone,route,connection_number,name1;
    TextInputLayout meterTil;
    TextInputEditText meterTilTXT;
    TextInputLayout areaTil;
    TextInputEditText areaTilTXT;
    TextInputLayout zoneTil;
    TextInputEditText zoneTilTXT;
    TextInputLayout seqTil;
    TextInputEditText seqTilTXT ;
    TextInputLayout nameTil;
    TextInputEditText nameTilTXT ;
    TextInputLayout routeTil;
    TextInputEditText routeTilTXT ;

    Button modify_route;

    DatabaseHelper dataBaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);

        Intent i = getIntent();
        meternumber= i.getStringExtra("item").trim();

        dataBaseHelper = new DatabaseHelper(route_Details.this);


        meterTil = (TextInputLayout) findViewById(R.id.meterNumber_TIL);
        meterTilTXT = (TextInputEditText) findViewById(R.id.meterNumber_TIL_txt);
        meterTilTXT.setText(meternumber);
        areaTil = (TextInputLayout) findViewById(R.id.area_TIL);
        areaTilTXT = (TextInputEditText) findViewById(R.id.area_TIL_txt);
       // areaTil.setEnabled(false);
        zoneTil = (TextInputLayout) findViewById(R.id.zone_TIL);
        zoneTilTXT = (TextInputEditText) findViewById(R.id.zone_TIL_txt);
       // zoneTil.setEnabled(false);
        seqTil = (TextInputLayout) findViewById(R.id.sequence_TIL);
        seqTilTXT = (TextInputEditText) findViewById(R.id.sequence_TIL_txt);

        nameTil = (TextInputLayout) findViewById(R.id.name_TIL);
        nameTilTXT = (TextInputEditText) findViewById(R.id.name_TIL_txt);
        routeTil = (TextInputLayout) findViewById(R.id.route_TIL);
        routeTilTXT = (TextInputEditText) findViewById(R.id.route_TIL_txt);



        modify_route= (Button)findViewById(R.id.btnmodify_route);
        modify_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("WARNING","You are about to Change Customer Routeee \n Are You Sure ?","cancelMethod1","okMethod1");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            // dataBaseHelper.settingDrop();
            Cursor res = dataBaseHelper.route_Details(meternumber);
            if (res != null && res.getCount() > 0) {
                while (res.moveToNext()) {
                    zone = res.getString(res.getColumnIndex("zone"));
                    seq = res.getString(res.getColumnIndex("seq"));
                    route = res.getString(res.getColumnIndex("route"));
                    connection_number = res.getString(res.getColumnIndex("connection_number"));
                    Toast.makeText(this, "Routeee Details: "+connection_number, Toast.LENGTH_SHORT).show();
                    Log.i("Routeee Details: ",connection_number);
                }
            } else {
                Toast.makeText(route_Details.this, "Couldn't get All Routeee Details", Toast.LENGTH_SHORT).show();
            }
            res.close();

            // dataBaseHelper.settingDrop();
            Cursor res1 = dataBaseHelper.getAllCustomers_ByConnectionNumber(connection_number);
            if (res1 != null ) {
                while (res1.moveToNext()) {
                    String fname = res1.getString(res1.getColumnIndex("first_name"));
                    if( fname.equals(null)){ fname = "";}
                    String mname = res1.getString(res1.getColumnIndex("middle_name"));
                    if( mname.equals(null)){mname = "";}
                    String lname = res1.getString(res1.getColumnIndex("last_name"));
                    if( lname.equals(null)){lname = "";}
                    name1 = fname + " " + mname + " " + lname;
                    area = res1.getString(res1.getColumnIndex("bill_area"));


                }
            }else {
                Toast.makeText(route_Details.this, "Couldn't get All Routeee Details", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Error Has Occoured"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        zoneTilTXT.setText(zone);
        seqTilTXT.setText(seq);
        routeTilTXT.setText(route);
        nameTilTXT.setText(name1);
        areaTilTXT.setText(area);

    }


    public void customDialog(String title, String message, final String cancelMethod, final String okMethod)
    {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(route_Details.this);
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
                        }
                    }
                }
        );
        builderSingle.show();
    }

    public  void cancelMethod1(){
        return;

    }
    public  void okMethod1()
    {

        area = areaTilTXT.getText().toString().trim();
        zone = zoneTilTXT.getText().toString().trim();
        seq = seqTilTXT.getText().toString().trim();
        name1 = nameTilTXT.getText().toString().trim();
        route = routeTilTXT.getText().toString().trim();
        //  Toast.makeText(Nyumbani.this, "Signed off", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(route_Details.this, route_Details_Modify.class);
        intent.putExtra("meternumber",meternumber);
        intent.putExtra("area",area);
        intent.putExtra("zone",zone);
        intent.putExtra("seq",seq);
        intent.putExtra("name",name1);
        intent.putExtra("route",route);
        startActivity(intent);
        finish();

    }


}
