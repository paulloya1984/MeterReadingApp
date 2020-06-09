package tz.co.ubunifusolutions.screens.activities.extras;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import tz.co.ubunifusolutions.screens.R;

public class Faults extends AppCompatActivity {
   ImageView imageView;
    Button btnTakePhoto, btnClear, btnSave;
    EditText txtWhere,txtSeverity,txtComment;
    File photoFile = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faults);

      imageView =  findViewById(R.id.imageView_tatizo);
//       txtWhere = findViewById(R.id.txtWhere);
//       txtComment = findViewById(R.id.txtComment);
//       txtSeverity = findViewById(R.id.txtSeverity);

        btnTakePhoto = findViewById(R.id.btnCaptureImage);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=  new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);

            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // String comment=txtComment.getText().toString().trim();
                //customDialog("WARNING","You are about to Cancel Reporting A Fault \n Are You Sure ?","cancelMethod1","okMethod1");

            }
        });

btnSave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        String where = txtWhere.getText().toString().trim();
//        String severity = txtSeverity.getText().toString().trim();
//        String comment = txtComment.getText().toString().trim();
//
//        if(where.isEmpty()){
//            txtWhere.setError("General Area Of Fault is Required");
//            txtWhere.requestFocus();
//            return;
//        }
//        else if(severity.isEmpty()){
//            txtSeverity.setError("Explain How Bad is the Fault");
//            txtSeverity.requestFocus();
//            return;
//        }else if(comment.isEmpty()){
//            txtComment.setError("Add a comment");
//            txtComment.requestFocus();
//            return;
//        }

        Toast.makeText(Faults.this, "BAdo Kusave Table Kumodify", Toast.LENGTH_SHORT).show();

    }
});

    }

    private void tatizo(){


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }





}
