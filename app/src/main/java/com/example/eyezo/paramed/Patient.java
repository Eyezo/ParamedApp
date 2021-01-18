package com.example.eyezo.paramed;

import java.util.Date;

public class Patient {

    private String iD;
    private String ethnicity;
    private String name;
    private  String surname;
    private  String gender;
    private String address;
    private String contact;
    private String nextKinName;
    private String nextKinSur;
    private String incidentNum;

    private String objectId ;
    private Date created ;
    private Date updated ;

    public Patient(){
        iD = null;
        ethnicity = null;
        name = null;
        surname = null;
        gender = null;
        address = null;
        contact = null;
        nextKinName = null;
        nextKinSur = null;
        incidentNum = null;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNextKinName() {
        return nextKinName;
    }

    public void setNextKinName(String nextKinName) {
        this.nextKinName = nextKinName;
    }

    public String getNextKinSur() {
        return nextKinSur;
    }

    public void setNextKinSur(String nextKinSur) {
        this.nextKinSur = nextKinSur;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getIncidentNum() {
        return incidentNum;
    }

    public void setIncidentNum(String incidentNum) {
        this.incidentNum = incidentNum;
    }
}
