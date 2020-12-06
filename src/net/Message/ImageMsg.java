package net.Message;

public class ImageMsg implements Message {

	private static final long serialVersionUID = -5745995913303494616L;
	private String sender,receive[];
	
	private String imageName;
	public ImageMsg(String sender, String imageName,String[] receive) {
		super();
		this.sender = sender;
		this.imageName = imageName;
		this.receive = receive;
	}
	public String getImageName(){
		return imageName;
	}
	public String toString(){
		
		
		if(receive==null)
			return sender+"对"+"大家"+"说"+imageName;
		String str = "";
		for(String s:receive){
			str+=s+";";
		}
		return sender+"对"+str+"说"+imageName;
	}
	public int getType() {
		return IMAGE;
	}
	public String getSender() {
		return sender;
	}
	public String[] getReceive() {
		return receive;
	}
	

}
