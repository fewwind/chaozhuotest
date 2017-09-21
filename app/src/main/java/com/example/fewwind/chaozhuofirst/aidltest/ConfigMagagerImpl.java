package com.example.fewwind.chaozhuofirst.aidltest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import org.json.JSONObject;

/**
 * Created by fewwind on 17-5-18.
 */

public class ConfigMagagerImpl extends Binder implements IConfigManager{

	private static final java.lang.String DESCRIPTPR = "configmanagerImp";
	// develop delete

	private String value;
	private Context mContext;


	public ConfigMagagerImpl(Context mContext) {
		this.attachInterface(this,DESCRIPTPR);
		this.mContext = mContext;
	}


	@Override public void setValue(String value) throws RemoteException {
		this.value = value;
		NetParams params = new NetParams();
		params.injectExtraData(null);
		NetParamsSub sub = new NetParamsSub();
		sub.injectExtraData(null);
	}

	public class NetParamsSub extends NetParams{
		@Override protected void injectExtraData(JSONObject jsonObj) {
			super.injectExtraData(jsonObj);

		}
	}

	@Override public String getValue() throws RemoteException {
		return value;
	}
	@Override public IBinder asBinder() {
		return this;
	}


	@Override protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)
		throws RemoteException {

		if (mContext.checkCallingOrSelfPermission("qw.qlm.ipctest.PERMISSION_CALL_REMOTE_SERVICE") == PackageManager.PERMISSION_DENIED){
		}

		return super.onTransact(code, data, reply, flags);
	}
}
