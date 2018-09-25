package com.xdaocloud.futurechain.dto;

public class Details {

    private String file;

    private String method;

    private Integer line_number;

    private String message;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getLine_number() {
        return line_number;
    }

    public void setLine_number(Integer line_number) {
        this.line_number = line_number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
