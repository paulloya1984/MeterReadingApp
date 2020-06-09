package tz.co.ubunifusolutions.screens.activities.wateja;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import tz.co.ubunifusolutions.screens.R;

public class bado_somewa extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ListView lv_bado;
    SearchView searchView_bado;
    ArrayAdapter<String> adapter;
    String[] data={"A Sample","A Sample","A Sample","B Sample","C Sample","C Sample","D Sample","D Sample","D Sample","E Sample","E Sample","E Sample"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bado_somewa);
        lv_bado= (ListView)findViewById(R.id.listview_bado);
        searchView_bado= (SearchView) findViewById(R.id.idsearch_bado);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        lv_bado.setAdapter(adapter);
        searchView_bado.setOnQueryTextListener(this);
        lv_bado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = String.valueOf(parent.getItemAtPosition(position));

                // Toast.makeText(MainActivity.this, meterNumber, Toast.LENGTH_LONG).show();
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(bado_somewa.this);
                dlgAlert.setMessage(item);
                dlgAlert.setTitle("Bawasa - Customer Details");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text= newText;
        adapter.getFilter().filter(newText);
        return false;
    }
}
