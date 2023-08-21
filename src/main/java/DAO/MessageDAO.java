package DAO;

import java.sql.*;

import Model.Message;
import Util.ConnectionUtil;

import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public List<Message> getAllMessages() {

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try  {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
    
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
                 System.out.println(e.getMessage());
        }
        
        return messages;
    }
    

        public Message getMessageById(int message_id) {
            Connection connection = ConnectionUtil.getConnection();
            Message message = null;
            try  {
                String sql = "SELECT * FROM message WHERE message_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, message_id);
                    ResultSet rs = preparedStatement.executeQuery();
        
                    if (rs.next()) {
                        message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                                rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return message;
        }
        
     
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
    
            preparedStatement.executeUpdate();
    
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
    
            while (pkeyResultSet.next()) {
                int generated_message_id = pkeyResultSet.getInt(1);
                message.setMessage_id(generated_message_id); 
            }
    
            return message;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    

        public Message updateMessage(int message_id, Message message) {
            Connection connection = ConnectionUtil.getConnection();
            System.out.println("Updating message with ID: " + message_id);
            System.out.println("Updating message content: " + message.message_text);
        
            try {
                String sql = "UPDATE message SET message_text=? WHERE message_id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
                preparedStatement.setString(1, message.message_text);
                preparedStatement.setInt(2, message_id);
            
                int rowsAffected = preparedStatement.executeUpdate();
                 System.out.println("/rowsAffected: " + rowsAffected);

                if (rowsAffected > 0) {
                    System.out.println("Update successful. Rows affected: " + rowsAffected);
                    return message; 
                } else {
                    System.out.println("Update failed. No rows affected.");
                    return null;
                }
            } catch (SQLException e) {
                System.out.println("An SQL exception occurred: " + e.getMessage());
                return null;
            }
            
        }
        
        

        public Message deleteMessageById(int messageId) throws SQLException {
            Connection connection = ConnectionUtil.getConnection();
            try {
                Message message = getMessageByIdForDelete(messageId);
                String sql = "DELETE FROM message WHERE message_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, messageId);
                int rowsAffected = statement.executeUpdate();
    
                if (rowsAffected == 1) {
                   
                    return message;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } 
            return null;
        }

  
        
        public boolean messageExists(int messageId) {
            Connection connection = ConnectionUtil.getConnection();
            try {
                String sql = "SELECT COUNT(*) FROM message WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, messageId);
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
        
    
        public Message getMessageByIdForDelete(int messageId) throws SQLException {
            Connection connection = ConnectionUtil.getConnection();
            try {
                String sql = "SELECT * FROM message WHERE message_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, messageId);
                ResultSet resultSet = statement.executeQuery();
    
                if (resultSet.next()) {
                    int id = resultSet.getInt("message_id");
                    int postedBy = resultSet.getInt("posted_by");
                    String messageText = resultSet.getString("message_text");
                    long timePostedEpoch = resultSet.getLong("time_posted_epoch");
    
                    return new Message(id, postedBy, messageText, timePostedEpoch);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } 
            return null; 
        }

     
        public List<Message> getMessagesByAccountId(int account_id) {
            List<Message> messages = new ArrayList<>();
            Connection connection = ConnectionUtil.getConnection(); 
        
            try {
                String sql = "SELECT * FROM message WHERE posted_by = ?";
                PreparedStatement statement = connection.prepareStatement(sql); 
        
                statement.setInt(1, account_id);
        
                ResultSet rs = statement.executeQuery();
        
                while (rs.next()) {
                    Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                    );
                    messages.add(message);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        
            return messages;
        }
        
    }
    
  
            
    
        

     

