package com.biblioteca.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca"; 

    private static final String USER = "root";

    private static final String PASSWORD = "root"; 

 

    public static Connection getConnection() throws SQLException {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL

            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {

            throw new SQLException("Error al cargar el driver de MySQL", e);

        }

    }
     }
