package com.xdaocloud.futurechain.dto.resp.eos;

public class KeyDTO {

    private String activePublicKey;

    private String activePrivateKey;


    private String ownerPublicKey;

    private String ownerPrivateKey;


    public String getActivePublicKey() {
        return activePublicKey;
    }

    public void setActivePublicKey(String activePublicKey) {
        this.activePublicKey = activePublicKey;
    }

    public String getActivePrivateKey() {
        return activePrivateKey;
    }

    public void setActivePrivateKey(String activePrivateKey) {
        this.activePrivateKey = activePrivateKey;
    }

    public String getOwnerPublicKey() {
        return ownerPublicKey;
    }

    public void setOwnerPublicKey(String ownerPublicKey) {
        this.ownerPublicKey = ownerPublicKey;
    }

    public String getOwnerPrivateKey() {
        return ownerPrivateKey;
    }

    public void setOwnerPrivateKey(String ownerPrivateKey) {
        this.ownerPrivateKey = ownerPrivateKey;
    }
}
