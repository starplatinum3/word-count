package top.starp.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EnvUtil {
    public static void main(String[] args) {
        EnvUtil.getEnv();
    }
    public  static  void getEnv(){
        Map<String,String > map = System.getenv();
        Iterator it = map.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            System.out.print(entry.getKey()+"=");
            System.out.println(entry.getValue());
        }

//        map.get("")
//        map.forEach((k,v)->{
//
//        });
//        java 遍历map
//        map.st
//        for (:map.Entry.comparingByKey()
//             ) {
//
//        }

//        Map<String, String> map = new HashMap<String, String>();
//        map.put("Java入门教程", "http://c.biancheng.net/java/");
//        map.put("C语言入门教程", "http://c.biancheng.net/c/");
        for (Map.Entry<String ,String> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();

//            String  mapKey = entry.getKey();
//            Object mapValue = entry.getValue();
//            System.out.println(mapKey + "：" + mapValue);
            if (mapKey.startsWith("HAD")) {
                System.out.println(mapKey + "：" + mapValue);
            }

//            if()
        }
    }
}
