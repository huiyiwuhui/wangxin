package com.wangxin.app.util.http;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


   /**
     * 无视Https证书是否正确的Java Http Client
     *
     *
     * @author huangxuebin
     *
     * @create 2012.8.17
     * @version 1.0
     */
public class HttpsUtil {


    /**
     * 忽视证书HostName
     */
    private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
        public boolean verify(String s, SSLSession sslsession) {
            System.out.println("WARNING: Hostname is not matched for cert.");
            return true;
        }
    };

     /**
     * Ignore Certification
     */
    private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {

        private X509Certificate[] certificates;

        public void checkClientTrusted(X509Certificate certificates[],
                String authType) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = certificates;
                System.out.println("init at checkClientTrusted");
            }
        }


        public void checkServerTrusted(X509Certificate[] ax509certificate,
                String s) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = ax509certificate;
                System.out.println("init at checkServerTrusted");
            }

        }


        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    };


    public static String httpsGet(String urlString) {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
        try {
//            URL url = new URL(urlString);
            URL url = new URL(null,urlString,new sun.net.www.protocol.https.Handler());
            /*
             * use ignore host name verifier
             */
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();


            // Prepare SSL Context
            TrustManager[] tm = { ignoreCertificationTrustManger };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());


            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(ssf);

            InputStream reader = connection.getInputStream();
            byte[] bytes = new byte[2048];
            int length = reader.read(bytes);


            do {
                buffer.write(bytes, 0, length);
                length = reader.read(bytes);
            } while (length > 0);


            // result.setResponseData(bytes);
            reader.close();

            connection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }
        String repString= new String (buffer.toByteArray());
        return repString;
    }


    public static void main(String[] args) {
//        String urlString = "https://218.202.0.241:8081/XMLReceiver";
//        String output = new String(HttpsUtil.getMethod(urlString));
//        System.out.println(output);



        String customer_get_info = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=//ACCESS_TOKEN&openid=//OPENID&lang=zh_CN";
		String token = "hu-7k6n5s3a2Zyyov0lPg65PMJgfYxcCrHaSUaIutVyV7rOpPjUMZbk2ws3ET91zRZ16_AFNrRHN5gDB_j-dTw87x8VtGb3hMhhjdIYJxWIJEZfAJAZUP";
		String openId = "om612jobCOLfp4e3i6LfkRvKVmo8";
		String url = customer_get_info.replace("//ACCESS_TOKEN", token).replace("//OPENID", openId);





		for (int i = 0; i < 100; i++) {
			String str = httpsGet(url);
			System.out.println(str);
		}
    }
}