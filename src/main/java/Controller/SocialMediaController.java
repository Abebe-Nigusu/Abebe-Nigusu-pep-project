package Controller;


import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import Model.Message;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {

        AccountService accountService;
        MessageService messageService;
        public SocialMediaController(){
            this.accountService = new AccountService();
        }

    
        public Javalin startAPI() {
            Javalin app = Javalin.create();
            
            app.post("/register", this::postRegistrationHandler);
            app.post("/login", this::postLoginHandler);
             app.post("/messages", this::postMessageHandler);
             app.get("/messages", this::getAllMessageHandler);

            // app.get("/accounts/{account_id}/messages", this::getMessageByAccountIdHandler);
            // app.get("/messages/{message_id}", this::getMessageByMessageIdHandler);
            app.patch("/messages/{message_id}", this::updateByMessageIdHandler);
            // app.delete("/messages/{message_id}", this::deleteByMessageIdHandler);
            

            return app;
        }

            private void postRegistrationHandler(Context ctx) throws JsonProcessingException {
                ObjectMapper mapper = new ObjectMapper();
                Account account = mapper.readValue(ctx.body(), Account.class);
            
                if (accountService.getAccountByAccountId(account.getAccount_id()) != null) {
                    ctx.status(400).result("Username cannot be blank.");
                    return;
                }
            
                System.out.println("Pass leng" + account.getPassword().length());
            
                if ((account.getPassword()).length() < 4) {
                    System.out.println(account.getPassword().length());
                    ctx.status(400).result(""); // Set an empty response body
                    return;
                }
            
                if ((account.getUsername()).length() < 1) {
                    System.out.println(account.getUsername().length());
                    ctx.status(400).result(""); // Set an empty response body
                    return;
                }
                Account addedAccount = accountService.registerAccount(account);
            
                if (addedAccount != null) {
                    ctx.json(mapper.writeValueAsString(addedAccount));
                    ctx.status(200);
                    return;
                } else {
                    ctx.status(400);
                    return;
                }
            }


            private void postLoginHandler(Context ctx) throws JsonProcessingException {
                ObjectMapper mapper = new ObjectMapper();
                Account account = mapper.readValue(ctx.body(), Account.class);
            
                Account loggedInAccount = accountService.loginAccount(account);
                System.out.println(("loo"+loggedInAccount));
            
                if (loggedInAccount == null) {
                    
                    ctx.status(401); // Unauthorized
                    ctx.result(""); // Set an empty response body for unsuccessful login
                    return;
                }
                
            
                boolean validUsername = loggedInAccount.getUsername().equals(account.getUsername());
                boolean validPassword = loggedInAccount.getPassword().equals(account.getPassword());
                // System.out.println("validUsername"+validUsername);
                //  System.out.println("validPassword"+validPassword);
                if (!validUsername || !validPassword) {
                loggedInAccount = null;
                    ctx.status(401); // Unauthorized
                    ctx.result(""); // Set an empty response body for unsuccessful login
                    return;
                }
                    if (!validUsername || validPassword) {
                loggedInAccount = null;
                    ctx.status(401); // Unauthorized
                    ctx.result(""); // Set an empty response body for unsuccessful login
                    return;
                }
                if (!validPassword) {
                loggedInAccount = null;
                ctx.status(401); // Unauthorized
                    ctx.result(""); // Set an empty response body for unsuccessful login
                    return;
                }
            
                if (validPassword ) {
                    System.out.println("HERE");
                loggedInAccount = new Account(account.getAccount_id(), account.getUsername(), account.getPassword());
                    ctx.status(200).json(loggedInAccount);
                    return;
                } else {
                    System.out.println("HERE2");
                    loggedInAccount = new Account(loggedInAccount.getAccount_id(), loggedInAccount.getUsername(), loggedInAccount.getPassword());
                    ctx.status(200).json(loggedInAccount);
                    return;
                }
            }

            
            public void getAllMessageHandler(Context ctx) {

                List<Message> messages = messageService.getAllMessagesList();
                    
                    if(messages.isEmpty()){
                        System.out.println("No messages avaliable!");
                        return;
                    }   else{   
                        ctx.json(messages).status(200);
                     }
                  
                     
            }


            public void postMessageHandler(Context ctx) {
                ObjectMapper o = new ObjectMapper();
                Message message;
                try {
                    message = o.readValue(ctx.body(), Message.class); 
                    messageService.addMessage(message);
                    ctx.json(message);
                    ctx.status(200);
                } catch (JsonProcessingException e) {
                System.out.println(e.getMessage());
                }
            
                }


            private void updateByMessageIdHandler(Context ctx) throws JsonProcessingException {
                ObjectMapper mapper = new ObjectMapper();
                Message message = mapper.readValue(ctx.body(), Message.class);
                Message updatedMessage = messageService.updateMessage(message.getMessage_id(), message);

                if(updatedMessage==null){
                    ctx.status(400);
                }else{
                    ctx.json(mapper.writeValueAsString(updatedMessage));
                }

                
                boolean existingMessageId = updatedMessage.message_id == message.getMessage_id();

                if ( !existingMessageId) {
                    ctx.status(400);
                    ctx.json("Message ID must be a positive integer and message_text not ove 255 characters!");
                    return;
                }
                
                if (updatedMessage.message_text == null || updatedMessage.message_text.isEmpty() || (updatedMessage.message_text.length() > 255)) {
                    ctx.status(400);
                    ctx.json("Message text cannot be blank!");
                    return;
                }
                else{
                    updatedMessage = new Message ( updatedMessage.getPosted_by(), updatedMessage.getMessage_text(), updatedMessage.getTime_posted_epoch());
                    ctx.status(200).json(updatedMessage);
                    return;
                }
            }    
                
        }
    

       
   


