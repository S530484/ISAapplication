package com.example.s530472.team01isanwmsu;

/**
 * Created by S530484 on 4/17/2018.
 */

public class PickupInfo {
    public String student_name;
    public String arrival_time;
    public String arrival_date;
    public String student_emailid;
    public String student_contact;
    public String objectId;
    public String flightnum;

    public PickupInfo() {
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }

    public String getStudent_emailid() {
        return student_emailid;
    }

    public void setStudent_emailid(String student_emailid) {
        this.student_emailid = student_emailid;
    }

    public String getStudent_contact() {
        return student_contact;
    }

    public void setStudent_contact(String student_contact) {
        this.student_contact = student_contact;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFlightnum() {
        return flightnum;
    }

    public void setFlightnum(String flightnum) {
        this.flightnum = flightnum;
    }

    @Override
    public String toString() {
        return  "Name: " + student_name +
                "\nArrival time: " + arrival_time +
                "\nArrival date: " + arrival_date +
                "\nEmailid: " + student_emailid +
                "\nFlight details: " + flightnum;
    }
}
