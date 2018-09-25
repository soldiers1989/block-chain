package com.xdaocloud.futurechain.util.tree;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeView implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 显示节点文本
     */
    private String text;

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 节点状态
     */
    private Boolean checked = false;
    private Boolean expanded = false;
    private Boolean disabled = false;

    /**
     * 节点的子节点
     */
    private List<TreeView> nodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JSONField(serialize = false)
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public List<TreeView> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeView> nodes) {
        this.nodes = nodes;
    }

    public TreeView() {}

    public TreeView(Long id, String text, Long parentId) {
        super();
        this.id = id;
        this.text = text;
        this.parentId = parentId;
    }

    public TreeView(Long id, String text, Long parentId, Boolean checked) {
        super();
        this.id = id;
        this.text = text;
        this.parentId = parentId;
        this.checked = checked;
    }

}