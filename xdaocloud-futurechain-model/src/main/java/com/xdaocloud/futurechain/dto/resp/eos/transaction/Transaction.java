package com.xdaocloud.futurechain.dto.resp.eos.transaction;

import java.util.List;

public class Transaction {

    private String compression;

    private TransactionMsg transaction;

    private List<String> signatures;

    public String getCompression() {
        return compression;
    }

    public void setCompression(String compression) {
        this.compression = compression;
    }

    public TransactionMsg getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionMsg transaction) {
        this.transaction = transaction;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }
}
