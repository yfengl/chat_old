package net.Message;

import java.io.Serializable;
/**
 * 
 * 所有消息必须实现的接口
 *
 */
public interface Message extends Serializable{
	int FILE_SEND = 3;
	int STRING = 1;
	int NEW_CLIENT = 0;
	int BREAK = 2;
	int SUCCESS = 1;
	int UNSUCCESS = 0;
	int FILE = 4;
	int DIRECT = 5;
	int FILE_END =10;
	int FILE_RECEIVE=6;
	int IMAGE = 7;
	int getType();
	////////////////////////////////
	int NEW_SNAKE = 8;
	int QUIT = 9;
	int SNAKE_MOVE = 11;
	///////////////////
	
	String getSender();
	String[] getReceive();
}
