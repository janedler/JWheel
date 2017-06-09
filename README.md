# JWheel
### 轮子控件
![](https://github.com/janedler/JWheel/raw/master/screen_1.png) 

#### 使用方法
##### 1.继承WheelTree
```java
/**
     * 构建轮子树的结构 必须实现
     *
     * @return
     */
    public abstract List<WheelNode> buildTree();

    /**
     * 每个节点比较算法 必须实现
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

```
##### 2.通过WheelUtil工具类调用轮子控件
```java
WheelUtil wheel = new WheelUtil(this, new WheelUtil.WeelUtilCallBack() {
           //取消
            @Override
            public void onBackClick() {
                Toast.makeText(this,"BackClick",Toast.LENGTH_SHORT).show();
            }
            //确定
            @Override
            public void onSureClick(String reslult) {
                Toast.makeText(this,reslult,Toast.LENGTH_SHORT).show();
            }
            //轮子关闭回调
            @Override
            public void onDismiss() {
            }
        });
```
##### 3.关闭轮子控件
```java
wheel.dismiss();
```
