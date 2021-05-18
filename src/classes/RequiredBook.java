package classes;

public class RequiredBook extends Book implements Comparable<RequiredBook>{

    private Author author;
    private int numberOfRequests;

    public RequiredBook(){
        author = new Author();
        numberOfRequests = 0;
    }

    public RequiredBook(String name, Author author, int yearOfPublication, int numberOfRequests){
        super(name, yearOfPublication);
        this.author = author;
        this.numberOfRequests = numberOfRequests;
    }

    public RequiredBook(String name, int numberOfPages, int yearOfPublication, String language, Author author, int numberOfRequests) {
        super(name, numberOfPages, yearOfPublication, language);
        this.author = author;
        this.numberOfRequests = numberOfRequests;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(int numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    public void increaseTheNumberOfRequests(){
        numberOfRequests += 1;
    }

    @Override
    public int compareTo(RequiredBook book) {
        if (this.getName().equals(book.getName())) {
            if(this.author.equals(book.getAuthor())){
                return this.getYearOfPublication() - book.getYearOfPublication();
            }
            else{
                return this.author.getFirstName().compareTo(book.getAuthor().getFirstName());
            }
        }
        return this.getName().compareTo(book.getName());
    }

    @Override
    public String toString() {
        return this.getName() + ", author: " + author + ", numberOfRequests=" + numberOfRequests;
    }
}

