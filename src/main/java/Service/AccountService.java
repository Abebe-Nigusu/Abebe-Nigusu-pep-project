package Service;

import Model.Account;

import java.util.List;

import DAO.AccountDAO;

public class AccountService {
     AccountDAO accountDAO;

        public AccountService() {
            accountDAO = new AccountDAO();
        }

        public AccountService(AccountDAO accountDAO){
                this.accountDAO = accountDAO;
            }

        public List<Account> getAllAccounts() {
                return accountDAO.getAllAccounts();
        
                // List<Account> allAccounts = accountDAO.getAllAccounts();
                // return allAccounts;
            
            }

        public Account getAccountByAccountId(int account_id){
                accountDAO.getAccountById(account_id);
                Account existingAccount = accountDAO.getAccountById(account_id);
                
                return existingAccount;      
            }

        public Account registerAccount(Account account){
            int accountId = account.getAccount_id();
            Account addedAccount = accountDAO.getAccountById(accountId);
        
            if (addedAccount != null) {
                return null;
            } else {

                
                Account insertedAccount = accountDAO.insertAccountRegistration(account);
                return insertedAccount;
            }
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


