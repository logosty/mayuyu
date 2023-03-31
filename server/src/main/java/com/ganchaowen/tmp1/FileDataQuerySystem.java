package com.ganchaowen.tmp1;

/**
 * @author ganyingle <ganyingle@kuaishou.com>
 * Created on 2023-03-31
 */
public class FileDataQuerySystem extends DataQuerySystem {
    @Override
    void open(String url) {
        System.out.println("打开了文件的url:" + url);
    }

    @Override
    void query(String cmd) {
        System.out.println("执行了文件的cmd");
    }

    @Override
    void close(String url) {
        System.out.println("关闭了文件的url");
    }
}
