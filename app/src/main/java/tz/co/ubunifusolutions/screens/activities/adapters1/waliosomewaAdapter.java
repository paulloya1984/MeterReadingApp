package tz.co.ubunifusolutions.screens.activities.adapters1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tz.co.ubunifusolutions.screens.R;

public class waliosomewaAdapter extends  RecyclerView.Adapter<waliosomewaAdapter.ViewHolder>  {
    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public waliosomewaAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.waliosomewa_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.myTextView.setText(animal);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


//    class ProductViewHolder extends RecyclerView.ViewHolder {
//
//        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
//        ImageView imageView;
//
//        public ProductViewHolder(View itemView) {
//            super(itemView);
//
//            textViewTitle = itemView.findViewById(R.id.textViewTitle);
//            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
//            textViewRating = itemView.findViewById(R.id.textViewRating);
//            textViewPrice = itemView.findViewById(R.id.textViewPrice);
//            imageView = itemView.findViewById(R.id.imageView);
//        }




    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvWaliosomewarow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
