package com.ganchaowen.tmp1;

/**
 * @author ganyingle <ganyingle@kuaishou.com>
 * Created on 2023-03-31
 * description: 查询抽象类
 */
public abstract class DataQuerySystem {
    abstract void open(String url); //这就是纯虚函数，只有定义，没有实现，具体实现需要子类来写

    abstract void query(String cmd);

    abstract void close(String url);

    void doFullThing(String url, String cmd) {
        this.open(url);
        this.query(cmd);
        this.close(url);
    }
}
