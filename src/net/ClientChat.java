package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import net.Message.FileReceiveMsg;
import net.Message.FileSendMsg;
import net.Message.Message;
import net.Message.MsgListener;
import net.Message.StringMsg;


public class ClientChat extends ClientInfo implements MsgListener {

	private Vector<String> clientList =  new Vector<String>();
	private static int fileSendPort = 6666;
	private int buffSize = 5000;
	
	public ClientChat(Socket socket, String name, String passWard,String loadState) {
		super(socket, 0, name, passWard,loadState);
		this.addMsgListener(this);
		
	}
	//连接服务器方法
	public boolean connect() throws IOException{
		Debug.out("准备连接服务器.....");
		DataInputStream dis = new DataInputStream(getSocket().getInputStream());
		DataOutputStream dos = new DataOutputStream(getSocket().getOutputStream());
		Debug.out("写入用户名.....");
		dos.writeUTF(getName());
		Debug.out("写入密码.....");
		dos.writeUTF(getPass());
		dos.writeUTF(getLoadState());
		if(dis.readInt()==Message.UNSUCCESS){
			Debug.out("连接失败!");
			setConnect(false);
			return false;
		}
		
		Debug.out("连接成功!准备接收在线用户信息...");
	
		int count = dis.readInt();
		Debug.out("准备接收"+count+"个客户信息....");
		while(count--!=0){
			clientList.add(dis.readUTF());
		}
		Debug.out("用户信息接收成功");
	
		setConnect(true);
		
		return true;
	}
	
	public Vector<String> getClientList() {
		return clientList;
	}
	
	public void msgReceive(Message msg) {
		//Debug.out(msg);

	}
	public void sendFile(final File file,final String []receive) throws IOException{
		
		new Thread(new Runnable(){
			public void run() {
				fileSending(file,receive);	
			}}).start();
		fileSendPort++;
	}
	//获取目录的大小
	//发送文件线程
	protected void fileSending(File file, String[] receive) {
		try {
			ServerSocket  ss = new ServerSocket(fileSendPort);
			int count ;
			if(receive==null){
				count = clientList.size();
			}else{
				count = receive.length;
			}
			//获取目录的大小
			long size = getDirSize(file);
			//发送文件消息
			sendMsg(new FileSendMsg(getName(),
					receive,file.getName(),size,getSocket().getLocalAddress().getHostAddress(),ss.getLocalPort()));
			
			while(count-->0){
				Socket s = ss.accept();
				new FileSender(file,s);
			}
			Debug.out("文件发送完毕!");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	
	private long getDirSize(File file) {
		if(file.isFile())
			return file.length();
		long size = 0;
		for(File f:file.listFiles()){
			size+=getDirSize(f);
		}
		return size;
	}
	
}
