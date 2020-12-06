package net.Message;
/**
 * 
 * 用于在客户端接受到文件或目录后
 * 向前台GUI发送接受文件情况的消息
 *
 */
public class FileReceiveMsg implements Message {

	private String sender,receive[];
	private long size,maxSize;//当前已经接受的文件大小
	
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
