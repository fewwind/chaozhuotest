package com.example.fewwind.chaozhuofirst.ui;

import com.example.fewwind.chaozhuofirst.R;
import com.example.fewwind.chaozhuofirst.base.BaseActivity;
import com.example.fewwind.chaozhuofirst.bean.FileObject;
import com.example.fewwind.chaozhuofirst.bean.HttpResult;
import com.example.fewwind.chaozhuofirst.bean.PushMessage;
import com.example.fewwind.chaozhuofirst.util.disk.CacheUtil;
import com.example.fewwind.chaozhuofirst.utils.RxBus;
import com.example.fewwind.chaozhuofirst.utils.SystemPropertyUtils;
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
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;


public class RxActivity extends BaseActivity {


    @Override public int getLayoutId() {
        return R.layout.activity_rx;
    }


    @Override public void initView() {

    }


    @Override public void initData() {
        timeOper();
    }


    private HttpResult getJson2(String txt, Type type) {
        return new Gson().fromJson(txt, type);
    }
    List<FileObject> mList;
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

    List<PushMessage> mListMsg = new ArrayList<>();

    // 将需要显示的数据集合分成三组1: 需要现在显示的 2：以后显示的 3：过期的
    private static final String KEY_SHOWING = "KEY_SHOWING";
    private static final String KEY_UNSHOW = "KEY_UNSHOW";
    private static final String KEY_SHOWED = "KEY_SHOWED";
    private static Long TIME_VAULE = 1000 * 60 * 60 * 24L;


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

        RxBus.getInstance().postEvent(new FileObject("rxbus", 1));
    }

    private void timeOper(){
        Observable.empty().timer(3, TimeUnit.SECONDS).flatMap(
                new Func1<Object, Observable<?>>() {
                    @Override public Observable<?> call(Object o) {
                        Logger.e("3");
                        return Observable.empty();
                    }
                }).timer(3, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override public void call(Long aLong) {
                Logger.e("6");
            }
        });
    }

}
