package Server;

import POJO.Peer;
import POJO.RFC;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ServerHelpers {
    public static String getMethodType(String message) {
        String[] messageSplit =message.split(" ",2);
        return messageSplit[0];
    }
    public static Integer getRFCNo(String message){
        String[] messageSplit = message.split("RFC ",2);
        String[] messageSplit2 =messageSplit[1].split(" ");
        return Integer.parseInt(messageSplit2[0]);
    }
    public static String getHostName(String message){
        String[] messageSplit = message.split("Host: ",2);
        String[] messageSplit2 =messageSplit[1].split(" ",2);
        return messageSplit2[0];
    }
    public static Integer getPortNumber(String message){
        String[] messageSplit = message.split("Port: ",2);
        if((messageSplit[1].replaceAll("\\s","")).length()==5){
            return Integer.parseInt((messageSplit[1].replaceAll("\\s","")));
        }
        String[] messageSplit2 =messageSplit[1].split(" ");
        return Integer.parseInt((messageSplit2[0].replaceAll("\\s","")));
    }
    public static String getRFCTitle(String message){
        String[] messageSplit =message.split("Title:",2);
        return messageSplit[1];
    }
    //the method is used to remove the enteries from the server when the client leaves.
    public static void deleteEntries(Peer peer, Map<Integer,Peer> peerMap, Map<Integer, RFC> rfcMap){
        System.out.println(peer.getHostName());
        System.out.println(peer.getUploadPortNumber());
        System.out.println(rfcMap);
        Iterator<Map.Entry<Integer,RFC>> it = rfcMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, RFC> pair = it.next();
            RFC rfc =pair.getValue();
            Set<Peer> peerList = rfc.getListOfPeerWithRFC();
            Iterator<Peer> iterator = peerList.iterator();

            while(iterator.hasNext()) {
                if(iterator.next().getUploadPortNumber().equals(peer.getUploadPortNumber())){
                    System.out.println("remove the peer");
                    iterator.remove();
                }
            }
            if(peerList.size()==0) {
                it.remove();
            }

        }
        peerMap.remove(peer.getUploadPortNumber());

    }

}
