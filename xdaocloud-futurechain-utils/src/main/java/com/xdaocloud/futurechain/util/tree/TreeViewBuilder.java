package com.xdaocloud.futurechain.util.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建tree
 */
public class TreeViewBuilder {

    public static List<TreeView> build(List<TreeView> nodes) {
        if (nodes == null) {
            return null;
        }
        List<TreeView> topNodes = new ArrayList<TreeView>();
        for (TreeView children : nodes) {
            Long pid = children.getParentId();
            if (pid == null || pid == 0) {
                topNodes.add(children);
                continue;
            }

            for (TreeView parent : nodes) {
                Long id = parent.getId();
                if (id != null && id.equals(pid)) {
                    if (parent.getNodes() == null) {
                        parent.setNodes(new ArrayList<TreeView>());
                    }
                    parent.getNodes().add(children);
                    continue;
                }
            }

        }
        return topNodes;
    }
}