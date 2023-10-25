package com.cafemanagement.perkpoint;
import java.sql.*;

public class database {

    public static Connection connectDB() {

        try {
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/cafe_perkpoint", "root", "");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
