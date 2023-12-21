package com.mycompany.dao.inter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class AbstractDAO {
    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/resume";
        String username = "root";
        String password = "rootPass";
        return DriverManager.getConnection(url, username, password);
    }
    
    private static EntityManagerFactory emf = null;

    public static EntityManager createEM() {
        // using double-checked locking to avoid unnecessary synchronization
        if (emf == null) {
            // Synchronize to ensure only one instance is created in a multithreaded environment
            synchronized (AbstractDAO.class) {
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory("resumeappPU");
                }
            }
        }
        return emf.createEntityManager();
    }

    public static void closeEMF() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
