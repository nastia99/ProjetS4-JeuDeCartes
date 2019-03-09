package core.interfaceScript.javafx;

import java.io.IOException;

import network.tcp.server.ThreadPoolServerTCP;

public class MainTry {
    public static void main(String[] args) throws InterruptedException {
        UIManager uim = UIManager.getInstance();
        Thread t = new Thread() {
            public void run(){
                uim.start();
            }
        };
        t.start();
        try {
            uim.awake();
        } catch (IOException e) {
            e.printStackTrace();
        }       
    }
}
