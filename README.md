# Library-Application
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
* Service

**Features**
* Adding a book in the library involving
    * If the author of the book is not in the library, then it is added.
    * If the section is not in the library, then it is added.
    * The book is added to the author and the corresponding section.
* Remove a book from the library involving:
    * If the author of the book has no other book in the library then it will be deleted.
    * The book will be removed from the section to which it belonged.
* Find all books from an author;
