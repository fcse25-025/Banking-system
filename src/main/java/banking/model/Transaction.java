package banking.model;

import java.util.Date;

public class Transaction{
    private int transactionID;
    private  String accountNumber;
    private Date transactionDate;
    private double amount;
    private String transactionType;


    public Transaction(int transactionID,String accountNumber, Date transactionDate, double amount, String transactionType){
        this.transactionID=transactionID;
        this.accountNumber=accountNumber;
        this.transactionDate=transactionDate;
        this.amount=amount;
        this.transactionType=transactionType;
    }
    public int getTransactionID(){
        return transactionID;
    }
    public void setTransactionID(int transactionID){
        this.transactionID=transactionID;
    }
    public Date getTransactionDate(){
        return transactionDate;
    }
    public void setTransactionDate(Date transactionDate){
        this.transactionDate=transactionDate;
    }
    public double getAmount(){
        return amount;
    }
    public void setAmount(double amount){
        this.amount=amount;
    }
    public String getTransactionType(){
        return transactionType;
    }
    public void setTransactionType(String transactionType){
        this.transactionType=transactionType;
    }
    public  String getAccountNumber(){
        return  accountNumber;
    }
    public void setAccountNumber(String accountNumber){
        this.accountNumber=accountNumber;
    }
}