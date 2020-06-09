package tz.co.ubunifusolutions.screens.adaptors;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.models.Listitem_badosomewa;
import tz.co.ubunifusolutions.screens.models.Listitem_waliosomewa;

public class BadoAdapter extends RecyclerView.Adapter<BadoAdapter.CustomViewHolder> {
    private List<Listitem_badosomewa> feedItemList;
    private Context mContext;


    public BadoAdapter(Context context, List<Listitem_badosomewa> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }


    @Override
    public BadoAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_badosomewa, null);
        BadoAdapter.CustomViewHolder viewHolder = new BadoAdapter.CustomViewHolder(view);
        return viewHolder;


    }


  //  @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final BadoAdapter.CustomViewHolder customViewHolder, int i) {
        final Listitem_badosomewa feedItem = feedItemList.get(i);


 }
 @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
}


    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView patient, phone, age;
        protected CardView cardView;


        public CustomViewHolder(View view) {
            super(view);


        }
    }


}