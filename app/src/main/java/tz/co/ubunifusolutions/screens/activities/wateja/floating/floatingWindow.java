package tz.co.ubunifusolutions.screens.activities.wateja.floating;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

public class floatingWindow extends Service {
    private WindowManager wm;
    private LinearLayout ll,llBody;
    private Button stop;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        wm =(WindowManager) getSystemService(WINDOW_SERVICE);
        ll = new LinearLayout(this);
        llBody = new LinearLayout(this);
        LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams llBodyParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        ll.setBackgroundColor(Color.argb(66,255,0,0));
        ll.setLayoutParams(llParameters);

        llBody.setBackgroundColor(Color.argb(99,0,0,255));
        llBody.setLayoutParams(llBodyParameters);

        stop = new Button(this);
        ViewGroup.LayoutParams btnParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        stop.setText("Stop");
        stop.setLayoutParams(btnParams);


        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(800,850,WindowManager.LayoutParams.TYPE_TOAST,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.x = 0;
        params.y = 0;
        params.gravity = Gravity.CENTER|Gravity.CENTER;

       // ll.addView(llBody);
        ll.addView(stop);
        wm.addView(ll,params);


        ll.setOnTouchListener(new View.OnTouchListener() {

            private WindowManager.LayoutParams updatedParams = params;
            int x,y;
            float touched_x,touched_y;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x = updatedParams.x;
                        y = updatedParams.y;

                        touched_x = event.getRawX();
                        touched_y = event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        updatedParams.x = (int)(x + ( event.getRawX() - touched_x));
                        updatedParams.y = (int)(y + ( event.getRawY() - touched_y));
                         wm.updateViewLayout(ll,updatedParams);
                         break;

                         default:
                             break;


                }
return false;
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(ll);
                stopSelf();
            }
        });

    }
}
