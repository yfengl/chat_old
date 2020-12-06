/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NetZone.java
 *
 * Created on 2009-4-25, 10:05:42
 */

package gui;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.ClientChat;
import net.Debug;
import net.Message.FileSendMsg;
import net.Message.ImageMsg;
import net.Message.Message;
import net.Message.MsgListener;
import net.Message.StringMsg;

/**
 *
 * @author Administrator
 */
public class ChatDialog extends javax.swing.JFrame  implements MsgListener, ListSelectionListener{

    /** Creates new form NetZone */
	private ClientChat client;
	private String sender;
	 public ChatDialog(ClientChat client,String sender) {
			this.client = client;
			this.sender =sender;
			initComponents();
			this.client.addMsgListener(this);
	      //  client.start();
		}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

    	ListModel = new javax.swing.DefaultListModel(); 
    	
        jPanel_msgRoot = new javax.swing.JPanel();
        jSplitPane_root = new javax.swing.JSplitPane();


        jSplitPane_msg = new javax.swing.JSplitPane();
        jPanel_receive = new javax.swing.JPanel();
       // jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane_receive = new MyEditor();
        jPanel_send = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane_send = new MyEditor();

        jPanel_buttons = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton_sendMsg = new javax.swing.JButton();
        jButton_sendFile = new javax.swing.JButton();
        jButton_quit = new javax.swing.JButton();
        jButton_face = new javax.swing.JButton("表情");

        jPanel_msgRoot.setLayout(new java.awt.BorderLayout());


      
      

        jSplitPane_msg.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel_receive.setLayout(new java.awt.BorderLayout());

   //     jScrollPane1.setViewportView(jEditorPane_receive);

        jPanel_receive.add(jEditorPane_receive.scrollPane, java.awt.BorderLayout.CENTER);

        
        
        jSplitPane_msg.setTopComponent(jPanel_receive);

        jPanel_send.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setViewportView(jEditorPane_send);

        jPanel_send.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jSplitPane_msg.setRightComponent(jPanel_send);

        jSplitPane_root.setLeftComponent(jSplitPane_msg);

        jPanel_msgRoot.add(jSplitPane_root, java.awt.BorderLayout.CENTER);

        
        jPanel2.setLayout(new java.awt.BorderLayout());
        
        jPanel_buttons.add(jButton_face);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("发送给：");
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);
        jButton_face.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jButton_faceActionPerformed(evt);
            }
        });
        jPanel_buttons.add(jPanel2);

        jLabel2.setText("所有人");
        jPanel3.add(jLabel2);

        jPanel_buttons.add(jPanel3);

        jButton_sendMsg.setText("发送信息");
        jButton_sendMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sendMsgActionPerformed(evt);
            }
        });
        jPanel_buttons.add(jButton_sendMsg);

        jButton_sendFile.setText("发送文件");
        jButton_sendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sendFileActionPerformed(evt);
            }
        });
        jPanel_buttons.add(jButton_sendFile);

        jButton_quit.setText("退出");
        jButton_quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_quitActionPerformed(evt);
            }
        });
        jPanel_buttons.add(jButton_quit);
        

        jEditorPane_receive.setEditable(false);
        jSplitPane_root.setResizeWeight(0.85);
        jSplitPane_msg.setResizeWeight(0.8);

        ListModel.add(0,"所有人");
        ListModel.add(1,client.getName());
 		for(String s:client.getClientList())
 		{
 			ListModel.add(ListModel.getSize(),s);
 		}
 
        
        getContentPane().add(jPanel_buttons, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel_msgRoot, java.awt.BorderLayout.CENTER);
        setTitle("NetZone "+client.getName()+"---"+client.getLoadState()+"状态");
        setSize(800,600);
        this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
        	
        });
       // pack();
    }// </editor-fold>
   //点击发送表情事件
    protected void jButton_faceActionPerformed(ActionEvent evt) {
    	client.sendMsg(new ImageMsg(client.getName(),FaceDialog.getFace(this),getReceive()));
	}
	private void jButton_sendMsgActionPerformed(java.awt.event.ActionEvent evt) {
		if(!jEditorPane_send.getText().equals("")){
			client.sendMsg(new StringMsg(client.getName(),jEditorPane_send.getText(),getReceive()));
	    	jEditorPane_send.setText("");
		}
    }

    private void jButton_sendFileActionPerformed(java.awt.event.ActionEvent evt) {
    	JFileChooser fd = new JFileChooser();
   		fd.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
   		if(fd.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
   			try {
				client.sendFile(fd.getSelectedFile(), getReceive());
			} catch (IOException e) {
				msg("发送文件出错!");
				e.printStackTrace();
			}
   		}
   		
    }
    public  void msg(Object o)
	{
		JOptionPane.showMessageDialog(this, o);
	}
    private void jButton_quitActionPerformed(java.awt.event.ActionEvent evt) {
       //System.exit(0);
    	jEditorPane_receive.insertMsg(new ImageMsg("cyg3","先知.gif",null));
    }
    public void msgReceive(Message msg) {
    	
		switch(msg.getType()){
		case Message.NEW_CLIENT:
			ListModel.add(ListModel.getSize(), msg.getSender());
			break;
		case Message.BREAK:
			ListModel.removeElement(msg.getSender());
			if(msg.getSender().equals(sender)){//如果对方已经掉线则退出窗口
				this.dispose();
			}
			break;
		case Message.STRING:
		//	addMsg(msg.toString());
			
			break;
		case Message.FILE_SEND:
			if(msg.getSender().equals(sender)){
				new FileReceiveFrame((FileSendMsg)msg);	
			}
			
			return;
		}
		if(msg.getSender().equals(sender)){
			addMsg(msg);
		}
	
		
	}
	private String[] getReceive(){
		return new String[]{sender};
	}
	 private void addMsg(Message msg)
	   {
		 
		 jEditorPane_receive.insertMsg(msg);
		  // jEditorPane_receive.setText(jEditorPane_receive.getText()+"\n"+msg);
		  
		  // JScrollBar js = jEditorPane_receive.getParent();
		  //js.setValue(js.getMaximum()+10);
		  
	   }
	public void valueChanged(ListSelectionEvent arg0) {
		if(getReceive()==null)
			this.jLabel2.setText("所有人");
		else{
			String text = "";
			for(String s:getReceive()){
				text+=s+",";
			}
			this.jLabel2.setText(text);
		}
		
		
	}
  public static void main(String s[]){
	  
  }

    // Variables declaration - do not modify
    private MyEditor jEditorPane_receive;
    private MyEditor jEditorPane_send;
    private javax.swing.JPanel jPanel_msgRoot;
    private javax.swing.JPanel jPanel_receive;
    private javax.swing.JPanel jPanel_send;
   // private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane_msg;
    private javax.swing.JSplitPane jSplitPane_root;
    
    private javax.swing.DefaultListModel ListModel;
    private javax.swing.JButton jButton_quit;
    private javax.swing.JButton jButton_face;
    private javax.swing.JButton jButton_sendFile;
    private javax.swing.JButton jButton_sendMsg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_buttons;
    // End of variables declaration
	

}
