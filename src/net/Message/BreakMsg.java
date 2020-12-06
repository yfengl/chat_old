package net.Message;

/**
 * 掉线消息
 */
public class BreakMsg implements Message {

	private static final long serialVersionUID = -2196323027752909587L;
	private String sender;
	public BreakMsg(String sender) {
		super();
		this.sender = sender;
	}

	public String[] getReceive() {
		
		return null;
	}

	@Override
	public String toString() {
		return sender+"掉线了！";
	}

	public String getSender() {
		
		return sender;
	}

	public int getType() {
		
		return BREAK;
	}

}
