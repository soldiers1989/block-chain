package com.xdaocloud.futurechain.dto.resp.etherum;

import java.math.BigInteger;

public class TransactionCountResponse {

    private String id;

    private String jsonrpc;

    private String result;

    private Object error;

    private Object rawResponse;

    private BigInteger transactionCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Object getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(Object rawResponse) {
        this.rawResponse = rawResponse;
    }

    public BigInteger getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(BigInteger transactionCount) {
        this.transactionCount = transactionCount;
    }
}
