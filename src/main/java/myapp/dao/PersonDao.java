package myapp.dao;

import myapp.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private static final String URL = "jdbc:postgresql://ec2-44-198-236-169.compute-1.amazonaws.com:5432/d84dfgfu1lnnl4";
    private static final String USERNAME = "uvaemfzyahkusw";
    private static final String PASSWORD = "20af4fe94934f4c4a74eb87c7bb3b1ab492ed930fcbb38a9b400f9735fd379f2";
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> getPeople() {
        List<Person> people = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from people order by id");
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setEmail(rs.getString("email"));
                person.setAge(rs.getInt("age"));

                people.add(person);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public void addPerson(Person person) {
        try {
            String sqlStatement = "insert into people(name, age, email) values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());

            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Person getPersonWithId(int id) {
        Person person = new Person();
        try {
            String sql = "select * from people where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                person.setId(id);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public void editPerson(Person person) {
        try {
            String sql = "update people set name=?, age=?, email=? where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, person.getId());

            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(int id) {
        String sqlDelete = "delete from people where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
