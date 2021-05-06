# Library Application
**Project developed in Java, using Object Oriented Programming concepts**

## Stage I

- [x] **Defining the system and implementing an application based on it**

The following classes were used in the system:
* Library
* Librarian
* Section
* Book, which extends into LibraryBook and RequiredBook
* Author,  which extends into LibraryAuthor
* Reader
* Loan
* LibraryService

- [x] **Features**

* Adding a book in the library;
    * If the author of the book is not in the library, then it is added.
    * If the section is not in the library, then it is added.
    * The book is added to the author and the corresponding section.
* Remove a book from the library;
    * If the author of the book has no other book in the library then it will be deleted.
    * The book will be removed from the section to which it belonged.
* See all books written by an author;
* See all the books in a section;
* See all the books in the library;
     * A book in the library can be found in its section or in its author.
* Adding a new reader;
* Removing a reader;
* Removing an author from the library;
     * When the author is removed, the books written by him are also removed.
* Borrowing a book;
     * If a book that is not in the library is requested, then it will be added to the list of required books;
* Returning a book;
* Find the most requested book;

## Stage II

- [x] **Extending the project from the first stage by using CSV files** 

During this stage, CSV files were created for the following classes:
* Librarian 
   * *Columns:* last name, first name, date of birth, address, phone number;
* Library Author 
   * *Columns:* last name, first name, book titles written by him;
* Library Book 
   * *Columns:* book name, number of pages, year of publication, language, author's last name, author's first name, section type, number of copies;
* Reader 
   * *Columns:* last name, first name, date of birth, CNP, address, phone number;
* Requested book 
   * *Columns:* book name, author's last name, author's first name, year of publication, number of requests;
* Section 
   * *Columns:* section type, section book titles.

Also, a generic singleton service was created for reading and writing from CSV which includes the following methods:
* *readCSV* - read the entire contents of the file;
* *readObjects* - transforms the read content into objects of a certain type received as a parameter;
* *writeCSV* - write an object to a file;
* *deleteFromCSV* - delete entire lines or only certain elements of the columns in the file;
* *updateBooksInCVS* - updates the titles of the books in the section or from the library author
* *updateNumberInCSV* - updates by adding or subtracting the number of required books or the number of books in the library


- [x] **Performing an audit service** 

Each time one of the features from the first stage takes place, the name of the action and the time at which the action took place are written in the [Audit.csv](https://github.com/AtasieOana/Library-Application/blob/main/Audit.csv) file.
