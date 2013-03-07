package com.secretary.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.*;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Base64;
import android.util.Log;

import java.util.Random;
/**
 * OAuth client
 *
 * @author Otto Virtanen, Taija Kostianen, Sonja Kärkkäinen, Joni Salminen
 * @version 14.2.2013
 */

public class OAuth {




	private StringBuilder baseUrl = new StringBuilder();
	private String baseString = "";
	private String signature ="";
	private HttpClient httpclient = new DefaultHttpClient();


	public OAuth(){}

	/**
	 * Hmac hash
	 * @param value
	 * @param key
	 * @return hashed string
	 */
	private static String hmac_sha1(String value, String key) {

		try {
			SecretKey secretKey = null;
			byte[] keyBytes = key.getBytes();
			secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKey);
			byte[] text = value.getBytes();
			return new String(Base64.encode(mac.doFinal(text), 0)).trim();
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch(InvalidKeyException e) {
			e.printStackTrace();
		}

		return null;

	}
	/**
	 * Get nonce
	 * @return
	 */
	private String getNonce(){
		Random ran = new Random();
		long nonce1 = ran.nextLong();

		Long nonce = Math.abs(new Long(nonce1));

		return nonce.toString();
	}
	/**
	 *
	 * @return current timestamp as seconds since january 01 1970
	 */
	private long getTimeStamp(){
		return System.currentTimeMillis() / 1000;
	}


	/**
	 *
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */

	public String requestToken() throws ParseException, IOException{
		String nonce = getNonce();

		long timeStamp = getTimeStamp();
		baseUrl.append("POST&");
		try {
			baseUrl.append(URLEncoder.encode(Consts.requestTokenUrl,"UTF-8"));

			baseUrl.append("&");
			baseUrl.append(URLEncoder.encode("oauth_callback=" + Consts.callBackUrl
					+ "&oauth_consumer_key=" +Consts.consumerKey
					+ "&oauth_nonce=" + nonce
					+ "&oauth_signature_method=" + Consts.signatureMethod
					+ "&oauth_timestamp=" + timeStamp
					+ "&oauth_version=" + Consts.oAuthVersion,"UTF-8"	
					));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		baseString = baseUrl.toString();


		signature = URLEncoder.encode(hmac_sha1(baseString, Consts.consumerSecret + "&"));

		//Lets send the header
		HttpPost httpPost = new HttpPost(Consts.requestTokenUrl);

		String header = "OAuth "
				+ "oauth_nonce=\"" + nonce+ "\", "
				+ "oauth_callback=\"" + Consts.callBackUrl+ "\", "
				+ "oauth_signature_method=\"" + Consts.signatureMethod + "\", "
				+ "oauth_timestamp=\"" + timeStamp+ "\", "
				+ "oauth_consumer_key=\"" + Consts.consumerKey + "\", "
				+ "oauth_signature=\"" + signature + "\", "
				+ "oauth_version=\"" + Consts.oAuthVersion + "\"";

		httpPost.setHeader("Authorization",header);
		HttpResponse response = httpclient.execute(httpPost);

		return EntityUtils.toString(response.getEntity());

	}//requestToken

}//class
