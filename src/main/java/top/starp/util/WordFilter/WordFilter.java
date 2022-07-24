package top.starp.util.WordFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFilter {
    /**
     * create by: Antares
     * description: 去掉特殊字符
     * create time: 2020/6/9 18:22
     * return
     **/
    public static String deleteSpecialWord (String txt) {
        String regEx = "[\n`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。，_·、？]";
        //可以在中括号内加上任何想要替换的字符，实际上是一个正则表达式

        //这里是将特殊字符换为aa字符串,""代表直接去掉
        String aa = "";

        Pattern p = Pattern.compile (regEx);

        Matcher m = p.matcher (txt);//这里把想要替换的字符串传进来

        return m.replaceAll (aa).trim ();

    }


}
