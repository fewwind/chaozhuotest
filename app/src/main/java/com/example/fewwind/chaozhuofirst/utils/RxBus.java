package com.example.fewwind.chaozhuofirst.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.SerializedSubject;

/**
 * Created by fewwind on 17-5-22.
 */

public class RxBus {

	private SerializedSubject<Object, Object> rxBus;
	private SerializedSubject<Object, Object> rxStickBus;


	private RxBus() {
		rxBus = new SerializedSubject(PublishSubject.create());
		rxStickBus = new SerializedSubject<>(ReplaySubject.create());

		//Intent {
		//	dat = content://com.android.externalstorage.documents/tree/5BF3-09F8:新文件夹 flg=0xc3 }
		//
		//	treeuri
		//		content://com.android.externalstorage.documents/tree/5BF3-09F8%3A%E6%96%B0%E6%96%87%E4%BB%B6%E5%A4%B9
		//	info 5 BF3 - 09F 8:新文件夹
		//	ruihodler uuid = 5 BF3 - 09F 8
		//	ruihodler path = 新文件夹
		}

		private static RxBus mInstance;


	public static RxBus getInstance() {
		if (mInstance == null) {
			synchronized (RxBus.class) {
				if (mInstance == null) {
					mInstance = new RxBus();
				}
			}
		}
		return mInstance;
	}


	public void postEvent(Object obj) {
		synchronized (RxBus.class) {
			if (this.hasObservers()) {
				rxBus.onNext(obj);
			}
		}
	}


	public void postStickEvent(Object event) {
		synchronized (RxBus.class) {
			rxStickBus.onNext(event);
		}
	}


	public <T> Observable<T> toObservable(Class<T> type) {
		return rxBus.asObservable().ofType(type).onBackpressureBuffer();
	}


	public <T> Observable<T> toStickObservable(Class<T> type) {
		return rxStickBus.asObservable().ofType(type).onBackpressureBuffer();
	}


	public void destory() {
		mInstance = null;
	}


	private boolean hasObservers() {
		return rxBus.hasObservers();
	}

}
