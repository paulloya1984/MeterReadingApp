package tz.co.ubunifusolutions.screens.fragments.reading;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.List;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.adapters1.readSearch_Adapter;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;
import tz.co.ubunifusolutions.screens.storage.DatabaseManager;
//import tz.co.ubunifusolutions.screens.activities.MapsActivityCurrentPlace;


public class readingSearchFragment extends Fragment  {
    private  View rootView;

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

    Context context = getActivity();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reading_fragment, container,false);



            searchView_bado= (SearchView) view.findViewById(R.id.idsearch_read);
            chkConnNumber = (CheckBox) view.findViewById(R.id.chkConnNumber);
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
            chkMeterNumber = (CheckBox) view.findViewById(R.id.chkMeterNumber);
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

            chkName = (CheckBox) view.findViewById(R.id.chkName);
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
            recyclerView = view.findViewById(R.id.rvAnimals);

            dataBaseHelper = new DatabaseHelper(getActivity());

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
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                adapter = new readSearch_Adapter(getActivity(), animalNames);
                //  adapter = new readSearch_Adapter(this,jinaLaMteja);

                adapter.setClickListener((readSearch_Adapter.ItemClickListener) context);
                recyclerView.setAdapter(adapter);

            }catch (Exception e ){
                Toast.makeText(getActivity(), "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();
//    recyclerView.setLayoutManager(new LinearLayoutManager(this));
//    adapter = new readSearch_Adapter(this, animalNames);
//    adapter.setClickListener(this);
//    recyclerView.setAdapter(adapter);
            }

            searchView_bado.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    CharSequence key =  searchView_bado.getQuery();
                    String desc="";
                    String Key = key.toString();
                    dataBaseHelper = new DatabaseHelper(context);

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
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        adapter = new readSearch_Adapter(getActivity(), animalNames);
                        adapter.setClickListener((readSearch_Adapter.ItemClickListener) getActivity());
                        recyclerView.setAdapter(adapter);

                    }catch (Exception e ){
                        Toast.makeText(getActivity(), "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();

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

                    }
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    CharSequence key =  searchView_bado.getQuery();
                    String desc="";
                    String Key = key.toString();
                    dataBaseHelper = new DatabaseHelper(getActivity());

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
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        adapter = new readSearch_Adapter(getActivity(), animalNames);
                        adapter.setClickListener((readSearch_Adapter.ItemClickListener) getActivity());
                        recyclerView.setAdapter(adapter);

                    }catch (Exception e ){
                        Toast.makeText(getActivity(), "An error While Generating List \n" +e.getMessage() , Toast.LENGTH_SHORT).show();

                    }

                    return true;
                }
            });
return  view;
        }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
     super.onViewCreated(view, savedInstanceState);






    }



    public  void cancelMethod1(){
        return;

    }
    public  void okMethod1(){

      //  Toast.makeText(Nyumbani.this, "Signed off", Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();*/
        // Toast.makeText(Nyumbani.this, "", Toast.LENGTH_SHORT).show();



    }
    public void customDialog(String title,String message,final String cancelMethod,final String okMethod){
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
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
}
