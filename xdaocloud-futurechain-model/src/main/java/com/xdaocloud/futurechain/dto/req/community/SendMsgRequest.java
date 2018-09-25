package com.xdaocloud.futurechain.dto.req.community;

import com.xdaocloud.futurechain.dto.req.huanxin.Size;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author LuoFuMin
 * @data 2018/9/6
 */
public class SendMsgRequest extends UserIdRequest implements Serializable {
    private static final long serialVersionUID = -1326701893231827256L;

    @NotNull
    private Long communityId;

    @NotNull
    private List<String> groupIds;

    /**
     * 消息类型，txt：文本消息,img:图片消息,audio:语音消息,video:视频消息
     */
    @NotBlank
    private String type;

    private String content;

    private String url;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 成功上传文件后返回的secret
     */
    private String secret;

    /**
     * 图片大小
     */
    private Size size;

    /**
     * 视频播放长度
     */
    private Integer length;

    /**
     * 成功上传视频缩略图返回的UUID
     */
    private String thumb;

    /**
     * 视频文件大小
     */
    private Long file_length;

    /**
     * 成功上传视频缩略图后返回的secret
     */
    private String thumb_secret;

    public String getThumb_secret() {
        return thumb_secret;
    }

    public void setThumb_secret(String thumb_secret) {
        this.thumb_secret = thumb_secret;
    }

    public Long getFile_length() {
        return file_length;
    }

    public void setFile_length(Long file_length) {
        this.file_length = file_length;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public List<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<String> groupIds) {
        this.groupIds = groupIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
