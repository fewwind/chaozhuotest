package com.example.fewwind.chaozhuofirst.aidltest;

import android.os.IInterface;
import android.os.RemoteException;

/**
 * Created by fewwind on 17-5-18.
 */

public interface IConfigManager extends IInterface{
	void setValue(String value) throws RemoteException;
	String getValue() throws RemoteException;
	// master add
}
