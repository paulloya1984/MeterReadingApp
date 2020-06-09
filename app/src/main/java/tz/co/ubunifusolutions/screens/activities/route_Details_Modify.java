package tz.co.ubunifusolutions.screens.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.wateja.Routeee;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper_node;

public class route_Details_Modify extends AppCompatActivity {

    Button save,cancel;
    String meternumber,area,zone,seq,name1,route;
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

    DatabaseHelper dataBaseHelper;

    String name_tmp,area_tmp,zone_tmp,seq_tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details_modify);

        Intent i = getIntent();
        meternumber= i.getStringExtra("meternumber").trim();
        area = i.getStringExtra("area").trim();
        zone = i.getStringExtra("zone").trim();
        seq = i.getStringExtra("seq").trim();
        name1 = i.getStringExtra("name");
        route = i.getStringExtra("route").trim();
        /**
         *  intent.putExtra("meternumber",meternumber);
         *         intent.putExtra("area",area);
         *         intent.putExtra("zone",zone);
         *         intent.putExtra("seq",seq);
         *         intent.putExtra("name",name1);
         *         intent.putExtra("route",route);*/


        save = (Button) findViewById(R.id.btnSave_M);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //savedata();
                customDialog("WARNING","You Are About to modify Customers Routeee \n Are You Sure ?","cancelMethod1","okMethod2");

            }
        });

        cancel =(Button) findViewById(R.id.btnCancel_M);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("WARNING","You Are About to cancel modifying  Routeee \n Are You Sure ?","cancelMethod1","okMethod1");

            }
        });


        meterTil = (TextInputLayout) findViewById(R.id.meterNumber_TIL_RModify);
        meterTilTXT = (TextInputEditText) findViewById(R.id.meterNumber_TILTXT_RModify);
        meterTilTXT.setText(meternumber);
        meterTilTXT.setEnabled(false);

        areaTil = (TextInputLayout) findViewById(R.id.areaTil_M);
        areaTilTXT = (TextInputEditText) findViewById(R.id.areaTilTXT_M);
        areaTilTXT.setText(area);

        zoneTil = (TextInputLayout) findViewById(R.id.zoneTil_M);
        zoneTilTXT = (TextInputEditText) findViewById(R.id.zoneTilTXT_M);
        zoneTilTXT.setText(zone);

        seqTil = (TextInputLayout)findViewById(R.id.seqTil_M);
        seqTilTXT = (TextInputEditText) findViewById(R.id.seqTilTXT_M);
        seqTilTXT.setText(seq);

        nameTil = (TextInputLayout)findViewById(R.id.nameTil_M);
        nameTilTXT = (TextInputEditText) findViewById(R.id.nameTilTXT_M);
        nameTilTXT.setText(name1);
        nameTilTXT.setEnabled(false);

        routeTil = (TextInputLayout)findViewById(R.id.routeTil_M);
        routeTilTXT = (TextInputEditText) findViewById(R.id.routeTilTXT_M);
        routeTilTXT.setText(route);

    }

    public void getRoute()
    {
        //route_Details
        dataBaseHelper = new DatabaseHelper(route_Details_Modify.this);

        Cursor result = dataBaseHelper.getArea(meternumber);

        Cursor res = dataBaseHelper.route_Details(meternumber);
        if(res.moveToFirst()){
            do{
                //meterList.add(res.getString(2));

               // animalNames.add(res.getString(res.getColumnIndex( "meter_number" )));
                // name_tmp,area_tmp,zone_tmp,seq_tmp
               // area_tmp = res.getString(res.getColumnIndex(""))
                zone_tmp = res.getString(res.getColumnIndex(""));
                seq_tmp = res.getString(res.getColumnIndex(""));

            }while (res.moveToNext());
        }
    }
public void savedata()
{


}
    public void customDialog(String title, String message, final String cancelMethod, final String okMethod)
    {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(route_Details_Modify.this);
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
                        else  if(okMethod.equals("okMethod2")){
                            okMethod2();
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
        Intent intent = new Intent(route_Details_Modify.this, Routeee.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }
    public  void okMethod2()
    {
        dataBaseHelper = new DatabaseHelper((route_Details_Modify.this));

        if (dataBaseHelper.insertRouteModify(name1,meternumber,area,zone,seq,route))
        {
            Toast.makeText(route_Details_Modify.this, "Your Request to modify route has been sent \n Please Follow up with your Supervisor ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(route_Details_Modify.this, Routeee.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        } else {
            Toast.makeText(route_Details_Modify.this, "An Error Has Occurred", Toast.LENGTH_LONG).show();

        }



    }



}
