package Clients;


import AppConstants.RFCTitle;
import ClientHelpers.ClientHelper;
import ClientHelpers.ClientToClientRequestMessages;
import ClientHelpers.ClientToClientResponseMessage;
import ClientHelpers.ClientToServerRequestMessages;
import POJO.Peer;
import POJO.RFC;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 *This is client number 2,the code for client 1 and 2 seems to be the same and could have been implemented as just
 * as a thread of a common client class
 * The reason to have separate clients were to make the client more autonmous and to design in a manner which would make
 * clients autonomous
 * @author: Jubin Antony Thykattil.
 */
public class Client1
{
    public static Socket SOCKET;
    public static void sendMessages() throws IOException {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader reader  = new BufferedReader(in);
        String ipAddress = "";
        String fileName = "";
        System.out.print("Please enter a valid Server Ip address: ");
        //read the entered ip address
        ipAddress = reader.readLine();
        SOCKET = new Socket(ipAddress, 7734);
        InputStream inputByte = SOCKET.getInputStream();
        BufferedInputStream input = new BufferedInputStream(inputByte);
        PrintWriter out = new PrintWriter(SOCKET.getOutputStream(), true);
        Peer peer = new Peer(InetAddress.getLocalHost().getHostName(), 49155);

        while(true)
        {
            byte[] buffer = new byte[1024];
            System.out.print("Enter the command : ");
            String userInput =reader.readLine();
            if(userInput.equals("STARTUP")) {
                LinkedList<RFC> RFCList = ClientHelper.getRFCList("src\\ClientFiles\\Client1");
                for (RFC rfc : RFCList) {
                    out.flush();
                    out.println(ClientToServerRequestMessages.generateAddMessage(rfc, peer));
                    out.flush();
                    input.read(buffer,0,buffer.length);
                    System.out.println(new String(buffer).trim());
                }
            }
            else if(userInput.equals("LIST")) {
                out.println(ClientToServerRequestMessages.generateListMessage(peer));
                input.read(buffer);
                System.out.println(new String(buffer).trim());
            }
            else if(userInput.equals("LOOKUP")) {
                System.out.print("enter rfc id for lookup : ");
                int rfcID = Integer.parseInt(reader.readLine());
                RFC rfc = new RFC(rfcID);
                rfc.setRFCName(RFCTitle.getRFCName(rfcID));
                out.println(ClientToServerRequestMessages.generateLookUpMessage(rfc,peer));
                input.read(buffer);
                String lookupInput = new String(buffer);
                if(ClientHelper.getMethod(lookupInput)!="200 OK") {
                    System.out.println(new String(buffer));
                }
                else{
                    /*System.out.println("Port Number : "+ClientHelper.getPortNumber(lookupInput.trim()));*/
                    System.out.println(new String(buffer).trim());
                }

            }
            else if(userInput.equals("LEAVE")){
                out.println(ClientToServerRequestMessages.generateLeaveServerMessage(peer));
                input.read(buffer);
                String leaveConfirm = new String(buffer);
                System.out.println(leaveConfirm.trim());
            }
            else if(userInput.equals("GET")){
                System.out.print("enter rfc id for the file : ");
                int rfcID = Integer.parseInt(reader.readLine());
                RFC rfc = new RFC(rfcID);
                rfc.setRFCName(RFCTitle.getRFCName(rfcID));
                out.println(ClientToServerRequestMessages.generateLookUpMessage(rfc,peer));
                input.read(buffer);
                String lookupInput = new String(buffer);
                System.out.println("lookUpInput:"+lookupInput.trim());
                if(!ClientHelper.getMethod(lookupInput).equals("200 OK")) {
                    System.out.println(new String(buffer));
                }
                else{
                  /*  System.out.println("Port Number : "+ClientHelper.getPortNumber(lookupInput.trim()));*/
                    int portNumber =ClientHelper.getPortNumber(lookupInput.trim());
                    /*System.out.println("Port Number Client1 : " +portNumber);*/
                    Socket tempSocket= new Socket(ipAddress, portNumber);
                    PrintWriter tempOut = new PrintWriter(tempSocket.getOutputStream(), true);
                    tempOut.println(ClientToClientRequestMessages.getMessage(rfcID,InetAddress.getLocalHost().getHostName()));
                    InputStream inputByte2 = tempSocket.getInputStream();
                    BufferedInputStream tempInput = new BufferedInputStream(inputByte2);
                    byte[] tempBuffer = new byte[1024];
                    StringBuffer sb = new StringBuffer("src\\ClientFiles\\Client1\\rfc");
                    sb.append(rfcID);
                    sb.append(".txt");
                    /*BufferedOutputStream outputFile = new BufferedOutputStream(new FileOutputStream(sb.toString()));*/
                    PrintWriter writer = new PrintWriter(sb.toString(), "UTF-8");
                    int bytesRead = 0;
                    int count=0;
                    while ((bytesRead = tempInput.read(tempBuffer)) != -1) {
                        String s =new String(tempBuffer);
                        String[] sSplit;
                        if(count==0) {
                            sSplit =s.split("Txt",2);
                            System.out.print(sSplit[0]);
                            System.out.println("Txt");
                            writer.print("tXT");
                            writer.println(sSplit[1]);
                            writer.flush();
                        }
                        else {
                            writer.println(new String(tempBuffer));
                        }
                        count++;
                    }
                    writer.close();
                    inputByte2.close();
                    tempInput.close();
                    tempOut.close();
                    tempSocket.close();
                    RFC tempRFC = new RFC(rfcID);
                    tempRFC.setRFCName(RFCTitle.getRFCName(rfcID));
                    out.println(ClientToServerRequestMessages.generateAddMessage(tempRFC, peer));
                    input.read(buffer);
                    System.out.println(new String(buffer));
                }
            }
            else{
                System.out.println(Server.ServerMessages.generateBadRequestMessage());
            }
        }
    }

    public static void main(String [] args) throws IOException {
        new receieveMessage().start();
        sendMessages();
    }
}


class receieveMessage extends Thread{
    BufferedOutputStream tempOut;
    BufferedInputStream fileReader;
    File file;
    BufferedInputStream tempInput;
    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(49155);
            while(true) {
                Socket clientSocket = serverSocket.accept();
                InputStream inputByte = clientSocket.getInputStream();
                tempInput = new BufferedInputStream(inputByte);
                tempOut = new BufferedOutputStream(clientSocket.getOutputStream());
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                tempInput.read(buffer);
                System.out.println("Inside Client 1 "+ new String(buffer));
                Integer rfcID =ClientHelper.getRFCIDFromGETMessage(new String(buffer));
                file= new File("src\\ClientFiles\\Client1\\rfc"+rfcID+".txt");
                fileReader = new BufferedInputStream(new FileInputStream(file));
                StringBuffer sb = new StringBuffer();
                sb.append("src\\ClientFiles\\Client1\\rfc");
                sb.append(rfcID);
                sb.append(".txt");
                tempOut.write(ClientToClientResponseMessage.generateOKMessage(sb.toString()).getBytes());
                while ((bytesRead = fileReader.read(buffer)) != -1) {
                    tempOut.write(buffer, 0, bytesRead);
                    tempOut.flush();
                }
                //close the connection after the download is finished
                if(tempOut!=null){tempOut.close();}
                if(tempInput!=null){tempInput.close();}
                if(fileReader!=null){fileReader.close();}
                if(clientSocket!=null){clientSocket.close();}

                System.out.println("out");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}