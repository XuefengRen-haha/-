/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */

package TwoInterface;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface serverInterface extends Remote{
    void getImage(byte[] b) throws RemoteException;
    boolean checkRoom() throws RemoteException;
    void registerListener(String[] details)throws RemoteException;
    boolean inviteUser(String str) throws RemoteException;
    void removeUser(String username) throws RemoteException;
    void broadcast(String msg) throws RemoteException;
    void isRoomEmpty(String[] details) throws RemoteException;
    void isSameName(String[] details) throws RemoteException;
    void exitRoom(String username) throws RemoteException;
    void endApplication() throws RemoteException;
}
