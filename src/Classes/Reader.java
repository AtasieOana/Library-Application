package Classes;

import java.util.*;

public class Reader {

    private String lastName;
    private String firstName;
    private Date dateOfBirth;
    private int CNP;
    private String address;
    private int phoneNumber;
    private ArrayList<Loan> loans;

    public Reader(){
        this("", "", null, 0, "", 0, new ArrayList<>());
    }

    public Reader(String lastName, String firstName, Date dateOfBirth, int CNP, String address, int phoneNumber, ArrayList<Loan> loans) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.CNP = CNP;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.loans = loans;
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

    public int getCNP() {
        return CNP;
    }

    public void setCNP(int CNP) {
        this.CNP = CNP;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }

}
