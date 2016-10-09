package studio.crazybt.blockingsms.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Brucelee Thanh on 09/10/2016.
 */

public class SmsBlocked {
    private String phoneNumber;
    private String time;
    private String content;

    public SmsBlocked() {

    }

    public SmsBlocked(String phoneNumber, String content, String time){
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.time = time;
    }

    public SmsBlocked(String phoneNumber, String content, Date time) {
        this.phoneNumber = phoneNumber;
        this.content = content;
        DateFormat df = new SimpleDateFormat("dd/MM");
        this.time = df.format(time);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(Date time) {
        DateFormat df = new SimpleDateFormat("dd/MM");
        this.time = df.format(time);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
