package DAO;
// package Application.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Util.ConnectionUtil;
import io.javalin.http.Context;

/**
 * A DAO is a class that mediates the transformation of data between the format
 * of objects in Java to rows in a
 * database. The methods here are mostly filled out, you will just need to add a
 * SQL statement.
 *
 * We may assume that the database has already created a table named 'author'.
 * It contains similar values as the Author class:
 * id, which is of type int and is a primary key,
 * name, which is of type varchar(255).
 */

public class AccountDAO {


    // public Account insertAccount(Account account) {
    //     Connection connection = ConnectionUtil.getConnection();
    //     try {
    //         // Write SQL logic here
    //         String sql = "Insert into account(username, password) values(?, ?); ";
    //         PreparedStatement preparedStatement = connection.prepareStatement(sql);

    //         // write preparedStatement's setString and setInt methods here.
          
    //         preparedStatement.setString(1, account.getUsername());
    //         preparedStatement.setString(2, account.getPassword());
          
    //         preparedStatement.executeUpdate();
    //         return account;
    //     } catch (SQLException e) {
    //         System.out.println(e.getErrorCode());
    //     }
    //     return null;
    // }


    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Write SQL logic here
            String sql = "INSERT INTO account(username, password) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
    
            preparedStatement.executeUpdate();
            return account;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        //return null;
    }


    public boolean getByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Account account = new Account();
                account.setUsername(resultSet.getString("username"));
                account.setPassword(resultSet.getString("password"));
                // You might need to fetch other fields as well

                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }

        return false;
    }

    

}

