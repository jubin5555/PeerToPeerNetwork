package ClientHelpers;

import POJO.RFC;
import AppConstants.RFCTitle;
import com.sun.deploy.association.utility.AppConstants;

import java.io.File;
import java.util.LinkedList;



public class ClientHelper {
    //getRFCList is used to iterate through the path provided to get the entire files which
    public static LinkedList<RFC>  getRFCList(String Path){
        LinkedList<RFC> rfc = new LinkedList<RFC>();
        File file = new File(Path);
        File[] paths = file.listFiles();
        for(File path:paths) {
            String fileName =path.getName();
            String[] removeRFCFromFileName =fileName.split("\\.",0);
            String[] textName=removeRFCFromFileName[0].split("c",0);
            String RFCId = textName[1];
            RFC tempRFC = new RFC(Integer.parseInt(RFCId));
            tempRFC.setRFCName(RFCTitle.getRFCName(Integer.parseInt(RFCId)));
            rfc.add(tempRFC);

        }
        return rfc;
    }
    //this method is used to get the port number
    public static int getPortNumber(String s){
        String[] sSplit = s.split("Port Number: ",2);
        return Integer.parseInt(sSplit[1]);
    }

    //this method is used to get the Message type.
    public static String getMethod(String s){
        String[] sSplit =s.split(" ",2);
        String[] sSplit2 =sSplit[1].split(System.getProperty("line.separator"));
       /* System.out.println("The method in clientHelper is: " + sSplit2[0]);*/
        return sSplit2[0];
    }
    //this method is used to get the id of the RFC which is requested by the other client.
    public static Integer getRFCIDFromGETMessage(String s) {
        String[] sSplit =s.split("RFC ");
        String[] sSplit2 =sSplit[1].split(" ");
        return Integer.parseInt(sSplit2[0]);
    }

}

