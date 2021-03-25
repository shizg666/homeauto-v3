package com.landleaf.homeauto.common.mqtt;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

import javax.net.SocketFactory;
import javax.net.ssl.*;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class MqttSslUtil {

    // 信任所有服务端证书
    public static SocketFactory getSocketFactory() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new MyTrustAllManager();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(null, trustAllCerts, null);
        SocketFactory factory = sc.getSocketFactory();
        return factory;

    }

    public static SocketFactory getSocketFactory(final String caCrtFile) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate
        PEMReader reader = new PEMReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(caCrtFile)));
        X509Certificate caCert = (X509Certificate) reader.readObject();
        reader.close();
        // CA certificate is used to authenticate server
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(caKs);


        // finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, tmf.getTrustManagers(), null);

        return context.getSocketFactory();

    }


    public static SSLSocketFactory getSocketFactory(String caCrtFile, String crtFile, String keyFile, String password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate：加载CA证书
        PEMReader reader = new PEMReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(caCrtFile)));
        X509Certificate caCert = (X509Certificate) reader.readObject();
        reader.close();

        // load client certificate：加载客户端证书
        reader = new PEMReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(crtFile)));
        X509Certificate cert = (X509Certificate) reader.readObject();
        reader.close();

        // load client private key：加载客户端私钥
        reader = new PEMReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(keyFile)), password::toCharArray);
        KeyPair key = (KeyPair) reader.readObject();
        reader.close();

        // CA certificate is used to authenticate server
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(caKs);

        // client key and certificates are sent to server so it can authenticate us
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("certificate", cert);
        ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(), new java.security.cert.Certificate[]{cert});
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());

        // finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }


}

class MyTrustAllManager implements TrustManager, X509TrustManager {
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public boolean isServerTrusted(X509Certificate[] certs) {
        return true;
    }

    public boolean isClientTrusted(X509Certificate[] certs) {
        return true;
    }

    @Override
    public void checkServerTrusted(X509Certificate[] certs, String authType)
            throws CertificateException {
        return;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] certs, String authType)
            throws CertificateException {
        return;
    }
}

