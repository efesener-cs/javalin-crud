package space.efesener;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class DB {
    Dotenv dotenv = Dotenv.load();
    String dbUrl = dotenv.get("dburl");
    String dbUserName = dotenv.get("dbuser");
    String dbPassword = dotenv.get("dbpassword");

    public DB() {
        init();
    }

    void init() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            Statement statement = connection.createStatement();
            String query = "create table if not exists data (id int primary key generated always as identity,data varchar(100));";
            statement.execute(query);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void insert(String data) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            PreparedStatement statement = connection.prepareStatement("insert into data(data)values(?);");
            statement.setString(1, data);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void update(int id, String data) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            PreparedStatement statement = connection.prepareStatement("update data set data=? where id=?;");
            statement.setString(1, data);
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void delete(int id) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            PreparedStatement statement = connection.prepareStatement("delete from data where id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String get(int id) {
        String data = null;
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            PreparedStatement statement = connection.prepareStatement("select * from data where id=?;");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            data = rs.next() ? rs.getString("data") : null;
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public String getAll() {
        StringBuilder data = new StringBuilder();
        try {
            Class.forName("org.postgresql.Driver");
            try (Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
                 Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery("SELECT data FROM data")) {
                while (rs.next()) {
                    if (data.length() > 0) {
                        data.append(System.lineSeparator());
                    }
                    data.append(rs.getString("data"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.toString();
    }
}
