# Library Application
**Project developed in Java, using Object Oriented Programming concepts**

## Stage I

**Defining the system and implementing an application based on it**

The following classes were used in the system:
* Library
* Librarian
* Section
* Book, which extends into LibraryBook and RequiredBook
* Author,  which extends into LibraryAuthor
* Reader
* Loan
* LibraryService

**Features**

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

**Extending the project from the first stage by using files:**
**Performing an audit service**

