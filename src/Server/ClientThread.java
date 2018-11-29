package Server;


import POJO.Peer;
import POJO.RFC;

import java.io.*;
import java.net.Socket;
import java.util.Set;

import static Server.Server.peerMap;
import static Server.Server.rfcMap;
/**
 * The class is used to maintain a separate thread for each client connection with the server
 * @author  Jubin Antony Thykattil.
 * **/
class ClientThread extends Thread
{
    private Socket socket;
    private BufferedReader reader;
    private BufferedOutputStream out;
    private BufferedInputStream fileReader;

    public ClientThread(Socket socket)
    {
        this.socket = socket;
    }

    public void run() {
        try {
            while (true) {
                //create the buffered reader
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //create the output buffered reader
                out = new BufferedOutputStream(socket.getOutputStream());
                PrintWriter out2 = new PrintWriter(socket.getOutputStream(), true);
                //read the filename
                while (true ) {
                    int count = 0;
                    StringBuffer sb = new StringBuffer();
                    while (count < 4) {
                        String fileName = reader.readLine();
                        sb.append(fileName);
                        sb.append((" "));
                        count++;
                    }
                    /*System.out.println("inside thread : "+sb.toString());*/
                    String methodType = ServerHelpers.getMethodType(sb.toString());
                    /*System.out.println(methodType);*/
                    Integer portNo = ServerHelpers.getPortNumber(sb.toString());
                    /*System.out.println(portNo);*/
                    String hostName = ServerHelpers.getHostName(sb.toString());
                    /*System.out.println(hostName);*/
                    Peer peer = new Peer(hostName, portNo);
                    peerMap.put(portNo, peer);
                    if (methodType.equals("ADD")) {
                        Integer RFCId = ServerHelpers.getRFCNo(sb.toString());
                        String RFCTile =ServerHelpers.getRFCTitle(sb.toString());
                        RFC rfc = new RFC(RFCId);
                        rfc.setRFCName(RFCTile);
                        if (rfcMap.containsKey(RFCId)) {
                            Set<Peer> tempSet = rfcMap.get(RFCId).getListOfPeerWithRFC();
                            Peer tempPeer = peerMap.get(portNo);
                            tempSet.add(tempPeer);
                            System.out.println(ServerMessages.generateOKForAdd(sb.toString()));
                            out2.flush();
                            out2.println(ServerMessages.generateOKForAdd(sb.toString()));
                        } else {
                            Peer tempPeer = peerMap.get(portNo);
                           /* System.out.println("PeerName: " + peer.getHostName());
                            System.out.println("PeerID: " + peer.getUploadPortNumber());*/
                            rfc.setListOfPeerWithRFC(tempPeer);
                            rfcMap.put(RFCId, rfc);
                            System.out.println(ServerMessages.generateOKForAdd(sb.toString()));
                            out2.flush();
                            out2.println(ServerMessages.generateOKForAdd(sb.toString()));
                        }
                    } else if (methodType.equals("LIST")) {
                       /* System.out.println("LIST");
                        System.out.println("Inside List : " + rfcMap);*/
                        System.out.println(ServerMessages.generateOKForList(rfcMap));
                        out2.println(ServerMessages.generateOKForList(rfcMap));
                    } else if (methodType.equals("LOOKUP")) {
                        Integer RFCId = ServerHelpers.getRFCNo(sb.toString());
                        RFC rfc = rfcMap.get(RFCId);
                        System.out.println("LOOKUP");
                        if (!rfcMap.containsKey(RFCId)) {
                            ServerMessages.generateNotFoundMessage();
                            out2.println(ServerMessages.generateNotFoundMessage());
                        } else {
                            System.out.println(ServerMessages.generateOKForLookUP(rfc, peerMap));
                            out2.println(ServerMessages.generateOKForLookUP(rfc, peerMap));
                            /*System.out.println("..............................................");*/
                        }
                    }
                    else if(methodType.equals("LEAVE")) {
                        ServerHelpers.deleteEntries(peer,peerMap,rfcMap);
                        out2.println(ServerMessages.generateOKForLeave());
                      /*  System.out.println("RFCMAP: " + rfcMap);
                        System.out.println("PeerMap "+peerMap);*/
                        out2.close();
                        closeConnection();
                        return;
                    }
                    else{
                            System.out.println(ServerMessages.generateBadRequestMessage());
                        }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeConnection()
    {
        try
        {
            if(out!=null)
            {
                out.close();
            }
            if(reader!=null)
            {
                reader.close();
            }
            if(fileReader!=null)
            {
                fileReader.close();
            }
            if(out!=null)
            {
                socket.close();
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }


}

