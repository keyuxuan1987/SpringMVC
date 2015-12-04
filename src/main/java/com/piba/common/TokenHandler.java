package com.piba.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("all")
public class TokenHandler {

	private static TokenHandler instance;

	private TokenHandler(){}
	
	public static synchronized TokenHandler getInstance() {
		if (instance == null) {
			instance = new TokenHandler();
		}
		return instance;
	}
	
	/**
	 * The timestamp used most recently to generate a token value.
	 */

	private long previous;

	/**
	 * Generate a new transaction token, to be used for enforcing a single
	 * request for a particular transaction.
	 *
	 * @param id
	 *            a unique Identifier for the session or other context in which
	 *            this token is to be used.
	 */
	public synchronized String generateToken(String id) {
		try {
			long current = System.currentTimeMillis();

			if (current == previous) {
				current++;
			}

			previous = current;

			byte[] now = new Long(current).toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(id.getBytes());
			md.update(now);

			return toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * Convert a byte array to a String of hexadecimal digits and return it.
	 *
	 * @param buffer
	 *            The byte array to be converted
	 */
	private String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);

		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}

		return sb.toString();
	}
	/**
	 *  Function:  customToken;
	 *  Author :  ASUS,    Version : 1.0,    First complete date : 2015年11月21日 下午3:29:31;
	 *  Description :自定义token
	 *  Param and Desciption :
	 *  Return:
	 *  History:
	 *    1. Date:2015年11月21日 下午3:29:31
	 *       Author:kelvin
	 *       Version:
	 *       Modification:
	 *
	 */
	public synchronized String customToken(Object... signs) {
		try {
			long current = System.currentTimeMillis();

			if (current == previous) {
				current++;
			}
			previous = current;

			MessageDigest md = MessageDigest.getInstance("MD5");
			
			StringBuffer sign = new StringBuffer();
			for (int i = 0; i < signs.length; i++) {
				Object str = signs[i];
				sign.append(str);
			}
			
			md.update(sign.toString().getBytes());
			return toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
