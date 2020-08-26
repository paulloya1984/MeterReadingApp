package tz.co.ubunifusolutions.screens.notification;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import tz.co.ubunifusolutions.screens.R;

public class CustomDialog_Picha extends Dialog implements android.view.View.OnClickListener  {
    public Activity c;
    public Dialog d;
    public Button yes;
    ImageView image;


    public CustomDialog_Picha(Activity a) {
        super(a);

        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_picha);
        yes = (Button) findViewById(R.id.btn_yes);
       yes.setOnClickListener(this);

      //  Bundle mArgs = getArguments();
       // String conn_number = mArgs.getString("conn_number");
     //  image = findViewById(R.id.picha_Mita);
        /***
         *
         *  waliosomewaViewHolder.image.setImageDrawable(null);
         *         waliosomewaViewHolder.image.setImageURI(Uri.parse(waliosomewa.getImage_Uri()));
         */



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_yes:
                c.finish();
                break;

            default:
                break;
        }
        dismiss();
    }

    }

