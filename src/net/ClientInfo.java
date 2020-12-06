package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import net.Message.BreakMsg;
import net.Message.Message;
import net.Message.MsgListener;



public class ClientInfo extends Thread   {

	private Socket socket;//
	private String loadState = "";
	private int ID;
	private String pass;
	private boolean isConnect = true;
	private List<MsgListener> msgListeners = new  LinkedList<MsgListener>();
	public ClientInfo(Socket socket, int id, String name, String passWard,String loadState) {
		super();
		this.socket = socket;
		ID = id;
		this.setName(name);
		this.pass = passWard;
		this.loadState = loadState;
	}
	public ClientInfo(String name){
		this.setName(name);
	}
	//添加监听器
	public boolean addMsgListener(MsgListener ml){
		return msgListeners.add(ml);
	}
	public boolean removeMsgListener(MsgListener ml){
		return msgListeners.remove(ml);
	}
	
	
	public void sendMsg(Message msg){
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(msg);
		} catch (IOException e) {
			Debug.out(e.getMessage());
			e.printStackTrace();
		}
	}
	public int getID(){
		return ID;
	}
	//向前台(本地消息监听者)发送消息
	public void sendMsgToListener(Message msg){
		for(Object ml:msgListeners.toArray()){
			((MsgListener)ml).msgReceive(msg);
		}
	}
	public void run() {
		while(isConnect){
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message)(ois.readObject());
				sendMsgToListener(msg);
			} catch (IOException e) {
				Debug.out(e.getMessage());
				
				
				sendMsgToListener(new BreakMsg(getName()));
				break;
				//e.printStackTrace();
				
			} catch (ClassNotFoundException e) {
				Debug.out(e.getMessage());
				
				e.printStackTrace();
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		return getName().equals(((ClientInfo)obj).getName());
	}
	public boolean isConnect() {
		return isConnect;
	}

	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setID(int id) {
		ID = id;
	}
	public String getLoadState() {
		return loadState;
	}
	public void setLoadState(String loadState) {
		this.loadState = loadState;
	}
	

	



}
