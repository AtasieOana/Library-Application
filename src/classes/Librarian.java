package classes;

import java.util.Date;

public class Librarian implements CSVCompatible {

    private String CNP;
    private String lastName;
    private String firstName;
    private Date dateOfBirth;
    private String address;
    private int salary;

    public Librarian(){
        this("","","",null,"",0);

    }

    public Librarian(String lastName, String firstName){
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = null;
        this.address = "";
        this.salary = 0;
        this.CNP = "";
    }

    public Librarian(String CNP,String lastName, String firstName, Date dateOfBirth, String address, int salary) {
        this.CNP = CNP;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.salary = salary;
    }

    public Librarian(String lastName, String firstName, Date dateOfBirth, String address, int salary) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.salary = salary;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    @Override
    public String toString() {
        return "Librarian{" +
                "CNP='" + CNP + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                '}';
    }
}
