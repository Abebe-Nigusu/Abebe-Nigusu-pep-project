
package Service;

import java.sql.SQLException;
import java.util.List;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {

        MessageDAO messageDAO;

        public MessageService() {
            this.messageDAO = new MessageDAO();
        }

    

        public List<Message> getAllMessagesList() {
            return messageDAO.getAllMessages();
        }
        
        public Message getMessagesByMessageId(int message_id) {
            return messageDAO.getMessageById(message_id);
        }

        public Message addMessage(Message message) {
            return messageDAO.insertMessage(message);
        }



        public Message updateMessage(int message_id, Message message) {
            if (message.message_text == null || message.message_text.isEmpty() || message.message_text.length() > 255) {
            
                return null;
            }
        
            messageDAO.updateMessage(message_id, message);
            return messageDAO.getMessageById(message_id);
        }
        
        
        public List<Message> getMessagesByAccountId(int account_id) {
            return messageDAO.getMessagesByAccountId(account_id);
        }

        public Message deleteMessageByMessageId(int message_id) {
            try {
                return messageDAO.deleteMessageById(message_id);
            } catch (SQLException e) {
                System.out.println(e.getMessage()); 
                return null;
            }
        }

        public boolean checkIfMessageExists(int messageId) {
            return messageDAO.messageExists(messageId);
        }
        
        

}
