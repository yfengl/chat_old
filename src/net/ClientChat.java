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
	//���ӷ���������
	public boolean connect() throws IOException{
		Debug.out("׼�����ӷ�����.....");
		DataInputStream dis = new DataInputStream(getSocket().getInputStream());
		DataOutputStream dos = new DataOutputStream(getSocket().getOutputStream());
		Debug.out("д���û���.....");
		dos.writeUTF(getName());
		Debug.out("д������.....");
		dos.writeUTF(getPass());
		dos.writeUTF(getLoadState());
		if(dis.readInt()==Message.UNSUCCESS){
			Debug.out("����ʧ��!");
			setConnect(false);
			return false;
		}
		
		Debug.out("���ӳɹ�!׼�����������û���Ϣ...");
	
		int count = dis.readInt();
		Debug.out("׼������"+count+"���ͻ���Ϣ....");
		while(count--!=0){
			clientList.add(dis.readUTF());
		}
		Debug.out("�û���Ϣ���ճɹ�");
	
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
	//��ȡĿ¼�Ĵ�С
	//�����ļ��߳�
	protected void fileSending(File file, String[] receive) {
		try {
			ServerSocket  ss = new ServerSocket(fileSendPort);
			int count ;
			if(receive==null){
				count = clientList.size();
			}else{
				count = receive.length;
			}
			//��ȡĿ¼�Ĵ�С
			long size = getDirSize(file);
			//�����ļ���Ϣ
			sendMsg(new FileSendMsg(getName(),
					receive,file.getName(),size,getSocket().getLocalAddress().getHostAddress(),ss.getLocalPort()));
			
			while(count-->0){
				Socket s = ss.accept();
				new FileSender(file,s);
			}
			Debug.out("�ļ��������!");
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
