/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abid.atm.bank;

import java.util.ArrayList;


public class Account {
    
    private String UUID;
    private String holderUUID;
    private String acctType;
    private double balance;    
    
    private ArrayList<Transaction> transactions;
    
    
    /**
     * Creates an object of Account
     * @param UUID         the UUID of the account
     * @param holderUUID   the UUID of the user of this account
     * @param acctType     the type of the account
     * @param balance      the amount present in the account
     */
    public Account(String UUID, String holderUUID,String acctType){
        this.UUID = UUID;
        this.holderUUID = holderUUID;
        this.acctType = acctType;        
        
    }
    
    /**
     * Get the type of the account
     * @return  the type of the account
     */
    public String getAcctType(){
        return this.acctType;
    }
    
    /**
     * Updates the balance of this account
     */
    public void updateBalance(){
        double tempBal = 0;
        for(Transaction tr: transactions){
            tempBal+=tr.getAmount();
        }
        balance = tempBal;
    }

    
    /**
     * Get the balance of the account
     * @return  the amount
     */
    public double getBalance(){        
        return this.balance;
    }
    
    /**
     * Get the UUID of the Account
     * @return   the UUID of the Accounts
     */
    public String getUUID(){
        return this.UUID;
    }
    
    /**
     * Set the lists of transactions associated with the account
     * @param transactions   the list of the transaction
     */
    public void setTransList(ArrayList<Transaction> transactions){
        this.transactions = transactions;
    }
    
    /**
     * Get summary line for the account 
     * @return  the string summary
     */
    public String getSummaryLine(){
        
        // get the account's balance
        double balance = this.getBalance();
        
        // format the summary line depending on 
        // whether tha balance is negative.
        if(balance >= 0){
            return String.format("%s : $%.02f : %s", this.UUID, balance, this.acctType);
        } else {            
            return String.format("%s : $(%.02f) : %s", this.UUID, balance, this.acctType);
        }
    }
    
    /**
     * Print transaction history of a particular account
     */
    public void printAccTransHistory(){
        
        System.out.printf("\nTransaction history for account %s\n", this.UUID);
        for(int t=this.transactions.size()-1; t>=0; t--){
            System.out.println(this.transactions.get(t).getSmryTransaction());
        }       
        System.out.println();
    }
    
    /**
     * Add a new transaction in this account
     * @param trans   The transaction object to be added
     */
    public void addTransaction(Transaction trans){
        
        // add the new transation to the current array list
        this.transactions.add(trans);
        
        // update the balance
        updateBalance();
    }
    
}
