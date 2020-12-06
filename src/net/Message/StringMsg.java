package net.Message;

public class StringMsg implements Message {

	private static final long serialVersionUID = -5745995913303494616L;
	private String sender,receive[];
	
	private String msg;
	public StringMsg(String sender, String msg,String[] receive) {
		super();
		this.sender = sender;
		this.msg = msg;
		this.receive = receive;
	}
	public String getMsg(){
		return msg;
	}
	public String toString(){
		
		
		if(receive==null)
			return sender+"对"+"大家"+"说:\n"+msg;
		String str = "";
		for(String s:receive){
			str+=s+";";
		}
		return sender+"对"+str+"说:\n"+msg;
	}
	public int getType() {
		return STRING;
	}
	public String getSender() {
		return sender;
	}
	public String[] getReceive() {
		return receive;
	}
	

}
