
package com.abid.atm.bank;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class Bank {
    
    private String branch;
    private User user;
    private DBManager dbm;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions = new ArrayList();
    
    
    /**
     * Create bank instance
     * @param branch  The branch name
     */
    public Bank(String branch){
        this.branch = "The Royal bank "+branch;
        
        // initiate the database connection
        dbm = new DBManager();
    }
    
    
    /**
     * Returns an User if the correct UUID/PIN combo is given
     * @param UUID  UUID of the User
     * @param PIN   PIN of the user
     * @return      An User object
     */
    public User userLogin(String UUID, String PIN){
        
        this.user = dbm.getUser(UUID, PIN);
        return user;
    }
    
    /**
     * Load all the accounts and transactions data
     */
    public void loadData(){
        
        // Retrieves all the Accounts associated with the user
        retrieveAcct(user.getUUID());
    }
    
    /**
     * Retrieves all the Accounts associated with the user
     * @param UUID  UUID of the User     
     */
    private void retrieveAcct(String UUID){
        
        this.accounts = dbm.getAccounts(UUID);
        
        // add the account ArrayList to the User
        user.setAcctList(accounts);
        
        // Retrieves all the transactions made by this User
        retrieveTransForUser();
    }
    
    /**
     * Retrieves all the transactions for specific account
     * @param UUID  The UUID of the account
     */
    private void retrieveTransForEachAcct(String UUID, Account acct){
        ArrayList<Transaction> tempTrans;
        tempTrans = dbm.getTransactions(UUID);
        
        // add the transaction ArrayList to its corresponding Account
        acct.setTransList(tempTrans);
        
        // update the balance for the account
        acct.updateBalance();
        
        transactions.addAll(tempTrans);
    }
    
    /**
     * Retrieves all the transactions made by this User
     */
    private void retrieveTransForUser(){
        for(Account acct: accounts){
            String acctUUID  = acct.getUUID();
            retrieveTransForEachAcct(acctUUID, acct);
        }
    }
    
    /**
     * Add a transaction to a particular account
     * @param acctIdx  the index of the account
     * @param amount   the amount of the transaction
     * @param memo     the memo of the transaction
     */
    public void addAcctTransaction(int acctIdx, double amount, String memo){
                
        //String date = formatDateForDB(new Date());
        
        // the account where this transaction will belong in
        Account theAcct = user.getSpecificAcctInfo(acctIdx);
        
        // the uuid of the account
        String theAcctUUID = theAcct.getUUID();
        
        // the new transaction
        // Transaction newTran = new Transaction(theAcctUUID,amount,memo,date); <-- Redundant
        Timestamp date = new Timestamp(System.currentTimeMillis());        
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Transaction newTran = new Transaction(theAcctUUID,amount,memo,dateStr);
        
        // add this new transaction to the ArrayList of all transactions of this user
        transactions.add(newTran);
        
        // add this new transaction to the owner account
        theAcct.addTransaction(newTran);
        
        // add this transaction into the database
        dbm.updateTransactions(theAcctUUID, amount, memo, dateStr);
        
        
    }
    
    /**
     * Formats util.Date to MySQL DateTime
     * @param date
     * @return 
     * 
     * Redundant method
     */
    private String formatDateForDB(Date date){
        
        String dateSQL = "";
        String dateString = ""+date;
        
        String month = dateString.substring(4, 7);
        String monthSQL = "";
        switch (month){
            case "Jan":
                monthSQL = "01";
                break;
            case "Feb":
                monthSQL = "02";
                break;
            case "Mar":
                monthSQL = "03";
                break;
            case "Apr":
                monthSQL = "04";
                break;
            case "May":
                monthSQL = "05";
                break;
            case "Jun":
                monthSQL = "06";
                break;
            case "Jul":
                monthSQL = "07";
                break;
            case "Aug":
                monthSQL = "08";
                break;
            case "Sep":
                monthSQL = "09";
                break;
            case "Oct":
                monthSQL = "10";
                break;
            case "Nov":
                monthSQL = "11";
                break;
            case "Dec":
                monthSQL = "12";
                break;
        }
        String daySQL = dateString.substring(8, 10);
        String timeSQL = dateString.substring(11, 19);
        String yearSQL = dateString.substring(24, 28);
        
        dateSQL = yearSQL+"-"+monthSQL+"-"+daySQL+" "+timeSQL;
        
        return dateSQL;
    }

    
    
    
}
