package club.hsspace.whypps.debugging.tableModel;

import club.hsspace.whypps.manage.Authentication;
import club.hsspace.whypps.model.Certificate;
import javafx.beans.property.SimpleStringProperty;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @ClassName: CertificateModel
 * @CreateTime: 2022/6/7
 * @Comment:
 * @Author: Qing_ning
 * @Mail: 1750359613@qq.com
 */
public class CertificateModel {

    private final SimpleStringProperty sign;
    private final SimpleStringProperty start;
    private final SimpleStringProperty end;
    private final SimpleStringProperty ipv4;
    private final SimpleStringProperty ipv6;
    private final SimpleStringProperty trust;
    private final SimpleStringProperty local;
    private final SimpleStringProperty hasPrivateKey;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CertificateModel(Authentication authentication, Certificate certificate) throws UnknownHostException {

        sign = new SimpleStringProperty(certificate.getSign());

        start = new SimpleStringProperty(dateFormat.format(certificate.getStartTime()));

        end = new SimpleStringProperty(dateFormat.format(certificate.getEndTime()));

        ipv4 = new SimpleStringProperty(Inet4Address.getByAddress(certificate.getIpv4()).getHostAddress());

        ipv6 = new SimpleStringProperty(Inet6Address.getByAddress(certificate.getIpv6()).getHostAddress());

        trust = new SimpleStringProperty(certificate.getTrust() + "");

        local = new SimpleStringProperty(certificate.isLocal()?"本地加载":"网络加载");

        hasPrivateKey = new SimpleStringProperty(authentication.getLocalCertificate() == certificate?"持有私钥":"未持有证书");

    }

    public String getSign() {
        return sign.get();
    }

    public SimpleStringProperty signProperty() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign.set(sign);
    }

    public String getStart() {
        return start.get();
    }

    public SimpleStringProperty startProperty() {
        return start;
    }

    public void setStart(String start) {
        this.start.set(start);
    }

    public String getEnd() {
        return end.get();
    }

    public SimpleStringProperty endProperty() {
        return end;
    }

    public void setEnd(String end) {
        this.end.set(end);
    }

    public String getIpv4() {
        return ipv4.get();
    }

    public SimpleStringProperty ipv4Property() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4.set(ipv4);
    }

    public String getIpv6() {
        return ipv6.get();
    }

    public SimpleStringProperty ipv6Property() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6.set(ipv6);
    }

    public String getTrust() {
        return trust.get();
    }

    public SimpleStringProperty trustProperty() {
        return trust;
    }

    public void setTrust(String trust) {
        this.trust.set(trust);
    }

    public String getLocal() {
        return local.get();
    }

    public SimpleStringProperty localProperty() {
        return local;
    }

    public void setLocal(String local) {
        this.local.set(local);
    }

    public String getHasPrivateKey() {
        return hasPrivateKey.get();
    }

    public SimpleStringProperty hasPrivateKeyProperty() {
        return hasPrivateKey;
    }

    public void setHasPrivateKey(String hasPrivateKey) {
        this.hasPrivateKey.set(hasPrivateKey);
    }
}
