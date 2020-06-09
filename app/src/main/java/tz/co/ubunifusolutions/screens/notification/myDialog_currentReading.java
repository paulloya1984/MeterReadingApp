package tz.co.ubunifusolutions.screens.notification;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import tz.co.ubunifusolutions.screens.R;

public class myDialog_currentReading extends AppCompatDialogFragment {
   int mNum;
   Button btnedit;
//    /**
//     * Create a new instance of MyDialogFragment, providing "num"
//     * as an argument.
//     */
//    static myDialog_currentReading newInstance(int num) {
//        myDialog_currentReading f = new myDialog_currentReading();
//
//        // Supply num input as an argument.
//        Bundle args = new Bundle();
//        args.putInt("num", num);
//        f.setArguments(args);
//
//        return f;
//    }
private TextView connectionNumber;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mNum = getArguments().getInt("num");
        String aa = getArguments().getString("data");
        Bundle mArgs = getArguments();
        String conn_number = mArgs.getString("conn_number");
        //String nameFav = mArgs.getString("fav_name");
       // String name = "";
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
//        builder.setView(inflater.inflate(R.layout.current_read_alert,null));
//        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//     }
//        });
// return builder.create();

        View view = inflater.inflate(R.layout.current_read_alert,null);
        builder.setView(view)
                .setTitle("Current Reading")
                .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // get prompts.xml view
                        LayoutInflater li = LayoutInflater.from(getActivity());
                        View promptsView = li.inflate(R.layout.read_modify_prompt, null);


                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity())
                                .setTitle("Rudia")
                                .setMessage("Usomaji wa mara ya pili");

                        // set prompts.xml to alertdialog builder
                         alertDialogBuilder.setView(promptsView);
                        final EditText userInput = (EditText) promptsView.findViewById(R.id.txtEdit_updateread);

                        // set dialog message
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                String c = String.valueOf(userInput.getText());
                                                Log.e("Mfano", "onClick: "+ c);


}
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        });


                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    }
                })

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


 }

 });


        connectionNumber = view.findViewById(R.id.txtConnectionNumber_dialog);
        connectionNumber.setText(aa);
return builder.create();
    }

    public void showkitu(){

    }

}
