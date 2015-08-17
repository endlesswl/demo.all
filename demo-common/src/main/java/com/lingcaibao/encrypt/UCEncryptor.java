package com.lingcaibao.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 与领彩宝通讯时的参数加密解密类
 * @author siyuan.shi
 *
 */
public class UCEncryptor {
	public static final String UC_KEY = "123456";		// 密钥
	public static final String DECODE = "DECODE";		// 解密
	public static final String ENCODE = "ENCODE";		// 加密

	/**
	 * 字符串加密以及解密函数
	 * 
	 * @param string
	 *            $string 原文或者密文
	 * @param string
	 *            $operation 操作(ENCODE | DECODE), 默认为 DECODE
	 * @param string
	 *            $key 密钥
	 * @param int $expiry 密文有效期, 加密时候有效， 单位 秒，0 为永久有效
	 * @return string 处理后的 原文或者 经过 base64_encode 处理后的密文
	 * 
	 * @example
	 * 
	 *          $a = authcode('abc', 'ENCODE', 'key'); $b = authcode($a,
	 *          'DECODE', 'key'); // $b(abc)
	 * 
	 *          $a = authcode('abc', 'ENCODE', 'key', 3600); $b =
	 *          authcode('abc', 'DECODE', 'key'); // 在一个小时内，$b(abc)，否则 $b 为空
	 */
	public static String uc_authcode(String $string, String $operation) {
		return uc_authcode($string, $operation, null);
	}

	public static String uc_authcode(String $string, String $operation, String $key) {
		return uc_authcode($string, $operation, $key, 0);
	}

	public static String uc_authcode(String $string, String $operation, String $key,
			int $expiry) {

		int $ckey_length = 4; // note 随机密钥长度 取值 0-32;
		// note 加入随机密钥，可以令密文无任何规律，即便是原文和密钥完全相同，加密结果也会每次不同，增大破解难度。
		// note 取值越大，密文变动规律越大，密文变化 = 16 的 $ckey_length 次方
		// note 当此值为 0 时，则不产生随机密钥

		$key = md5($key != null ? $key : UC_KEY);
		String $keya = md5(substr($key, 0, 16));
		String $keyb = md5(substr($key, 16, 16));
		String $keyc = $ckey_length > 0 ? ($operation.equals(DECODE) ? substr(
				$string, 0, $ckey_length) : substr(md5(microtime()),
				-$ckey_length))
				: "";

		String $cryptkey = $keya + md5($keya + $keyc);
		int $key_length = $cryptkey.length();

		$string = $operation.equals(DECODE) ? base64_decode(substr($string,
				$ckey_length)) : sprintf("%010d", $expiry > 0 ? $expiry
				+ time() : 0)
				+ substr(md5($string + $keyb), 0, 16) + $string;
		int $string_length = $string.length();

		StringBuffer $result1 = new StringBuffer();

		int[] $box = new int[256];
		for (int i = 0; i < 256; i++) {
			$box[i] = i;
		}

		int[] $rndkey = new int[256];
		for (int $i = 0; $i <= 255; $i++) {
			$rndkey[$i] = (int) $cryptkey.charAt($i % $key_length);
		}

		int $j = 0;
		for (int $i = 0; $i < 256; $i++) {
			$j = ($j + $box[$i] + $rndkey[$i]) % 256;
			int $tmp = $box[$i];
			$box[$i] = $box[$j];
			$box[$j] = $tmp;
		}

		$j = 0;
		int $a = 0;
		for (int $i = 0; $i < $string_length; $i++) {
			$a = ($a + 1) % 256;
			$j = ($j + $box[$a]) % 256;
			int $tmp = $box[$a];
			$box[$a] = $box[$j];
			$box[$j] = $tmp;

			$result1.append((char) (((int) $string.charAt($i)) ^ ($box[($box[$a] + $box[$j]) % 256])));

		}

		if ($operation.equals(DECODE)) {
			String $result = $result1.substring(0, $result1.length());
			if ((Integer.parseInt(substr($result.toString(), 0, 10)) == 0 || Long
					.parseLong(substr($result.toString(), 0, 10)) - time() > 0)
					&& substr($result.toString(), 10, 16).equals(
							substr(md5(substr($result.toString(), 26) + $keyb),
									0, 16))) {
				return substr($result.toString(), 26);
			} else {
				return "";
			}
		} else {
			return $keyc
					+ base64_encode($result1.toString()).replaceAll("=", "");
		}
	}

	protected static String md5(long input) {
		return md5(String.valueOf(input));
	}

	protected static String md5(String input) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return byte2hex(md.digest(input.getBytes()));
	}

	protected static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString();
	}

	protected static String substr(String input, int begin, int length) {
		return input.substring(begin, begin + length);
	}

	protected static long microtime() {
		return System.currentTimeMillis();
	}

	protected static String substr(String input, int begin) {
		if (begin > 0) {
			return input.substring(begin);
		} else {
			return input.substring(input.length() + begin);
		}
	}

	protected static String base64_decode(String input) {
		try {
			return new String(Base64.decode(input.toCharArray()), "iso-8859-1");
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	protected static String base64_encode(String input) {
		try {
			return new String(Base64.encode(input.getBytes("iso-8859-1")));
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	protected static long time() {
		return System.currentTimeMillis() / 1000;
	}

	protected static String sprintf(String format, long input) {
		String temp = "0000000000" + input;
		return temp.substring(temp.length() - 10);
	}
	
	public static String encrypt(String $code){
		try {
			return UCEncryptor.uc_authcode(new String(Base64.encode($code.getBytes("UTF-8"))), ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String decrypt(String $code){
		try {
			return new String(Base64.decode(UCEncryptor.uc_authcode($code, DECODE).toCharArray()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		// https://test.lingcaibao.com/user_interface?action=b2ecmUan6%2BrMNtL7wRJeCDfnr7brjpCXsDbNjt9Cdnth&channel=3244nK1W0Tm2I%2FnPrLvRzMVjH%2FsqRYsP9jm6vDNquGiN&phone=912bBJDqB7y29l7P6rk0MwwO9o8aUCOuPuOjRaZ27XavytKBAVumokHxAXdP&thridId=5cd2aKv4oum2XIusL9vRbnH9VAI84YK7RO0SH4IDJlq3WPQsSEcb1ZCmfRAb&sign=79adcf5a8c5abd84006fb3942b6dd497
		UCEncryptor encryptor = new UCEncryptor();
		String paramEncode = encryptor.encrypt("13718154066haihaing");
		System.out.println(paramEncode);
//		System.out.println(encryptor.decrypt(paramEncode));
//		System.out.println(encryptor.decrypt("c5d5M2Rvr1WPj6CHV/chZU8405jPNZ7aHGS90lf91UsOe5t90g"));
// 		//ec71gUqTBfycTSkWm7+2DBD38/RdUeGxDGIo+jY2oROr3IuBygRNTGVI1omT/7CCa/bUtmax7hYbbkO6CMd/g/fZEL+cjWe3QpVAYkrXjodmmMNoCpiOvg1sln3uh8dB3d2P2wJPJG4dmL+oUlO9Tm84JePSi5Y369b+62BJgyr0s2/a07UZ9sInTQBceLgJRp9CA5wx5gQqlmU0ZFQ9YAgrnesnooUln7YK0nI
//		System.out.print(URLEncoder.encode("http://192.168.1.160/user_interface","UTF-8"));
		
	}

}
