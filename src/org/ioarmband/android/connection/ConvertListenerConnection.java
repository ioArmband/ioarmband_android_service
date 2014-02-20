package org.ioarmband.android.connection;

import org.ioarmband.android.connection.aidl.RemoteCommunicationServiceListener;
import org.ioarmband.android.connection.message.impl.android.GestureMessageAndroid;
import org.ioarmband.android.connection.message.impl.android.MessageAndroid;
import org.ioarmband.android.connection.message.impl.android.MessageContainer;
import org.ioarmband.android.connection.message.impl.android.TextMessageAppMessageAndroid;
import org.ioarmband.net.connection.manager.ServiceOutConnection;
import org.ioarmband.net.message.Command;
import org.ioarmband.net.message.Message;
import org.ioarmband.net.message.impl.GestureMessage;
import org.ioarmband.net.message.impl.TextMessageAppMessage;

import android.os.RemoteException;

public class ConvertListenerConnection {

	public ConvertListenerConnection(RemoteCommunicationServiceListener serviceListener) {
		this.serviceListener = serviceListener;
	}
	
	private RemoteCommunicationServiceListener serviceListener;
	
	
	public RemoteCommunicationServiceListener getServiceListener() {
		return serviceListener;
	}


	public void setServiceListener(
			RemoteCommunicationServiceListener serviceListener) {
		this.serviceListener = serviceListener;
	}


	public ServiceOutConnection getsOutConnection() {
		return sOutConnection;
	}


	public void setsOutConnection(ServiceOutConnection sOutConnection) {
		this.sOutConnection = sOutConnection;
	}

	private ServiceOutConnection sOutConnection = new ServiceOutConnection() {
		
		@Override
		public void onConnectionClose() {
			try {
				serviceListener.onConnectionClose();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void onCommandReiceved(Command command) {

			// Convert command to MessageContainer
			Message msg = command.getMessage();
			MessageAndroid messageAndroid = null;
			
		 if(command.getClazz().equals(TextMessageAppMessage.class.getName()))
		 {
			 TextMessageAppMessage message = (TextMessageAppMessage) msg;
			 messageAndroid = new TextMessageAppMessageAndroid(message);
			 
		 }else if(command.getClazz().equals(GestureMessage.class.getName()))
		 {
			 GestureMessage message = (GestureMessage) msg;
			 messageAndroid = new GestureMessageAndroid(message);
		 }
			
		 if(messageAndroid != null)
		 {
		
				MessageContainer messageContainer = new MessageContainer();
				messageContainer.setMessageAndroid(messageAndroid);
	
				try {
					serviceListener.onCommandReiceved(messageContainer);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			
		 }

		}
		
		@Override
		public void onWinControl() {
			try {
				serviceListener.onWinControl();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void onLoseControl() {
			try {
				serviceListener.onLoseControl();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void onConnectionStarted() {
			try {
				serviceListener.onConnectionStarted();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	
	
	
	
}
