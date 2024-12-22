package il.cshaifasweng.OCSFMediatorExample.client;

import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;

public class SimpleClient extends AbstractClient {

	public static SimpleClient client = null;
	private String ID;
	public static String host="localhost";
	public  static int port=3000;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Warning.class)) {
			EventBus.getDefault().post(new WarningEvent((Warning) msg));
		}
		String message = msg.toString();
		if (message.equals("X") || message.equals("O")) {
			getClient().setID(message);
			EventBus.getDefault().post(new newGameEvent(message));
			System.out.println("my id is"+getID());
		}
		if(message.contains("wait"))
		{
			EventBus.getDefault().post(new inWaitEvent(message));
		}
		if(message.startsWith("#newMove,"))
		{
			EventBus.getDefault().post(new playerMoveEvent(message));
		}
		if(message.contains("is the winner"))
		{
			String winner=message.substring(26,27);
			EventBus.getDefault().post(new WinEvent(winner));
		}

			System.out.println(message);
	}
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient(host, port);
		}
		return client;
	}
	public String getID()
	{
		return ID;
	}
	public void setID(String id)
	{
		ID=id;
	}
}