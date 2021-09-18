/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */




package managerClient;





import TwoInterface.clientInterface;
import TwoInterface.serverInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Locale;

public class manager extends UnicastRemoteObject implements  clientInterface {


    private String userName;
    private String hostName;
    private String serverServiceName;
    private String clientServiceName;
    private managerGUI GUI;
    private serverInterface whiteboard;


    public manager(String username,String IP,String port) throws RemoteException{
        this.userName = username.trim();
        this.hostName  = IP + ":" + port;
        this.serverServiceName = "whiteboardServer";
        this.clientServiceName = "Create";
        this.GUI = new managerGUI();
    }

    public static void main(String [] args) {
        Locale.setDefault(Locale.ENGLISH);
        manager mang;
        try {
            if (args.length < 2){
                JOptionPane.showMessageDialog(null, "Input the address and port.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            if(args.length == 2) {
                JOptionPane.showMessageDialog(null, "Please input your userName", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            mang = new manager(args[2],args[0],args[1]);
            mang.connectServer();
        } catch (RemoteException e) {

            e.printStackTrace();
        }
    }

    public void connectServer() {
        try {

            String[] details = {userName,hostName,clientServiceName};
            Naming.rebind("rmi://" + hostName + "/" + clientServiceName, this);
            whiteboard = (serverInterface) Naming.lookup("rmi://" + hostName + "/" + serverServiceName);
            whiteboard.isRoomEmpty(details);
            whiteboard.registerListener(details);
            GUI.set_whiteboard(whiteboard);
            GUI.getpanel().setwhiteboard(whiteboard);
        } catch (RemoteException | NotBoundException e1) {

            JOptionPane.showMessageDialog(null, "Fail to connect with Server.", "Error", JOptionPane.ERROR_MESSAGE);
            e1.printStackTrace();
            System.exit(0);
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }
    }



    @Override
    public void receiveNotice(String message) {

        GUI.gettextArea().append(message);
    }


    @Override
    public void updateUserList(String[] currentUsers) {

        GUI.getJlist().setListData(currentUsers);
    }


    @Override
    public boolean allowByManager(String str) {
        int flag = JOptionPane.showConfirmDialog(null,str + " wants to share your whiteboard\n" ,"Allow?", JOptionPane.YES_NO_OPTION);
        if(flag == 1) {
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public void downloadImage(byte[] bytes) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(in);
            GUI.getpanel().load(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void reject(String str) {
        JOptionPane.showMessageDialog(null, str , "Information", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    @Override
    public void inform(String str) {

        Thread t = new Thread(()->{
            JOptionPane.showMessageDialog(null, str, "Information", JOptionPane.INFORMATION_MESSAGE);
        });
        t.start();
    }
}

