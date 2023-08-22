package Controller;

import java.util.ArrayList;
import java.util.Collections;
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

        public SocialMediaController() {
            this.accountService = new AccountService();
            this.messageService = new MessageService();
        }

        public Javalin startAPI() {
            Javalin app = Javalin.create();

            app.post("/register", this::postRegistrationHandler);
            app.post("/login", this::postLoginHandler);
            app.post("/messages", this::postMessageHandler);
            app.get("/messages", this::getAllMessageHandler);
            app.get("/accounts/{account_id}/messages", this::getAllMessageByAccountIdHandler);
            app.get("/messages/{messageId}", this::getMessageByMessageIdHandler);
            app.patch("/messages/{messageId}", this::updateByMessageIdHandler);
            app.delete("/messages/{messageId}", this::deleteByMessageIdHandler);

            return app;
        }


        private void postRegistrationHandler(Context ctx) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(ctx.body(), Account.class);

            if (accountService.getAccountByAccountId(account.getAccount_id()) != null) {
                ctx.status(400);
                return;
            }


            if ((account.getPassword()).length() < 4) {
                ctx.status(400).result("");
                return;
            }

            if ((account.getUsername()).length() < 1) {
                ctx.status(400).result("");
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

            if (loggedInAccount == null) {
                ctx.status(401); 
                ctx.result("");
                return;
            }

            boolean validUsername = loggedInAccount.getUsername().equals(account.getUsername());
            boolean validPassword = loggedInAccount.getPassword().equals(account.getPassword());

            if (!validUsername || !validPassword) {
                ctx.status(401);
                ctx.result(""); 
            } else {
                ctx.status(200).json(loggedInAccount);
            }
        }


        public void getAllMessageHandler(Context ctx) {
            List<Message> messages = messageService.getAllMessagesList();
            System.out.println("messahes" + messages);

            if (messages == null || messages.isEmpty()) {
                ctx.json(Collections.emptyList());
            } else {
                ctx.json(messages);
            }
        }
        

        public void getAllMessageByAccountIdHandler(Context ctx) {
            int accountId = Integer.parseInt(ctx.pathParam("account_id"));
            List<Message> messages = messageService.getMessagesByAccountId(accountId);

            if (messages == null || messages.isEmpty()) {
                ctx.json(Collections.emptyList());
            } else {
                ctx.json(messages);
            }
        }

        private void getMessageByMessageIdHandler(Context ctx) {
            int messageId = Integer.parseInt(ctx.pathParam("messageId"));

            Message message = messageService.getMessagesByMessageId(messageId);

            if (message != null) {
                ctx.json(message).status(200);
            } else {
                ctx.status(200);
                ctx.result("");
            }
        }


        private boolean isValidMessage(Message message) {
            return message != null &&
                    message.getPosted_by() > 0 && 
                    message.getMessage_text() != null && !message.getMessage_text().isEmpty()
                    && message.getMessage_text().length() <= 254 &&
                    message.getTime_posted_epoch() > 0;
        }

        public void postMessageHandler(Context ctx) {
            ObjectMapper o = new ObjectMapper();
            Message message;

            try {
                message = o.readValue(ctx.body(), Message.class);

                int postedByUserId = message.getPosted_by();
                if (!accountService.checkIfAccountExists(postedByUserId)) {
                    ctx.status(400);
                    ctx.result("");
                    return; 
                }

                if (message.getMessage_text().isEmpty()) { 
                    ctx.status(400);
                    ctx.result("");
                    return;
                }

                if (!isValidMessage(message)) {
                    ctx.status(400);
                }

                if (isValidMessage(message)) {
                    messageService.addMessage(message);
                    ctx.json(message);
                    ctx.status(200);
                } else {
                    ctx.status(400);
                    ctx.result("");
                }
            } catch (JsonProcessingException e) {
                ctx.status(400);
                ctx.result("Invalid JSON format");
                System.out.println(e.getMessage());
            }
        }


        private void updateByMessageIdHandler(Context ctx) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            Message message = mapper.readValue(ctx.body(), Message.class);
            boolean validUpdate = true;
    

            if (message.getMessage_text() == null || message.getMessage_text().isEmpty()
                    || message.message_text.length() >= 255) {
                ctx.status(400);
                ctx.result("");
                validUpdate = false;
            }

            int messageId = Integer.parseInt(ctx.pathParam("messageId"));

            if (validUpdate) {
            
                Message updateMessage = messageService.updateMessage(messageId, message);
            

                if (updateMessage == null) {
                    ctx.status(400);
                    ctx.result("");
                } else {
                    ctx.status(200);
                    ctx.json(updateMessage);
                }
            }
        }


        private void deleteByMessageIdHandler(Context ctx) throws JsonProcessingException {

            int messageId = Integer.parseInt(ctx.pathParam("messageId"));

            Message deletedMessage = messageService.deleteMessageByMessageId(messageId);

            if (deletedMessage != null) {
                ctx.json(deletedMessage);
            } else {
                ctx.status(200);
                ctx.result("");
            }
        }

}
