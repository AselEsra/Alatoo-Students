package sample;

public class Students{
    private int Id;
    private String name;
    private String faculty;
    private String email;
    private int phoneNumber;

    public Students(int id, String name, String faculty, String email, int phoneNumber) {
        this.Id = id;
        this.name=name;
        this.faculty = faculty;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
