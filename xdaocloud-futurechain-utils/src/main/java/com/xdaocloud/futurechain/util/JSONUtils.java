package com.xdaocloud.futurechain.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JSONUtils {

    private static Logger logger = LoggerFactory.getLogger(JSONUtils.class);

/*
    Entity entity = JSON.parseObject("输入json", new TypeReference<Entity>() {
    });*/


    public static void main(String[] args) {
        String json = "{\"code\":500,\"message\":\"Internal Service Error\",\"error\":{\"code\":3120001,\"name\":\"wallet_exist_exception\",\"what\":\"Wallet already exists\"," +
                "\"details\":[{\"message\":\"Wallet with name: 'cmd' already exists at /root/.local/share/eosio/nodeos/data/./cmd.wallet\",\"file\":\"wallet_manager.cpp\",\"line_number\":41,\"method\":\"create\"}]}}";

  /*      EosResponse eosResponse = JSON.parseObject(json, new TypeReference<EosResponse>() {
        });

        System.out.println("==eosResponse=="+eosResponse.getError());*/
    }

    /**
     * 单个对象的所有键值
     *
     * @param object 单个对象
     * @return Map<String, Object> map   所有   String键   Object值
     */
    public static Map<String, Object> getValue(Object object) {
        Map<String, Object> map = new HashMap<String, Object>();
        Object obj = JSONObject.toJSON(object);//将对象转化为json格式对象
        JSONObject jsonObject = JSONObject.parseObject(obj.toString());//转化为json对象
        Set<String> keys = jsonObject.keySet();//获取所有的键
        for (String key : keys) {
            map.put(key, jsonObject.get(key));//根据键获取值
            logger.info("====key====" + key + "====value====" + jsonObject.get(key));
        }
        return map;
    }

    /**
     * 获取环信群id
     *
     * @param object 创建环信群返回信息
     * @return groupid 环信群id
     */
    public static String gethxGroupId(Object object) {
        if (null != object) {
            Object valueObj = getValue(object, "data");
            if (null != valueObj) {
                JSONObject jsonObject = JSONObject.parseObject(valueObj.toString());//转化为json对象
                String groupid = jsonObject.get("groupid").toString();
                return groupid;
            }
        }
        return null;
    }

    public static Object getValue(Object object, String key) {
        Object obj = JSONObject.toJSON(object);//将对象转化为json格式对象
        JSONObject jsonObject = JSONObject.parseObject(obj.toString());//转化为json对象
        Set<String> keys = jsonObject.keySet();//获取所有的键
        for (String keyStr : keys) {
            if (key.equals(key)) {
                Object valueObj = jsonObject.get(key);
                return valueObj;
            }
        }
        return null;
    }


    /**
     * 单个对象的某个键的值
     *
     * @param object 对象
     * @param key    键
     * @return Object   键在对象中所对应得值
     */
    public static Object getValueByKey(Object object, String key) {
        Object obj = JSONObject.toJSON(object);//将对象转化为json格式对象
        JSONObject jsonObject = JSONObject.parseObject(obj.toString());//转化为JSONObject对象
        System.out.println("单个对象的某个键的值====" + jsonObject.get(key));
        return jsonObject.get(key);//根据键获取值
    }


    /**
     * 对象装换成json
     *
     * @param object
     * @return
     */
    public static JSONObject turnJSON(Object object) {
        Object obj = JSONObject.toJSON(object);//将对象转化为json格式对象
        JSONObject jsonObject = JSONObject.parseObject(obj.toString());//转化为JSONObject对象
        return jsonObject;
    }

    /**
     * 多个（列表）对象的所有键值
     *
     * @param object
     * @return List<Map<String,Object>> 列表中所有对象的所有键值
     * ex:[{pjzyfy=0.00, xh=01, zzyl=0.00, mc=住院患者压疮发生率, pjypfy=0.00, rs=0, pjzyts=0.00, czydm=0037, lx=921, zssl=0.00},
     * {pjzyfy=0.00, xh=02, zzyl=0.00, mc=新生儿产伤发生率, pjypfy=0.00, rs=0, pjzyts=0.00, czydm=0037, lx=13, zssl=0.00},
     * {pjzyfy=0.00, xh=03, zzyl=0.00, mc=阴道分娩产妇产伤发生率, pjypfy=0.00, rs=0, pjzyts=0.00, czydm=0037, lx=0, zssl=0.00},
     * {pjzyfy=0.00, xh=04, zzyl=0.75, mc=输血反应发生率, pjypfy=0.00, rs=0, pjzyts=0.00, czydm=0037, lx=0, zssl=0.00},
     * {pjzyfy=5186.12, xh=05, zzyl=0.00, mc=剖宫产率, pjypfy=1611.05, rs=13, pjzyts=7.15, czydm=0037, lx=13, zssl=0.00}]
     */
    public static List<Map<String, Object>> getValues(List<Object> object) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Object obj = JSONArray.toJSON(object);//转化为JSONArray格式
        JSONArray jarr = JSONArray.parseArray(obj.toString()); //转化为JSONArray对象
        for (Iterator<Object> iterator = jarr.iterator(); iterator.hasNext(); ) {
            JSONObject job = (JSONObject) iterator.next();
            Set<String> s = job.keySet();
            Map<String, Object> listChild = new HashMap<String, Object>();
            //获取对象所有键值
            for (String string : s) {
                listChild.put(string, job.get(string));
            }
            //将对象添加到列表中
            list.add(listChild);
        }
        System.out.println("多个（列表）对象的所有键值====" + list.toString());
        return list;
    }

    /**
     * 多个（列表）对象的某个键的值
     *
     * @param object
     * @param key    要查找的值的字段名
     * @return List<Object>  键在列表中对应的所有值
     * ex:[住院患者压疮发生率, 新生儿产伤发生率, 阴道分娩产妇产伤发生率, 输血反应发生率, 剖宫产率]
     */
    public static List<Object> getValuesByKey(List<Object> object, String key) {
        Object obj = JSONArray.toJSON(object);//将对象转化为json格式对象
        JSONArray jarr = JSONArray.parseArray(obj.toString());
        List<Object> list = new ArrayList<Object>();
        for (Iterator<Object> iterator = jarr.iterator(); iterator.hasNext(); ) {
            JSONObject job = (JSONObject) iterator.next();
            list.add(job.get(key));//根据键获取值并添加到list中
        }
        System.out.println("多个（列表）对象的某个键的值列表====" + list.toString());
        return list;
    }


}
