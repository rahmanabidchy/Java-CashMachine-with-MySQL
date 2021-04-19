
package com.abid.atm.bank;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


public class DBManager {
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String CONN = "jdbc:mysql://localhost/bank";
    
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    
    private User currUser;
    private ArrayList<Account> userAccts;
    private ArrayList<Transaction> acctTrans;
        
    
    /**
     * Retrieves an user from the database
     * Return an User object if UUID and PIN entered correctly
     * 
     * @param UUID  UUID of the user
     * @param PIN   PIN of the user
     * @return      return an User object with the given UUID/PIN combo
     */
    public User getUser(String UUID, String PIN){
        
        String SQL = "SELECT * FROM user WHERE UUID =";       
        
        try{
            conn = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
            //System.out.println("Database connected");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(SQL+" "+UUID);
            
            //init a User
            
            rs.next();           
            if(PIN.equals(rs.getString("PIN"))){                                
                currUser = new User(rs.getString("UUID"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("email"));
            }
            
                                
        } catch(SQLException e){
            System.err.print(e);
        } finally {
            
            try{
        
                if(rs!=null){
                    rs.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }                
        
        return currUser;
        
    }
    
    /**
     * Retrieves all the accounts of a particular user from the database
     * @param UUID  ID of the user
     * @return      An ArrayList of all the accounts
     */
    public ArrayList<Account> getAccounts(String UUID){
        
        userAccts = new ArrayList();
        String SQL = "SELECT * FROM account WHERE holderUUID =";
                
        
        try{
            conn = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
            //System.out.println("Database connected");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(SQL+" "+UUID);
            
            // init accounts ArrayList
            while(rs.next()){
                Account acct = new Account(rs.getString("UUID"),rs.getString("holderUUID"),rs.getString("acctType"));
                userAccts.add(acct);

            }
        } catch(SQLException e){
            System.err.print(e);
        } finally {
            
            try{
        
                if(rs!=null){
                    rs.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }                
        
        return userAccts;

    }
    
    /**
     * Retrieves all the transaction associated with a particular account from the database
     * @param UUID  ID of the account
     * @return      An ArrayList of all the transactions
     */
    public ArrayList<Transaction> getTransactions(String UUID){
        
        String SQL = "SELECT * FROM transaction WHERE acctUUID =";
        acctTrans = new ArrayList();
        
        try{
            conn = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
            //System.out.println("Database connected");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(SQL+" "+UUID);
            
            // init accounts ArrayList
            while(rs.next()){                               
                Transaction trans = new Transaction(rs.getString("acctUUID"),rs.getDouble("amount"),rs.getString("memo"),""+rs.getDate("date")+" "+rs.getTime("date"));
                acctTrans.add(trans);
            }
        } catch(SQLException e){
            System.err.print(e);
        } finally {
            
            try{
        
                if(rs!=null){
                    rs.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }                
        
        return acctTrans;

    }
    
    
    public void updateTransactions(String acctUUID, double amount, String memo,String date){
        
        String SQL = "INSERT INTO `transaction` (`acctUUID`,`amount`,`memo`,`date`) VALUES (?,?,?,?)";
        
        try(
                Connection conn = DriverManager.getConnection(CONN, USERNAME, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(SQL);
                ){
            
            stmt.setString(1, acctUUID);
            stmt.setDouble(2, amount);
            stmt.setString(3, memo);            
            stmt.setTimestamp(4, Timestamp.valueOf(date));
            
            
            stmt.execute();
            
        } catch(SQLException ex){
            System.out.println("Error updating transaction");
        }
    }
}
