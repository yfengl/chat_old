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
			Debug.out("准备发送文件"+path+"...");
			dos.writeUTF(path);
			FileInputStream fis = new FileInputStream(sendFile);
			dos.writeLong(sendFile.length());//写入文件大小
			byte buff[] = new byte[buffSize];
			int size;
			while((size=fis.read(buff))!=-1){
				Debug.out("发送->"+size);
				dos.write(buff,0,size);
			}
			Debug.out("发送文件成功！");
			fis.close();
			//dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}//写入文件信息
			
		}else{
			for(File f:sendFile.listFiles()){
				try {
					if(!f.isFile()){
						String path = f.getPath().substring(file.getPath().length(),f.getPath().length());
						Debug.out("发送目录信息------"+path);
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
			dos.writeInt(Message.FILE_END);//发送结束消息
			dos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		try {
		
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			//如果是文件
			if(file.isFile()){
				Debug.out("准备发送文件...");
				dos.writeInt(Message.FILE);//写入文件信息
				FileInputStream fis = new FileInputStream(file);
				byte buff[] = new byte[buffSize];
				int size;
				while((size=fis.read(buff))!=-1){
					Debug.out("发送-"+size);
					dos.write(buff,0,size);
				}
				Debug.out("发送文件成功！");
				fis.close();
				dos.close();
				socket.close();
			}else{
				for(File f:file.listFiles()){
					
					Debug.out(f.getPath());
				}
			}
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	*/
		
	}

}
