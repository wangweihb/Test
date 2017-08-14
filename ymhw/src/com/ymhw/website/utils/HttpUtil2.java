package com.ymhw.website.utils;
import java.io.IOException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
public class HttpUtil2 {
	/**
	 * 
	 * @param url 请求url
	 * @param parameterName post请求参数名称
	 * @param parameterValue post请求值
	 * @return
	 */
	public static String formPost(String url,String parameterName,String parameterValue){
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		//填入各个表单域的值
		NameValuePair[] data = {
			new NameValuePair(parameterName, parameterValue),
	    };
		//将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		
		//执行postMethod
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
		//301或者302
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY){
		   //从头中取出转向的地址
		   Header locationHeader = postMethod.getResponseHeader("location");
		   String location = null;
		   if(locationHeader != null){
			   location = locationHeader.getValue();
			   System.out.println("The page was redirected to:" + location);
		   }else{
			   System.err.println("Location field value is null.");
		   }
		   return location;
		}else{
			System.out.println(postMethod.getStatusLine());
	        String str = "";
	        try {
	        	str = postMethod.getResponseBodyAsString();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }       
	        postMethod.releaseConnection();
	        return str;
		}
	}
	}
