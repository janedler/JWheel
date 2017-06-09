package com.janedler.util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.janedler.R;
import com.janedler.manager.base.WheelNode;
import com.janedler.manager.base.WheelTree;
import com.janedler.ui.OnWheelChangedListener;
import com.janedler.ui.WheelChooseAdapter;
import com.janedler.ui.WheelView;

import java.util.List;

/**
 * 轮子工具类
 * Created by janedler on 2017/6/8.
 */

public class WheelUtil {

    private Activity mActivity;
    private PopupWindow mPopupWindow;
    private LinearLayout mWheelViewContent;
    private TextView mBackView;
    private TextView mSureView;
    private WheelTree mTree;

    public WheelUtil(Activity activity, final WeelUtilCallBack callBack) {
        this.mActivity = activity;
        mPopupWindow = new PopupWindow(mActivity);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.view_wheel_layout, null);
        mWheelViewContent = (LinearLayout) view.findViewById(R.id.wheel_view_content);
        mBackView = (TextView) view.findViewById(R.id.backImg);
        mSureView = (TextView) view.findViewById(R.id.sureImg);
        mPopupWindow.setContentView(view);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x50000000));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (callBack != null) callBack.onDismiss();
            }
        });

        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) callBack.onBackClick();
            }
        });

        mSureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBack != null) {
                    int childCount = mWheelViewContent.getChildCount();
                    WheelView nextWheelView = (WheelView) mWheelViewContent.getChildAt(childCount - 1);
                    WheelChooseAdapter adapter = ((WheelChooseAdapter) nextWheelView.getViewAdapter());
                    WheelNode node = adapter.getItemText(nextWheelView.getCurrentItem());
                    callBack.onSureClick(mTree.reslultSure(node));
                }
            }
        });
    }

    /**
     * 关闭轮子
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) mPopupWindow.dismiss();
    }


    /**
     * 展示JWheel
     *
     * @param tree
     */
    public void showPicker(WheelTree tree) {
        if (tree == null) return;
        mTree = tree;
        mWheelViewContent.removeAllViews();
        List<WheelNode> nodes = tree.getWheelNodes();
        renderJWheelView(nodes, 0);
        mPopupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 选中
     *
     * @param col 列
     * @param row 行
     */
    public void selectItem(int row, int col) {
        if (mWheelViewContent == null) return;
        int childCount = mWheelViewContent.getChildCount();
        if (row >= childCount) return;
        WheelView nextWheelView = (WheelView) mWheelViewContent.getChildAt(row);
        int currentItem = nextWheelView.getCurrentItem();
        nextWheelView.setCurrentItem(col);
        nextWheelView.notifyChangingListeners(currentItem, col);
    }


    private void renderJWheelView(final List<WheelNode> nodes, int index) {
        if (nodes == null || nodes.size() <= 0) return;
        WheelView wheelView = buildWheelView(nodes, null);
        wheelView.setTag(index);
        wheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int childCount = mWheelViewContent.getChildCount();
                if (Integer.parseInt(wheel.getTag().toString()) == (childCount - 1)) {
                    return;
                }
                WheelView nextWheelView = (WheelView) mWheelViewContent.getChildAt(Integer.parseInt(wheel.getTag().toString()) + 1);
                WheelChooseAdapter adapter = ((WheelChooseAdapter) nextWheelView.getViewAdapter());
                if (newValue >= nodes.size()) {
                    return;
                }
                adapter.setData(nodes.get(newValue).getChildNodes(), 0);
                nextWheelView.setCurrentItem(0);
            }
        });
        mWheelViewContent.addView(wheelView);
        List<WheelNode> nodeList = nodes.get(0).getChildNodes();
        renderJWheelView(nodeList, ++index);
    }

    private WheelView buildWheelView(List<WheelNode> nodes, Object object) {
        WheelView wheelView = new WheelView(mActivity);
        wheelView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
        WheelChooseAdapter adapter = new WheelChooseAdapter(mActivity);
        adapter.setData(nodes, object);
        wheelView.setViewAdapter(adapter);
        wheelView.setVisibleItems(5);
        wheelView.setWheelBackground(R.color.white);
        wheelView.setWheelForeground(R.mipmap.line2);
        wheelView.setCurrentItem(0);
        return wheelView;
    }

    /**
     * 轮子监听事件
     */
    public interface WeelUtilCallBack {
        //点击返回按钮
        void onBackClick();

        //点击确定按钮
        void onSureClick(String reslult);

        //轮子被关闭
        void onDismiss();
    }

}
