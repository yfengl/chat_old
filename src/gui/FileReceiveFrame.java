package gui;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.Debug;
import net.Message.FileReceiveMsg;
import net.Message.FileSendMsg;
import net.Message.Message;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FileDialog.java
 *
 * Created on 2009-4-24, 15:56:00
 */

/**
 *
 * @author Administrator
 */
public class FileReceiveFrame extends javax.swing.JDialog implements Runnable{

    /** Creates new form FileDialog */
	private FileSendMsg fsm;
	private String savePath ;
	private Socket socket;
	private int buffSize = 5000;
	private long nowSize = 0;
    public FileReceiveFrame(FileSendMsg fsm) {
        super();
        this.fsm = fsm;
        
        new Thread(this).start();
    }
    /*
	public void startReceive(){
		try {
			socket= new Socket(fsm.getIp(),fsm.getPort());
			if(JOptionPane.showConfirmDialog(null, 
					"收到文件"+fsm.getFileName()+",来自："+fsm.getSender()+"\n你是否要接收该文件?",
					"收到文件",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION ){
				JFileChooser fc = new JFileChooser();
				if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
					
					//client.ReceiveFile(s,fc.getCurrentDirectory().getPath()+"\\"+fc.getSelectedFile().getName());
					jProgressBar1.setMaximum((int)fsm.getSize()/1024);
					jProgressBar1.setString("0/"+(fsm.getSize()/1024));
					jLabel1.setText("收到文件"+fsm.getFileName()+",来自："+fsm.getSender());
					savePath = fc.getCurrentDirectory().getPath()+"\\"+fc.getSelectedFile().getName();
					new Thread(this).start();
					this.setVisible(true);
				}
			}else{
				socket.close();
			}
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return;
			} catch (IOException e) {
				
				e.printStackTrace();
				return;
			} 		
	}*/
	public void run() {
		
		try {
			socket= new Socket(fsm.getIp(),fsm.getPort());
			if(JOptionPane.showConfirmDialog(null, 
					"收到文件"+fsm.getFileName()+",来自："+fsm.getSender()+"\n你是否要接收该文件?",
					"收到文件",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION ){
				JFileChooser fc = new JFileChooser();
				if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
					initComponents();
					//client.ReceiveFile(s,fc.getCurrentDirectory().getPath()+"\\"+fc.getSelectedFile().getName());
					jProgressBar1.setMaximum((int)fsm.getSize()/1024);
					jProgressBar1.setString("0/"+(fsm.getSize()/1024));
					jLabel1.setText("收到文件"+fsm.getFileName()+",来自："+fsm.getSender());
					savePath = fc.getCurrentDirectory().getPath()+"\\"+fc.getSelectedFile().getName();
					this.setVisible(true);
				}
			}else{
				socket.close();
			}
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return;
			} catch (IOException e) {
				
				e.printStackTrace();
				return;
			} 		
		try {
			//Socket s = new Socket(msg.getIp(),msg.getPort());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			int head;
			//如果是个目录则先创建文件夹
			File root = new File(savePath);
			if(!root.isFile()){
				Debug.out("准备创建目录->"+savePath+"...");
				root.mkdirs();
			}
			while((head=dis.readInt())!=Message.FILE_END){
				if(head==Message.FILE){//判断接受的是否为文件
					//
					String path = dis.readUTF();
					Debug.out("准备创建文件->"+savePath+""+path+"...");
					File f =new File(savePath+""+path);
					jLabel1.setText("收到文件"+fsm.getFileName()+",来自："+fsm.getSender()+"正在接受文件->"+path);
					f.createNewFile();
					Debug.out("准备接收文件...");
					long size = dis.readLong();//读取文件大小
					FileOutputStream fos = new  FileOutputStream(f);
					byte buff[] = new byte[buffSize];
					int receiveSize;//一次写入缓存区的大小
					if(size<=buffSize){
						dis.read(buff,0,(int)size);
						fos.write(buff,0,(int)size);
						//向GUI发送消息
						updateProgress(size);
					//	sendMsgToListener(new FileReceiveMsg(getName(),new String[]{getName()},nowSize));
					
					}else{
						while( (receiveSize = dis.read(buff))!=-1){
							Debug.out("接收->"+receiveSize);
							fos.write(buff,0,receiveSize);
							updateProgress(receiveSize);
							size-=receiveSize;
							if(size<=buffSize){
								dis.read(buff,0,(int)size);
								fos.write(buff,0,(int)size);
								updateProgress(size);
								break;
							}
						}
					}
					Debug.out("接收文件成功！");
					//dis.close();
					fos.close();
				}
				else{
					String path = dis.readUTF();
					
					File f =new File(savePath+"\\"+path);
					Debug.out("准备创建文件夹->"+savePath+"\\"+path+"...");
					f.mkdirs();
					//dis.close();
				}
			}
			dis.close();
			
			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	private void updateProgress(long size){
		nowSize+=size;
		jProgressBar1.setValue((int)nowSize/1024);
		jProgressBar1.setString(nowSize/1024+"K/"+fsm.getSize()/1024+"K");
	}
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 20, 300, 23);

        jButton1.setText("转发");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(180, 100, 90, 23);

        jButton2.setText("取消");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(280, 100, 90, 23);

        jProgressBar1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jProgressBar1PropertyChange(evt);
            }
        });
        getContentPane().add(jProgressBar1);
        jProgressBar1.setBounds(30, 50, 340, 20);
        jProgressBar1.setStringPainted(true);
        
        this.setSize(480,180);
     //   pack();
    }// </editor-fold>

    private void jProgressBar1PropertyChange(java.beans.PropertyChangeEvent evt) {
        if(jProgressBar1.getValue()==jProgressBar1.getMaximum()){
        	jLabel1.setText("文件->"+fsm.getFileName()+"接受成功!");
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    	
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
    	if(jProgressBar1.getValue()<jProgressBar1.getMaximum()&&
    			JOptionPane.showConfirmDialog(null,"正在接受文件,你确定要关闭吗?",
    					"确定",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION ){
    		return;
    	}
    	this.dispose();
    	try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

  
    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration
	

}
