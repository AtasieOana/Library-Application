package Classes;

import java.util.Objects;

public class Author{

    private String lastName;
    private String firstName;

    public Author(){
        this("","");
    }

    public Author(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Author author = (Author) o;
        return lastName.equalsIgnoreCase(author.lastName) && firstName.equalsIgnoreCase(author.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName);
    }

    @Override
    public String toString() {
        return "lastName=" + lastName +
                ", firstName=" + firstName;
    }
}
