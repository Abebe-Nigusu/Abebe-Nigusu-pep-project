
package DAO;


import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

import java.util.ArrayList;
import java.util.List;


public class AccountDAO {

    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM account";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public Account getAccountByUsername(String username) {
    Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "SELECT * FROM account WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            return new Account(rs.getInt("account_id"), rs.getString("username"),
                    rs.getString("password"));
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return null;
}


    public Account getAccountById(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here            
            String sql = "select * from account where account_id = ?" ;
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, account_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public Account insertAccountRegistration(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "Insert into account(username, password) values(?, ?); ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);

           
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
          
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getInt(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
          
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean accountExists(int accountId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT COUNT(*) FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        return false;
    }


    public Account insertAccountLogin(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "Insert into account(username, password) values(?, ?); ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getInt(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }

            //return account;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    
}


