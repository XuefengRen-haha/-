/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */

package Server;

import TwoInterface.clientInterface;
import TwoInterface.serverInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;




public class boardServer extends UnicastRemoteObject implements serverInterface {
    private ArrayList<user> users;
    private byte[] bytes;
    public boardServer() throws RemoteException {
        super();
        users = new ArrayList<user>();

    }
    @Override
    public void getImage(byte[] bytes) {

        this.bytes = bytes;
        for(user u : users){
            try {
                u.getClient().downloadImage(bytes);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public synchronized void registerListener(String[] details) throws RemoteException {

        System.out.println(details[0] + " has joined the session");
        System.out.println(details[0] + "'s hostname : " + details[1]);
        System.out.println(details[0] + "'s RMI service : " + details[2]);

        try {
            clientInterface nextClient = (clientInterface) Naming.lookup("rmi://" + details[1] + "/" + details[2]);
            users.add(new user(details[0], nextClient));
            if(users.size() > 1) {
                if(inviteUser(users.get(users.size() - 1).getName())) {
                    users.remove(users.size() - 1);
                    nextClient.reject("The manager reject your request.\n");
                    return ;
                }
            }
            if(bytes != null) {
                nextClient.downloadImage(bytes);
            }
            broadcast("Notice: " + details[0] + " has joined.\n");
            updateUserList();
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (NotBoundException e) {

            e.printStackTrace();
        }
    }

    private void updateUserList() {

        String[] currentUsers = new String[users.size()];
        for(int i = 0; i< currentUsers.length; i++){
            currentUsers[i] = users.get(i).getName();
        }

        for(user c : users){
            try {
                c.getClient().updateUserList(currentUsers);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean checkRoom() {

        if(users.size() == 0) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean inviteUser(String str) throws RemoteException {

        return users.get(0).getClient().allowByManager(str);
    }

    @Override
    public void removeUser(String username) throws RemoteException {

        int index = 0;
        for(int i = 0; i < users.size();i++) {
            if(users.get(i).getName().equals(username)) {
                index = i;
            }
        }
        if(index == 0) {
            users.get(0).getClient().inform("Fail to kick out.");
        }
        else {
            clientInterface removedUser = users.get(index).getClient();
            String removedUserName = users.get(index).getName();
            users.remove(index);
            users.get(0).getClient().inform("Kick out Successfully!");
            broadcast("Notice :" + removedUserName + " has been kicked out!\n");
            updateUserList();
            Thread t = new Thread(()->{
                try {
                    removedUser.reject("You have been kicked out.\n");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }


    @Override
    public void broadcast(String message) {
        for(user c : users){
            try {
                c.getClient().receiveNotice(message + "\n");
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void isRoomEmpty(String[] details) throws RemoteException {

        if(users.size() > 0) {
            try {
                clientInterface nextClient = (clientInterface) Naming.lookup("rmi://" + details[1] + "/" + details[2]);
                nextClient.reject("The white board has been created\n");
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (NotBoundException e) {

                e.printStackTrace();
            }
        }
    }


    @Override
    public void isSameName(String[] details) throws RemoteException {

        try {
            clientInterface nextClient = (clientInterface) Naming.lookup("rmi://" + details[1] + "/" + details[2]);
            for(user c : users){
                if(c.getName().equals(details[0])) {
                    nextClient.reject("Your username is being used.\n");
                }
            }
        } catch (MalformedURLException | NotBoundException e) {

            e.printStackTrace();
        }
    }


    @Override
    public void exitRoom(String username) {

        int index = 0;
        for(int i = 0;i < users.size();i++) {
            if(users.get(i).getName().equals(username)) {
                index = i;
            }
        }
        users.remove(index);
        broadcast("Notice: " + username + " has exited!\n");
        updateUserList();
    }


    @Override
    public void endApplication() {

        System.exit(0);
    }

}
