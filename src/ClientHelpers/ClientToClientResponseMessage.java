package ClientHelpers;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientToClientResponseMessage {
    public static String generateOKMessage(String FileName){
        StringBuffer sb =new StringBuffer();
        sb.append("P2P-CI/1.0 200 OK");
        sb.append(System.getProperty("line.separator"));
        sb.append("Date: ");
        sb.append(getCurrentDate());
        sb.append(System.getProperty("line.separator"));
        sb.append("OS: ");
        sb.append(System.getProperty("os.name"));
        sb.append(System.getProperty("line.separator"));
        sb.append("Last-Modified: ");
        sb.append(getLastModifiedDate(FileName));
        sb.append(System.getProperty("line.separator"));
        sb.append("File Length: ");
        sb.append(getFileLength(FileName));
        sb.append(System.getProperty("line.separator"));
        sb.append("Content-Type: ");
        sb.append("Txt");
        return sb.toString();


    }
    public static String generateBadRequestMessage(){
        StringBuffer sb =new StringBuffer();
        sb.append("P2P-CI/1.0 400 Bad Request");
        sb.append(System.getProperty("line.separator"));
        sb.append("Date: ");
        sb.append(getCurrentDate());
        sb.append(System.getProperty("line.separator"));
        sb.append("OS: ");
        sb.append(System.getProperty("os.name"));
        return sb.toString();
    }
    public static String generateNotFoundMessage(){
        StringBuffer sb =new StringBuffer();
        sb.append("P2P-CI/1.0 404 Not Found ");
        sb.append(System.getProperty("line.separator"));
        sb.append("Date: ");
        sb.append(getCurrentDate());
        sb.append(System.getProperty("line.separator"));
        sb.append("OS: ");
        sb.append(System.getProperty("os.name"));
        return sb.toString();

    }
    public static String generateCIVerionNotSupportedMessage(){
        StringBuffer sb =new StringBuffer();
        sb.append("P2P-CI/1.0 505 P2P-CI Version Not Supported");
        sb.append(System.getProperty("line.separator"));
        sb.append("Date: ");
        sb.append(getCurrentDate());
        sb.append(System.getProperty("line.separator"));
        sb.append("OS: ");
        sb.append(System.getProperty("os.name"));
        return sb.toString();
    }

    private static  String getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return (formatter.format(date));

    }
    private static  String getLastModifiedDate(String fileName){
        File f = new File(fileName);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return (formatter.format(f.lastModified()));
    }
    private static Long getFileLength(String fileName){
       File f = new File(fileName);
       return f.length();
    }
   /* public static void main(String[] args)
    {
        System.out.println(generateOKMessage("C:\\Users\\jajubina\\Desktop\\PeerToPeerFileSharingSystem\\src\\ServerFiles\\RFC1"));
    }*/

}
