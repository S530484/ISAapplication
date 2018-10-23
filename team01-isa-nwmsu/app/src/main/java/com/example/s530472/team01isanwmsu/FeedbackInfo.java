package com.example.s530472.team01isanwmsu;

import java.util.Date;

/**
 * Created by S530472 on 3/9/2018.
 */

public class FeedbackInfo {
    public String phonenumber;
    public String feedbackmsg;
    public String objectId;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getFeedback() {
        return feedbackmsg;
    }

    public void setFeedback(String feedback) {
        this.feedbackmsg = feedback;
    }

    @Override
    public String toString() {
        return "FeedbackInfo{" +
                "phonenumber='" + phonenumber + '\'' +
                ", feedback='" + feedbackmsg + '\'' +
                '}';
    }
}
