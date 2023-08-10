package Service;

import java.sql.SQLException;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    MessageDAO messageDAO;

        public MessageService() {
            messageDAO = new MessageDAO();
        }

        public MessageService(MessageDAO messageDAO){
                this.messageDAO = messageDAO;
            }


        public List<Message> getAllMessagesList() {
            return messageDAO.getAllMessages();
        }

        public Message getMessagesByMessageId() {
            return messageDAO.getMessageById(0 );
        }

        public Message addMessage(Message message){
            Message newMessage = messageDAO.insertMessage(message);
            return newMessage;
        }

        public Message updateMessage(int message_id, Message message){
            messageDAO.updateMessage(message_id, message);
            Message existingMessage = messageDAO.getMessageById(message_id);
             return existingMessage;      
           
         }


       public List<Message> getMessagesByAccountId() {
            return messageDAO.getMessagesByAccountId(0 );
        }
        
        
        public Message deleteMessagesById() {
            try {
                return messageDAO.deleteMessageById(0 );
            } catch (SQLException e) {
               System.out.println(e.getMessage());
            }
            return null;
        }  


    
}
