package com.ganchaowen.tmp1;

/**
 * @author ganyingle <ganyingle@kuaishou.com>
 * Created on 2023-03-31
 * description: 数据库查询类
 */
public class DbDataQuerySystem extends DataQuerySystem {
    @Override
    //这个就是子类实现的父类的纯虚函数
    void open(String url) {
        System.out.println("打开了db的url:" + url);
    }

    @Override
    void query(String cmd) {
        System.out.println("执行了db的cmd");
    }

    @Override
    void close(String url) {
        System.out.println("关闭了db的url");
    }
}
