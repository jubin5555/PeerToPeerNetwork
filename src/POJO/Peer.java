package POJO;

public class Peer {
    private String hostName;
    private Integer uploadPortNumber;

    public Peer(String hostName,Integer uploadPortNumber){
        this.hostName=hostName;
        this.uploadPortNumber=uploadPortNumber;
    }
    public String getHostName(){
        return this.hostName;
    }
    public Integer getUploadPortNumber(){
        return this.uploadPortNumber;
    }
}
