package POJO;

import java.util.HashSet;
import java.util.Set;

public class RFC {
    private Integer RFCId;
   /* private String RFCName;*/
    private Set<Peer> setOfPeerWithRFC ;

    public RFC(int RFCId/*,String RFCName*/) {
        this.RFCId =RFCId;
        /*this.RFCName =RFCName;*/
        this.setOfPeerWithRFC =new HashSet<Peer>();
    }

    public void setListOfPeerWithRFC(Peer peer)
    {
        this.setOfPeerWithRFC.add(peer);
    }

    public int getRFCId(){
        return this.RFCId;
    }

  /*  public String getRFCName(){
        return this.RFCName;
    }*/

    public Set<Peer> getListOfPeerWithRFC(){
        return this.setOfPeerWithRFC;
    }
}
