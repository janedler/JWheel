# JTime
### 轮子控件<br>

![](https://github.com/janedler/JWheel/raw/master/screen_1.jpg) 

#### 使用方法
##### 在启动页请先调用 
```java
JTimeUtil.instance.initTime(this);
```
##### 1.建议使用此方法 直接返回本地缓存通过计算后的时间
```java
JTimeUtil.instance.getCurrentTimeMillis(context);
```
##### 2.阻塞式 时间过长可能会爆出ANR
```java
JTimeUtil.instance.getSyncCurrentTimeMillis(context);
```
##### 3.异步式 异步返回当前网络时间戳
```java
JTimeUtil.instance.getAsynCurrentTimeMillis(context,new JTimeUtil.TimeCallBack(){
                    @Override
                    public void onTimeCallBack(long time) {
                        
                    }
                });
```
