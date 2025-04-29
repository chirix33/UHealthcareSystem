package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String URL = 
      "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
    private static final String USER = "fp510";
    private static final String PASS = "510";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
