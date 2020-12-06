package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ZoneFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public ZoneFrame()
	{
		this.setBackground(new Color(1,255,1));
		this.setSize(400,300);
		this.setTitle("Net Zone 0.1");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		
	}
	public  void msg(Object o)
	{
		JOptionPane.showMessageDialog(this, o);
	}

}
