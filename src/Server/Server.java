package Server;

import POJO.Peer;
import POJO.RFC;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This Server maintains connections with multiple hosts by running the clientThread
 * Each thread maintains a connection with one client
 * @author: jubin
 */
public class Server
{
    //map to maintain the information about each RFC
    public static Map<Integer,RFC> rfcMap = new HashMap<Integer,RFC>();
    //map to maintain the information about each Peer.
    public static Map<Integer,Peer> peerMap =new HashMap<Integer,Peer>();

    public static void main(String [] args)
    {
        try
        {
            //initialize the Server Socket class
            ServerSocket serverSocket = new ServerSocket(7734);

            //boolean variable to stop the server
            boolean isStopped = false;
            while(!isStopped )
            {
                //create client socket object
                Socket clientSocket = serverSocket.accept();
                //create and start client thread
                ClientThread clientThread = new ClientThread(clientSocket);
                clientThread.start();
            }
        }
        catch(IOException e)
        {
            System.out.println("Port 7734 is already opened! Please use another port.");
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}


