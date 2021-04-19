

package com.abid.atm.bank;


public class Transaction {
    private String acctUUID;
    private double amount;
    private String memo;
    private String date;    
    
    /**
     * Create an object of transaction
     * @param acctUUID  the UUID of the associated account
     * @param amount    the amount of transaction
     * @param memo      the memo of the transaction
     * @param date      the date of the transaction
     */
    public Transaction(String acctUUID, double amount,String memo, String date){
        this.acctUUID = acctUUID;
        this.amount = amount;
        this.memo = memo;
        this.date = date;
        
    }
    
    public double getAmount(){
        return this.amount;
    }
    
    /**
     * Get a string summarizing the transaction
     * @return  the summary string
     */
    public String getSmryTransaction(){
        if(this.amount >=0 ){
            return String.format("%s : $%.02f : %s" , this.date, this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s" , this.date,-this.amount, this.memo);
        }        
    }
    
    
}
