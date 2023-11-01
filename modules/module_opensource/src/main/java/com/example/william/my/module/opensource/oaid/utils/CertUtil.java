package com.example.william.my.module.opensource.oaid.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CertUtil {

    public static String getCertInfo(String appCertPem) {
        CertificateFactory fact;
        InputStream in = new ByteArrayInputStream(appCertPem.getBytes());
        X509Certificate appCert;
        try {
            fact = CertificateFactory.getInstance("X.509");
            appCert = (X509Certificate) fact.generateCertificate(in);
        } catch (CertificateException e) {
            return "[Cert Format Error]";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String certInfo = "Cert: \nSubjectName: " + appCert.getSubjectX500Principal().getName() +
                "\nNot Before: " + sdf.format(appCert.getNotBefore()) +
                "\nNot After: " + sdf.format(appCert.getNotAfter());
        try {
            appCert.checkValidity();
        } catch (CertificateExpiredException e) {
            return certInfo + "\n[Expired]";
        } catch (CertificateNotYetValidException e) {
            return certInfo + "\n[NotYetValid]";
        }
        return certInfo + "\n[Valid]";
    }
}
