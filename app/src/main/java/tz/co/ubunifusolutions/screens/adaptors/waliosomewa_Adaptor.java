package tz.co.ubunifusolutions.screens.adaptors;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.models.Listitem_waliosomewa;

public class waliosomewa_Adaptor extends RecyclerView.Adapter<waliosomewa_Adaptor.waliosomewaViewHolder>

{
   private List<Listitem_waliosomewa> listitem_waliosomewaList;
   private Context context;

    public waliosomewa_Adaptor(View.OnClickListener listitem_waliosomewaList, List<Listitem_waliosomewa> context) {
//        this.listitem_waliosomewaList = listitem_waliosomewaList;
//        this.context = context;
    }

    @NonNull
    @Override
    public waliosomewaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_waliosomewa,viewGroup,false);
        return new waliosomewaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull waliosomewaViewHolder waliosomewaViewHolder, int i) {

        Listitem_waliosomewa listitemWaliosomewa = listitem_waliosomewaList.get(i);
        waliosomewaViewHolder.meternumber_.setText(listitemWaliosomewa.getMeternumber_waliosomewa());
        waliosomewaViewHolder.currentreading_.setText(listitemWaliosomewa.getCurrentreading_waliosomewa());
        waliosomewaViewHolder.previousreading_.setText(listitemWaliosomewa.getPreviuosreading_waliosomewa());
        waliosomewaViewHolder.connectionnumber_.setText(listitemWaliosomewa.getConnectionnumber_waliosomewa());
    }

    @Override
    public int getItemCount() {
        return listitem_waliosomewaList.size();
    }

    public  class waliosomewaViewHolder extends RecyclerView.ViewHolder
    {

        public TextView meternumber_;
        public TextView connectionnumber_;
        public TextView previousreading_;
        public TextView currentreading_;

        public waliosomewaViewHolder(@NonNull View itemView) {
            super(itemView);

            meternumber_ = (TextView) itemView.findViewById(R.id.txtMeternumber_RV);
            connectionnumber_= (TextView) itemView.findViewById(R.id.txtConnNumber_RV);
            previousreading_= (TextView) itemView.findViewById(R.id.txtPreviousRead_RV);
            currentreading_= (TextView) itemView.findViewById(R.id.txtCurrentreading_RV);
        }
    }
}
