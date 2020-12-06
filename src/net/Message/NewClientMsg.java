package net.Message;



public class NewClientMsg implements Message {

	
	private static final long serialVersionUID = 3992079384191167112L;
	private String sender;
	
	public NewClientMsg(String sender) {
		super();
		this.sender = sender;
	}

	public String[] getReceive() {
		
		return null;
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return sender+"…œœﬂ¡À£°";
	}

	public String getSender() {
		
		return sender;
	}

	public int getType() {
		// TODO Auto-generated method stub
		return NEW_CLIENT;
	}

	

}
