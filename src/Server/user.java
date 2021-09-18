/**
 * Author : Xuefeng REN
 * Student ID: 1011257
 * Surname: XUEFENGR
 */

package Server;


import TwoInterface.clientInterface;

public class user {
    public String name;
    public clientInterface client;

    //constructor
    public user(String name, clientInterface client){
        this.name = name;
        this.client = client;
    }
    //getters and setters
    public String getName(){
        return name;
    }
    public clientInterface getClient(){
        return client;
    }
}
