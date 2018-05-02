package com.example.fewwind.chaozhuofirst;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.DownloadManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.input.InputManager;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.StatFs;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.example.fewwind.chaozhuofirst.adapter.baseadapter.CommonAdapter;
import com.example.fewwind.chaozhuofirst.adapter.baseadapter.base.ViewHolder;
import com.example.fewwind.chaozhuofirst.bean.MainIntent;
import com.example.fewwind.chaozhuofirst.rxtest.ObservableF;
import com.example.fewwind.chaozhuofirst.rxtest.SubscriberF;
import com.example.fewwind.chaozhuofirst.ui.EllipseTipView;
import com.example.fewwind.chaozhuofirst.ui.MainFragment;
import com.example.fewwind.chaozhuofirst.ui.RvActivity;
import com.example.fewwind.chaozhuofirst.ui.SecondFragment;
import com.example.fewwind.chaozhuofirst.ui.view.AdImageView;
import com.example.fewwind.chaozhuofirst.ui.view.FailingBall;
import com.example.fewwind.chaozhuofirst.util.disk.Utils;
import com.example.fewwind.chaozhuofirst.utils.DataUtil;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.fewwind.chaozhuofirst.rxtest.ObservableF.creat;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    private TextView mTvAnim;
    private AdImageView mIvAd;
    RecyclerView mRv;
    private Handler mHanlder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        Logger.i("On Creat");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)
                findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTvAnim = (TextView) findViewById(R.id.id_tv_anim);
        mRv = (RecyclerView) findViewById(R.id.id_rv_main);
        mRv.setLayoutManager(new GridLayoutManager(this,2));
        mRv.setAdapter(new CommonAdapter<MainIntent>(this,R.layout.item_main, DataUtil.getMainIntent()){

            @Override
            protected void convert(ViewHolder holder, final MainIntent mainIntent, int position) {
                holder.getView(R.id.id_item_tv_main);
                holder.setText(R.id.id_item_tv_main,mainIntent.name);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this,mainIntent.clazz));

                    }
                });
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final FailingBall ball = (FailingBall) findViewById(R.id.ball);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Notification.Builder builder = new Notification.Builder(mContext)
                        .setTicker("tiker")
                        .setContentTitle("title")
                        .setContentText("content")
                        .setSmallIcon(R.drawable.ic_album_black_24dp)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.wof));
                final Notification notification = builder.build();
                final NotificationManager manager = (NotificationManager) mContext.getSystemService(
                        Context.NOTIFICATION_SERVICE);

                //Util.launchApp(view.getContext());
                send();
                InputManager inputManager = (InputManager) getSystemService(Context.INPUT_SERVICE);

                view.postDelayed(new Runnable() {
                    @Override public void run() {
                        manager.notify(0, notification);
                    }
                }, 2000);

                addView();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                Utils.test();
                //startActivity(intent);
                //startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

        final KenBurnsView kbv = (KenBurnsView) findViewById(R.id.image);
        kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }


            @Override
            public void onTransitionEnd(Transition transition) {
            }
        });
        kbv.setImageResource(R.drawable.closebtn_forcus);
        kbv.setBackgroundResource(R.drawable.wof);
        //testCache();
        showAnim(mTvAnim);//
        SecondFragment secondFragment = SecondFragment.newInstance("", "");
        MainFragment mainFragment = MainFragment.newInstance("", "");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_main,mainFragment);
        transaction.commit();
        fm.beginTransaction();

        getWindow().getDecorView().post(new Runnable() {
            @Override public void run() {
                // 想正确获取图片宽高或者view的宽高，必须在执行 两次 PerformTraversals 方法，这个方法正好在这之后
                Logger.i(kbv.getWidth()+"==="+kbv.getHeight());
            }
        });

        fab.post(new Runnable() {
            @Override public void run() {
                Logger.w(kbv.getWidth()+"==="+kbv.getHeight());
            }
        });

        File files = new File(Environment.getExternalStorageDirectory().getPath()+"/.CZWall");
        if (files.exists()){
        }

        Context mContext = this;

        startActivity(new Intent(MainActivity.this, RvActivity.class));
        ClassLoader classLoader = getClassLoader();
        while (null!=classLoader){
            Logger.i(classLoader.toString());
            Logger.w(mContext.getClassLoader().toString());
            classLoader = classLoader.getParent();
        }
        EllipseTipView tipView = (EllipseTipView) findViewById(R.id.tip_view);
        tipView.setShow(0,"娜可露露");
        EllipseTipView  ifng = new EllipseTipView(this);
        mIvAd = (AdImageView) findViewById(R.id.ad_image);
        Logger.w("资源id"+DataUtil.getIdByName("wo"));
        new Thread(){
            @Override public void run() {
                super.run();
                for (int i = 0; i < 33; i++) {
                    final int j = i;
                    try {
                        Thread.sleep(666);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Logger.v("ondraw = "+j);
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            mIvAd.setDx(j*3);
                        }
                    });
                }
            }
        }.start();
    }


    private void send() {
        final Intent intent = new Intent();
        //intent.setClassName("com.android.settings",
        //    "com.android.settings.deviceinfo.StorageUnmountReceiver");
        intent.setAction("unment");
        sendBroadcast(intent, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
    }
    ExecutorService fixedThreadPool;

    private void showAnim(View view) {
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                2f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                2f, 1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, pvhY, pvhZ);
        //objectAnimator.setDuration(1000).start();
        objectAnimator.setRepeatCount(1);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        //ObjectAnimator.ofFloat(view,"scaleX",1f,5f,1f).setDuration(1000).start();
        //ObjectAnimator.ofFloat(view,"scaleY",1f,5f,1f).setDuration(1000).start();

        fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 1; i < 1; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Logger.v("线程："+threadName+",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override protected void onResume() {
        super.onResume();
        Logger.d("On Resume");
        scanSD();
    }


    private void scanSD() {
        String downPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        .getPath();
        MediaScannerConnection.scanFile(this, new String[] {downPath  }, null, null);
        Logger.w(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getPath());
        DownloadManager manager =
                (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //manager.addCompletedDownload("f.apk", "apk", true,
        //        "*/*", downPath, 6000, true);
        String ss = Util.getBuildInfo();
        //Logger.w(ss);
        //ToasF.show(ss);
        File datapath = Environment.getDataDirectory();
        StatFs dataFs=new StatFs(datapath.getPath());
        long sizes=(long)dataFs.getFreeBlocks()*(long)dataFs.getBlockSize();
        long available=sizes/((1024*1024));
        //Logger.w("size"+available);
        new Thread(){
            @Override public void run() {
                super.run();
                Logger.d("1"+Thread.currentThread().getName());
                //Looper.prepare();
                mHanlder = new Handler(Looper.getMainLooper());
                mHanlder.post(new Runnable() {
                    @Override public void run() {
                        Logger.v("2"+Thread.currentThread().getName());
                    }
                });
                //Looper.loop();
            }
        }.start();

        HandlerThread handlerThread = new HandlerThread("fewwind");
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override public void run() {

            }
        });
        Logger.w("---"+ new RuntimeException("fewwind"));

    }


    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
    /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
      /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
      /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
        /* 中文字符长度为2 */
                valueLength += 2;
            } else {
        /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }


    private void testCustomRX() {

        //ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        //am.clearApplicationUserData();
        creat(new ObservableF.OnSubscribeF<String>() {
            @Override public void call(SubscriberF<? super String> subscriber) {
                subscriber.onNext("");
            }
        }).subscribe(new SubscriberF<String>() {
            @Override public void Completed() {

            }

            @Override public void onError(Throwable t) {
            }
            @Override public void onNext(String s) {

            }
        });

        ObservableF observableF = ObservableF.creat(new OnsubScribeFImp());
        SubscriberF subscriberF = new SubScribeFImp();
        observableF.subscribe(subscriberF);

    }

    private class OnsubScribeFImp implements ObservableF.OnSubscribeF{
        @Override public void call(SubscriberF subscriber) {
            subscriber.onStart();
        }
    }
    private class SubScribeFImp extends SubscriberF{

        @Override public void Completed() {

        }


        @Override public void onError(Throwable t) {

        }


        @Override public void onNext(Object o) {

        }
    }


    @Override protected void onPause() {
        Logger.v("On Pause");
        super.onPause();
        mHanlder.removeCallbacksAndMessages(null);
        mHanlder = null;
    }


    @Override protected void onStop() {
        Logger.w("On Stop");
        super.onStop();
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        Logger.e("On Destory");
    }
}
