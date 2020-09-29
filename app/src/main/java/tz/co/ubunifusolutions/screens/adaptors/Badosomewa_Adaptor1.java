package tz.co.ubunifusolutions.screens.adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.new_Reading;
import tz.co.ubunifusolutions.screens.activities.read_Search;
import tz.co.ubunifusolutions.screens.models.Badosomewa_Model1;
import tz.co.ubunifusolutions.screens.models.Waliosomewa_Model1;

public class Badosomewa_Adaptor1 extends RecyclerView.Adapter<Badosomewa_Adaptor1.BadosomewaViewHolder> {

    List<Badosomewa_Model1> badosomewa_model1List;
    Context mContext ;


    public Badosomewa_Adaptor1(List<Badosomewa_Model1> badosomewa_model1List) {
        this.badosomewa_model1List = badosomewa_model1List;

    }

    @NonNull
    @Override
    public BadosomewaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.readsearch_row,viewGroup,false);
       return new BadosomewaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadosomewaViewHolder badosomewaViewHolder, int i) {

        Badosomewa_Model1 badoseomewa = badosomewa_model1List.get(i);
        badosomewaViewHolder.connectionNumber.setText(badoseomewa.getConnection_Number());
        badosomewaViewHolder.meterNumber.setText(badoseomewa.getMeter_Number());
        badosomewaViewHolder.jina.setText(badoseomewa.getCustomer_Name());

    }

    @Override
    public int getItemCount() {
        return badosomewa_model1List.size();
    }

    public class BadosomewaViewHolder extends RecyclerView.ViewHolder {
        TextView meterNumber, connectionNumber,jina;
        LinearLayout badosomewaLayout;

        public BadosomewaViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();

            meterNumber = itemView.findViewById(R.id.tvAnimalName);
            connectionNumber = itemView.findViewById(R.id.tvConnectionNumber);
            jina = itemView.findViewById(R.id.tvJina_la_Mteja);



                  badosomewaLayout = itemView.findViewById(R.id.badosomewa_Linearlayout);
          badosomewaLayout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Badosomewa_Model1 badosomewa_model1 = badosomewa_model1List.get(getAdapterPosition());
                  badosomewa_model1.getConnection_Number();
                  String meternumber = badosomewa_model1.getMeter_Number();
                  if(meternumber.equals("Synchronise Data")){
                      Toast.makeText(mContext, "Please Update your Database to Continue", Toast.LENGTH_SHORT).show();
                      return;
                  }
                  else {
                      //  Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(mContext, new_Reading.class);
                      intent.putExtra("item", badosomewa_model1.getMeter_Number());
                      intent.putExtra("from", "new_Read");
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      mContext.startActivity(intent);
                  }
              }
          });

        }
    }
}
