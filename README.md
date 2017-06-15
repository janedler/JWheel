# JWheel
### 轮子控件 
![](https://github.com/janedler/JWheel/raw/master/screen_1.png) 

#### 自定义扩展方法 具体可以参考RegionTree、CustomTree
##### 继承WheelTree
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

#### 使用方法 具体可以参考MainActivity

##### 1.通过WheelUtil工具类调用轮子控件
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
##### 2.关闭轮子控件
```java
wheel.dismiss();
```
##### 3.轮子定位 (api定位，可以进行扩展) 
```java
/**
     * @param col 列
     * @param row 行
     */
wheel.selectItem(int row, int col);
```
