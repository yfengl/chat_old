package net.Message;

/**
 * 
 */
public class FileSendMsg implements Message {

	
	private static final long serialVersionUID = -5412219265191297885L;

	private String sender,receive[],ip,fileName;
	private long size;
	private int port;
	
	public FileSendMsg(String sender, String receive[], String fileName,long size,String ip, int port) {
		super();
		this.sender = sender;
		this.receive = receive;
		this.ip = ip;
		this.port = port;
		this.fileName = fileName;
		this.size = size;
	}
	public long getSize(){
		return size;
	}
	public String getIp() {
		return ip;
	}
	public String getFileName(){
		return fileName;
	}
	public int getPort() {
		return port;
	}

	@Override
	public String toString() {
		
		return "�յ��ļ�,����:"+sender+",IP:"+ip+",�˿�:"+port+",�ļ�����"+fileName+",��С��"+size;
	}

	public String[] getReceive() {
		
		return receive;
	}

	public String getSender() {
		
		return sender;
	}

	public int getType() {
		
		return FILE_SEND;
	}

	
}
