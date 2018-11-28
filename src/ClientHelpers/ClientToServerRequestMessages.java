package ClientHelpers;

import POJO.Peer;
import POJO.RFC;


public class ClientToServerRequestMessages {
    //send add for each file
    public static String generateAddMessage(RFC rfc, Peer peer ){
        StringBuffer sb = new StringBuffer();
        sb.append("ADD RFC ");
        sb.append(rfc.getRFCId());
        sb.append(" P2P-CI/1.0");
        sb.append(System.getProperty("line.separator"));
        sb.append("Host: ");
        sb.append(peer.getHostName());
        sb.append(System.getProperty("line.separator"));
        sb.append("Port: ");
        sb.append(peer.getUploadPortNumber());
        //sb.append(System.getProperty("line.separator"));
        return sb.toString();
    }

    public static String generateLookUpMessage(RFC rfc,Peer peer){
        StringBuffer sb = new StringBuffer();
        sb.append("LOOKUP RFC ");
        sb.append(rfc.getRFCId());
        sb.append(" P2P-CI/1.0");
        sb.append(System.getProperty("line.separator"));
        sb.append("Host: ");
        sb.append(peer.getHostName());
        sb.append(System.getProperty("line.separator"));
        sb.append("Port: ");
        sb.append(peer.getUploadPortNumber());
        //sb.append("Title: ");
        //sb.append(rfc.getRFCName());
        return sb.toString();
    }
    public static String generateListMessage(Peer peer){
        StringBuffer sb = new StringBuffer();
        sb.append("LIST RFC ");
        sb.append(" P2P-CI/1.0");
        sb.append(System.getProperty("line.separator"));
        sb.append("Host: ");
        sb.append(peer.getHostName());
        sb.append(System.getProperty("line.separator"));
        sb.append("Port: ");
        sb.append(peer.getUploadPortNumber());
        return sb.toString();
    }
    public static String generateLeaveServerMessage(Peer peer){
        StringBuffer sb = new StringBuffer();
        sb.append("LEAVE RFC ");
        sb.append(" P2P-CI/1.0");
        sb.append(System.getProperty("line.separator"));
        sb.append("Host: ");
        sb.append(peer.getHostName());
        sb.append(System.getProperty("line.separator"));
        sb.append("Port: ");
        sb.append(peer.getUploadPortNumber());
        return sb.toString();

    }




}
