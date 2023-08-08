package Service;

import Model.Account;
import DAO.AccountDAO;
public class AccountService {
    AccountDAO accountDAO;

public Account addAccount(Account account){
        Account newaccount = accountDAO.insertAccount(account);
        return newaccount;
   }


   public boolean getByUsername(String username){
    boolean existingAccount = accountDAO.getByUsername(username);
    return existingAccount;
}
}


