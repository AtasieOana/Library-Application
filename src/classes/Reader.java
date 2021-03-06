package classes;

import java.util.*;

public class Reader implements CSVCompatible{

    private String lastName;
    private String firstName;
    private Date dateOfBirth;
    private String CNP;
    private String address;
    private String phoneNumber;

    public Reader(){
        this("", "", "", null, "", "");

    }

    public Reader(String CNP, String lastName, String firstName, Date dateOfBirth,  String address,
                  String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.CNP = CNP;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getType(){
        return "Reader";
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String  getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Reader reader = (Reader) o;
        return Objects.equals(lastName, reader.lastName) &&
                Objects.equals(firstName, reader.firstName) &&
                Objects.equals(dateOfBirth, reader.dateOfBirth) &&
                Objects.equals(CNP, reader.CNP) &&
                Objects.equals(address, reader.address) &&
                Objects.equals(phoneNumber, reader.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, dateOfBirth, CNP, address, phoneNumber);
    }

    @Override
    public String toString() {
        return "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", CNP='" + CNP + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'';
    }
}
