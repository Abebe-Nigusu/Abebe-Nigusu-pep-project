package Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {
    AccountService accountService;
    public SocialMediaController(){
        this.accountService = new AccountService();
    }

  
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegistrationHandler);
        app.post("/login", this::postLoginHandler);
        //app.post(localhost:8080/messages, this.postMessageHandler);

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
    
     

        //    if (account.getPassword() == null || (account.getPassword()).length() < 4) {
        //     ctx.status(400).result("Password must be at least 4 characters long");
        //     return;
        // }

        //   if (account.getPassword() == null || account.getPassword().isEmpty()) {
        //     ctx.status(400).result("Password must be at least 4 characters long.");
        //   }
      
        //   if (users.containsKey(account.getUsername()) ) {
        //     ctx.status(400).result("Username already exists.");
        //   }
      
        // int accountId = account.getAccount_id();
        //   Account addedAccount = accountService.getAccountByAccountId(accountId);

        //   System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + addedAccount);

        //   if(addedAccount != null){
           
        //     } else{
        //         ctx.status(400);
        //         return;
        //     } 
      
      
   
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
    } 

       
    //     if ((account.getPassword().length() > 4) || ((account.getUsername().length() == 0) && (account.getUsername() == null)))  {
    //          ctx.json(mapper.writeValueAsString(addedAccount));
    //         ctx.status(200);
    //         return;
    //     } else{

    //         ctx.status(400);
    //    }

    //    }
        
        // if ( account.getUsername().length()<1)  {
        //     ctx.status(400);
        //     //ctx.result("Password must be at least 4 characters long");
        //     return;
        // }
        // if(accountService.getByUsername(account.getUsername()) ==true){
        //  ctx.status(400);
        // }

     

        // if(addedAccount==null){
        //     ctx.status(400);
        //     return;
        // }else{
        //     ctx.json(mapper.writeValueAsString(addedAccount));
        //     ctx.status(200);
        //     return;
        // }
    // }


    // private void postRegistrationHandler(Context ctx) throws JsonProcessingException {
    //     ObjectMapper mapper = new ObjectMapper();
    //     String requestBody = ctx.body();
    //     System.out.println(requestBody);
    
    //     if (requestBody == null || requestBody.isEmpty()) {
    //         ctx.status(400);
    //         return;
    //     }
    
    //     Account account = mapper.readValue(requestBody, Account.class);
    //     System.out.println("*************************");
    //         System.out.println(account.getPassword());
    //         System.out.println(account.getUsername());

    //     if (account.getPassword() == null || account.getPassword().length() < 4) {
    //         ctx.status(400);
    //         return;
    //     }
    //     if (account.getUsername() == null || account.getUsername().length() < 1) {
    //         ctx.status(400);
    //         return;
    //     }
    //     // if (accountService.getByUsername(account.getUsername())) {
    //     //     ctx.status(400);
    //     //     return;
    //     // }
    //     Account addedAccount;
    
    //    try {
    //       addedAccount = accountService.addAccount(account);
    //    } catch (Exception e) {
    //     System.out.println(" *******************" + e.getMessage());
        
    //    }
    //     //System.out.println(addedAccount);
    
    //     if (addedAccount == null) {
    //         ctx.status(400);
    //     } else {
    //         ctx.json(mapper.writeValueAsString(addedAccount));
    //         ctx.status(200);
    //     }
    // }
    

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }


