package AppConstants;

import java.util.HashMap;
import java.util.Map;

public class RFCTitle {
    public static Map<Integer,String > rfcNameMapping = new HashMap<Integer, String>();
    public static void setNamesForRfc() {
        rfcNameMapping.put(1,"Host-Software");
        rfcNameMapping.put(2,"Control-Links");
        rfcNameMapping.put(3,"Document");
        rfcNameMapping.put(4,"Network");
        rfcNameMapping.put(5,"DEL");
        rfcNameMapping.put(6,"Conversations");
        rfcNameMapping.put(7,"Host-Interface");
        rfcNameMapping.put(10,"Convention");
        rfcNameMapping.put(11,"Software Proces");
        rfcNameMapping.put(12,"FLOW Diagram");
    }
    public static String getRFCName(int select) {
        setNamesForRfc();
       return rfcNameMapping.get(select);
    }

}
