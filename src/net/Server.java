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
	//�ͻ����������
	private int IDMark = 0;
	//�洢�ͻ���Ϣ������
	private Vector<ClientInfo> clints = new Vector<ClientInfo>();
	
	public Server() throws IOException {
		super(Config.getServerPort());
		serverThread.start();
	}
	
	public void run() {
		Debug.out("��������ʼ����.....");
		while(true){
			try {
				Socket s = this.accept();//���տͻ�����
				Debug.out("�û�׼������.....");
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				Debug.out("��ȡ�û���.....");
				String name = dis.readUTF();
				Debug.out("��ȡ����.....");
				String pass = dis.readUTF();
				Debug.out("��ȡ��½״̬...");
				String state = dis.readUTF();
				//��֤�û���Ч��
				if(validate(name,pass)){
					ClientInfo c = new ClientInfo(s,IDMark++,name,pass,state);
					dos.writeInt(Message.SUCCESS);
					
					Debug.out("д�뵱ǰ�û��б�.....");
					dos.writeInt(loadClientCount);//д�뵱ǰû�������û�������
					for(ClientInfo ci:clints){
						if(!ci.getLoadState().equals("����"))
							dos.writeUTF(ci.getName());
					}
					if(!state.equals("����"))//����������½ �򲻷��͵�½��Ϣ
					{
						sendMsg(new NewClientMsg(name));
						loadClientCount++;
					}
					c.addMsgListener(this);//����Լ�Ϊ��Ϣ�¼�������
					c.start();
					clints.add(c);
					Debug.out("���û�����,��ǰ����������->"+clints.size());
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
	
//��֤�û���Ч��
	private boolean validate(String name, String pass) {
		for(ClientInfo ci:clints){
			if(ci.getName().equals(name))
				return false;
		}
		return true;
	}
	//������Ϣ�ķ���
	private void sendMsg(Message msg){
		if(msg.getReceive()==null)//���Ϊ������Ϊ�����͹㲥��Ϣ
		{
			for(ClientInfo ci:clints){
				ci.sendMsg(msg);
			}
		}else{
			for(ClientInfo ci:clints){//�ҵ������� ������
				if(Message.FILE_SEND!=msg.getType()&&ci.getName().equals(msg.getSender())){//�Լ�Ҳ�ǽ����� 
					ci.sendMsg(msg);
				}
				for(String receive:msg.getReceive()){
					if(msg.getType()==Message.FILE_SEND&&ci.getName().equals(receive)){//������ļ���Ϣ��ֻ���������� �������Լ�
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
			if(!clints.remove((clints.indexOf(new ClientInfo(msg.getSender())))).getLoadState().equals("����")){
				sendMsg(msg);
				loadClientCount--;
				Debug.out("�ɹ��Ƴ�"+msg.getSender());
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
