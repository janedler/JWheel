package com.janedler.manager;

import android.text.TextUtils;

import com.janedler.manager.base.WheelNode;
import com.janedler.manager.base.WheelTree;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janedler on 2017/6/9.
 */

public class CustomTree extends WheelTree {


    private List<String> mTexts;

    public CustomTree(List<String> texts){
        this.mTexts = texts;
        super.initTree();
    }

    @Override
    public List<WheelNode> buildTree() {
        if (mTexts == null || mTexts.size() <= 0) return null;
        List<WheelNode> nodes = new ArrayList<>();
        for (String text : mTexts) {
            WheelNode textNode = new WheelNode(text);
            textNode.mNodeShowTv = text;
            textNode.setParentNode(null);
            nodes.add(textNode);
        }
        return nodes;
    }

    @Override
    public boolean compare(WheelNode node, WheelNode findNode) {
        if (node == null || findNode == null) return false;
        Object nodeObj = node.mObject;
        Object findNodeObj = findNode.mObject;
        if (nodeObj instanceof String && findNodeObj instanceof String){
            String nodeText = (String) nodeObj;
            String findNodeText = (String) findNodeObj;
            return isFindNode(nodeText,findNodeText);
        }

        return false;
    }

    @Override
    public String reslultSure(WheelNode lastNode) {
        JSONObject jsonObject = treeTraversal(lastNode,null);
        return jsonObject.toString();
    }

    private JSONObject  treeTraversal(WheelNode node, JSONObject jsonObject){
        if (jsonObject == null) jsonObject = new JSONObject();
        if (node == null) return jsonObject;
        Object obj = node.mObject;
        if(obj != null && obj instanceof String){
            String text = (String) node.mObject;
            try {
                jsonObject.put("text",text);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        WheelNode parentNode =  node.getParentNode();
        return treeTraversal(parentNode,jsonObject);
    }


    private boolean isFindNode(String text,String findText) {
        if (!TextUtils.isEmpty(text) && !TextUtils.isEmpty(findText) && text.equals(findText)){
            return true;
        }
        return false;
    }


}
