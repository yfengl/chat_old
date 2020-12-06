package net.Message;
/**
 * 
 * �����ڿͻ��˽��ܵ��ļ���Ŀ¼��
 * ��ǰ̨GUI���ͽ����ļ��������Ϣ
 *
 */
public class FileReceiveMsg implements Message {

	private String sender,receive[];
	private long size,maxSize;//��ǰ�Ѿ����ܵ��ļ���С
	
	public FileReceiveMsg(String sender, String[] receive, long size) {
		super();
		this.sender = sender;
		this.receive = receive;
		this.size = size;
	}
	public long getSize(){
		return size;
	}
	public String[] getReceive() {
		return receive;
	}

	public String getSender() {
		return sender;
	}

	public int getType() {
		
		return FILE_RECEIVE;
	}

}
