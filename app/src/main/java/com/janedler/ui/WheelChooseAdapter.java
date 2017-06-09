package com.janedler.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.janedler.R;
import com.janedler.manager.base.WheelNode;

import java.util.List;

/**
 * Created by janedler on 2016/12/14.
 */

public class WheelChooseAdapter extends AbstractWheelAdapter {

    private List<WheelNode> mItems;
    protected LayoutInflater inflater;
    protected int itemResourceId;
    private Object mObject;

    public WheelChooseAdapter(Context context) {
        this.itemResourceId = R.layout.adatper_wheel_choose_item;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<WheelNode> items, Object object){
        this.mItems = items;
        this.mObject = object;
        notifyDataChangedEvent();
    }

    public Object getObject(){
        return this.mObject;
    }

    @Override
    public int getItemsCount() {
        if (mItems == null)
            return 0;
        return mItems.size();
    }

    private View getLayoutView(int itemResourceId, ViewGroup parent) {
        return inflater.inflate(itemResourceId, parent, false);
    }

    public WheelNode getItemText(int index) {
        if (index >= 0 && index < mItems.size()) {
            WheelNode item = mItems.get(index);
            return item;
        }
        return null;
    }


    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        if (index >= 0 && index < getItemsCount()) {
            if (convertView == null) {
                convertView = getLayoutView(itemResourceId, parent);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.adapterItemTv);
            if (textView != null) {
                WheelNode node = getItemText(index);
                textView.setText(node.mNodeShowTv);
                textView.setLines(1);
            }
            return convertView;
        }
        return null;
    }
}