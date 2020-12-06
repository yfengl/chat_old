package gui;


import java.awt.ScrollPane;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;

import net.Debug;
import net.Message.ImageMsg;
import net.Message.Message;

public class MyEditor extends JTextPane {
	public JScrollPane scrollPane = new JScrollPane();
	public MyEditor(){
		scrollPane.setViewportView(this);
		setText("欢迎你来到NetZone!\n");
		this.setDoubleBuffered(true);
		//scrollPane.add(this);
	//	this.add(scrollPane, java.awt.BorderLayout.CENTER);
	}

	public void insertMsg(Message msg){
		this.setEditable(true);
		setCaretPosition(this.getText().length());//将光标插入到最后
	
		switch(msg.getType()){
		case Message.STRING:
			
			this.replaceSelection(msg.toString()+"\n");
			break;
		case Message.IMAGE:
			Debug.out(((ImageMsg)msg).getImageName());
			this.replaceSelection(msg.getSender()+":\n");
			setCaretPosition(this.getText().length());//将光标插入到最后
			this.insertIcon(new ImageIcon("resources/Images/"+((ImageMsg)msg).getImageName()));
			setCaretPosition(this.getText().length());//将光标插入到最后
			this.replaceSelection("\n");
			break;
		}
		//setCaretPosition(this.getText().length());//将光标插入到最后
		//this.insertComponent(new JSeparator());
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
		setCaretPosition(this.getText().length());//将光标插入到最后
		this.setEditable(false);
		Debug.out(msg+""+getText().length());
	}
}
