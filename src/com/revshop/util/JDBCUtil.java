package com.revshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class JDBCUtil {

    private static final String URL ="jdbc:oracle:thin:@localhost:1521/xepdb1";
    private static final String USER = "revshop";
    private static final String PASSWORD = "revshop1";

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Oracle JDBC Driver not found. Add ojdbc jar.", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}