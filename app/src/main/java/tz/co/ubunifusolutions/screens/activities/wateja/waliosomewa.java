package tz.co.ubunifusolutions.screens.activities.wateja;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.adapters1.waliosomewaAdapter;
import tz.co.ubunifusolutions.screens.activities.wateja.floating.floatingWindow;
import tz.co.ubunifusolutions.screens.notification.myDialog_currentReading;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper_node;

public class waliosomewa extends AppCompatActivity implements waliosomewaAdapter.ItemClickListener {


    private static final String TAG = "Waliosomewa:";
    waliosomewaAdapter adapter;
    ListView lv;
    SearchView searchView_waliosomewa;
   // ArrayAdapter<String> adapter;



    private AlertDialog.Builder builder;
    private List<String> meterList;
    DatabaseHelper dataBaseHelper;
    ArrayList<String> animalNames;
    RecyclerView recyclerView;
    String data1= "";

   //String[] data={"A Sample","A Sample","A Sample","B Sample","C Sample","C Sample","D Sample","D Sample","D Sample","E Sample","E Sample","E Sample"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waliosomewa);



        // data to populate the RecyclerView with
        animalNames = new ArrayList<>();
        animalNames.add("Synchronise Data");

        searchView_waliosomewa = (SearchView) findViewById(R.id.idsearch_waliosomewa);


        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvAnimals);
        registerForContextMenu(recyclerView);
        dataBaseHelper = new DatabaseHelper(waliosomewa.this);

        //Cursor res = dataBaseHelper.getMeterNumber_RV();
        Cursor res = dataBaseHelper.getMeterNumber_waliosomewa();

        if (res.moveToFirst()) {
            animalNames.clear();
            do {
                //meterList.add(res.getString(2));

                animalNames.add(res.getString(res.getColumnIndex("meter_number")));
            } while (res.moveToNext());
        }

        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new waliosomewaAdapter(this, animalNames);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "An error While Generating List \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        searchView_waliosomewa.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                CharSequence key =  searchView_waliosomewa.getQuery();

                String Key = key.toString();
                dataBaseHelper = new DatabaseHelper(waliosomewa.this);

                // Cursor res = dataBaseHelper.getMeterNumber_RV_where(Key);

                Cursor res = dataBaseHelper.getMeterNumber_RVWaliosomewa_where(Key);
                if(res.moveToFirst()){
                    animalNames.clear();
                    do{
                        //meterList.add(res.getString(2));

                        animalNames.add(res.getString(res.getColumnIndex( "meter_number" )));
                    }while (res.moveToNext());
                }

                try {
                    recyclerView.setLayoutManager(new LinearLayoutManager(waliosomewa.this));
                    adapter = new waliosomewaAdapter(waliosomewa.this, animalNames);
                    adapter.setClickListener(waliosomewa.this);
                    recyclerView.setAdapter(adapter);

                }catch (Exception e ){
                    Toast.makeText(waliosomewa.this, "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();

                }

                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                CharSequence key =  searchView_waliosomewa.getQuery();

                String Key = key.toString();
                dataBaseHelper = new DatabaseHelper(waliosomewa.this);

               // Cursor res = dataBaseHelper.getMeterNumber_RV_where(Key);

                Cursor res = dataBaseHelper.getMeterNumber_RVWaliosomewa_where(Key);
                if(res.moveToFirst()){
                    animalNames.clear();
                    do{
                        //meterList.add(res.getString(2));

                        animalNames.add(res.getString(res.getColumnIndex( "meter_number" )));
                    }while (res.moveToNext());
                }

                try {
                    recyclerView.setLayoutManager(new LinearLayoutManager(waliosomewa.this));
                    adapter = new waliosomewaAdapter(waliosomewa.this, animalNames);
                    adapter.setClickListener(waliosomewa.this);
                    recyclerView.setAdapter(adapter);

                }catch (Exception e ){
                    Toast.makeText(waliosomewa.this, "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();

                }

                return true;

            }
        });



    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
/*If kuna more than one item zenye context tumia switch (v.getId())

* */
       menu.setHeaderTitle("More Options");

   getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.edit_read:

               Toast.makeText(this, "Edit Reading", Toast.LENGTH_SHORT).show();
               return true;

           case R.id.Delete_read:
               Toast.makeText(this, "Edit Reading", Toast.LENGTH_SHORT).show();
               return true;
           default:
               return super.onContextItemSelected(item);

       }

       }

    public String getreading(String key){
        String key1 = key;
        dataBaseHelper = new DatabaseHelper(waliosomewa.this);

        // Cursor res = dataBaseHelper.getMeterNumber_RV_where(Key);

      //  Cursor res = dataBaseHelper.getAllReadings(key1);
        Cursor res = dataBaseHelper.getAllReadings_Num(key1);
        String connection_number,current_reading,meter_number,readingDate,bill_area,status,data1= "",route,seq;

        if(res.moveToFirst()){
            do{
                // animalNames.add(res.getString(res.getColumnIndex( "meter_number" )));
                meter_number = res.getString(res.getColumnIndex("meter_number"));
                connection_number = res.getString(res.getColumnIndex("connection_number"));
                current_reading = res.getString(res.getColumnIndex("current_reading"));
                 readingDate = res.getString(res.getColumnIndex("reading_date"));
                bill_area = res.getString(res.getColumnIndex("bill_area"));
                route = res.getString(res.getColumnIndex("route"));
                seq = res.getString(res.getColumnIndex("seq"));
                status = res.getString(res.getColumnIndex("meter_status"));

                data1 += "\nMeter Number: "+meter_number;
                data1 += "\nConnection_number: " + connection_number;
                data1 += "\nCurrent_reading: " + current_reading;
                data1 += "\nReading Date: " + readingDate;
                data1 += "\nBilling Area: " + bill_area;
                data1 += "\nRoute: " + route;
                data1 += "\nSequence: " + seq;
                data1 += "\nMeter Status:" + status;

            }while (res.moveToNext());
            Log.e(TAG,data1);
        }

        return data1;
    }

    @Override
    protected void onStart() {
        super.onStart();

        CharSequence key =  searchView_waliosomewa.getQuery();

        String Key = key.toString();
        dataBaseHelper = new DatabaseHelper(waliosomewa.this);

        // Cursor res = dataBaseHelper.getMeterNumber_RV_where(Key);

        //  Cursor res = dataBaseHelper.getAllReadings(key1);
       // Cursor res = dataBaseHelper.getAllReadings(key1);
        Cursor res = dataBaseHelper.getAllReadings_Num(Key);
        String connection_number,current_reading,meter_number,readingDate,bill_area,status;

        if(res.moveToFirst()){

            do{
                // animalNames.add(res.getString(res.getColumnIndex( "meter_number" )));
                meter_number = res.getString(res.getColumnIndex("meter_number"));
                connection_number = res.getString(res.getColumnIndex("connection_number"));
                current_reading = res.getString(res.getColumnIndex("current_reading"));
             //   readingDate = res.getString(res.getColumnIndex("readingDate"));
                bill_area = res.getString(res.getColumnIndex("bill_area"));
//                status = res.getString(res.getColumnIndex("status"));

                data1 += "\nMeter Number:"+meter_number;
                data1 += "\n connection_number:" + connection_number;
                data1 += "\n current_reading:" + current_reading;
             //   data1 += "\n readingDate:" + readingDate;
                data1 += "\n bill_area:" + bill_area;
              //  data1 += "\n status:" + status;

            }while (res.moveToNext());
            Log.e(TAG,data1);
        }



    }

    @Override
    public void onItemClick(View view, int position) {
        String meternumber = adapter.getItem(position);
        if(meternumber.equals("Synchronise Data")){
            Toast.makeText(this, "Please Update your Database to Continue", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            String readingmoja=getreading(meternumber);

            Bundle args = new Bundle();
            args.putString("conn_number", adapter.getItem(position));
           args.putString("data", readingmoja);
           myDialog_currentReading mydialog = new myDialog_currentReading();

           mydialog.setArguments(args);
           mydialog.show(getSupportFragmentManager(),"Current Reading");

          //  startService(new Intent(waliosomewa.this, floatingWindow.class));

        }
    }



    public void showtoast(String cc)
    {
      //  String c = String.valueOf(userInput.getText());
       // Toast.makeText(this, "From input dialog"+cc, Toast.LENGTH_SHORT).show();
    }


}
