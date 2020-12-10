package Example.OkHttpTesting;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class App {
	static OkHttpClient client = getUnsafeOkHttpClient();

	public static void main(String[] args) throws Exception {
		Request request = new Request.Builder().url("http://localhost:8080").build();

		System.out.println("connected");

		Response response = client.newCall(request).execute();
		Protocol prt = response.protocol();
		System.out.println(prt);
		System.out.println(response.body().string());
	}

	private static OkHttpClient getUnsafeOkHttpClient() {
		try {
			// Create a trust manager that does not validate certificate chains
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					// TODO Auto-generated method stub

				}

				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					// TODO Auto-generated method stub

				}

				public X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return new X509Certificate[0];
				}

			} };

			// Install the all-trusting trust manager
			final SSLContext sslContext = SSLContext.getInstance("SSL");

			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			return new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
					.hostnameVerifier(new HostnameVerifier() {

						public boolean verify(String arg0, SSLSession arg1) {
							// TODO Auto-generated method stub
							return true;
						}
					}).protocols(Collections.singletonList(Protocol.H2_PRIOR_KNOWLEDGE)).build();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
