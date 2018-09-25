package com.xdaocloud.futurechain.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageInfo;
import java.io.Serializable;
import java.util.Collection;

public class PageResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private long totalCount;
    private long totalPages;
    private int pageCount;
    private Collection<?> items;

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Collection<?> getItems() {
        return this.items;
    }

    public void setItems(Collection<?> items) {
        this.items = items;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalPages() {
        if (this.totalCount % (long)this.pageSize == 0L) {
            this.totalPages = this.totalCount / (long)this.pageSize;
        } else {
            this.totalPages = this.totalCount / (long)this.pageSize + 1L;
        }

        return this.totalPages;
    }

    public PageResponse() {
    }

    public PageResponse(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageResponse(int pageNum, int pageSize, long totalCount, long totalPages, int pageCount, Collection<?> items) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.pageCount = pageCount;
        this.items = items;
    }

    public PageResponse(PageInfo<?> page, Collection<?> list) {
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.totalCount = page.getTotal();
        this.totalPages = (long)page.getPages();
        this.pageCount = page.getSize();
        this.items = list;
    }

    public PageResponse(PageInfo<?> page) {
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.totalCount = page.getTotal();
        this.totalPages = (long)page.getPages();
        this.pageCount = page.getSize();
        this.items = page.getList();
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.totalCount == 0L;
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return this.totalCount != 0L && this.items != null && this.items.size() > 0;
    }
}