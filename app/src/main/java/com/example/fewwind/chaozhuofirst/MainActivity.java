package com.example.fewwind.chaozhuofirst;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;
import com.example.fewwind.chaozhuofirst.bean.FileObject;
import com.example.fewwind.chaozhuofirst.bean.HttpResult;
import com.example.fewwind.chaozhuofirst.bean.PushMessage;
import com.example.fewwind.chaozhuofirst.util.disk.CacheUtil;
import com.example.fewwind.chaozhuofirst.utils.SystemPropertyUtils;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    private TextView mTvAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)
            findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTvAnim = (TextView) findViewById(R.id.id_tv_anim);

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

                //Util.launchApp(view.getContext());

                showAnim(mTvAnim);

                send();;
                InputManager inputManager = (InputManager) getSystemService(Context.INPUT_SERVICE);

                view.postDelayed(new Runnable() {
                    @Override public void run() {
                        manager.notify(0, notification);
                    }
                }, 2000);

                addView();

                Logger.w("时间 --"+Util.getFormatedDate(MainActivity.this, System.currentTimeMillis()).replace("/",""));
            }
        });

        KenBurnsView kbv = (KenBurnsView) findViewById(R.id.image);
        kbv.setVisibility(View.GONE);
        kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }
            @Override
            public void onTransitionEnd(Transition transition) {
            }
        });

        //testCache();
        testDemo();
        Logger.w("版本×××"+BuildConfig.DEBUG);
        showAnim(mTvAnim);//

        getWindow().getDecorView().getBackground().getColorFilter();
    }

    private void send(){
        final Intent intent = new Intent();
        //intent.setClassName("com.android.settings",
        //    "com.android.settings.deviceinfo.StorageUnmountReceiver");
        intent.setAction("unment");
        sendBroadcast(intent, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
    }


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

        String property = SystemPropertyUtils.getSystemProperty(OS_CHANNEL_KEY);
        Toast.makeText(this,property,Toast.LENGTH_LONG).show();
    }




    List<FileObject> mList;
    List<PushMessage> mListMsg = new ArrayList<>();

    // 将需要显示的数据集合分成三组1: 需要现在显示的 2：以后显示的 3：过期的
    private static final String KEY_SHOWING = "KEY_SHOWING";
    private static final String KEY_UNSHOW = "KEY_UNSHOW";
    private static final String KEY_SHOWED = "KEY_SHOWED";
    private static Long TIME_VAULE = 1000 * 60 * 60 * 24L;


    private void testDemo() {
        mListMsg.add(new PushMessage(100l, "过期", 0));
        mListMsg.add(new PushMessage(100l, "过期", 1));
        mListMsg.add(new PushMessage(1000l * 1492513088, "显示", 2));
        mListMsg.add(new PushMessage(1000l * 1492513088, "显示", 3));
        mListMsg.add(new PushMessage(1000l * 1492613088, "存起来", 4));
        mListMsg.add(new PushMessage(1000l * 1492613088, "存起来", 5));

        //保存最大的 version 作为下次请求的version
        mListMsg.clear();
        Observable.from(mListMsg).map(new Func1<PushMessage, Integer>() {
            @Override public Integer call(PushMessage pushMessage) {
                return pushMessage.version;
            }
        }).toSortedList().flatMap(new Func1<List<Integer>, Observable<Integer>>() {
            @Override public Observable<Integer> call(List<Integer> integers) {
                return Observable.from(integers);
            }
        }).takeLast(1).subscribe(new Action1<Integer>() {
            @Override public void call(Integer integer) {
                Logger.i("最大==" + integer);
            }
        });

        Observable.from(mListMsg).groupBy(new Func1<PushMessage, String>() {
            @Override public String call(PushMessage pushMessage) {

                if (pushMessage.starttime > System.currentTimeMillis()) {
                    return KEY_UNSHOW;
                } else if (System.currentTimeMillis() - pushMessage.starttime < TIME_VAULE) {
                    return KEY_SHOWING;
                } else {
                    return KEY_SHOWED;
                }
            }
        }).subscribe(new Action1<GroupedObservable<String, PushMessage>>() {
            @Override
            public void call(final GroupedObservable<String, PushMessage> sObservable) {
                sObservable.toList().subscribe(new Action1<List<PushMessage>>() {
                    @Override public void call(List<PushMessage> pushMessages) {
                        switch (sObservable.getKey()) {
                            case KEY_SHOWING:
                                Logger.v("显示" + pushMessages);
                                break;
                            case KEY_UNSHOW:
                                Logger.w("将要显示" + pushMessages);
                                break;
                            case KEY_SHOWED:
                                Logger.e("过期" + pushMessages);
                                break;
                        }
                    }
                });
            }
        });
        //public String message;
        String json = "{'code':0,'msg':'ok','data':[{'message':'good'},{'message':'bad'}]}";

        new Gson().fromJson(json, HttpResult.class);
        //new Gson().fromJson(json, HttpResult<List<PushMessage>>);

        Type type = new TypeToken<HttpResult<List<PushMessage>>>() {}.getType();
        //HttpResult<List<PushMessage>> data =new Gson().fromJson(json,type);
        //HttpResult<List<PushMessage>> data2 = getJson2(json, type);
        //Logger.e("gson 解析 ==" + data.toString());
        HttpResult<List<PushMessage>> newData = fromJson(json,
            new TypeToken<HttpResult<List<PushMessage>>>() {});
        Logger.e("gson 解析 ==" + newData.toString());

        String property = SystemPropertyUtils.getSystemProperty(OS_CHANNEL_KEY);
        Toast.makeText(this,property,Toast.LENGTH_LONG).show();
    }

    public static final String OS_CHANNEL_KEY = "persist.sys.phoenix.channel";
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String json, TypeToken<T> token) {
        Gson gson = new Gson();
        try {
            return (T) gson.fromJson(json, token.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.w(json + " 无法转换为 " + token.getRawType().getName() + " 对象!", ex);
            return null;
        }
    }


    private HttpResult getJson2(String txt, Type type) {
        return new Gson().fromJson(txt, type);
    }


    private void testCache() {
        Map<Integer, FileObject> mCache = new HashMap<>();
        mCache.put(1, new FileObject("第一", 1));
        mCache.put(2, new FileObject("第二", 2));
        mCache.put(3, new FileObject("第三", 3));
        mCache.put(10, new FileObject("第十", 10));
        mList = new ArrayList<>();
        mList.add(mCache.get(1));
        mList.add(mCache.get(2));
        mList.add(mCache.get(3));
        mList.add(mCache.get(10));

        CacheUtil.getInstance().put("key", (Serializable) mCache);

        Map<Integer, FileObject> mNewCache = CacheUtil.getInstance().getAsSerializable("key");
        for (Integer intKey : mNewCache.keySet()) {
            //Logger.d(mNewCache.get(intKey).toString());
        }

        Set<Map.Entry<Integer, FileObject>> entries = mNewCache.entrySet();
        Iterator<Map.Entry<Integer, FileObject>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, FileObject> next = iterator.next();
            FileObject file = next.getValue();
            if (file.getVersion() > 1) {
                iterator.remove();
                showNotify();
            }
        }
        //for (FileObject bean : mList) {
        //    fixList(bean);
        //}

        //for (Integer intKey:mNewCache.keySet()) {
        //    Logger.w(mNewCache.get(intKey).toString());
        //}

        Observable.from(mListMsg).subscribe(new Action1<PushMessage>() {
            @Override public void call(PushMessage pushMessage) {

            }
        }, new Action1<Throwable>() {
            @Override public void call(Throwable throwable) {

            }
        });

        Observable.from(mList).toMap(new Func1<FileObject, Integer>() {
            @Override public Integer call(FileObject fileObject) {
                return fileObject.getVersion();
            }
        }, new Func1<FileObject, FileObject>() {
            @Override public FileObject call(FileObject fileObject) {
                return fileObject;
            }
        }).subscribe(new Action1<Map<Integer, FileObject>>() {
            @Override public void call(Map<Integer, FileObject> fileMaps) {
                for (Integer intKey : fileMaps.keySet()) {
                    Logger.w(fileMaps.get(intKey).toString());
                }
            }
        });

        Observable.from(mList).groupBy(new Func1<FileObject, Boolean>() {
            @Override public Boolean call(FileObject fileObject) {
                return fileObject.getVersion() % 2 == 0;
            }
        }).subscribe(new Action1<GroupedObservable<Boolean, FileObject>>() {
            @Override
            public void call(final GroupedObservable<Boolean, FileObject> bObservable) {
                bObservable.toList().subscribe(new Action1<List<FileObject>>() {
                    @Override public void call(List<FileObject> fileObjects) {
                        Boolean key = bObservable.getKey();
                        Logger.v("key=" + key + "--Value=" + fileObjects.toString());
                    }
                });
            }
        });

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override public void call(Subscriber<? super Integer> subscriber) {

            }
        });
        Iterator<FileObject> iteratorList = mList.iterator();
        while (iteratorList.hasNext()) {
            FileObject file = iteratorList.next();
            if (file.getVersion() == 2) {
                //iteratorList.remove();
            }
        }

        Observable.from(mList).groupBy(new Func1<FileObject, Integer>() {
            @Override public Integer call(FileObject fileObject) {
                if (fileObject.getVersion() < 2) {
                    return 2;
                } else if (fileObject.getVersion() < 6) {
                    return 6;
                } else {
                    return 10;
                }

            }
        }).subscribe(new Action1<GroupedObservable<Integer, FileObject>>() {
            @Override
            public void call(GroupedObservable<Integer, FileObject> inOb) {
                inOb.toList().subscribe(new Action1<List<FileObject>>() {
                    @Override public void call(List<FileObject> fileObjects) {
                        Logger.e(fileObjects.toString());
                    }
                });
            }
        });
    }


    private void showNotify() {

    }


    private void fixList(FileObject file) {
        switch (file.getVersion()) {
            case 1:
                file.title = "改了1";
                break;
            case 2:
                file.setVersion(6);
                break;
            case 3:
                file = new FileObject("xiugai  3", 9);
                break;
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
