package top.starp.util;

//import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//@Component
public class SensitiveWordFilter {
	public static int minMatchTYpe = 1;      //最小匹配规则
	public static int maxMatchType = 2;      //最大匹配规则
	@SuppressWarnings ("rawtypes")
	private Map sensitiveWordMap;

	/**
	 * 构造函数，初始化敏感词库
	 */
	public SensitiveWordFilter () {
		sensitiveWordMap = new SensitiveWordInit ().initKeyWord ();
	}

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

	public static void main (String[] args) {


//		待检测语句字数：307
//		敏感词的数量：3
//		语句中包含敏感词的个数为：2。包含：[高清裸体图, gugube]
//		总共消耗时间为：9
//		String string = "Song(songId=null, songName=好怀念觉得静静地记, mood=testmood, createTime=null, singer=testsinger, isOpen=null, callTimes=null, user=WeChatUser callTimes=null, user=WeChatUser(userId=20, openId=null, nickName=null, avatarUrl=null, userSex=null, userArea=null, userPhone=null, dayOrder=null), calls=null)\n";
		String string = "Song(gugube ，高清裸体图songId=null, songName=好怀念觉得静静地记, mood=testmood, createTime=null, singer=testsinger, isOpen=null, callTimes=null, user=WeChatUser callTimes=null, user=WeChatUser(userId=20, openId=null, nickName=null, avatarUrl=null, userSex=null, userArea=null, userPhone=null, dayOrder=null), calls=null)\n";
		System.out.println ("待检测语句字数：" + string.length ());
		long beginTime = System.currentTimeMillis ();
		SensitiveWordFilter filter = new SensitiveWordFilter ();
		System.out.println ("敏感词的数量：" + filter.sensitiveWordMap.size ());
		Set<String> set = filter.getSensitiveWord (string, 1);
		long endTime = System.currentTimeMillis ();
		System.out.println ("语句中包含敏感词的个数为：" + set.size () + "。包含：" + set);
		System.out.println ("总共消耗时间为：" + (endTime - beginTime));
	}

	/**
	 * 判断文字是否包含敏感字符
	 *
	 * @param txt
	 *         文字
	 * @param matchType
	 *         匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 *
	 * @return 若包含返回true，否则返回false
	 */
	public boolean isContainSensitiveWord (String txt, int matchType) {
		txt = deleteSpecialWord (txt);
		boolean flag = false;
		for (int i = 0; i < txt.length (); i++) {
			int matchFlag = this.CheckSensitiveWord (txt, i, matchType); //判断是否包含敏感字符
			if (matchFlag > 0) {    //大于0存在，返回true
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取文字中的敏感词
	 *
	 * @param txt
	 *         文字
	 * @param matchType
	 *         匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 */
	public Set<String> getSensitiveWord (String txt, int matchType) {
		txt = deleteSpecialWord (txt);
		Set<String> sensitiveWordList = new HashSet<String> ();

		for (int i = 0; i < txt.length (); i++) {
			int length = CheckSensitiveWord (txt.toLowerCase (), i, matchType);    //判断是否包含敏感字符
			if (length > 0) {    //存在,加入list中
				sensitiveWordList.add (txt.toLowerCase ().substring (i, i + length));
				i = i + length - 1;    //减1的原因，是因为for会自增
			}
		}

		return sensitiveWordList;
	}

	/**
	 * 替换敏感字字符
	 *
	 * @param replaceChar
	 *         替换字符，默认*
	 */
	public String replaceSensitiveWord (String txt, int matchType, String replaceChar) {
		String resultTxt = txt;
		Set<String> set = getSensitiveWord (txt, matchType);     //获取所有的敏感词
		Iterator<String> iterator = set.iterator ();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext ()) {
			word = iterator.next ();
			replaceString = getReplaceChars (replaceChar, word.length ());
			resultTxt = resultTxt.replaceAll (word, replaceString);
		}

		return resultTxt;
	}

	/**
	 * 获取替换字符串
	 */
	private String getReplaceChars (String replaceChar, int length) {
		String resultReplace = replaceChar;
		for (int i = 1; i < length; i++) {
			resultReplace += replaceChar;
		}

		return resultReplace;
	}

	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：<br>
	 */
	@SuppressWarnings ({"rawtypes"})
	public int CheckSensitiveWord (String txt, int beginIndex, int matchType) {

		boolean flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0;     //匹配标识数默认为0
		char word;
		Map nowMap = sensitiveWordMap;
		for (int i = beginIndex; i < txt.length (); i++) {
			word = txt.toLowerCase ().charAt (i);
//			System.out.println (word);
			nowMap = (Map) nowMap.get (word);     //获取指定key
			if (nowMap != null) {     //存在，则判断是否为最后一个
				matchFlag++;     //找到相应key，匹配标识+1
				if ("1".equals (nowMap.get ("isEnd"))) {       //如果为最后一个匹配规则,结束循环，返回匹配标识数
					flag = true;       //结束标志位为true
					if (SensitiveWordFilter.minMatchTYpe == matchType) {    //最小规则，直接返回,最大规则还需继续查找
						break;
					}
				}
			} else {     //不存在，直接返回
				break;
			}
		}
		if (matchFlag < 2 || ! flag) {        //长度必须大于等于1，为词
			matchFlag = 0;
		}
		return matchFlag;
	}
}
