package com.xdaocloud.futurechain.dto.req.huanxin;

public class Message {

    /**
     * 消息类型，txt：文本消息,img:图片消息,audio:语音消息,video:视频消息,cmd:透传消息
     */
    private String type;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 透传内容
     */
    private String action;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 成功上传文件后返回的secret
     */
    private String secret;

    /**
     * 语音或视频长度
     */
    private Integer length;

    /**
     * //成功上传视频缩略图返回的UUID
     */
    private String thumb;

    /**
     * 视频文件大小
     */
    private Long file_length;

    /**
     *  成功上传视频缩略图后返回的secret
     */
    private String thumb_secret;

    /**
     * 图片大小
     */
    private Size size;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Long getFile_length() {
        return file_length;
    }

    public void setFile_length(Long file_length) {
        this.file_length = file_length;
    }

    public String getThumb_secret() {
        return thumb_secret;
    }

    public void setThumb_secret(String thumb_secret) {
        this.thumb_secret = thumb_secret;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

}
