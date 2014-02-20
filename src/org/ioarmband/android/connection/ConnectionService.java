package org.ioarmband.android.connection;

import org.ioarmband.android.connection.aidl.IRemoteCommunicationService;
import org.ioarmband.android.connection.aidl.IRemoteCommunicationService.Stub;
import org.ioarmband.android.connection.aidl.RemoteCommunicationServiceListener;
import org.ioarmband.android.connection.message.impl.android.MessageAndroid;
import org.ioarmband.android.connection.message.impl.android.MessageContainer;
import org.ioarmband.net.connection.manager.ServiceOutConnection;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class ConnectionService extends Service {

	
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.d("ConnectionService","onBind");
		return mBinder;
	}
	
	// implementation of the aidl interface
		private final IRemoteCommunicationService.Stub mBinder=new Stub() {

			private BluetoothAndroidConnectionManager manageBluetoothConnexion = BluetoothAndroidConnectionManager.getInstance();
			//private RemoteCommunicationServiceListener communicationListener;
			ConnectionServiceSet convertListenerConnection = new ConnectionServiceSet();

			@Override
			public void useConnection(RemoteCommunicationServiceListener communicationListener)
					throws RemoteException {
				Log.d("ConnectionService"," ");
				Log.d("ConnectionService","communicationListener = "+communicationListener);
				
				convertListenerConnection.addConnection(communicationListener);
				ServiceOutConnection serviceOutConnection = convertListenerConnection.getConnection(communicationListener).getsOutConnection();
				manageBluetoothConnexion.useConnection(serviceOutConnection);
			
			}

			@Override
			public void unUseConnection(RemoteCommunicationServiceListener communicationListener)
					throws RemoteException {
				Log.d("ConnectionService","unUseConnection");
				
				ConvertListenerConnection listenerConnection = convertListenerConnection.getConnection(communicationListener);
				if(listenerConnection !=null)
				{
					ServiceOutConnection serviceOutConnection = listenerConnection.getsOutConnection();
					manageBluetoothConnexion.removeUseConnection(serviceOutConnection);
					convertListenerConnection.removeConnection(communicationListener);
				}

			}
			

			@Override
			public void sendMessage(MessageContainer messageContainer)
					throws RemoteException {
				Log.d("ConnectionService","sendMessage");
				
				MessageAndroid msg = messageContainer.getMessageAndroid();
				
				Log.d("ConnectionService","sendMessage "+"class = "+msg.getClass()+" -> "+msg.toString());
				
				manageBluetoothConnexion.sendMessage(msg.getOriginalMessage());
			}
		};
}
