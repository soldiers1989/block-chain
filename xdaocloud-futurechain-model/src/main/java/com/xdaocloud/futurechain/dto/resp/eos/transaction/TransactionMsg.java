package com.xdaocloud.futurechain.dto.resp.eos.transaction;

import java.util.List;

public class TransactionMsg {

    private String expiration;

    private Integer region;

    private Integer ref_block_num;

    private Integer ref_block_prefix;

    private Integer max_net_usage_words;

    private Integer max_kcpu_usage;

    private Integer delay_sec;

    private List<Object> context_free_actions;

    private List<Actions> actions;

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public Integer getRegion() {
        return region;
    }

    public void setRegion(Integer region) {
        this.region = region;
    }

    public Integer getRef_block_num() {
        return ref_block_num;
    }

    public void setRef_block_num(Integer ref_block_num) {
        this.ref_block_num = ref_block_num;
    }

    public Integer getRef_block_prefix() {
        return ref_block_prefix;
    }

    public void setRef_block_prefix(Integer ref_block_prefix) {
        this.ref_block_prefix = ref_block_prefix;
    }

    public Integer getMax_net_usage_words() {
        return max_net_usage_words;
    }

    public void setMax_net_usage_words(Integer max_net_usage_words) {
        this.max_net_usage_words = max_net_usage_words;
    }

    public Integer getMax_kcpu_usage() {
        return max_kcpu_usage;
    }

    public void setMax_kcpu_usage(Integer max_kcpu_usage) {
        this.max_kcpu_usage = max_kcpu_usage;
    }

    public Integer getDelay_sec() {
        return delay_sec;
    }

    public void setDelay_sec(Integer delay_sec) {
        this.delay_sec = delay_sec;
    }

    public List<Object> getContext_free_actions() {
        return context_free_actions;
    }

    public void setContext_free_actions(List<Object> context_free_actions) {
        this.context_free_actions = context_free_actions;
    }

    public List<Actions> getActions() {
        return actions;
    }

    public void setActions(List<Actions> actions) {
        this.actions = actions;
    }
}
