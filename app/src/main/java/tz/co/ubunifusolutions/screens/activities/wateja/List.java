package tz.co.ubunifusolutions.screens.activities.wateja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.Activity_Main2;
import tz.co.ubunifusolutions.screens.activities.new_Reading;
import tz.co.ubunifusolutions.screens.adaptors.BadoAdapter;
import tz.co.ubunifusolutions.screens.adaptors.TayariAdapter;
import tz.co.ubunifusolutions.screens.models.Listitem_badosomewa;
import tz.co.ubunifusolutions.screens.models.Listitem_waliosomewa;

public class List extends AppCompatActivity {

    private static final String TAG ="Wateja List File" ;
    CardView WaliosomewaCard, HawajasomewaCard;


    private RecyclerView recyclerView;
    private BadoAdapter B_adapter;
    private TayariAdapter T_adapter;

    private java.util.List<Listitem_waliosomewa> waliosomewaList;
    private  java.util.List<Listitem_badosomewa> badosomewaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = (RecyclerView) findViewById(R.id.waliosomewa_Recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        waliosomewaList = new ArrayList<>();
        badosomewaList = new ArrayList<>();

        for(int i=0;i<=10;i++)
        {

            waliosomewaList.add(new Listitem_waliosomewa());
            //  waliosomewaList waliosomewaList11 = new Listitem_waliosomewa("Meter Number "+i,"Current Reading " +i,"Connection Number "+ i,"Previous Reading "+ i);
        }

        B_adapter = new BadoAdapter(this, badosomewaList);

    WaliosomewaCard = (CardView) findViewById(R.id.waliosomewacardId);
    WaliosomewaCard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

           //recyclerView.setAdapter(T_adapter);
          //  recyclerView.setAdapter(B_adapter);
            //Toast.makeText(List.this, "Waliosomewa List generate", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(List.this, waliosomewa.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }
    });

   // T_adapter = new TayariAdapter(this,waliosomewaList);
    Log.i(TAG,"Hapa Error 1");
    HawajasomewaCard = (CardView) findViewById(R.id.hawajasomewacardId);
    HawajasomewaCard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //  recyclerView.setAdapter(B_adapter);
          //  Toast.makeText(List.this, "Hawasomwa List Generate", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(List.this, bado_somewa.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

        }
    });

    }
}
