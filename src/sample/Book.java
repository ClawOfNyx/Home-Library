package sample;

import java.util.Locale;

public class Book extends Author{
    private String nameOfBook;
    private int dateOfRelease;

    public Book (String nameOfAuthor, String gender, String nameOfBook, int dateOfRelease) {
        super(nameOfAuthor, gender);

        this.nameOfBook = nameOfBook;
        this.dateOfRelease = dateOfRelease;
    }

    public String getNameOfBook() {
        return nameOfBook;
    }

    public String getAuthor() {
        return super.toString();
    }

    public int getDateOfRelease() {
        return dateOfRelease;
    }

    //--------------Part-2--------------

    public String getAuthorName() {
        return super.getNameOfAuthor();
    }

    public String getAuthorGender() {
        return super.getGender();
    }

    public void setDateOfRelease(int dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public String toString() {
        return nameOfBook + " by author " + super.toString() + "; released in " + dateOfRelease + ".";
    }
}
