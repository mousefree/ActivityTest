package mouse.test.utils;

import java.io.IOException;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;
import android.util.Log;


public class HttpConnector {

	private final static int TIMEOUT = 1000;
	private final static int CONNECT_TIMEOUT = 6000;
	private final static int SO_TIMEOUT = 10000;

	private static HttpConnector instance = new HttpConnector();
	private int nThreads = 3;
	private ThreadPoolExecutor sExecutorService;

	// 服务器秘钥
	private static KeyStore trustStore;

	public static KeyStore getTrustStore() {
		return trustStore;
	}

	public static void setTrustStore(KeyStore trustStore) {
		HttpConnector.trustStore = trustStore;
	}

	private HttpClient httpClient;
	private HttpClient httpsClient;

	private HttpConnector() {
		sExecutorService = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(nThreads);
	}

	public static HttpConnector getInstance() {
		return instance;
	}

	public int getThreadsPoolSize() {
		return nThreads;
	}

	public void setThreadsPoolSize(int size) {
		nThreads = size;
		sExecutorService.setMaximumPoolSize(nThreads);
		sExecutorService.setCorePoolSize(nThreads);
		AndroidHttpClient ahc;
	}

	public void sendRequest(Runnable runnable) {
		sExecutorService.execute(runnable);
	}
	

	public synchronized HttpClient getHttpClient() {
		if (null == httpClient) {
			// 初始化工作
			HttpParams params = new BasicHttpParams();

			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params,
					HTTP.DEFAULT_CONTENT_CHARSET);
			HttpProtocolParams.setUseExpectContinue(params, true);

			// 设置连接管理器的超时
			ConnManagerParams.setTimeout(params, TIMEOUT);

			// 设置连接超时
			HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);

			// 设置Socket超时
			HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);

			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schemeRegistry.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 433));

			ClientConnectionManager connManager = new ThreadSafeClientConnManager(
					params, schemeRegistry);

			httpClient = new DefaultHttpClient(connManager, params);
		}

		return httpClient;
	}
	
	public String doGet(String url, Map params) {  
        /* 建立HTTPGet对象 */  
        String paramStr = "";  
        Iterator iter = params.entrySet().iterator();  
        while (iter.hasNext()) {  
            Map.Entry entry = (Map.Entry) iter.next();  
            Object key = entry.getKey();  
            Object val = entry.getValue();  
            paramStr += paramStr = "&" + key + "=" + val;  
        }  
        if (!paramStr.equals("")) {  
            paramStr = paramStr.replaceFirst("&", "?");  
            url += paramStr;  
        }  
 //       url = "http://192.168.8.26:3000/Q?timestamp=2015-02-10+23%3A08%3A02.32&nonce=1193959466&token=mouse&signature=b244de2ca72d94548e859c2959b1892ba9d9e076";
 //       url = "http://http://app.kinglandsoft.com:9051/q?frelationid=0001%2F0113%2F0155%2F0116%2F4933&FReportClass=TDJ_ZMPushMessage&FFuncClass=ReadCZInformation";   
        HttpGet httpRequest = new HttpGet(url);  
        String strResult = "doGetError";  
        try {  
            /* 发送请求并等待响应 */  
            HttpResponse httpResponse = HttpConnector.getInstance().getHttpClient().execute(httpRequest);  
 //       	HttpClient lshttpClient = new DefaultHttpClient();
 //       	HttpResponse httpResponse = lshttpClient.execute(httpRequest);
            /* 若状态码为200 ok */  
            if (httpResponse.getStatusLine().getStatusCode() == 200) {  
                /* 读返回数据 */  
                strResult = EntityUtils.toString(httpResponse.getEntity());  
            } else {  
                strResult = "Error Response: "  
                        + httpResponse.getStatusLine().toString();  
            }  
        } catch (ClientProtocolException e) {  
            strResult = e.getMessage().toString();  
            e.printStackTrace();  
        } catch (IOException e) {  
            strResult = e.getMessage().toString();  
            e.printStackTrace();  
        } catch (Exception e) {  
            strResult = e.getMessage().toString();  
            e.printStackTrace();  
        }  
        Log.v("strResult", strResult);  
        return strResult;  
    }  
    public String doPost(String url, List<NameValuePair> params) {  
        /* 建立HTTPPost对象 */  
        HttpPost httpRequest = new HttpPost(url);  
        String strResult = "doPostError";  
        try {  
            /* 添加请求参数到请求对象 */  
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
            /* 发送请求并等待响应 */  
            HttpResponse httpResponse = HttpConnector.getInstance().getHttpClient().execute(httpRequest);  
            /* 若状态码为200 ok */  
            if (httpResponse.getStatusLine().getStatusCode() == 200) {  
                /* 读返回数据 */  
                strResult = EntityUtils.toString(httpResponse.getEntity());  
            } else {  
                strResult = "Error Response: "  
                        + httpResponse.getStatusLine().toString();  
            }  
        } catch (ClientProtocolException e) {  
            strResult = e.getMessage().toString();  
            e.printStackTrace();  
        } catch (IOException e) {  
            strResult = e.getMessage().toString();  
            e.printStackTrace();  
        } catch (Exception e) {  
            strResult = e.getMessage().toString();  
            e.printStackTrace();  
        }  
        Log.v("strResult", strResult);  
        return strResult;  
    } 
	
/*	

	public synchronized HttpClient getHttpsClient() {
		if (null == httpsClient) {
			// 初始化工作
			HttpParams params = new BasicHttpParams();

			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params,
					HTTP.DEFAULT_CONTENT_CHARSET);
			HttpProtocolParams.setUseExpectContinue(params, true);

			// 设置连接管理器的超时
			ConnManagerParams.setTimeout(params, TIMEOUT);

			// 设置连接超时
			HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);

			// 设置Socket超时
			HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);

			SchemeRegistry schemeRegistry = new SchemeRegistry();

			schemeRegistry.register(new Scheme("http",
					new EasySSLSocketFactory(), 80));
			try {
				schemeRegistry.register(new Scheme("https",
						new SSLSocketFactory(trustStore), 443));
			} catch (Exception e) {
				Log.e("HttpRequest", "getHttpsClient.register.Exception", e);
				schemeRegistry.register(new Scheme("https",
						new EasySSLSocketFactory(), 443));
			}
			// schemeRegistry.register(new Scheme("http", PlainSocketFactory
			// .getSocketFactory(), 80));
			// schemeRegistry.register(new Scheme("https", SSLSocketFactory
			// .getSocketFactory(), 80));

			ClientConnectionManager connManager = new ThreadSafeClientConnManager(
					params, schemeRegistry);

			httpsClient = new DefaultHttpClient(connManager, params);
		}

		return httpsClient;
	}
	
*/
	
}

