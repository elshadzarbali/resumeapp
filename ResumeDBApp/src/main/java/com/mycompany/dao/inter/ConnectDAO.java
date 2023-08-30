package com.mycompany.dao.inter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectDAO {
    public Connection connect() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/resume";
        String username = "root";
        String password = "rootPass";
        return DriverManager.getConnection(url, username, password);
    }
}
