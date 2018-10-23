package com.example.s530472.team01isanwmsu;

import java.util.Date;

/**
 * Created by S530472 on 4/10/2018.
 */

public class EventInfo {

    public String event_name;
    public String event_time;
    public String location;
    public String duration;
    public String objectId;
    public String event_date;
    public String event_description;

    public EventInfo() {
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    @Override
    public String toString() {
        return "EventInfo:\n" +
                "Event Name: " + event_name + "\n" +
                "Event Time: " + event_time + "\n" +
                "Location: " + location + "\n" +
                "Duration: " + duration + " hours \n" +
                "Event Date: " + event_date + "\n" +
                "Event Description: " + event_description + "\n";
    }

}
