package com.ganchaowen.tmp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ganyingle <ganyingle@kuaishou.com>
 * Created on 2023-03-31
 * description: 对外提供操作的类
 */
public class DataQueryOperation {


    @SuppressWarnings("checkstyle:LocalVariableName")
    public static void main(String[] args) {
        List<String[]> params = new ArrayList<>();
        String[] param1 = new String[] {"db", "10.64.6.4", "select * from user"};
        String[] param2 = new String[] {"fs", "d:\\test", "salary"};
        String[] param3 = new String[] {"db", "10.64.6.10", "select * from book"};
        String[] param4 = new String[] {"fs", "c:\\stu", "student"};
        params.add(param1);
        params.add(param2);
        params.add(param3);
        params.add(param4);

        Map<String, DataQuerySystem> systemMap = new HashMap<>();   //操作对象的缓存 数据源池子，为了保证只有一个
        for (int i = 0; i < params.size(); i++) {
            String[] param = params.get(i);

            String type = param[0]; //第一个参数是操作类
            DataQuerySystem querySystem = systemMap.get(type);  //从数据源池子中获取对应的

            //如果没有对应的数据源，就要new一个对应的对象
            if (querySystem == null) {
                if (type == "db") {
                    querySystem = new DbDataQuerySystem();
                } else if (type == "fs") {
                    querySystem = new FileDataQuerySystem();
                }
                //new 完就赛到数据源池子中
                systemMap.put(type, querySystem);
            }

            //用对应的操作方法操作就行了
            querySystem.doFullThing(param[1], param[2]);
        }
    }
}
