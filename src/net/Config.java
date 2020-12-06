package net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Config {
    //获取服务器端口
    private File file;
    private int serverPort;
    private String serverIP;
    private static Config config;

    private Config() throws IOException {
        file=new File("resources/serverConfig.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private boolean readFormFile() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            br.readLine();
            serverPort = Integer.parseInt(br.readLine());
            br.readLine();
            serverIP = br.readLine();//"127.0.0.1";
            System.out.println("成功 读取配置文件：serverPort->" + serverPort + ",serverIP->" + serverIP);
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
        //TODO 自动生成 catch 块
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Config getConfig() throws IOException {
        if (config == null) {
            config = new Config();
            config.readFormFile();
        }
        return config;
    }

    public int getPort() {
        return serverPort;
    }

    public String getIP() {
        return serverIP;
    }

    public static int getServerPort() throws IOException {
        return getConfig().getPort();
    }

    public static String getServerIP() throws IOException {
        return getConfig().getIP();
    }
}
