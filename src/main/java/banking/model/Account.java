package banking.model;
public abstract class account{
    private String accountNumber;
    private double balance;
    private String branch;
    private String accountType;
    private String status;

    public Account(String accountNumber,double balance,String branch,String accountType,String status){
        this.accountNumber=accountNumber;
        this.balance=balance;
        this.branch=branch;
        this.accountType=accountType;
        this.status=status;
    }
  public String getAccountNumber(){
    return accountNumber;
  }
  public double getBalance(){
    return balance;
  }
  public String getBranch(){
    return branch;
  }
  public String getAccountType(){
    return accountType;
  }
  public String getStatus(){
    return status;
  }
  public void setAccountNumber(String accountNumber){
    this.accountNumber=accountNumber;
  }
  public void setBalance(double balance){
    this.balance=balance;
  }
  public void setBranch(String branch){
     this.branch=branch;
  }
  public void setAccountType(String accountType){
    this.accountType=accountType;
  }
  public void setStatus(String status){
    this.status=status;
  }
}