package com.xdaocloud.futurechain.util.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建tree
 */
public class TreeDataBuilder<T extends TreeData> {

    public List<T> build(List<T> nodes) {
        if (nodes == null) {
            return null;
        }
        List<T> trees = new ArrayList<T>();
        //两个for循环遍历
        //第一层
        for (T children : nodes) {
            Long pid = children.getParentId();
            //判断有无父节点，有父节点进入第二层for循环寻找父节点，无父节点直接添加到trees
            if (pid == null || pid == 0) {
                trees.add(children);
                continue;
            }
            //第二层循环
            for (T parent : nodes) {
                Long id = parent.getId();
                //判断当前节点的id是否等于第一层for循环的pid，如果相等把上一层for循环的节点数据加入到当前节点的子节点
                if (id != null && id.equals(pid)) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<TreeData>());
                    }
                    parent.getChildren().add(children);
                    continue;
                }
            }
        }
        return trees;
    }
}