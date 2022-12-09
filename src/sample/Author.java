package sample;

public class Author {
    private String nameOfAuthor;
    private String gender = "not specified";

    public Author(String nameOfAuthor, String gender) {
        this.nameOfAuthor = nameOfAuthor;
        setGender(gender);
    }

    public String getNameOfAuthor() {
        return nameOfAuthor;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String toString() {
        return nameOfAuthor + " (" + gender + ")";
    }
}
