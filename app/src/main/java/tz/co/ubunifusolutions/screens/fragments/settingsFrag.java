package tz.co.ubunifusolutions.screens.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tz.co.ubunifusolutions.screens.R;
import tz.co.ubunifusolutions.screens.activities.LoginActivity;
import tz.co.ubunifusolutions.screens.session.IpAddress;
import tz.co.ubunifusolutions.screens.storage.DatabaseHelper;


public class settingsFrag extends Fragment {

    private static final String TAG = "Settings" ;
    DatabaseHelper dataBaseHelper;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Preference-Global" ;

    EditText ipLocal,ipPublic;
    Button btnSave, btnCancel, btnchangeSetting;
    TextView txtResult;

    IpAddress ip;//global variable

    String name_From="setting";
    public settingsFrag() {
        // Required empty public constructor
    }

     public static settingsFrag newInstance(String param1, String param2) {
        settingsFrag fragment = new settingsFrag();
       return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_settings, container, false);
        dataBaseHelper = new DatabaseHelper(getActivity());

        ipLocal =(EditText) view.findViewById(R.id.txtIpLocal);
        ipPublic = (EditText) view.findViewById(R.id.txtIpPublic);
        txtResult = (TextView) view.findViewById(R.id.txtResult_Settings);
        btnCancel = (Button) view.findViewById(R.id.btnClear_Settings);
        btnSave = (Button) view.findViewById(R.id.btnSave_Settings);
        btnchangeSetting = (Button) view.findViewById(R.id.btnChange_Settings);

        isSettings();
        btnCancel.setEnabled(false);
        btnSave.setEnabled(false);
        ipLocal.setEnabled(false);
        ipPublic.setEnabled(false);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("WARNING","You are about to Clear the Entries \n Are You Sure ?",
                        "cancelMethod1","okMethod1");

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                settingsAdd();
            }
        });
        btnchangeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("WARNING","You are About to Change System Settings \n Are You Sure ?",
                        "cancelMethod1","okMethod2");

            }
        });

return view;
    }
    public void settingsAdd()
    {
        String LocalIP = ipLocal.getText().toString().trim();
        String PublicIP = ipPublic.getText().toString().trim();
        if (LocalIP.isEmpty()) {
            ipLocal.setError("Local IP Is Required");
            ipLocal.requestFocus();
            return;
        }
        if (PublicIP.isEmpty()) {
            ipPublic.setError("Public IP is required");
            ipPublic.requestFocus();
            return;
        }
        try {
            LocalIP = LocalIP+":8080";
            dataBaseHelper = new DatabaseHelper(getActivity());
            if (dataBaseHelper.insertSettings(LocalIP, PublicIP)) {

                Log.i(TAG, "Settings Added Successful \n" + "Local IP: " + LocalIP + "\nPublic IP: " + PublicIP);
                Toast.makeText(getActivity(), "Settings Added Successful \n" + "Local IP: " + LocalIP + "\nPublic IP: " + PublicIP, Toast.LENGTH_LONG).show();
            } else {
                Log.e(TAG, "An Error Has Occoured while adding Settings ");
                Log.i(TAG, "Closing Database Connection");
                Toast.makeText(getActivity(), "An Error Has Occoured while adding Settings ", Toast.LENGTH_LONG).show();
                dataBaseHelper.close();
               // Intent i = getIntent();
                Intent i = getActivity().getIntent();
                name_From= i.getStringExtra("from").trim();
                if(name_From.equals("Login")){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    ///intent.putExtra("from","Login");
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            }
        }
        catch (Exception e){
            Toast.makeText(getActivity(), "An Exception Has Occurred \n"+ e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "An Exception Has Occurred "+e.getMessage());
        }
        btnCancel.setEnabled(false);
        btnSave.setEnabled(false);
        ipLocal.setText("");
        ipLocal.setEnabled(false);
        ipPublic.setEnabled(false);
        ipPublic.setText("");
        btnchangeSetting.setEnabled(true);
        isSettings();
    }
    public void customDialog(String title,String message,final String cancelMethod,final String okMethod)
    {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setTitle(title);
        builderSingle.setIcon(R.drawable.ic_warning_black_24dp);
        builderSingle.setMessage(message);
        builderSingle.setNegativeButton(
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(cancelMethod.equals("cancelMethod1")){
                            cancelMethod1();
                        }

                    }
                }
        );
        builderSingle.setPositiveButton(
                "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(okMethod.equals("okMethod1")){
                            okMethod1();
                        } else if(okMethod.equals("okMethod2")){
                            okMethod2();
                        }
                    }
                }
        );
        builderSingle.show();
    }
    public  void cancelMethod1(){
        return;

    }
    public  void okMethod1(){

        //  Toast.makeText(Nyumbani.this, "Signed off", Toast.LENGTH_SHORT).show();
       /* Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();*/
        // Toast.makeText(Nyumbani.this, "", Toast.LENGTH_SHORT).show();
        ipPublic.setText("");
        ipLocal.setText("");
        ipLocal.requestFocus();
    }

    public  void okMethod2(){

        ipPublic.setEnabled(true);
        ipLocal.setEnabled(true);
        btnchangeSetting.setEnabled(false);
        btnSave.setEnabled(true);
        btnCancel.setEnabled(true);
        try {
            dataBaseHelper.settingDropAll();
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), "An Error Has Occured"+e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void isSettings() {
        String LocalIPAddress = null,PublicIPAddress = null;
        try {
            // dataBaseHelper.settingDrop();
            Cursor res = dataBaseHelper.getSettings_All();
            StringBuffer stringBuffer = new StringBuffer();
            if (res != null && res.getCount() > 0) {
                while (res.moveToNext()) {
                    stringBuffer.append("Local_IP_Address"+res.getString(res.getColumnIndex("ip_local"))+"\n");
                    LocalIPAddress = res.getString(res.getColumnIndex("ip_local"));
                    stringBuffer.append("Public_IP_Address"+res.getString(res.getColumnIndex("ip_public"))+"\n");
                    PublicIPAddress = res.getString(res.getColumnIndex("ip_public"));
                }
                txtResult.setText(stringBuffer.toString());
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putString("Local IP", LocalIPAddress);
//            editor.putString("Public IP", PublicIPAddress);
                //  editor.commit();
               // Toast.makeText(getActivity(), "Data Retrieved Successfully", Toast.LENGTH_SHORT).show();

                // adding data

                Context cntx = getActivity();
                ip = new IpAddress(cntx); //in oncreate

////and now we set sharedpreference then use this like
//
                ip.setLocalIP(LocalIPAddress);

                ip.setPublicIP(PublicIPAddress);


            } else {
                //Toast.makeText(getActivity(), "Data Not Retrieved Successfully", Toast.LENGTH_SHORT).show();
                Toast toast = new Toast(getActivity());
                toast.setDuration(Toast.LENGTH_LONG);

                //inflate view
                View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
                ((TextView) custom_view.findViewById(R.id.message)).setText("Data Not Retrieved Successfully");
                ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
                ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.red_600));

                toast.setView(custom_view);
                toast.show();

            }
        }
        catch (Exception e){
           // Toast.makeText(getActivity(), "Error HAs Occoured"+e.getMessage(), Toast.LENGTH_LONG).show();

            Toast toast = new Toast(getActivity());
            toast.setDuration(Toast.LENGTH_LONG);

            //inflate view
            View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
            ((TextView) custom_view.findViewById(R.id.message)).setText("An Error Has Occurred"+e.getMessage());
            ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
            ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.red_600));

            toast.setView(custom_view);
            toast.show();
        }
    }
}
