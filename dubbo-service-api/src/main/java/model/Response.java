package model;

import java.io.Serializable;

public class Response implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8453712624016389984L;

    private String            name;
    private String            ipAdrress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAdrress() {
        return ipAdrress;
    }

    public void setIpAdrress(String ipAdrress) {
        this.ipAdrress = ipAdrress;
    }

}
