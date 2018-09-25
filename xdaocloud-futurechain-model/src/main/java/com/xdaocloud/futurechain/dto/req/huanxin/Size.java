package com.xdaocloud.futurechain.dto.req.huanxin;

/**
 * @author LuoFuMin
 * @data 2018/9/6
 */
public class Size {

    private String width;

    private String height;

    public Size(String width, String height) {
        this.width = width;
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
