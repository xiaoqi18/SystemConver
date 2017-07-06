package com.xiaoqi18.myutil;

import org.apache.commons.lang3.CharUtils;

import com.framework.common.util.StringUtils;
import com.uoojee.commons.lang.NumberUtils;

/** 进制转换工具类.
 * 26进制转换
 * 52进制转换
 * @author Fu_XueYong
 */

public class SystemConver {
	/** 补位最小值.
	 */
	private static final int FILL_MINI = 0;
	/** 补位范围最大值.
	 */
	private static final int FILL_LIMIT_MAX = 7;
	/** 补位范围最大值.
	 */
	private static final int FILL_MAX = FILL_LIMIT_MAX + 1;
	/** 26进制.
	 */
	private static final int SYSTEM_26 = 26;
	/** 52进制.
	 */
	private static final int SYSTEM_52 = 52;
	/** 补位函数.
	 * @author Fu_XueYong
	 * @param s 26进制字符串
	 * @param length 总长度
	 * @return 返回部位后的完整字符串
	 */
	private static String fill(final String s, final int length) {
		//验证s的合法性
		if (StringUtils.isNotEmpty(s)
				&& NumberUtils.isPositive(length)) {
			//得到补位数量的大小
			int fillSize = length - s.length();
			//补位数量大小的合法范围
			if (FILL_LIMIT_MAX >= fillSize) {
				int[] randomArray = randomNum(fillSize);
				int[] fillArray = fillRandom(1, length);
				char[] c = new char[length];
				for (int i = 0; i < randomArray.length; i++) {
					c[fillArray[i] - 1] = (char)
							(randomArray[i] + 48);
				}
				char[] stoc = s.toCharArray();
				for (int j = 0, l = 0; j < stoc.length; j++) {
					for (int k = 0; k < c.length; k++) {
						if (CharUtils.isAsciiPrintable(c[k])) {
							continue;
						} else {
							c[k] = stoc[l];
							l++;
						}
					}
				}
				return String.valueOf(c);
			} else {
				return s;
			}
		}
		return null;
	}
	/** 将指定的自然数转换为26进制表示。映射关系：[1-26] ->[A-Z].
	 * @author Fu_XueYong
	 * @param n 自然数（如果无效，则返回空字符串）.
	 * @param length 转换后的长度
	 * @return 26进制表示.
	 */
	public static String numberToSystem26(long n, final int length) {
		if (NumberUtils.isPositive(n)
				&& NumberUtils.isPositive(length)) {
			String s = "";
			while (n > 0) {
				long m = n % SYSTEM_26;
				if (m == 0) {
					m = SYSTEM_26;
				}
				s = (char) (m + 64) + s;
				n = (n - m) / SYSTEM_26;
			}
			return fill(s, length);
		} else {
			return "";
		}
	}

	/** 将指定的26进制表示转换为自然数。映射关系：[A-Z] ->[1-26].
	 * @author Fu_XueYong
	 * @param s 26进制表示（如果无效，则返回0）.
	 * @return 自然数.
	 */
	public static long system26ToNumber(String s) {
		//字符串必须为15位
		/*AssertUtils.isTrue(null != s && s.length() == 15,
				"[Assertion failed]  主键必须为15位");*/
		/*s = s.substring(s.length() - 8).toUpperCase();
		//字符串首先去掉随机数前缀
		char[] tc = s.toCharArray();
		for (int i = 0; i < tc.length; i++) {
			if (tc[i] >= 'A' && tc[i] <= 'Z') {
				s = s.substring(i);
				break;
			}
		}
		//字符串检验规则:必须由大写英文字母
		Matcher m = Pattern.compile("[A-Z]+").matcher(s);
		AssertUtils.state(m.matches(), "[Assertion failed]  主键规则不符合");*/
		long n = 0, j = 1;
		for (int i = s.length() - 1; i >= 0; i--) {
			char c = s.toCharArray()[i];
			if (c < 'A' || c > 'Z') {
				continue;
			}
			n += ((int) c - 64) * j;
			j *= SYSTEM_26;
		}
		return n;
	}
	/** 将指定的自然数转换为52进制表示。映射关系：[1-26,27-52] ->[A-Z,a-z].
	 * @author Fu_XueYong
	 * @param n 自然数（如果无效，则返回空字符串）.
	 * @param length 转换后的长度.
	 * @return 52进制表示.
	 */
	public static String numberToSystem52(long n, final int length) {
		if (NumberUtils.isPositive(n)
				&& NumberUtils.isPositive(length)) {
			String s = "";
			while (n > 0) {
				long m = n % SYSTEM_52;
				m = m == 0 ? SYSTEM_52 : m;
				if ((m + 64) > 90) {
					s = (char) (m + 70) + s;
				} else {
					s = (char) (m + 64) + s;
				}
				n = (n - m) / SYSTEM_52;
			}
			return fill(s, length);
		} else {
			return "";
		}
	}

	/**
	 * 将指定的52进制表示转换为自然数。映射关系：[A-Z,a-z] ->[1-26,27-52].
	 * @author Fu_XueYong
	 * @param s 52进制表示（如果无效，则返回0）.
	 * @return 自然数.
	 */
	public static long system52ToNumber(final String s) {
		if (StringUtils.isBlank(s)) {
			return 0L;
		}
		long n = 0, j = 1;
		for (int i = s.length() - 1; i >= 0; i--) {
			char c = s.toCharArray()[i];
			if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) {
				continue;
			}
			if (((int) c - 96) > 0) {
				n += ((int) c - 70) * j;
			} else {
				n += ((int) c - 64) * j;
			}
			j *= SYSTEM_52;
		}
		return n;
	}

	public static void main(String[] args) {
		System.out.println(numberToSystem26(9999999999999999L, 12));
		//System.out.println(system52ToNumber(numberToSystem52(127, 8)));
		//System.out.println(numberToSystem26(127, 8));
		//System.out.println(system26ToNumber(numberToSystem26(1, 8)));
	}
	
	/** 生成补位数的角标位置.
	 * @author Fu_XueYong
	 * @param min  最小个数
	 * @param max 最大个数
	 * @return 角标数组
	 */
	private static int[] fillRandom(final int min, final int max) {
		int count = 0;// 计数
		boolean flag = true;// 判断该数在数组中是否已经存在
		int num;// 随机数
		int[] array = new int[max];// 定义一个补位数组
		while (count < max) {
			num = (int) ((Math.random() * max) + min);
			flag = true; // 假设数组内还没有这个随机数
			for (int i = 0; i < array.length; i++) {
				if (array[i] == num) {
					flag = false;// 遍历这个数组，如果有值等于当前的随机数则设置为false
					break;// 已经存在了就没必要在继续循环了，break退出
				}
			}
			if (flag) {// flag为true才会进入
				array[count] = num;// 把当前的随机数添加到数组中
				count++;// 每添加一个随机数count加1
			}
		}
		return array;
	}
	/** 生成补位用的随机数
	 * @author Fu_XueYong
	 * @param length 补位长度
	 * @return
	 */
	private static int[] randomNum(final int length){
		int[] numArr = new int[length];
		for (int i = 0; i < numArr.length; i++) {
			numArr[i] = (int) (Math.random() * 10);
		}
		return numArr;
	}
	
	public static String decodeStr(String str){
		str = str.replace("&lt;", "<");
		str = str.replace("&gt;", ">");
		str = str.replace("&quot;", "\"");
		str = str.replace("&#039;", "'");
		str = str.replace("&#40;", "(");
		str = str.replace("&#41;", ")");
		str = str.replace("&amp;", "&");
		return str;
	}

}
