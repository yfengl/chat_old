package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import net.Message.Message;
import net.Message.MsgListener;
import net.Message.NewClientMsg;

public class Server extends ServerSocket implements Runnable ,MsgListener{


	private Thread serverThread = new Thread(this);
	private int loadClientCount = 0;
	//客户编号生成器
	private int IDMark = 0;
	//存储客户信息的容器
	private Vector<ClientInfo> clints = new Vector<ClientInfo>();
	
	public Server() throws IOException {
		super(Config.getServerPort());
		serverThread.start();
	}
	
	public void run() {
		Debug.out("服务器开始运行.....");
		while(true){
			try {
				Socket s = this.accept();//接收客户接入
				Debug.out("用户准备加入.....");
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				Debug.out("读取用户名.....");
				String name = dis.readUTF();
				Debug.out("读取密码.....");
				String pass = dis.readUTF();
				Debug.out("读取登陆状态...");
				String state = dis.readUTF();
				//验证用户有效性
				if(validate(name,pass)){
					ClientInfo c = new ClientInfo(s,IDMark++,name,pass,state);
					dos.writeInt(Message.SUCCESS);
					
					Debug.out("写入当前用户列表.....");
					dos.writeInt(loadClientCount);//写入当前没用隐身用户的数量
					for(ClientInfo ci:clints){
						if(!ci.getLoadState().equals("隐身"))
							dos.writeUTF(ci.getName());
					}
					if(!state.equals("隐身"))//如果是隐身登陆 则不发送登陆消息
					{
						sendMsg(new NewClientMsg(name));
						loadClientCount++;
					}
					c.addMsgListener(this);//添加自己为消息事件监听者
					c.start();
					clints.add(c);
					Debug.out("新用户加入,当前在线总人数->"+clints.size());
				}
				else
				{
					dos.writeInt(Message.UNSUCCESS);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
//验证用户有效性
	private boolean validate(String name, String pass) {
		for(ClientInfo ci:clints){
			if(ci.getName().equals(name))
				return false;
		}
		return true;
	}
	//发送信息的方法
	private void sendMsg(Message msg){
		if(msg.getReceive()==null)//如果为接受者为空则发送广播信息
		{
			for(ClientInfo ci:clints){
				ci.sendMsg(msg);
			}
		}else{
			for(ClientInfo ci:clints){//找到接受者 并发送
				if(Message.FILE_SEND!=msg.getType()&&ci.getName().equals(msg.getSender())){//自己也是接收者 
					ci.sendMsg(msg);
				}
				for(String receive:msg.getReceive()){
					if(msg.getType()==Message.FILE_SEND&&ci.getName().equals(receive)){//如果是文件信息则只发给接受者 不发给自己
						ci.sendMsg(msg);
					}
					else if(msg.getType()!=Message.FILE_SEND&&(ci.getName().equals(receive))){
						//Debug.out(msg.getSender()+" to "+msg.getReceive());
						ci.sendMsg(msg);
						//break;
					}
				}
			}
		}
			
		
	}
	public void msgReceive(Message msg) {
		switch(msg.getType()){
		case Message.BREAK:
			if(!clints.remove((clints.indexOf(new ClientInfo(msg.getSender())))).getLoadState().equals("隐身")){
				sendMsg(msg);
				loadClientCount--;
				Debug.out("成功移除"+msg.getSender());
			}
			return;
		}
		sendMsg(msg);
		Debug.out(msg);
	}
	public static void main(String[] args)throws Exception {
		Server s = new Server();

	}

}
