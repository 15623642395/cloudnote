package org.tedu.cloudnote.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class NoteUtil {
	/**
	 * 将日期格式的字符串转成long表示
	 * @param dateString 日期格式的字符串 
	 * @return 日期的long表示
	 * @throws Exception
	 */
	public static Long convertDateStringToLong(
		String dateString) throws Exception{
		SimpleDateFormat sdf = 
			new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateString);
		return date.getTime();
	}
	
	/**
	 * 生成主键值
	 * @return 主键值
	 */
	public static String createId(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	/**
	 * 密码MD5加密
	 * "摘要算法"处理
	 * @param str 明文
	 * @return 密文
	 */
	public static String md5(String str){
		if(str==null || "".equals(str.trim())){
			return null;
		}
		try {
			MessageDigest digest = 
				MessageDigest.getInstance("MD5");
			byte[] input = str.getBytes();
			byte[] output = digest.digest(input);
			//System.out.println(output.length);
			//采用Base64算法将加密后的字节数组转成字符串
			String ret = 
				Base64.encodeBase64String(output);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args){
//		String s1 = md5("123456");
//		String s2 = md5("123456");
//		System.out.println(s1);
//		System.out.println(s2);
		System.out.println(createId());
		System.out.println(createId());
	}
}


