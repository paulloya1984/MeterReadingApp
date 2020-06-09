package tz.co.ubunifusolutions.screens.activities.wateja;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.adapters1.route_Adapter;
import tz.co.ubunifusolutions.screens.activities.route_Details;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper_node;

public class Routeee extends AppCompatActivity implements route_Adapter.ItemClickListener{
    private static final String TAG = "Routeee Activity";
    route_Adapter adapter;
    SearchView searchView_route;

    private List<String> meterList;
    DatabaseHelper dataBaseHelper;
    ArrayList<String> animalNames;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        // data to populate the RecyclerView with
        animalNames = new ArrayList<>();
        animalNames.add("Synchronise Data");


        searchView_route= (SearchView) findViewById(R.id.idsearch_route);

        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvAnimals_route);


        dataBaseHelper = new DatabaseHelper(Routeee.this);
        Cursor res = dataBaseHelper.getMeterNumber_RV();

        if(res.moveToFirst()){
            animalNames.clear();
            do{
                //meterList.add(res.getString(2));

                animalNames.add(res.getString(res.getColumnIndex( "meter_number" )));
            }while (res.moveToNext());
        }

        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new route_Adapter(this, animalNames);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);

        }catch (Exception e ){
            Toast.makeText(this, "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();

        }

        searchView_route.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                CharSequence key =  searchView_route.getQuery();
                String desc="";
                String Key = key.toString();
                dataBaseHelper = new DatabaseHelper(Routeee.this);

                Cursor res = dataBaseHelper.getMeterNumber_RV_where(Key,desc);

                if(res.moveToFirst()){
                    animalNames.clear();
                    do{
                        //meterList.add(res.getString(2));

                        animalNames.add(res.getString(res.getColumnIndex( "meter_number" )));
                    }while (res.moveToNext());
                }

                try {
                    recyclerView.setLayoutManager(new LinearLayoutManager(Routeee.this));
                    adapter = new route_Adapter(Routeee.this, animalNames);
                    adapter.setClickListener(Routeee.this);
                    recyclerView.setAdapter(adapter);

                }catch (Exception e ){
                    Toast.makeText(Routeee.this, "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();

                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                CharSequence key =  searchView_route.getQuery();
                String desc="";
                String Key = key.toString();
                dataBaseHelper = new DatabaseHelper(Routeee.this);

                Cursor res = dataBaseHelper.getMeterNumber_RV_where(Key,desc);

                if(res.moveToFirst()){
                    animalNames.clear();
                    do{
                        //meterList.add(res.getString(2));

                        animalNames.add(res.getString(res.getColumnIndex( "meter_number" )));
                    }while (res.moveToNext());
                }

                try {
                    recyclerView.setLayoutManager(new LinearLayoutManager(Routeee.this));
                    adapter = new route_Adapter(Routeee.this, animalNames);
                    adapter.setClickListener(Routeee.this);
                    recyclerView.setAdapter(adapter);

                }catch (Exception e ){
                    Toast.makeText(Routeee.this, "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();

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
            Intent intent = new Intent(Routeee.this, route_Details.class);
            intent.putExtra("item", adapter.getItem(position));
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }
}
