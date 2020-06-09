package tz.co.ubunifusolutions.screens.adaptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tz.co.ubunifusolutions.screens.R;
//import tz.co.ubunifusolutions.screens.models.Listitem_badosomewa;
import tz.co.ubunifusolutions.screens.models.Listitem_waliosomewa;

public class TayariAdapter extends RecyclerView.Adapter<TayariAdapter.CustomViewHolder> {
    private List<Listitem_waliosomewa> feedItemList;
    private Context mContext;

    public TayariAdapter(List<Listitem_waliosomewa> feedItemList, Context mContext) {
        this.feedItemList = feedItemList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TayariAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_waliosomewa, null);
        TayariAdapter.CustomViewHolder viewHolder = new TayariAdapter.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TayariAdapter.CustomViewHolder customViewHolder, int i) {
        final Listitem_waliosomewa feedItem = feedItemList.get(i);
     }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder
    {

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
