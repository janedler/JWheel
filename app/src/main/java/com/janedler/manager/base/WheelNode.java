package com.janedler.manager.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Node 节点
 * Created by janedler on 2017/6/7.
 */
public class WheelNode {

    //父节点
    private WheelNode mParentNode;

    //子节点集合
    private List<WheelNode> mChildNodes;

    //节点数据
    public Object mObject;

    //节点展示数据
    public String mNodeShowTv;

    public WheelNode(Object obj){
        this.mObject = obj;
    }

    /**
     * 设置父节点
     * @param node
     */
    public void setParentNode(WheelNode node){
        this.mParentNode = node;
    }

    /**
     * 添加子节点
     * @param node
     */
    public void addChildNode(WheelNode node){
        if (mChildNodes == null) mChildNodes = new ArrayList<>();
        mChildNodes.add(node);
    }

    public WheelNode getParentNode(){
        return mParentNode;
    }

    public List<WheelNode> getChildNodes(){
        return mChildNodes;
    }


}
