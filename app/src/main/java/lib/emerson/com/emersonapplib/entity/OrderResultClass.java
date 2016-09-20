package lib.emerson.com.emersonapplib.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/15.
 */
public class OrderResultClass implements Serializable {
    private String  _input_charset;
    private String  appid;
    private String  body;
    private String  notify_url;
    private String  out_trade_no;
    private String  partner;
    private String  payment_type;
    private String  seller;
    private String  service;
    private String  sign;
    private String  subject;
    private String  timeout_express;
    private String  timestamp;
    private String  total_fee;


    public String get_input_charset() {
        return _input_charset;
    }

    public String getAppid() {
        return appid;
    }

    public String getBody() {
        return body;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public String getPartner() {
        return partner;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getSeller() {
        return seller;
    }

    public String getService() {
        return service;
    }

    public String getSign() {
        return sign;
    }

    public String getSubject() {
        return subject;
    }

    public String getTimeout_express() {
        return timeout_express;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void set_input_charset(String _input_charset) {
        this._input_charset = _input_charset;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    @Override
    public String toString() {
        return "OrderResultClass{" +
                "_input_charset='" + _input_charset + '\'' +
                ", appid='" + appid + '\'' +
                ", body='" + body + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", partner='" + partner + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", seller='" + seller + '\'' +
                ", service='" + service + '\'' +
                ", sign='" + sign + '\'' +
                ", subject='" + subject + '\'' +
                ", timeout_express='" + timeout_express + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", total_fee='" + total_fee + '\'' +
                '}';
    }



}
