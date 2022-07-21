package org.cuko.cloud.service.gatekeeper.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RouterConfig {


    private static Map<String,String> config = new HashMap<>();

    static {
        config.put("/api","http://192.168.158.121:9009");
    }

    public static String match(String path) throws Exception{
        Set<String> strings = config.keySet();
        String s = null;
        for (String string : strings) {
            boolean equals = path.startsWith(string);
            if(equals){
                s = config.get(string);
            }
        }
        if(null == s){
            throw new Exception("["+ path +"]没有找到指定路由");
        }
        return s;
    }

    public static String get(String path) throws Exception{
        Set<String> strings = config.keySet();
        for (String string : strings) {
            boolean equals = path.startsWith(string);
            if(equals){
                return string;
            }
        }
        throw new Exception("["+ path +"]没有找到指定映射");
    }

}
