package tz.co.ubunifusolutions.screens.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.adapters1.readSearch_Adapter;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper_node;

public class read_Search extends AppCompatActivity implements readSearch_Adapter.ItemClickListener {
    readSearch_Adapter adapter;
    SearchView searchView_bado;

    private List<String> meterList;
    DatabaseHelper dataBaseHelper;
    ArrayList<String> animalNames;
    ArrayList<String> jinaLaMteja;
    RecyclerView recyclerView;
    CheckBox chkConnNumber,chkMeterNumber,chkName;

    String Full_name = "";
    String f_name,m_name,l_name;
    Cursor res,res_name;
    private static final String TAG = "From Read Search class";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_search);
        searchView_bado= (SearchView) findViewById(R.id.idsearch_read);
        chkConnNumber = (CheckBox) findViewById(R.id.chkConnNumber);
        chkConnNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkConnNumber.isChecked()){
                    chkName.setChecked(false);
                    chkMeterNumber.setChecked(false);
                    searchView_bado.setInputType( InputType.TYPE_CLASS_NUMBER);
                    searchView_bado.requestFocus();
                }
            }
        });
        chkMeterNumber = (CheckBox) findViewById(R.id.chkMeterNumber);
        chkMeterNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkMeterNumber.isChecked()){
                    chkConnNumber.setChecked(false);
                    chkName.setChecked(false);
                    searchView_bado.setInputType( InputType.TYPE_CLASS_NUMBER);
                    searchView_bado.requestFocus();
                }
            }
        });

        chkName = (CheckBox) findViewById(R.id.chkName);
        chkName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkMeterNumber.setChecked(false);
                chkConnNumber.setChecked(false);
                searchView_bado.setInputType( InputType.TYPE_CLASS_TEXT);
                searchView_bado.requestFocus();

            }
        });

        chkMeterNumber.setChecked(true);

       // boolean checked = ((CheckBox) view).isChecked();

        // data to populate the RecyclerView with
        animalNames = new ArrayList<>();
       // jinaLaMteja = new ArrayList<>();

        animalNames.add("Synchronise Data");
       // jinaLaMteja.add("Synchronise Data");




        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvAnimals);

        dataBaseHelper = new DatabaseHelper(read_Search.this);

        res = dataBaseHelper.getMeterNumber_RV();
        res_name = dataBaseHelper.getName_RV();
       if(res.moveToFirst()){
            animalNames.clear();
            do{
                //meterList.add(res.getString(2));

                animalNames.add(res.getString(res.getColumnIndex( "meter_number" )));
               // animalNames.add(res.getString(res.getColumnIndex("connection_number")));
            }while (res.moveToNext());

        }

       //if(res_name !=null && res_name.getCount()>0)
        if(res_name.moveToFirst()){
            do{
                 f_name = res_name.getString(res_name.getColumnIndex("first_name"));
               // Toast.makeText(read_Search.this, f_name , Toast.LENGTH_SHORT).show();
                 m_name = res_name.getString(res_name.getColumnIndex("middle_name"));
                 l_name = res_name.getString(res_name.getColumnIndex("last_name"));
                Full_name = f_name + " " + m_name + " " +l_name;
                //Log.i(TAG,Full_name);
           //     animalNames.add(Full_name);
            } while(res_name.moveToNext());
        }
//        for (int i = 0; i < animalNames.size(); i++) {
//           Log.i(TAG,animalNames.get(i));
//        }
        res.close();
        res_name.close();
try {
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new readSearch_Adapter(this, animalNames);
  //  adapter = new readSearch_Adapter(this,jinaLaMteja);

    adapter.setClickListener(this);
    recyclerView.setAdapter(adapter);

}catch (Exception e ){
    Toast.makeText(this, "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();
//    recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    adapter = new readSearch_Adapter(this, animalNames);
//    adapter.setClickListener(this);
//    recyclerView.setAdapter(adapter);
}

searchView_bado.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        String desc = "";
        if(chkConnNumber.isChecked()){
            desc = "Connection_Number";

        }else if(chkMeterNumber.isChecked())
        {  desc = "Meter_Number";}
        else if(chkName.isChecked())
        {  desc = "Name";}
        Log.e(TAG, "onQueryTextSubmit: "+desc );
       CharSequence key =  searchView_bado.getQuery();

       String Key = key.toString();
        dataBaseHelper = new DatabaseHelper(read_Search.this);

        Cursor res = dataBaseHelper.getMeterNumber_RV_where(Key,desc);

        if(res.moveToFirst()){
            animalNames.clear();
            do{
                //meterList.add(res.getString(2));

                animalNames.add(res.getString(res.getColumnIndex( "meter_number" )));
               // animalNames.add(res.getString(res.getColumnIndex("connection_number")));
/**Changes za 15/08/2019*/
              // animalNames.add(res.getString(res.getColumnIndex("first_name")));
             //  animalNames.add(res.getString(res.getColumnIndex("middle_name")));
            //   animalNames.add(res.getString(res.getColumnIndex("last_name")));
             //   f_name = res.getString(res.getColumnIndex("first_name"));
           //     m_name = res.getString(res.getColumnIndex("middle_name"));
           //     l_name = res.getString(res.getColumnIndex("last_name"));
           //     Full_name = f_name +" "+ m_name +" "+l_name;
           // /    animalNames.add(Full_name);
           //    animalNames.add(res.getString(res.getColumnIndex("work_phone")));
           //    animalNames.add(res.getString(res.getColumnIndex("bill_area")));

                /**Mwisho*/

            }while (res.moveToNext());
        }

        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(read_Search.this));
            adapter = new readSearch_Adapter(read_Search.this, animalNames);
            adapter.setClickListener(read_Search.this);
            recyclerView.setAdapter(adapter);

        }catch (Exception e ){
            Toast.makeText(read_Search.this, "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();

        }

        return true;

    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.chkConnNumber:
                if (checked){
                    chkMeterNumber.setChecked(true);
                }
                // Put some meat on the sandwich
            else
                // Remove the meat
                break;
            case R.id.chkMeterNumber:
                if (checked){

                }
                // Cheese me
            else
                // I'm lactose intolerant
                break;
            case R.id.chkName:
                if (checked){}
                // Cheese me
                else
                    // I'm lactose intolerant
                    break;
            // TODO: Veggie sandwich
        }
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        String desc = "";
        if(chkConnNumber.isChecked()){
            desc = "Connection_Number";
        }else if(chkMeterNumber.isChecked())
        {  desc = "Meter_Number";}
        else if(chkName.isChecked())
        {  desc = "Name";}
        Log.e(TAG, "onQueryTextChange: "+desc );
        CharSequence key =  searchView_bado.getQuery();

        String Key = key.toString();
        dataBaseHelper = new DatabaseHelper(read_Search.this);

        Cursor res = dataBaseHelper.getMeterNumber_RV_where(Key,desc);

        if(res.moveToFirst()){
            animalNames.clear();
            do{
                //meterList.add(res.getString(2));

                animalNames.add(res.getString(res.getColumnIndex( "meter_number" )));
               // animalNames.add(res.getString(res.getColumnIndex("connection_number")));

                /**Changes za 15/08/2019*/
              //  animalNames.add(res.getString(res.getColumnIndex("first_name")));
              //  animalNames.add(res.getString(res.getColumnIndex("middle_name")));
             //   animalNames.add(res.getString(res.getColumnIndex("last_name")));
              //  f_name = res.getString(res.getColumnIndex("first_name"));
             //   m_name = res.getString(res.getColumnIndex("middle_name"));
             //   l_name = res.getString(res.getColumnIndex("last_name"));
            //    Full_name = f_name +" "+ m_name +" "+l_name;
             //   animalNames.add(Full_name);
             //   animalNames.add(res.getString(res.getColumnIndex("work_phone")));
             //  animalNames.add(res.getString(res.getColumnIndex("bill_area")));

                /**Mwisho*/
            }while (res.moveToNext());
        }

        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(read_Search.this));
            adapter = new readSearch_Adapter(read_Search.this, animalNames);
            adapter.setClickListener(read_Search.this);
            recyclerView.setAdapter(adapter);

        }catch (Exception e ){
            Toast.makeText(read_Search.this, "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();

        }

        return true;
    }
});

}

    @Override
    public void onItemClick(View view, int position) {
        String meternumber = adapter.getItem(position);
        if(meternumber.equals("Synchronise Data")){
            Toast.makeText(this, "Please Update your Database to Continue", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            //  Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(read_Search.this, new_Reading.class);
            intent.putExtra("item", adapter.getItem(position));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }


}
