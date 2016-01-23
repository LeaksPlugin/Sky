package com.talesdev.core.database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * @author MoKunz
 */
public class Database {
    static final String DB_NAME = "jdbc:mysql://localhost:3306/minecraft";
    static final String DB_USER = "root";
    static final String DB_PASS = "moco2010";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        long t1 = System.currentTimeMillis();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_NAME, DB_USER, DB_PASS);
            statement = connection.createStatement();
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update("moco2010$Z".getBytes());
                System.out.println(String.format("%064x", new BigInteger(1, md.digest())));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            ResultSet rs = statement.executeQuery("SELECT * FROM authme");
            System.out.println("** Authme players list **");
            while (rs.next()) {
                System.out.println("Player name : " + rs.getString("username") + " (" + rs.getInt("id") + ")");
                System.out.println("    Password : " + rs.getString("password"));
                System.out.println("    IP : " + rs.getString("ip"));
                System.out.println("    Last login : " + rs.getLong("lastlogin"));
                System.out.println("    Email : " + rs.getString("email"));
            }
            rs.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Time used : " + (System.currentTimeMillis() - t1) + " ms");
    }
}
