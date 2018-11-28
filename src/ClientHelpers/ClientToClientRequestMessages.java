package ClientHelpers;

/**
 * This class is used to return the messages which are send from the client to other clients.
 * Currently the only method present is GET
 * StringBuffer is used here because of been thread safe
 * @author: Jubin Antony Thykattil.
 * *
 **/
public class ClientToClientRequestMessages {
    public static String getMessage(Integer RFCId, String hostName) {
        StringBuffer sb = new StringBuffer();
        sb.append("GET RFC ");
        sb.append(RFCId+" ");
        sb.append("P2P-CI/1.0");
        sb.append(System.getProperty("line.separator"));
        sb.append("Host: ");
        sb.append(hostName);
        sb.append(System.getProperty("line.separator"));
        sb.append("OS: ");
        sb.append(System.getProperty("os.name"));
        return sb.toString();
    }
}
