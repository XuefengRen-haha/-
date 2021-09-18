/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */

package userClient;





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

public class joinUser extends UnicastRemoteObject implements clientInterface {


    private String userName;
    private String hostName;
    private String serviceName;
    private String clientServiceName;
    private userGUI GUI;
    private serverInterface whiteboard;


    public joinUser(String username,String IP,String port) throws RemoteException {
        this.userName = username.trim();
        this.hostName  = IP + ":" + port;
        this.serviceName = "whiteboardServer";
        this.clientServiceName = "Create";
        this.GUI = new userGUI();
    }

    public static void main(String [] args) {
        Locale.setDefault(Locale.ENGLISH);
        joinUser joinuser;
        try {
            if (args.length < 2){
                JOptionPane.showMessageDialog(null, "Input the address and port.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            if(args.length == 2) {
                JOptionPane.showMessageDialog(null, "Please input your username", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            joinuser = new joinUser(args[2],args[0],args[1]);
            joinuser.connectServer();
        } catch (RemoteException e) {

            e.printStackTrace();
        }
    }

    public void connectServer() {
        try {

            String[] details = {userName,hostName,clientServiceName};
            Naming.rebind("rmi://" + hostName + "/" + clientServiceName, this);
            whiteboard = (serverInterface) Naming.lookup("rmi://" + hostName + "/" + serviceName);
            if(!whiteboard.checkRoom()) {
                JOptionPane.showMessageDialog(null, "The application has not been created.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            whiteboard.isSameName(details);
            whiteboard.registerListener(details);
            GUI.set_whiteboard(whiteboard);
            GUI.setUsername(userName);
            GUI.getpanel().setwhiteboard(whiteboard);
        } catch (RemoteException | NotBoundException e1) {

            JOptionPane.showMessageDialog(null, "Fail to connect with the Server.", "Error", JOptionPane.ERROR_MESSAGE);
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

        return false;
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

        JOptionPane.showMessageDialog(null, str , "Error", JOptionPane.ERROR_MESSAGE);
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

