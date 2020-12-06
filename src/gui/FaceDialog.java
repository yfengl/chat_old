package gui;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FaceDialog extends JDialog implements ActionListener {

	
	private JPanel buttonPanel = new JPanel(new FlowLayout());
	private JScrollPane buttonScrollPane = new JScrollPane(buttonPanel);
	private String iconName = "";
	public FaceDialog(Frame f) throws HeadlessException {
		super(f, true);
		setTitle("选择一个表情！");
		//setLayout(new FlowLayout());
		setSize(500,220);
		File dir = new File("resources/images");
		for(File file:dir.listFiles()){
			if(file.getName().endsWith(".gif")){
				JButton bt = new JButton(file.getName(),new ImageIcon("resources/Images/"+file.getName()));
				bt.addActionListener(this);
				buttonPanel.add(bt);
			}
		}
	add(buttonScrollPane);
	this.setVisible(true);
		
	}
	public String getIconName() {
		return iconName;
	}
	
	public void actionPerformed(ActionEvent ae) {
		iconName=ae.getActionCommand();
		this.setVisible(false);
	}
	public static void main(String s[]){
		System.out.println(getFace(null));
	}
	public static String getFace(Frame f){
		FaceDialog fd = new FaceDialog(f);
		String iconName = fd.getIconName();
		fd.dispose();
		return iconName;
	}

	
}
