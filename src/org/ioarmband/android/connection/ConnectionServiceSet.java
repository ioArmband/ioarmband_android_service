package org.ioarmband.android.connection;

import java.util.HashSet;

import org.ioarmband.android.connection.aidl.RemoteCommunicationServiceListener;

import android.os.RemoteException;
import android.util.Log;

public class ConnectionServiceSet extends HashSet<ConvertListenerConnection>
{
	public void addConnection(RemoteCommunicationServiceListener connection){
		
		if(!isConnectionExist(connection))
		{
			add(new ConvertListenerConnection(connection));
		}
		
	}
	
	public void removeConnection(RemoteCommunicationServiceListener connection){
		
		ConvertListenerConnection convertListenerConnection = getConnection(connection);
		
		if(convertListenerConnection!= null)
		{
			remove(convertListenerConnection);
		}
		
	}

	
	public boolean isConnectionExist(RemoteCommunicationServiceListener connection)
	{
		Log.d("ConnectionServiceSet"," ");
		Log.d("ConnectionServiceSet"," ");
		Log.d("ConnectionServiceSet","connection = "+connection);
		for (ConvertListenerConnection convertListenerConnection : this) {
			Log.d("ConnectionServiceSet","convertListenerConnection = "+convertListenerConnection.getServiceListener());
			
			try {
				if(convertListenerConnection.getServiceListener().isEquals(connection.getIdClass()))
				{
					Log.d("ConnectionServiceSet","isConnectionExist = true");
					return true;
					
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.d("ConnectionServiceSet","isConnectionExist = false");
		return false;
	}
	
	public ConvertListenerConnection getConnection(RemoteCommunicationServiceListener connection)
	{
		for (ConvertListenerConnection convertListenerConnection : this) {
			try {
				if(convertListenerConnection.getServiceListener().isEquals((connection.getIdClass())))
				{
					return convertListenerConnection;
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
}
