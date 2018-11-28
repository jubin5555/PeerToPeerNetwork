package Server;

import POJO.Peer;
import POJO.RFC;

import java.util.*;
/***
 * The class is used to maintain a uniformity about Server messages which are send to the clients
 * The messages sends are replies to ADD,Lookup,LIST messages from the client
 * @author: jubin
 */
public class ServerMessages {
    public static String generateOKForAdd(String clientResponse){
        StringBuffer sb = new StringBuffer();
        sb.append("P2P-CI/1.0 200 OK");
        sb.append(System.getProperty("line.separator"));
        sb.append(clientResponse);
        return sb.toString();
    }
    public static String generateOKForLookUP(RFC rfc,Map<Integer,Peer> peer) {
        StringBuffer sb = new StringBuffer();
        sb.append("P2P-CI/1.0 200 OK");
        sb.append(System.getProperty("line.separator"));
        sb.append(generatePeerInformation(rfc,peer));
        return sb.toString();
    }
    public static String generateOKForList(Map<Integer,RFC> rfc) {
        System.out.println("Inside generateOkList");
        StringBuffer sb = new StringBuffer();
        sb.append("P2P-CI/1.0 200 OK");
        sb.append(System.getProperty("line.separator"));
        sb.append(generateDatabase(rfc));
        return sb.toString();
    }

    public static String generateOKForLeave( ){
        StringBuffer sb = new StringBuffer();
        sb.append("P2P-CI/1.0 200 OK");
        sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

    public static String generateBadRequestMessage(){
        StringBuffer sb =new StringBuffer();
        sb.append("P2P-CI/1.0 400 Bad Request");
        return sb.toString();
    }
    public static String generateNotFoundMessage(){
        StringBuffer sb =new StringBuffer();
        sb.append("P2P-CI/1.0 404 Not Found ");
        return sb.toString();

    }


    private static String generateDatabase(Map<Integer,RFC> rfc){
        StringBuffer sb = new StringBuffer();
        System.out.println("Inside generateDatabase");
        for(Integer iterator :rfc.keySet())
        {
            /*sb.append("RFCName: ");*/
            //sb.append(rfcIterator.getRFCName());
            System.out.println(rfc.get(iterator).getRFCId());
            Set<Peer> peerList = rfc.get(iterator).getListOfPeerWithRFC();
            for(Peer peer :peerList)
            {
                sb.append("ID: ");
                sb.append(rfc.get(iterator).getRFCId()+" ");
                sb.append("hostname: ");
                sb.append(peer.getHostName()+" ");
                sb.append("upload port number: ");
                sb.append(peer.getUploadPortNumber());
                sb.append(System.getProperty("line.separator"));
            }

        }
     return sb.toString();
    }
    private static String generatePeerInformation(RFC rfc,Map<Integer,Peer> peer){
        StringBuffer sb = new StringBuffer();
        Set<Peer> peersForRfC = rfc.getListOfPeerWithRFC();
            for(Peer peerIterator:peersForRfC)
            {
                for(Integer count : peer.keySet() )
                {
                    if(peer.get(count).getUploadPortNumber().equals(peerIterator.getUploadPortNumber()))
                    {
                        sb.append("Name of peer : ");
                        sb.append(peer.get(count).getHostName()+" ");
                        sb.append("Port Number: ");
                        sb.append(peer.get(count).getUploadPortNumber());
                        sb.append(System.getProperty("line.separator"));
                    }
                }

            }
        return sb.toString();

    }
}
