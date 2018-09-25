package com.xdaocloud.futurechain.dto.resp.notice;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 公告数据
 *
 * @author lixuzhou@foxmail.com
 * @version 0.1 2018/3/7
 */
public class NoticeDTO implements Serializable {

    private static final long serialVersionUID = 484499548107215692L;

    /**
     * ID
     */
    @JsonIgnore
    private Long id;

    /**
     * 公告类型：0-网页 1-原生页面
     */
    private Byte type;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 公告跳转URL
     */
    private String url;


    /**
     * @return Returns the id
     **/
    public Long getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Returns the type
     **/
    public Byte getType() {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * @return Returns the content
     **/
    public String getContent() {
        return content;
    }

    /**
     * @param content The content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return Returns the url
     **/
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }


}
