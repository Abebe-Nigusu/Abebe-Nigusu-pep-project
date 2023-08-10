package DAO;


import java.sql.*;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

        public List<Message> getAllMessages(){
            Connection connection = ConnectionUtil.getConnection();
            List<Message> messages = new ArrayList<>();
            try {
                //Write SQL logic here
                String sql = "SELECT * FROM message";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();

                while(rs.next()){
                    Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                            rs.getString("message_text"),  rs.getLong("time_posted_epoch") );
                    messages.add(message);
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return messages;
        }


        public Message getMessageById(int message_id){
            Connection connection = ConnectionUtil.getConnection();
            try {
                //Write SQL logic here            
                String sql = "select * from message where message_id = ?" ;
                
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                //write preparedStatement's setString and setInt methods here.
                preparedStatement.setInt(1, message_id);

                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                            rs.getString("message_text"),  rs.getLong("time_posted_epoch") );
                    return message;
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }


        public Message insertMessage(Message message) {
            Connection connection = ConnectionUtil.getConnection();
            try {
                // Write SQL logic here
                String sql = "Insert into message(posted_by, message_text, time_posted_epoch) values(?,? ?); ";
                PreparedStatement preparedStatement = connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);

                // write preparedStatement's setString and setInt methods here.
            
                preparedStatement.setInt(1, message.posted_by);
                preparedStatement.setString(2, message.message_text);
                preparedStatement.setLong(2, message.time_posted_epoch);
            
                preparedStatement.executeUpdate();

                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if (pkeyResultSet.next()) {
                    int generated_message_id = (int) pkeyResultSet.getInt(1);
                    return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(),message.getTime_posted_epoch());
                }
                // return message;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }


        public void updateMessage(int message_id, Message message){
            Connection connection = ConnectionUtil.getConnection();
            try {
                //Write SQL logic here
                String sql = "update message set message.posted_by=?, message.message_text=?, message.time_posted_epoch=? where message_id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                //write PreparedStatement setString and setInt methods here.
                preparedStatement.setInt(1, message.posted_by);
                preparedStatement.setString(2, message.message_text);
                preparedStatement.setLong(3, message.time_posted_epoch);
                
                preparedStatement.setInt(4, message_id);
               
                preparedStatement.executeUpdate();
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }


        public Message deleteMessageById(int message_id) throws SQLException {
            Connection connection = ConnectionUtil.getConnection();
            try  {
                String sql = "DELETE FROM messages WHERE message_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql); 
                statement.setInt(1, message_id);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 1) {
                    Message message = getMessageById(message_id);
                    return message;
                } 
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                }

            return null;
        }

        public List<Message> getMessagesByAccountId(int account_id) {
            Connection connection = ConnectionUtil.getConnection(); 
                
                try { String sql = "SELECT * FROM messages WHERE account_id = ?";
                    PreparedStatement statement = connection.prepareStatement(sql); 
                    statement.setInt(1, account_id);
                    ResultSet rs = statement.executeQuery();
                        List<Message> messages = new ArrayList<>();
                        while (rs.next()) {
                           
                                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                                        rs.getString("message_text"),  rs.getLong("time_posted_epoch") );
                             messages.add(message);; 
                             return messages;
                            }     
                        } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        return null;
                           
                        } 

    }
    
        // ... (other methods)
    
        // @Override
        // public boolean deleteMessageById(int messageId) { 
        //     private List<Message> messages = new ArrayList<>();
        //     private AtomicInteger messageIdCounter = new AtomicInteger(1);

        //         for (Iterator<Message> iterator = messages.iterator(); iterator.hasNext();) {
        //             Message message = iterator.next();
        //             if (message.getMessageId() == messageId) {
        //                 iterator.remove();
        //                 return true;
        //             }
        //         }
        //         return false;
        // }

        
            
    
        

        // private static void deleteMessage(int message_id) {
        //     try {
        //         int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        //         Message deletedMessage = messageService.deleteMessage(messageId);
    
        //         if (deletedMessage != null) {
        //             ctx.json(deletedMessage);
        //         } else {
        //             ctx.result("").status(200);
        //         }
        //     } catch (NumberFormatException e) {
        //         ctx.result(" ").status(200);
        //     }
        // }
        

