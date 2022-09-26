package com.example.hausemanager.CostumeClass;

import java.io.Serializable;

public class Person implements Serializable {

    private String personID;
    private String personName;
    private String personPhoneNumber;
    private String personGander;

    public Person() {
    }

    public Person(String personID, String personName, String personPhoneNumber, String personGander) {
        this.personID = personID;
        this.personName = personName;
        this.personPhoneNumber = personPhoneNumber;
        this.personGander = personGander;
    }

    public String getPersonGander() {
        return personGander;
    }

    public void setPersonGander(String personGander) {
        this.personGander = personGander;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonPhoneNumber() {
        return personPhoneNumber;
    }

    public void setPersonPhoneNumber(String personPhoneNumber) {
        this.personPhoneNumber = personPhoneNumber;
    }
}
