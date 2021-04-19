
package com.abid.atm;

import com.abid.atm.bank.Bank;
import com.abid.atm.bank.User;
import java.util.Scanner;


public class CashMachineApp {
    
    private static Scanner sc;
    private static Bank theBank;
    private static User currUser;
    private static String UUID;
    private static String PIN;
    
    /**
     * Check the user login credential and help user login
     * returns   the current User if the user is valid
     */
    private static User login(){
                        
        System.out.println("Welcome.");
        String repeat = "";
        int quit = 1;
        
        do{
            System.out.println("Please insert the card"+repeat+". Or enter UUID manually.");
            UUID = sc.nextLine();            
            
            System.out.println("Enter the PIN number.");
            PIN = sc.nextLine();

            currUser = theBank.userLogin(UUID, PIN);
            
            if(currUser == null){
                System.out.println("INCORRECT UUID/PIN combination. Please try again.");
                System.out.println("Press any key to try again.\nOr press 0 to quit");
                quit = sc.nextInt();
                // if key pressed on 0, terminate whole process.                
                repeat = " again";
            }
        } while(currUser == null && quit != 0);
        return currUser;
    }
    
    /**
     * Show the transaction history for an account     
     */
    public static void showTransHistory(){
        
        int theAcct;
        
        // get the account whose transaction history to look at
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transaction you want to see: ", currUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if(theAcct<0 || theAcct>= currUser.numAccounts()){
                System.out.println("Invalid account. Please try again. ");
            }
        } while(theAcct<0 || theAcct>= currUser.numAccounts());
        
        // print the transaction history
        currUser.printAccTransHistory(theAcct);
    }
    
    /**
     * Print the initial account summary
     */
    private static void acctSummary(){
       // print a summary of the user's account
        currUser.printAccountsSummary(); 
    }
    
    /**
     * Process a fund deposit to an account
     */
    public static void depositFunds(){
        
        // inits
        int toAcct;        
        double amount;
        double acctBal;
        String memo;
        
        // get the account deposit to
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +"to deposit in: ", currUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct<0 || toAcct >= currUser.numAccounts()){
                System.out.println("Invalid account.Please try again.");
            }
        } while(toAcct<0 || toAcct >= currUser.numAccounts());      
        acctBal = currUser.getAcctBalance(toAcct);
        
        // the user can only deposit 5000 per transaction
        double maxDeposit = 5000.00;
        // get the amount to deposit
        do{
            System.out.printf("Enter the amount to deposit (max $%.02f): $",maxDeposit);
            amount = sc.nextDouble();
            if( amount <0 ){
                System.out.println("Amount must be greater than zero");                
            } else if( amount > maxDeposit){
                System.out.printf("The deposit amount must not exceed $%.02f.\n",maxDeposit);
            }
        } while(amount < 0 || amount>maxDeposit);
        
        // gobble up the rest of previous input
        sc.nextLine();
        
        // get the memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        
        // do the withdraw
        theBank.addAcctTransaction(toAcct, amount, memo);
        
    }
    
    /**
     * Process a fund withdraw from an account
     */
    public static void withdrawFunds(){
        
        // inits
        int fromAcct;        
        double amount;
        double acctBal;
        String acctType;
        String memo;
        
        // get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +"to withdraw from: ", currUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct<0 || fromAcct >= currUser.numAccounts()){
                System.out.println("Invalid account.Please try again.");
            }
        } while(fromAcct<0 || fromAcct >= currUser.numAccounts());      
        acctBal = currUser.getAcctBalance(fromAcct);
        acctType = currUser.getAcctType(fromAcct);
        double maxWithDraw;
        
        if(acctType.equals("Checking")){
            // the user can overdraw up to 500 less than 0
            maxWithDraw = acctBal + 500.00;
        } else {
            maxWithDraw = acctBal;
        }
        
        // get the amount to transfer
        do{
            System.out.printf("Enter the amount to withdraw (max $%.02f): $", maxWithDraw);
            amount = sc.nextDouble();
            if( amount <0 ){
                System.out.println("Amount must be greater than zero");                
            } else if( amount > maxWithDraw){
                System.out.printf("Insufficient fund.\nAmount must not be greater than\n"+
                        "balance of $%.02f.\n", maxWithDraw);
            }
        } while(amount < 0 || amount > maxWithDraw);
        
        // gobble up the rest of previous input
        sc.nextLine();
        
        // get the memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();
        
        // do the withdraw
        theBank.addAcctTransaction(fromAcct, -1*amount, memo);

    }
    
    /**
     * Process transferring funds from one account to another
     */
    public static void transferFunds(){
        
        // inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        
        // get the account to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +"to transfer from: ", currUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct<0 || fromAcct >= currUser.numAccounts()){
                System.out.println("Invalid account.Please try again.");
            }
        } while(fromAcct<0 || fromAcct >= currUser.numAccounts());      
        acctBal = currUser.getAcctBalance(fromAcct);
        
        // get the account to transfer to
        do{
            System.out.printf("Enter the number (1-%d) of the account\n" +"to transfer to: ", currUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct<0 || toAcct >= currUser.numAccounts()){
                System.out.println("Invalid account.Please try again.");
            }
        } while(toAcct<0 || toAcct >= currUser.numAccounts());      
        
        // get the amount to transfer
        do{
            System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
            amount = sc.nextDouble();
            if( amount <0 ){
                System.out.println("Amount must be greater than zero");                
            } else if( amount > acctBal){
                System.out.printf("Amount must not be greater than\n"+
                        "balance of $%.02f.\n", acctBal);
            }
        } while(amount < 0 || amount > acctBal);
        
        // finally, do the transfer
        theBank.addAcctTransaction(fromAcct, -1*amount, String.format(
                "Tranfer to account %s", currUser.getAcctUUID(toAcct)));
        theBank.addAcctTransaction(toAcct, amount, String.format(
                "Tranfer from account %s", currUser.getAcctUUID(fromAcct)));
    }
    
    /**
     * The User menu with which the customer interacts with
     */
    private static void userMenu(){
                
        
        // init
        int choice;
        
        // user menu
        do{
            System.out.printf("Welcome %s, what would you like to do?\n", currUser.getName());
            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Withdraw");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.println("Enter choice: ");
            choice = sc.nextInt();
            
            if(choice <1 || choice >5){
                System.out.println("Invalid choice. Please choose 1-5\n\n");                
            }
        } while(choice <1 || choice >5);
        
        // process the choice
        switch (choice){
            case 1:
                showTransHistory();
                break;
            case 2:
                withdrawFunds();
                break;
            case 3:
                depositFunds();
                break;
            case 4:
                transferFunds();
               break;
            case 5:
                // gobble up previous input
                sc.nextLine();
                break;
        }
        
        // redisplay this menu unless the user wants to quit
        if(choice != 5){
            userMenu();
        }
        
        
    }
    
    public static void main(String [] args){
        
        sc = new Scanner(System.in);
        String branch = "Uttara branch";
        
        theBank = new Bank(branch);
                
        currUser = CashMachineApp.login();
        
        if(currUser != null){
            System.out.println("Login successful.");
            
            // load all the accounts and transactions data
            theBank.loadData();
            acctSummary();
            userMenu();
        } 

        System.out.println("\n\n\nThank you for using our service.\nHave a good day!\n=========================================");
    }
}
