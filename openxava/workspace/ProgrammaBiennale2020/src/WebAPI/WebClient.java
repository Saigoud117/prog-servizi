package WebAPI;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.*;

import okhttp3.*;
import okhttp3.logging.*;
import okhttp3.logging.HttpLoggingInterceptor.*;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.*;


public class WebClient {

    //URLS
	private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "Accept";
    private static final String TEXT_JSON = "application/json";
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String GZIP = "gzip, deflate, br";
    @SuppressWarnings("unused")
	private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private static OkHttpClient client;

    private static String endpoint() {
        return "https://www.serviziocontrattipubblici.it/";
    }
    
    private static String endpointProduzione() {
        return "http://app-contrattipubblici.regione.marche.it/";
    }

    public static Retrofit retrofit() {
        return new Retrofit.Builder().baseUrl(endpoint())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(WebClient.okHttpClient())
                .build();
    }
    
    public static Retrofit retrofitProduzione() {
        return new Retrofit.Builder().baseUrl(endpointProduzione())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(WebClient.okHttpClientProduzione())
                .build();
    }

    private static OkHttpClient okHttpClient() {
        if (client == null) {
            synchronized (OkHttpClient.class) {
                if (client == null) {
                    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                    
                 // Create a trust manager that does not validate certificate chains
                    final TrustManager[] trustAllCerts = new TrustManager[] {
                        new X509TrustManager() {
                          @Override
                          public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                          }

                          @Override
                          public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                          }

                          @Override
                          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                          }
                        }
                    };
                    
                    try {
	                    // Install the all-trusting trust manager
	                    final SSLContext sslContext = SSLContext.getInstance("SSL");
	                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
	                    // Create an ssl socket factory with our all-trusting manager
	                    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
	                    
	                    clientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
	                    clientBuilder.hostnameVerifier(new HostnameVerifier() {
	                      @Override
	                      public boolean verify(String hostname, SSLSession session) {
	                        return true;
	                      }
	                    });
                    }
                    catch (Exception e) {
                    	
                    }

                    //clientBuilder.addNetworkInterceptor(new UserAgentInterceptor());
                    clientBuilder.addInterceptor(new HttpLoggingInterceptor(new Logger() {@Override
	                    public void log(String arg0) {
	                    	// TODO Auto-generated method stub
	                    	System.out.println(arg0);
                    	}	
                    }).setLevel(Level.BASIC));
                    clientBuilder.connectTimeout(100, TimeUnit.SECONDS);
                    clientBuilder.readTimeout(100, TimeUnit.SECONDS);
                    client = clientBuilder.build();
                }
            }
        }
        return client;
    }
    
    private static OkHttpClient okHttpClientProduzione() {
        if (client == null) {
            synchronized (OkHttpClient.class) {
                if (client == null) {
                	//ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS).build();
                    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                    /*
                 // Create a trust manager that does not validate certificate chains
                    final TrustManager[] trustAllCerts = new TrustManager[] {
                        new X509TrustManager() {
                          @Override
                          public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                          }

                          @Override
                          public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                          }

                          @Override
                          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                          }
                        }
                    };
                    
                    try {
	                    // Install the all-trusting trust manager
	                    final SSLContext sslContext = SSLContext.getInstance("SSL");
	                    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
	                    // Create an ssl socket factory with our all-trusting manager
	                    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
	                    
	                    clientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
	                    clientBuilder.hostnameVerifier(new HostnameVerifier() {
	                      @Override
	                      public boolean verify(String hostname, SSLSession session) {
	                        return true;
	                      }
	                    });
                    }
                    catch (Exception e) {
                    	
                    }
                    */
                    //clientBuilder.addNetworkInterceptor(new UserAgentInterceptor());
                    clientBuilder.addInterceptor(new HttpLoggingInterceptor(new Logger() {@Override
	                    public void log(String arg0) {
	                    	// TODO Auto-generated method stub
	                    	System.out.println(arg0);
                    	}	
                    }).setLevel(Level.BASIC));
                    clientBuilder.connectTimeout(100, TimeUnit.SECONDS);
                    clientBuilder.readTimeout(100, TimeUnit.SECONDS);
                    //client = clientBuilder.connectionSpecs(Collections.singletonList(spec)).build();
                    client = clientBuilder.build();
                }
            }
        }
        return client;
    }

    //@SuppressWarnings("unused")
	private static class UserAgentInterceptor implements Interceptor {


        @Override
        public Response intercept(Chain chain) throws
                IOException {
            final Request originalRequest = chain.request();
            final Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader(ACCEPT)
                    .addHeader(ACCEPT, TEXT_JSON)
                    .removeHeader(CONTENT_TYPE)
                    .addHeader(CONTENT_TYPE, TEXT_JSON)
                    .removeHeader(ACCEPT_ENCODING)
                    .addHeader(ACCEPT_ENCODING, GZIP)
                    //.removeHeader(CONTENT_TYPE)
                    //.addHeader(CONTENT_TYPE, TEXT_JSON)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }
}
