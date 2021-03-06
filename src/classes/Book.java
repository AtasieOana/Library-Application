package classes;

public abstract class Book{

    private String name;
    private int numberOfPages;
    private int yearOfPublication;
    private String language;

    public Book(){
        this("",0,0,"");
    }

    public Book(String name, int numberOfPages, int yearOfPublication, String language) {
        this.name = name;
        this.numberOfPages = numberOfPages;
        this.yearOfPublication = yearOfPublication;
        this.language = language;
    }

    public Book(String name, int yearOfPublication){
        this.yearOfPublication = yearOfPublication;
        this.name = name;
        this.numberOfPages = 0;
        this.language = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



    @Override
    public String toString() {
        return "Book: " +
                "name=" + name + ",\n" +
                "      numberOfPages=" + numberOfPages + ",\n" +
                "      yearOfPublication=" + yearOfPublication + ",\n" +
                "      language=" + language + "\n";
    }
}
