package com.xdaocloud.futurechain.dto.resp.baiduyun;

/**
 *
 * @author LiMaoDao
 * @date 2018/7/5
 */
public class BaiDuYunTextResponse {

    /**
     * 审核未通过的类别列表与详情
     */
    public Object reject;

    /**
     * 待人工复审的类别列表与详情
     */
    public Object review;
    /**
     * 请求中是否包含违禁，0表示非违禁，1表示违禁，2表示建议人工复审
     */
    public Number spam;
    /**
     * 审核通过的类别列表与详情
     */
    public Object pass;

    public Object getReject() {
        return reject;
    }

    public void setReject(Object reject) {
        this.reject = reject;
    }

    public Object getReview() {
        return review;
    }

    public void setReview(Object review) {
        this.review = review;
    }

    public Number getSpam() {
        return spam;
    }

    public void setSpam(Number spam) {
        this.spam = spam;
    }

    public Object getPass() {
        return pass;
    }

    public void setPass(Object pass) {
        this.pass = pass;
    }
}
