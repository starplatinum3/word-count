package top.starp.util;

//import org.springframework.core.io.ClassPathResource;
import top.starp.util.WordFilter.WordFilter;

import java.io.*;
import java.util.*;

public class SensitiveWordInit {
    public static HashMap sensitiveWordMap;
    private static String ENCODING = "UTF-8";    //字符编码

    static {
        initKeyWord ();
    }

    public SensitiveWordInit () {
        super ();
    }


    public static Map initKeyWord () {
        try {
            File file=new File("D:\\proj\\springBoot\\wordCount\\src\\main\\resources\\keywords.txt");
//            file.getin
//            inputstream
            InputStream initialStream = new FileInputStream(file);

            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile (initialStream);
//            System.out.println (keyWordSet.size ());
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap (keyWordSet);
            //spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return sensitiveWordMap;
    }


    private static void addSensitiveWordToHashMap (Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap (keyWordSet.size ());
        String keyWord;
        Map nowMap;
        Map<String, String> newWorMap;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator ();
        while (iterator.hasNext ()) {
            keyWord = iterator.next ();    //关键字
            nowMap = sensitiveWordMap;
            for (int i = 0; i < keyWord.length (); i++) {
                //把词拆成一个一个字
                char keyChar = keyWord.charAt (i);
//                System.out.println (keyChar);
                Object wordMap = nowMap.get (keyChar);       //获取

                if (wordMap != null) {        //如果存在该key，直接赋值
                    System.out.println("===___+_+_+_+__");
                    System.out.println("有wordMap");
                    System.out.println("现在的 map 就是这个wordMap，赋值新的");
                    System.out.println(wordMap);
                    nowMap = (Map) wordMap;
                } else {
                    System.out.println("=====");
                    System.out.println("不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个");
                    //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<> ();
                    newWorMap.put ("isEnd", "0");     //不是最后一个
                    nowMap.put (keyChar, newWorMap);
                    System.out.println("这个字符 放个新的map");
                    System.out.println("keyChar");
                    System.out.println(keyChar);
                    System.out.println("newWorMap");
                    System.out.println(newWorMap);
                    nowMap = newWorMap;
                }

                if (i == keyWord.length () - 1) {
                    System.out.println("------------");
                    System.out.println("遍历到这个关键字的最后一个字符 最后一个 就是end了 ");
                    System.out.println("keyWord");
                    System.out.println(keyWord);
//                    System.out.println("i");
//                    System.out.println(i);
                    nowMap.put ("isEnd", "1");    //最后一个
                }
            }
        }
//        System.out.println (sensitiveWordMap);
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     * 一行一行
     */
    @SuppressWarnings ("resource")
    private static Set<String> readSensitiveWordFile (InputStream inputStream) throws Exception {
        Set<String> set = null;

//        ClassPathResource resource = new ClassPathResource ("Keywords.txt");
//        File file = resource.getFile ();
//        InputStream

        InputStreamReader read = new InputStreamReader (inputStream, ENCODING);
        try {
            if (inputStream != null) {      //文件流是否存在
                set = new HashSet<> ();
                BufferedReader bufferedReader = new BufferedReader (read);
                String txt;
                while ((txt = bufferedReader.readLine ()) != null) {
                    //读取文件，将文件内容放入到set中
//                    System.out.println (txt);
                    set.add (WordFilter.deleteSpecialWord (txt));
                }
            } else {         //不存在抛出异常信息
                throw new Exception ("敏感词库文件不存在");
            }
        } catch (Exception e) {

        } finally {
            read.close ();     //关闭文件流
        }
        return set;
    }
}
