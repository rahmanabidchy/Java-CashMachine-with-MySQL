
package com.abid.atm.bank;

import java.util.ArrayList;


public class User {
    
    private String UUID;
    private String firstName;
    private String lastName;    
    private String email;
    
    private ArrayList<Account> accounts;
    
    public User(String UUID, String firstName, String lastName, String email){
        
        this.UUID = UUID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
                
    }
    
    
    /**
     * Set the lists of accounts associated with the user
     * @param accounts   the list of the accounts
     */
    public void setAcctList(ArrayList<Account> accounts){
        this.accounts = accounts;
    }
    
    /**
     * Returns a specific account info of the user
     * @param idx  the index of the account
     * @return     the specific account
     */
    public Account getSpecificAcctInfo(int idx){
        return this.accounts.get(idx);
    }
    
    /**
     * Return the UUID of a specific account
     * @param idx   the index of the account
     * @return      the UUID of that account
     */
    public String getAcctUUID(int idx){
        return getSpecificAcctInfo(idx).getUUID();
    }
    
    /**
     * Get the UUID of the user
     * @return  the UUID of the user
     */
    public String getUUID(){
        return this.UUID;
    }
    
    /**
     * Get the full name of the user
     * @return  the full name of the user
     */
    public String getName(){
        return this.firstName+" "+this.lastName;
    }
    
    /**
     * Get the email address of the user
     * @return   the email address of the user
     */
    public String getEmail(){
        return this.email;
    }
    
    /**
     * Print summaries of the account of this user
     */
    public void printAccountsSummary(){
        
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for(int a=0; a<this.accounts.size(); a++){
            System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }
    
    /**
     * The number of accounts for that user
     * @return  the number of accounts
     */
    public int numAccounts(){
        return accounts.size();
    }
    
    /**
     * Print transaction history of a particular account
     * @param idx  The index number of the account
     */
    public void printAccTransHistory(int idx){
        accounts.get(idx).printAccTransHistory();
    }
    
    /**
     * Get the balance of a particular account
     * @param acctIdx  the index of the account to use
     * @return         the balance of the account
     */
    public double getAcctBalance(int acctIdx){
        return this.accounts.get(acctIdx).getBalance();
    }
    
    /**
     * Get the type of account
     * @param acctIdx  the index of the account to use
     * @return         the balance of the accounts
     */
    public String getAcctType(int acctIdx){
        return this.accounts.get(acctIdx).getAcctType();
    }
}
