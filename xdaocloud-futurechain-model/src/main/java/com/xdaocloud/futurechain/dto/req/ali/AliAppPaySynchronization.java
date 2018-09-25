package com.xdaocloud.futurechain.dto.req.ali;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class AliAppPaySynchronization {

    private String memo;

    private String resultStatus;

    private Result result;


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static void main(String[] args) {
        String str ="{\n" +
                "    \"memo\" : \"xxxxx\",\n" +
                "    \"result\" : {\n" +
                "                    \"alipay_trade_app_pay_response\":{\n" +
                "                        \"code\":\"10000\",\n" +
                "                        \"msg\":\"Success\",\n" +
                "                        \"app_id\":\"2014072300007148\",\n" +
                "                        \"out_trade_no\":\"081622560194853\",\n" +
                "                        \"trade_no\":\"2016081621001004400236957647\",\n" +
                "                        \"total_amount\":\"0.01\",\n" +
                "                        \"seller_id\":\"2088702849871851\",\n" +
                "                        \"charset\":\"utf-8\",\n" +
                "                        \"timestamp\":\"2016-10-11 17:43:36\"\n" +
                "                    },\n" +
                "                    \"sign\":\"NGfStJf3i3ooWBuCDIQSumOpaGBcQz+aoAqyGh3W6EqA/gmyPYwLJ2REFijY9XPTApI9YglZyMw+ZMhd3kb0mh4RAXMrb6mekX4Zu8Nf6geOwIa9kLOnw0IMCjxi4abDIfXhxrXyj********\",\n" +
                "                    \"sign_type\":\"RSA2\"\n" +
                "                },\n" +
                "    \"resultStatus\" : \"9000\"\n" +
                "}";


        AliAppPaySynchronization aliAppPaySynchronization = JSON.parseObject(str, new TypeReference<AliAppPaySynchronization>() {
        });

        System.out.println("============="+aliAppPaySynchronization.getMemo());

        System.out.println("=====getOut_trade_no========"+aliAppPaySynchronization.getResult().getAlipay_trade_app_pay_response().getOut_trade_no());

    }
}
