package com.xdaocloud.futurechain.controller;

import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.util.*;

/**
 * @author LuoFuMin
 * @data 2018/7/23
 */
public class Test {
    private static final Logger LOG = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws Exception {

        String[] members = new String[]{"13808962307"};

        JSONArray jsonArray = new JSONArray();
        jsonArray.add("13808962307");

        System.out.println("===resultMsg1===" + jsonArray.toJSONString());

        String password = "642c4c68f340b4b0d3d2cab4563633172444c4be79b0d215363e0b5ce2da5f4f75636ebb8beedf29";

        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();


        System.out.println("====" + passwordEncoder.matches("123456", password));


        LinkedList<Integer> linkedList = new LinkedList<>();

        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.addFirst(0);
        System.out.println("====linkedList===" + linkedList.remove(3));

        for (Iterator iter = linkedList.iterator(); iter.hasNext(); ) {
            System.out.println("==iter==" + iter.next());
        }


        for (Integer integ : linkedList) {

            System.out.println("===v=====" + integ);
        }

        jsonArray.add("a");

        for (int i = 0; i < jsonArray.size(); i++) {
            System.out.println("======" + jsonArray.remove("a"));

        }

        List list = new ArrayList();

        list.add(1);
        list.add(1);
        list.add(5);
        list.add(6);
        list.add(2);

        for (Object o : list) {
            System.out.println("====Object====" + o.toString());
        }

        HashMap<String, String> map = new HashMap<>(3);

        map.put("2", "two");
        map.put("1", "one");
        map.put("1", "1");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key : " + entry.getKey() + " , value : " + entry.getValue());
        }

        //Set 集合存和取的顺序不一致。
        Set hs = new HashSet();
        hs.add("世界军事");
        hs.add("兵器知识");
        hs.add("舰船知识");
        hs.add("汉和防务");
        hs.add("汉和防务");
        System.out.println(hs);
        // [舰船知识, 世界军事, 兵器知识, 汉和防务]
        Iterator it = hs.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        List<Integer> nums = new ArrayList<Integer>();
        nums.add(3);
        nums.add(5);
        nums.add(1);
        nums.add(0);
        System.out.println(nums);
        Collections.sort(nums);
        System.out.println(nums);

        nums.add(10);

        System.out.println("===="+nums);
        Collections.sort(nums);
        System.out.println("===="+nums);
/*
        Rpc rpc = new Rpc("http://192.168.1.74:8888");


        String uuid = UUID.randomUUID().toString();

        LOG.info("============= 通过种子生成私钥 ===============");
        String owner_private_key = Ecc.seedPrivate(uuid);
        LOG.info("owner_private_key :" + owner_private_key);

        LOG.info("============= 通过私钥生成公钥 ===============");
        String owner_public_key = Ecc.privateToPublic(owner_private_key);
        LOG.info("owner_public_key:" + owner_public_key);

        LOG.info("============= 通过种子生成私钥 ===============");
        String active_private_key = Ecc.seedPrivate(UUID.randomUUID().toString());
        LOG.info("active_private_key :========" + owner_private_key + "\n");

        LOG.info("============= 通过私钥生成公钥 ===============");
        String active_public_key = Ecc.privateToPublic(active_private_key);
        LOG.info("active_public_key :" + owner_public_key + " \n ");

        LOG.info("============= 创建账户并且抵押 ===============");
        try {
            Transaction t2 = rpc.createAccount("eosio_public_key", "eosio", "kkmm", owner_public_key, active_public_key, 8192l, "100.00 MPC", "100.00 MPC", 0l);
            System.out.println("创建成功 = " + t2.getTransactionId() + " \n ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            Transaction t1 = rpc.transfer("5J7UP2eBtgJiVCeCt368zWuiNdMdAoTwF4worSgHBxnMz7a5476", "maitoken", "maimarket", "kkmm", "12.2821 MAI", "");
            System.out.println("转账成功 = " + t1.getTransactionId() + " \n ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            Transaction t1 = rpc.transfer(active_private_key, "maitoken", "kkmm", "maimarket", "1.0000 MAI", "");
            System.out.println("转账成功 = " + t1.getTransactionId() + " \n ");
        } catch (ApiException ex) {
            System.out.println("=====code:" + ex.getError().getError().getCode() + "=====Name: " + ex.getError().getError().getName() + "=====What: " + ex.getError().getError().getWhat());
        }




        Account account = rpc.getAccount("kkmm");

        System.out.println("=====" + account.getRamUsage());
        System.out.println("=====" + account.getRamQuota());

        System.out.println("=====" + account.getCpuWeight());
        System.out.println("=====" + account.getNetLimit().getUsed());
        System.out.println("=====" + account.getNetWeight());
*/


        //System.out.println("===active_private_key==" + active_private_key);
/*
        System.out.println("============= 查询帐户余额 ===============");
        try {
            CurrencyBalanceReq myCurrencyBalanceReq = new CurrencyBalanceReq();
            myCurrencyBalanceReq.setCode("maitoken");
            myCurrencyBalanceReq.setAccount("pengchao2");
            myCurrencyBalanceReq.setSymbol("MAI");
            List<String> mCurrencyBalance = rpc.getCurrencyBalance(myCurrencyBalanceReq);
            System.out.println("帐户余额 = " + mCurrencyBalance.get(0) + " \n ");
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
    }


}
