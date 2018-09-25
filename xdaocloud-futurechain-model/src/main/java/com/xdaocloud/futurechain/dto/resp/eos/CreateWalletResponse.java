package com.xdaocloud.futurechain.dto.resp.eos;

public class CreateWalletResponse {

    private String walletName;
    private String passPhrase;
    private String activePrivateKey;
    private String activePublicKey;
    private String ownerPrivateKey;
    private String ownerPublicKey;
    private String walletPass;

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }

    public String getActivePrivateKey() {
        return activePrivateKey;
    }

    public void setActivePrivateKey(String activePrivateKey) {
        this.activePrivateKey = activePrivateKey;
    }

    public String getActivePublicKey() {
        return activePublicKey;
    }

    public void setActivePublicKey(String activePublicKey) {
        this.activePublicKey = activePublicKey;
    }

    public String getOwnerPrivateKey() {
        return ownerPrivateKey;
    }

    public void setOwnerPrivateKey(String ownerPrivateKey) {
        this.ownerPrivateKey = ownerPrivateKey;
    }

    public String getOwnerPublicKey() {
        return ownerPublicKey;
    }

    public void setOwnerPublicKey(String ownerPublicKey) {
        this.ownerPublicKey = ownerPublicKey;
    }

    public String getWalletPass() {
        return walletPass;
    }

    public void setWalletPass(String walletPass) {
        this.walletPass = walletPass;
    }
}
