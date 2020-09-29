package tz.co.ubunifusolutions.screens.activities;

import android.content.Context;
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
import tz.co.ubunifusolutions.screens.activities.wateja.waliosomewa;
import tz.co.ubunifusolutions.screens.adaptors.Badosomewa_Adaptor1;
import tz.co.ubunifusolutions.screens.models.Badosomewa_Model1;

import tz.co.ubunifusolutions.screens.models.Waliosomewa_Model1;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper_node;

public class read_Search extends AppCompatActivity implements readSearch_Adapter.ItemClickListener {
    readSearch_Adapter adapter;
    SearchView searchView_bado;

    private List<String> meterList;
    DatabaseHelper dataBaseHelper,dtHelper;
    ArrayList<String> animalNames;
//    ArrayList<String> jinaLaMteja;

    List<Badosomewa_Model1> badosomewa_model1List;
    RecyclerView recyclerView;
    CheckBox chkConnNumber,chkMeterNumber,chkName;

    String Full_name = "";
    String f_name,m_name,l_name;
    Cursor res,res_name,rs;
    private static final String TAG = "From Read Search class";
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_search);
        dataBaseHelper = new DatabaseHelper(read_Search.this);
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
        read_Search.mContext = getApplicationContext();

 // data to populate the RecyclerView with
        animalNames = new ArrayList<>();

        animalNames.add("Synchronise Data");
      
        // set up the RecyclerView

        recyclerView = findViewById(R.id.rvAnimals);

        initData();
        setRecyclerView();



        //search view
        

searchView_bado.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

    @Override
    public boolean onQueryTextSubmit(String query) {
        String meter_Number = "", connection_Number = "",   customer_Name ="";
        badosomewa_model1List = new ArrayList<>();

        String desc = "";
        if (chkConnNumber.isChecked()) {
            desc = "Connection_Number";
            searchView_bado.setQueryHint("Enter Valid Account Number");

        } else if (chkMeterNumber.isChecked()) {
            desc = "Meter_Number";
            searchView_bado.setQueryHint("Enter Valid Meter Number");

        } else if (chkName.isChecked()) {
            desc = "Name";
            searchView_bado.setQueryHint("Enter Customer Name");
        }
      //  Log.e(TAG, "onQueryTextSubmit: " + desc);
        CharSequence key = searchView_bado.getQuery();
        String Key = key.toString();

        dataBaseHelper = new DatabaseHelper(read_Search.this);
        Cursor res = dataBaseHelper.getMeterNumber_RV_where(Key, desc);

        if (res.moveToFirst()) {
            badosomewa_model1List.clear();
            do {
                meter_Number = res.getString(res.getColumnIndex("meter_number"));
                connection_Number = res.getString(res.getColumnIndex("connection_number"));
                Cursor res_namba = dataBaseHelper.getCustomer_Number(connection_Number);
                if (res_namba.moveToFirst()) {
                    do {
                        String f_name = res_namba.getString(res_namba.getColumnIndex("first_name"));
                        String m_name = res_namba.getString(res_namba.getColumnIndex("middle_name"));
                        String l_name = res_namba.getString(res_namba.getColumnIndex("last_name"));
                        customer_Name = f_name + " " + m_name + " " + l_name;
                    } while (res_namba.moveToNext());
                }
                badosomewa_model1List.add(new Badosomewa_Model1(meter_Number,connection_Number,customer_Name,true,"","",""));
            } while (res.moveToNext());
        } else if (res.moveToFirst() == false) {
            switch (desc) {
                case "Connection_Number":
                    if (Key.length() == 0) {
                        adapter.notifyDataSetChanged();
                        dtHelper = new DatabaseHelper(read_Search.this);

                        rs = dtHelper.getMeterNumber_RV();

                        if (rs.moveToFirst()) {
                            badosomewa_model1List.clear();
                            do {
                               meter_Number = rs.getString(rs.getColumnIndex("meter_number"));
                                connection_Number = rs.getString(rs.getColumnIndex("connection_number"));

                                Cursor res_namba = dataBaseHelper.getCustomer_Number(connection_Number);
                                if (res_namba.moveToFirst()) {
                                    do {
                                        String f_name = res_namba.getString(res_namba.getColumnIndex("first_name"));
                                        String m_name = res_namba.getString(res_namba.getColumnIndex("middle_name"));
                                        String l_name = res_namba.getString(res_namba.getColumnIndex("last_name"));
                                        customer_Name = f_name + " " + m_name + " " + l_name;
                                    } while (res_namba.moveToNext());
                                }
                                badosomewa_model1List.add(new Badosomewa_Model1(meter_Number,connection_Number,customer_Name,true,"","",""));
                            } while (rs.moveToNext());
                        }
                    } else if (Key.length() != 6) {
                       badosomewa_model1List.clear();
                       badosomewa_model1List.add(new Badosomewa_Model1("Account Haipo/Not Valid !!","","",true,"","",""));
                    }
                    break;
                case "Meter_Number":
                    if (Key.length() == 0) {
                        adapter.notifyDataSetChanged();
                        dtHelper = new DatabaseHelper(read_Search.this);

                        rs = dtHelper.getMeterNumber_RV();

                        if (rs.moveToFirst()) {
                            badosomewa_model1List.clear();
                            do {
                                meter_Number = rs.getString(rs.getColumnIndex("meter_number"));
                                connection_Number = rs.getString(rs.getColumnIndex("connection_number"));

                                Cursor res_namba = dataBaseHelper.getCustomer_Number(connection_Number);
                                if (res_namba.moveToFirst()) {
                                    do {
                                        String f_name = res_namba.getString(res_namba.getColumnIndex("first_name"));
                                        String m_name = res_namba.getString(res_namba.getColumnIndex("middle_name"));
                                        String l_name = res_namba.getString(res_namba.getColumnIndex("last_name"));
                                        customer_Name = f_name + " " + m_name + " " + l_name;
                                    } while (res_namba.moveToNext());
                                }
                                badosomewa_model1List.add(new Badosomewa_Model1(meter_Number,connection_Number,customer_Name,true,"","",""));
                            } while (rs.moveToNext());
                        }
                    } else {
                        badosomewa_model1List.clear();
                        badosomewa_model1List.add(new Badosomewa_Model1("Mita Haipo/Not Valid !!","","",true,"","",""));
                    }
                    break;
                case "Name":
                    if (Key.length() == 0) {
                        adapter.notifyDataSetChanged();
                        dtHelper = new DatabaseHelper(read_Search.this);

                        rs = dtHelper.getMeterNumber_RV();

                        if (rs.moveToFirst()) {
                            badosomewa_model1List.clear();
                            do {
                                meter_Number = rs.getString(rs.getColumnIndex("meter_number"));
                                connection_Number = rs.getString(rs.getColumnIndex("connection_number"));

                                Cursor res_namba = dataBaseHelper.getCustomer_Number(connection_Number);
                                if (res_namba.moveToFirst()) {
                                    do {
                                        String f_name = res_namba.getString(res_namba.getColumnIndex("first_name"));
                                        String m_name = res_namba.getString(res_namba.getColumnIndex("middle_name"));
                                        String l_name = res_namba.getString(res_namba.getColumnIndex("last_name"));
                                        customer_Name = f_name + " " + m_name + " " + l_name;
                                    } while (res_namba.moveToNext());
                                }
                                badosomewa_model1List.add(new Badosomewa_Model1(meter_Number,connection_Number,customer_Name,true,"","",""));
                            } while (rs.moveToNext());
                        }
                    } else {
                        badosomewa_model1List.clear();
                        badosomewa_model1List.add(new Badosomewa_Model1("Jina Halipo/Not Valid !!","","",true,"","",""));
                    }
                    break;
            }
    }

        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(adapter);
            setRecyclerView();

        }catch (Exception e ){
            Toast.makeText(read_Search.this, "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return true;
    }

//    public void onCheckboxClicked(View view) {
//        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.chkConnNumber:
//                if (checked){
//                    chkMeterNumber.setChecked(true);
//                }
//            else
//                break;
//            case R.id.chkMeterNumber:
//                if (checked){
//                }
//
//            else
//                               break;
//            case R.id.chkName:
//                if (checked){}
//                             else
//                                    break;
//                 }
//    }
    @Override
    public boolean onQueryTextChange(String newText) {
        String meter_Number = "", connection_Number = "",   customer_Name ="";
        badosomewa_model1List = new ArrayList<>();

        String desc = "";
        if (chkConnNumber.isChecked()) {
            desc = "Connection_Number";
            searchView_bado.setQueryHint("Enter Valid Account Number");

        } else if (chkMeterNumber.isChecked()) {
            desc = "Meter_Number";
            searchView_bado.setQueryHint("Enter Valid Meter Number");

        } else if (chkName.isChecked()) {
            desc = "Name";
            searchView_bado.setQueryHint("Enter Customer Name");
        }
        //  Log.e(TAG, "onQueryTextSubmit: " + desc);
        CharSequence key = searchView_bado.getQuery();
        String Key = key.toString();

        dataBaseHelper = new DatabaseHelper(read_Search.this);
        Cursor res = dataBaseHelper.getMeterNumber_RV_where(Key, desc);

        if (res.moveToFirst()) {
            badosomewa_model1List.clear();
            do {
                meter_Number = res.getString(res.getColumnIndex("meter_number"));
                connection_Number = res.getString(res.getColumnIndex("connection_number"));
                Cursor res_namba = dataBaseHelper.getCustomer_Number(connection_Number);
                if (res_namba.moveToFirst()) {
                    do {
                        String f_name = res_namba.getString(res_namba.getColumnIndex("first_name"));
                        String m_name = res_namba.getString(res_namba.getColumnIndex("middle_name"));
                        String l_name = res_namba.getString(res_namba.getColumnIndex("last_name"));
                        customer_Name = f_name + " " + m_name + " " + l_name;
                    } while (res_namba.moveToNext());
                }
                badosomewa_model1List.add(new Badosomewa_Model1(meter_Number,connection_Number,customer_Name,true,"","",""));
            } while (res.moveToNext());
        } else if (res.moveToFirst() == false) {
            switch (desc) {
                case "Connection_Number":
                    if (Key.length() == 0) {
                      //  adapter.notifyDataSetChanged();
                        dtHelper = new DatabaseHelper(read_Search.this);

                        rs = dtHelper.getMeterNumber_RV();

                        if (rs.moveToFirst()) {
                            badosomewa_model1List.clear();
                            do {
                                meter_Number = rs.getString(rs.getColumnIndex("meter_number"));
                                connection_Number = rs.getString(rs.getColumnIndex("connection_number"));

                                Cursor res_namba = dataBaseHelper.getCustomer_Number(connection_Number);
                                if (res_namba.moveToFirst()) {
                                    do {
                                        //10373705
                                        String f_name = res_namba.getString(res_namba.getColumnIndex("first_name"));
                                        String m_name = res_namba.getString(res_namba.getColumnIndex("middle_name"));
                                        String l_name = res_namba.getString(res_namba.getColumnIndex("last_name"));
                                        customer_Name = f_name + " " + m_name + " " + l_name;
                                    } while (res_namba.moveToNext());
                                }
                                badosomewa_model1List.add(new Badosomewa_Model1(meter_Number,connection_Number,customer_Name,true,"","",""));
                            } while (rs.moveToNext());
                        }
                    } else if (Key.length() != 6) {
                        badosomewa_model1List.clear();
                        badosomewa_model1List.add(new Badosomewa_Model1("Account Haipo/Not Valid !!","","",true,"","",""));
                    }
                    break;
                case "Meter_Number":
                    if (Key.length() == 0) {
                        //adapter.notifyDataSetChanged();
                        dtHelper = new DatabaseHelper(read_Search.this);

                        rs = dtHelper.getMeterNumber_RV();

                        if (rs.moveToFirst()) {
                            badosomewa_model1List.clear();
                            do {
                                meter_Number = rs.getString(rs.getColumnIndex("meter_number"));
                                connection_Number = rs.getString(rs.getColumnIndex("connection_number"));

                                Cursor res_namba = dataBaseHelper.getCustomer_Number(connection_Number);
                                if (res_namba.moveToFirst()) {
                                    do {
                                        String f_name = res_namba.getString(res_namba.getColumnIndex("first_name"));
                                        String m_name = res_namba.getString(res_namba.getColumnIndex("middle_name"));
                                        String l_name = res_namba.getString(res_namba.getColumnIndex("last_name"));
                                        customer_Name = f_name + " " + m_name + " " + l_name;
                                    } while (res_namba.moveToNext());
                                }
                                badosomewa_model1List.add(new Badosomewa_Model1(meter_Number,connection_Number,customer_Name,true,"","",""));
                            } while (rs.moveToNext());
                        }
                    } else {
                        badosomewa_model1List.clear();
                        badosomewa_model1List.add(new Badosomewa_Model1("Mita Haipo/Not Valid !!","","",true,"","",""));
                    }
                    break;
                case "Name":
                    if (Key.length() == 0) {
                        adapter.notifyDataSetChanged();
                        dtHelper = new DatabaseHelper(read_Search.this);

                        rs = dtHelper.getMeterNumber_RV();

                        if (rs.moveToFirst()) {
                            badosomewa_model1List.clear();
                            do {
                                meter_Number = rs.getString(rs.getColumnIndex("meter_number"));
                                connection_Number = rs.getString(rs.getColumnIndex("connection_number"));

                                Cursor res_namba = dataBaseHelper.getCustomer_Number(connection_Number);
                                if (res_namba.moveToFirst()) {
                                    do {
                                        String f_name = res_namba.getString(res_namba.getColumnIndex("first_name"));
                                        String m_name = res_namba.getString(res_namba.getColumnIndex("middle_name"));
                                        String l_name = res_namba.getString(res_namba.getColumnIndex("last_name"));
                                        customer_Name = f_name + " " + m_name + " " + l_name;
                                    } while (res_namba.moveToNext());
                                }
                                badosomewa_model1List.add(new Badosomewa_Model1(meter_Number,connection_Number,customer_Name,true,"","",""));
                            } while (rs.moveToNext());
                        }
                    } else {
                        badosomewa_model1List.clear();
                        badosomewa_model1List.add(new Badosomewa_Model1("Jina Halipo/Not Valid !!","","",true,"","",""));
                    }
                    break;
            }
        }

        try {
//            recyclerView.setLayoutManager(new LinearLayoutManager(read_Search.this));
            adapter = new readSearch_Adapter(read_Search.this, animalNames);
//            adapter.setClickListener(read_Search.this);
//            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(adapter);
            setRecyclerView();

        }catch (Exception e ){
            Toast.makeText(read_Search.this, "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();
        }
        return true;
    }
});

}

    private void setRecyclerView() {
        Badosomewa_Adaptor1 badosomewa_adaptor1 = new Badosomewa_Adaptor1(badosomewa_model1List);
        recyclerView.setAdapter(badosomewa_adaptor1);
    }

    private void initData() {

        badosomewa_model1List = new ArrayList<>();
        Cursor res = dataBaseHelper.getMeterNumber_RV();
        Cursor res_name = dataBaseHelper.getName_RV();
        String meter_Number = "", connection_Number = "",   customer_Name ="";

        if (res.moveToFirst()) {
            badosomewa_model1List.clear();
            do {
                meter_Number = res.getString(res.getColumnIndex("meter_number"));
                connection_Number = res.getString(res.getColumnIndex("connection_number"));

                Cursor res_namba = dataBaseHelper.getCustomer_Number(connection_Number);
                if (res_namba.moveToFirst()) {
                    do {
                        String f_name = res_namba.getString(res_namba.getColumnIndex("first_name"));
                        String m_name = res_namba.getString(res_namba.getColumnIndex("middle_name"));
                        String l_name = res_namba.getString(res_namba.getColumnIndex("last_name"));
                        customer_Name = f_name + " " + m_name + " " + l_name;
                    } while (res_namba.moveToNext());
                }
                badosomewa_model1List.add(new Badosomewa_Model1(meter_Number,connection_Number,customer_Name,true,"","",""));
            }while (res.moveToNext() );
        }
        else
        {
            badosomewa_model1List.add(new Badosomewa_Model1("Synchronise Data","","Synchronise Data",true,"","",""));
        }
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this, "An error While Generating List \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public static boolean isNumeric(String strNum) {
            if (strNum == null) {
                return false;
            }
            try {
                double d = Double.parseDouble(strNum);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
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
            intent.putExtra("from", "new_Read");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }


}
