package com.example.fewwind.chaozhuofirst;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)
            findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

                Notification.Builder builder = new Notification.Builder(mContext)
                    .setTicker("tiker")
                    .setContentTitle("title")
                    .setContentText("content").setSmallIcon(R.drawable.wof);
                final Notification notification = builder.build();
                final NotificationManager manager = (NotificationManager) mContext.getSystemService(
                    Context.NOTIFICATION_SERVICE);

                Util.launchApp(view.getContext());

                view.postDelayed(new Runnable() {
                    @Override public void run() {
                        manager.notify(0, notification);
                    }
                }, 2000);

                addView();
            }
        });

        KenBurnsView kbv = (KenBurnsView) findViewById(R.id.image);
        kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }


            @Override
            public void onTransitionEnd(Transition transition) {

            }
        });

    }


    private void addView() {
        final Button mBtn = new Button(this);
        mBtn.setText("Flaot");
        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0,
            PixelFormat.TRANSPARENT);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.gravity = Gravity.RIGHT | Gravity.CENTER;
        layoutParams.x = 300;
        layoutParams.y = 300;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        final WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //manager.addView(mBtn, layoutParams);

        mBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {

                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE: {
                        layoutParams.x = x;
                        layoutParams.y = y;
                        manager.updateViewLayout(mBtn, layoutParams);
                        break;
                    }
                    default:
                        break;
                }

                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
