package myapp.models;

import javax.validation.constraints.*;

public class Person {
    private int id;
    @NotEmpty(message="Name should not be empty")
    @Size(min = 3, message="Minimum size for name is 2")
    private String name;
    @Email
    private String email;
    @Min(value=0, message = "Minimum age is 0")
    @Max(value = 150, message = "Maximum age is 150")
    private int age;

    public Person() {}

    public Person(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
