package tz.co.ubunifusolutions.screens.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.wateja.bado_somewa;
import tz.co.ubunifusolutions.screens.activities.wateja.waliosomewa;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link walisomewaFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class walisomewaFrag extends Fragment {

    CardView WaliosomewaCard, HawajasomewaCard;
    public walisomewaFrag() {
        // Required empty public constructor
    }


    public static walisomewaFrag newInstance(String param1, String param2) {
        walisomewaFrag fragment = new walisomewaFrag();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_walisomewa2, container, false);

        WaliosomewaCard = (CardView) view.findViewById(R.id.waliosomewacardId);
        WaliosomewaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //recyclerView.setAdapter(T_adapter);
                //  recyclerView.setAdapter(B_adapter);
                //Toast.makeText(List.this, "Waliosomewa List generate", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), waliosomewa.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }
        });


        HawajasomewaCard = (CardView) view.findViewById(R.id.hawajasomewacardId);
        HawajasomewaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  recyclerView.setAdapter(B_adapter);
                //  Toast.makeText(List.this, "Hawasomwa List Generate", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), bado_somewa.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

            }
        });


        return view;
    }
}
