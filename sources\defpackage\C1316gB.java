package defpackage;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

/* renamed from: gB reason: default package and case insensitive filesystem */
class C1316gB extends X509Certificate {
    private final X509Certificate a;

    public C1316gB(X509Certificate x509Certificate) {
        this.a = x509Certificate;
    }

    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        this.a.checkValidity();
    }

    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        this.a.checkValidity(date);
    }

    public int getBasicConstraints() {
        return this.a.getBasicConstraints();
    }

    public Set<String> getCriticalExtensionOIDs() {
        return this.a.getCriticalExtensionOIDs();
    }

    public byte[] getExtensionValue(String str) {
        return this.a.getExtensionValue(str);
    }

    public Principal getIssuerDN() {
        return this.a.getIssuerDN();
    }

    public boolean[] getIssuerUniqueID() {
        return this.a.getIssuerUniqueID();
    }

    public boolean[] getKeyUsage() {
        return this.a.getKeyUsage();
    }

    public Set<String> getNonCriticalExtensionOIDs() {
        return this.a.getNonCriticalExtensionOIDs();
    }

    public Date getNotAfter() {
        return this.a.getNotAfter();
    }

    public Date getNotBefore() {
        return this.a.getNotBefore();
    }

    public PublicKey getPublicKey() {
        return this.a.getPublicKey();
    }

    public BigInteger getSerialNumber() {
        return this.a.getSerialNumber();
    }

    public String getSigAlgName() {
        return this.a.getSigAlgName();
    }

    public String getSigAlgOID() {
        return this.a.getSigAlgOID();
    }

    public byte[] getSigAlgParams() {
        return this.a.getSigAlgParams();
    }

    public byte[] getSignature() {
        return this.a.getSignature();
    }

    public Principal getSubjectDN() {
        return this.a.getSubjectDN();
    }

    public boolean[] getSubjectUniqueID() {
        return this.a.getSubjectUniqueID();
    }

    public byte[] getTBSCertificate() throws CertificateEncodingException {
        return this.a.getTBSCertificate();
    }

    public int getVersion() {
        return this.a.getVersion();
    }

    public boolean hasUnsupportedCriticalExtension() {
        return this.a.hasUnsupportedCriticalExtension();
    }

    public String toString() {
        return this.a.toString();
    }

    public void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.a.verify(publicKey);
    }

    public void verify(PublicKey publicKey, String str) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.a.verify(publicKey, str);
    }
}
