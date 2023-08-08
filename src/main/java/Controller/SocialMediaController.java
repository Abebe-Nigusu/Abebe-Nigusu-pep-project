package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    public SocialMediaController(){
        this.accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegistrationHandler);

        return app;
    }


    private Account postRegistrationHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        Account addedAccount = accountService.addAccount(account);

        boolean condition = ((account.getUsername() == null || account.getAccount_id() ==0
        || account.getPassword() == null || account.getPassword().length() < 4
        || account.getUsername() != null));
       
        if(addedAccount != null){
                ctx.json(mapper.writeValueAsString(addedAccount));
                ctx.status(200);   
            }
           ctx.status(400);
        return addedAccount;
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


