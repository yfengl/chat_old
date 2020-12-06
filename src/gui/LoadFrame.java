package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import net.ClientChat;
import net.Config;


public class LoadFrame extends ZoneFrame {

	public LoadFrame ()
	{
		initFrame();
		initComponents();
	}
	private void initComponents() {
		
		JPanel_Input.setLayout(null);
		JComboBox_Name.setEditable(true);
		JPanel_Input.setBounds(25, 50, 350, 150);
		JLabel_Name.setBounds(5, 5, 100, 30);
		JLabel_Pass.setBounds(5, 40, 100, 30);
		JLabel_State.setBounds(5, 80, 100, 30);
		JComboBox_Name.setBounds(120, 5, 200, 30);
		JTextField_Pass.setBounds(120, 40, 200, 30);
		JComboBox_State.setBounds(120, 80, 200, 30);
		JPanel_Input.add(JLabel_Name);
		JPanel_Input.add(JLabel_Pass);
		JPanel_Input.add(JLabel_State);
		JPanel_Input.add(JComboBox_Name);
		JPanel_Input.add(JTextField_Pass);
		JPanel_Input.add(JComboBox_State);
		
		JButton_Accede.setBounds(100, 200, 70, 30);
		JButton_Cancel.setBounds(250, 200, 70, 30);
		
		JButton_Accede.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				JButton_Accede_Click();
			}
		});
		JButton_Cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				JButton_Cancel_Click();
			}
		});
		

		this.add(JPanel_Input);
		this.add(JButton_Accede);
		this.add(JButton_Cancel);
	}
	//��½��ť�¼� 
	protected void JButton_Accede_Click() {

		if(JComboBox_Name.getSelectedItem().toString().trim().equals(""))
		{
			msg("�û�����������");
			return;
		}
			try {
				ClientChat client = new ClientChat(new Socket(Config.getServerIP(),Config.getServerPort()),
						JComboBox_Name.getSelectedItem().toString(),
						new String(JTextField_Pass.getPassword()),
						JComboBox_State.getSelectedItem().toString());
						
				if(client.connect())
				{
					System.out.println("��½�ɹ�");
					new NetZone(client).setVisible(true);
					this.dispose();
				}
				else
				{
					msg("û�����ӷ��������û����ظ�����½ʧ�ܣ�");
					return;
				}
				
			} catch (UnknownHostException e) {
				msg("��½ʧ�ܣ�");
				e.printStackTrace();
			} catch (IOException e) {
				msg("��½ʧ�ܣ�");
				e.printStackTrace();
			}
	}
	protected void JButton_Cancel_Click() {
		System.exit(0);
	}
	private void initFrame() {
		int width=400,height=300;
		this.setSize(width,height);
		this.setLocation((1024-width)/2, (768-height)/2);
		this.setResizable(false);
		this.setLayout(null);
	}

	public static void main(String[] args) {
		
		new LoadFrame().setVisible(true);

	
	}
	
	
	private static final long serialVersionUID = -551206865970130703L;
	private JPanel JPanel_Input = new JPanel();
	private JLabel JLabel_Name = new JLabel("�û�����");
	private JLabel JLabel_Pass = new JLabel("���룺");
	private JLabel JLabel_State = new JLabel("��½״̬��");
	private JComboBox JComboBox_Name = new JComboBox();
	private JPasswordField JTextField_Pass = new JPasswordField();
	private JComboBox JComboBox_State = new JComboBox(new String[]{"����","����"}); 
	private JButton JButton_Accede = new JButton("ȷ��");
	private JButton JButton_Cancel = new JButton("ȡ��");
}
