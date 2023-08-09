package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

public Account registerAccount(Account account){
        Account newaccount = accountDAO.insertAccountRegistration(account);
        return newaccount;
   }

public Account loginAccount(Account account){
    Account newaccount = accountDAO.insertAccountLogin(account);
    return newaccount;
}

//    public boolean getByUsername(String username){
//     boolean existingAccount = accountDAO.getByUsername(username);
//     return existingAccount;
// }

}


