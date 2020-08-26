package tz.co.ubunifusolutions.screens.adaptors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.new_Reading;
import tz.co.ubunifusolutions.screens.activities.read_Search;
import tz.co.ubunifusolutions.screens.activities.wateja.waliosomewa;
import tz.co.ubunifusolutions.screens.models.Waliosomewa_Model1;
import tz.co.ubunifusolutions.screens.notification.CustomDialog_Picha;
import tz.co.ubunifusolutions.screens.notification.myDialog_currentReading;

public class Waliosomewa_Adapter1 extends  RecyclerView.Adapter<Waliosomewa_Adapter1.WaliosomewaViewHolder> {

    List<Waliosomewa_Model1> waliosomewa_model1List;
    Context mContext ;


    public Waliosomewa_Adapter1(List<Waliosomewa_Model1> waliosomewa_model1List) {
        this.waliosomewa_model1List = waliosomewa_model1List;
    }

    @NonNull
    @Override
    public Waliosomewa_Adapter1.WaliosomewaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.waliosomewa_row,viewGroup,false);
        return new WaliosomewaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Waliosomewa_Adapter1.WaliosomewaViewHolder waliosomewaViewHolder, int i) {

        Waliosomewa_Model1 waliosomewa = waliosomewa_model1List.get(i);
        waliosomewaViewHolder.meterNumber.setText(waliosomewa.getMeter_Number());
        waliosomewaViewHolder.connectionNumber.setText(waliosomewa.getConnection_Number());
        waliosomewaViewHolder.currentReading.setText(waliosomewa.getCurrent_Reading());
        waliosomewaViewHolder.jina.setText(waliosomewa.getCustomer_Name());
        waliosomewaViewHolder.area_code.setText(waliosomewa.getArea_code());
        waliosomewaViewHolder.seq.setText(waliosomewa.getSeq());
        waliosomewaViewHolder.route.setText(waliosomewa.getRoute());

        waliosomewaViewHolder.image.setImageDrawable(null);
        waliosomewaViewHolder.image.setImageURI(Uri.parse(waliosomewa.getImage_Uri()));


       // waliosomewaViewHolder.image.setText(waliosomewa.getMeter_Number());

        boolean isExpandable = waliosomewa_model1List.get(i).isExpandable();
        waliosomewaViewHolder.expand.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return waliosomewa_model1List.size();
    }

    public class WaliosomewaViewHolder extends RecyclerView.ViewHolder {

        TextView meterNumber, connectionNumber,currentReading,jina,area_code,seq,route;
        ImageView image;
        Button btnEdit;
        RelativeLayout open,expand;


        public WaliosomewaViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            meterNumber = itemView.findViewById(R.id.tvWaliosomewarow);
            connectionNumber = itemView.findViewById(R.id.textViewConnectionNum);
            currentReading = itemView.findViewById(R.id.textViewCurrentReading);
            jina =  itemView.findViewById(R.id.textViewCustomerName);
            area_code = itemView.findViewById(R.id.txtArea_code);
            seq= itemView.findViewById(R.id.txtSeq);
            route = itemView.findViewById(R.id.txtRoute);
            image = itemView.findViewById(R.id.imageView);
            open = itemView.findViewById(R.id.opening_layout);
            expand =itemView.findViewById(R.id.expandable);
            btnEdit = itemView.findViewById(R.id.btnEdit);



            open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Waliosomewa_Model1 waliosomewa_model1 = waliosomewa_model1List.get(getAdapterPosition());
                    waliosomewa_model1.setExpandable(!waliosomewa_model1.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Waliosomewa_Model1 waliosomewa_model1 = waliosomewa_model1List.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, new_Reading.class);
                    intent.putExtra("item", waliosomewa_model1.getMeter_Number());

                    intent.putExtra("from", "waliosomewa");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent);
                }
            });

            image.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.e("On Click", "Onyesha picha ");


                    // custom dialog
                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.custom_dialog_picha);
                    dialog.setTitle("Meter Reading");

                    Waliosomewa_Model1 waliosomewa_model1 = waliosomewa_model1List.get(getAdapterPosition());
                    ImageView image = (ImageView) dialog.findViewById(R.id.picha_Mita);
                    image.setImageURI(Uri.parse(waliosomewa_model1.getImage_Uri()));

                    Button dialogButton = (Button) dialog.findViewById(R.id.btn_yes);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

        }
    }
}
