
package com.abid.atm.bank;

import java.util.ArrayList;


public class Test {
    
    
    public static void tesmaint(String[] args){
        DBManager dbm = new DBManager();
        
        User user = dbm.getUser("850506", "982261");
        
        if(user!=null){
            System.out.print("Name: "+user.getName()+" Email:   "+user.getEmail()+"\n");           
        } else {
            System.out.println("User is null");
        }
        
        
        ArrayList<Account> accounts = dbm.getAccounts("941212");
        
        if(accounts!=null){
            for(Account acct: accounts){
                System.out.print("Acct Tyoe: "+acct.getAcctType()+" Balance:   "+acct.getBalance()+"\n");                
            }
            
        } else {
            System.out.println("Account is null");
        }
        
        
        ArrayList<Transaction> trans = dbm.getTransactions("7404937111");
        for(Transaction trs: trans){
            System.out.print(trs.getSmryTransaction()+"\n");
        }
    }
    
}
