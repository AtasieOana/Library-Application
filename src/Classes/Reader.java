package Classes;

import java.util.*;
import java.util.regex.PatternSyntaxException;


public class Reader {

    private String lastName;
    private String firstName;
    private Date dateOfBirth;
    private String CNP;
    private String address;
    private String phoneNumber;

    public Reader(){
        this("", "", null, "", "", "");


    }

    public Reader(String lastName, String firstName, Date dateOfBirth, String CNP, String address,
                  String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        if (!CNP.matches("^[0-9]{13}$")) {
            throw new PatternSyntaxException("CNP is invalid!", "^[0-9]{13}$", -1);
        }
        else{
            this.CNP = CNP;
        }
        this.address = address;
        if (!phoneNumber.matches("^0[0-9]{9}$")) {
            throw new PatternSyntaxException("Phone number is invalid!", "^0[0-9]{9}$", -1);
        }
        else{
                this.phoneNumber = phoneNumber;
        }
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
        if (!CNP.matches("^[0-9]{13}$")) {
            throw new PatternSyntaxException("CNP is invalid!", "^[0-9]{13}$", -1);
        }
        else{
            this.CNP = CNP;
        }
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
        if (!phoneNumber.matches("^0[0-9]{9}$")) {
            throw new PatternSyntaxException("Phone number is invalid!", "^0[0-9]{9}$", -1);
        }
        else{
            this.phoneNumber = phoneNumber;
        }
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
}
