package org.ioarmband.android.connection;

import java.io.IOException;

import org.ioarmband.net.connection.StreamedConnection;
import org.ioarmband.net.connection.manager.ConnectionManager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.util.Log;


public class BluetoothAndroidConnectionManager extends ConnectionManager {


	private static BluetoothAndroidConnectionManager instance = null;

	private BluetoothSocket bluetoothSocket;
	
	private BluetoothAndroidConnectionManager()
	{
		super();
		
	}

	public static synchronized BluetoothAndroidConnectionManager getInstance() {
		if(instance == null)
		{
			instance = new BluetoothAndroidConnectionManager();
		}
		return instance;
	}
	
	
	@Override
	public void LauchDiscovery() {

		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
		}
		if (mBluetoothAdapter.isEnabled()) {
			BluetoothAndroidDiscoveryManager androidDiscoveryManager = new BluetoothAndroidDiscoveryManager();
			androidDiscoveryManager.startdiscoveryDevice();
		}
		
	}

	
	@Override
	public void closeConnectionComplementary() {

		Log.d("BluetoothConnectionManager", "BluetoothConnectionManager close");

		try {
			if (bluetoothSocket != null) {
				bluetoothSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		bluetoothSocket = null;

	}

	@Override
	public void newConnectionComplementary(Object bluetoothSocket) {
		Log.d("BluetoothConnectionManager", "newConnectionComplementary");
		this.bluetoothSocket = (BluetoothSocket) bluetoothSocket;
		
		  try {
			streamConnection = new StreamedConnection(this.bluetoothSocket.getInputStream(), this.bluetoothSocket.getOutputStream());
			streamConnection.addConnectionListener(connection);
			
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
