package com.xdaocloud.futurechain.dto.resp.eos.transaction;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class TransactionResponse {

    private String transaction_id;

    private Boolean broadcast;

    private Transaction transaction;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Boolean getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Boolean broadcast) {
        this.broadcast = broadcast;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public static void main(String[] args) {

        String jsonStr = "{\"transaction_id\":\"e169ec03de6f51d401ba765a04a7dbbe48a93819afde04f7fbc6be9e8f909642\",\"broadcast\":true,\"transaction\":{\"compression\"" +
                ":\"none\",\"transaction\":{\"expiration\":\"2018-05-15T06:16:45\",\"region\":0,\"ref_block_num\":34727," +
                "\"ref_block_prefix\":165404857,\"max_net_usage_words\":0,\"max_kcpu_usage\":0,\"delay_sec\":0,\"context_free_actions\":[]," +
                "\"actions\":[{\"account\":\"eostoken\",\"name\":\"transfer\",\"authorization\":[{\"actor\":\"quanpengchao\",\"permission\":\"active\"}]," +
                "\"data\":\"404d436caa3a8db6000000d349bda88e1027000000000000044d4149000000000f5472616e7366657220746f20796f75\"}]}," +
                "\"signatures\":[\"SIG_K1_KAHP4n81U7PSd7QtJHtLSAEdxz8ayymGJAWSr3YxxNTc53h47cCweeyRmfgh4bRUfvHD3a8cRyTGz1gqQxbSihZAFbir55\"]}}";

        TransactionResponse transactionResponse = JSON.parseObject(jsonStr, new TypeReference<TransactionResponse>() {
        });

        System.out.println("===transactionResponse=="+transactionResponse.getTransaction_id());
    }

}
