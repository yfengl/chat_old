package net;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import net.Message.Message;

public class FileSender extends Thread {

	private File file;
	private Socket socket;
	private int buffSize = 5000;
	public FileSender(File file, Socket socket) {
		super();
		this.file = file;
		this.socket = socket;
		this.start();
	}
	private void sendFile(File sendFile,DataOutputStream dos){
		if(sendFile.isFile()){
			
			try {
			dos.writeInt(Message.FILE);
			String path = sendFile.getPath().substring(file.getPath().length(),sendFile.getPath().length());
			Debug.out("׼�������ļ�"+path+"...");
			dos.writeUTF(path);
			FileInputStream fis = new FileInputStream(sendFile);
			dos.writeLong(sendFile.length());//д���ļ���С
			byte buff[] = new byte[buffSize];
			int size;
			while((size=fis.read(buff))!=-1){
				Debug.out("����->"+size);
				dos.write(buff,0,size);
			}
			Debug.out("�����ļ��ɹ���");
			fis.close();
			//dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}//д���ļ���Ϣ
			
		}else{
			for(File f:sendFile.listFiles()){
				try {
					if(!f.isFile()){
						String path = f.getPath().substring(file.getPath().length(),f.getPath().length());
						Debug.out("����Ŀ¼��Ϣ------"+path);
						dos.writeInt(Message.DIRECT);
						dos.writeUTF(path);
						//dos.close();
					}
				//Debug.out(f.getPath().substring((int)file.getPath().length(), (int)f.getPath().length()));
					sendFile(f,dos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void run() {
		try {
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			sendFile(file,dos);
			dos.writeInt(Message.FILE_END);//���ͽ�����Ϣ
			dos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		try {
		
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			//������ļ�
			if(file.isFile()){
				Debug.out("׼�������ļ�...");
				dos.writeInt(Message.FILE);//д���ļ���Ϣ
				FileInputStream fis = new FileInputStream(file);
				byte buff[] = new byte[buffSize];
				int size;
				while((size=fis.read(buff))!=-1){
					Debug.out("����-"+size);
					dos.write(buff,0,size);
				}
				Debug.out("�����ļ��ɹ���");
				fis.close();
				dos.close();
				socket.close();
			}else{
				for(File f:file.listFiles()){
					
					Debug.out(f.getPath());
				}
			}
		} catch (IOException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	*/
		
	}

}
