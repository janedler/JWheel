package com.janedler.manager.base;

import java.util.List;

/**
 * WheelUtil 树
 * Created by janedler on 2017/6/7.
 */
public abstract class WheelTree {

    private List<WheelNode> mRootNode;

    protected void initTree(){
        mRootNode = buildTree();
    }

    public List<WheelNode> getWheelNodes(){
        return mRootNode;
    }

    /**
     * 构建轮子树的结构 必须实现
     *
     * @return
     */
    public abstract List<WheelNode> buildTree();

    /**
     * 每个节点直接的比较算法 必须实现
     *
     * @return
     */
    public abstract boolean compare(WheelNode node, WheelNode findNode);


    /**
     * 点击返回按钮的时候返回数据 必须实现
     * @param lastNode 表示最后一个子节点
     * @return
     */
    public abstract String reslultSure(WheelNode lastNode);


    protected void findNodePosition(WheelNode node, IndexCallBack callBack) {
        FindNodeThread thread = new FindNodeThread(mRootNode, node, callBack);
        new Thread(thread).start();
    }

    private class FindNodeThread implements Runnable {

        private List<WheelNode> mNodes;
        private WheelNode mFindNode;
        private int mResultIndex;
        private IndexCallBack mCallBack;

        public FindNodeThread(List<WheelNode> nodes, WheelNode findNode, IndexCallBack callBack) {
            this.mNodes = nodes;
            this.mFindNode = findNode;
            this.mResultIndex = 0;
            this.mCallBack = callBack;
        }

        @Override
        public void run() {
            try {
                findNodeIndex(mNodes, mFindNode);
            } catch (InterruptedException e) {

            } finally {
                if (this.mCallBack != null) this.mCallBack.onIndex(mResultIndex);
            }
        }

        private void findNodeIndex(List<WheelNode> nodes, WheelNode findNode) throws InterruptedException {
            if (nodes == null || nodes.size() <= 0) return;
            for (int i = 0; i < nodes.size(); i++) {
                WheelNode node = nodes.get(i);
                if (node == null) continue;
                if (compare(node, findNode)) {
                    mResultIndex = i;
                    throw new InterruptedException();
                }
                List<WheelNode> childNodes = node.getChildNodes();
                if (childNodes != null && childNodes.size() > 0) {
                    findNodeIndex(childNodes, findNode);
                }
            }
        }
    }

    public interface IndexCallBack {
        void onIndex(int index);
    }

}
