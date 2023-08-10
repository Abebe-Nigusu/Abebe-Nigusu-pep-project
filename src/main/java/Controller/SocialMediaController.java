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
        // app.patch("/messages/{message_id}", this::updateByMessageIdHandler);
        // app.delete("/messages/{message_id}", this::deleteByMessageIdHandler);
        

        return app;
    }


    private void postRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

 
        //  Map<String, String> users = new HashMap<>();

       
          if (accountService.getAccountByAccountId(account.getAccount_id()) != null) {
            ctx.status(400).result("Username cannot be blank.");
            return;
          } 

          if ( (account.getPassword()).length() < 4) {
                ctx.status(400).result("Password must be at least 4 characters long");
                return;
          }

        Account addedAccount = accountService.registerAccount(account);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + addedAccount);

        if(addedAccount != null){
          ctx.json(mapper.writeValueAsString(addedAccount));
          ctx.status(200);  
          return;
          } else{
              ctx.status(400);
              return;
          } 
    
      
   
    }
      

        private void postLoginHandler(Context ctx) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(ctx.body(), Account.class);
    
            Account loggedInAccount = accountService.loginAccount(account);
           
            // if(loggedInAccount == null){
            //        ctx.status(400); 
            //     } else{   
            //         ctx.json(mapper.writeValueAsString(loggedInAccount));
            //     ctx.status(200);   
            //         }

                // Account existingUser = new Account(account.getAccount_id(), account.getUsername(), account.getPassword());

                if (loggedInAccount.getUsername().equals(account.getUsername()) &&
                loggedInAccount.getPassword().equals(account.getPassword())) {
                    ctx.status(401); // Unauthorized
                               
                } else {  
                    // Create a response JSON with account details including account_id
                    Account loggedInUser = new Account(loggedInAccount.getAccount_id(), loggedInAccount.getUsername(), loggedInAccount.getPassword() );
                    ctx.status(200).json(loggedInUser);   
                }
               
             
            }


            public void getAllMessageHandler(Context ctx) {
                List<Message> messages = messageService.getAllMessagesList();
                ctx.json(messages).status(200);
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
               
        }
    

       
   


