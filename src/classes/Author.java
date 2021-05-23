package classes;

import java.util.Objects;

public class Author{

    private int idAuthor;
    private String lastName;
    private String firstName;


    public Author() {
        this(0,"","");
    }

    public Author(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Author(int idAuthor, String lastName, String firstName) {
        this.idAuthor = idAuthor;
        this.lastName = lastName;
        this.firstName = firstName;
    }


    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
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
