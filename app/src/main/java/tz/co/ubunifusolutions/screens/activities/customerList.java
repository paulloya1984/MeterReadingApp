package tz.co.ubunifusolutions.screens.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.api.HttpHandler;

public class customerList extends AppCompatActivity {

    private String TAG = customerList.class.getSimpleName();
    ArrayList<HashMap<String, String>> CustomerList;
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomerList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);


    }


    }
