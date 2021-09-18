/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */

package TwoInterface;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface clientInterface extends Remote{
    void receiveNotice(String message) throws RemoteException;
    void updateUserList(String[] currentUsers) throws RemoteException;
    boolean allowByManager(String str) throws RemoteException;
    void downloadImage(byte[] b) throws RemoteException;
    void reject(String str)throws RemoteException;
    void inform(String str) throws RemoteException;
}
