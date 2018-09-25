package com.xdaocloud.futurechain.config;

import javax.swing.text.StyledEditorKit.BoldAction;

/**
 * GeetestWeb配置文件
 * 
 *
 */
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
	private static final String geetest_id = "66766a325d2207c510da302db237f49d";
	private static final String geetest_key = "8c4cbb0f69ea48ee3f5c368ff2cf3224";
    private static final boolean newfailback = true;

    public static final String getGeetest_id() {
        return geetest_id;
    }

    public static final String getGeetest_key() {
        return geetest_key;
    }
    
    public static final boolean isnewfailback() {
        return newfailback;
    }

}