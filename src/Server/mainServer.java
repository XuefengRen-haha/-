/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */

package Server;



import TwoInterface.serverInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class mainServer{
    public static void main(String[] args) {
        try {
            serverInterface whiteboard = new boardServer();
            int port = Integer.parseInt(args[0]);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("whiteboardServer", whiteboard);
            System.out.println("The server has started using the port: "+ port);
        } catch (RemoteException e) {

            e.printStackTrace();
        } catch (AlreadyBoundException e) {

            e.printStackTrace();
        }
    }
}

