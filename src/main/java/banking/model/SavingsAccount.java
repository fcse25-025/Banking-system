package banking.model
public class SavingsAccount extends Account implements InterestBearing{
    private double interestRate;

    public SavingsAccount(String accountNumber,double balance,String branch,String accountType,String status,double interestRate){
        super(accountNumber,balance,branch,accountType,status);

        this.interestRate=interestRate;
    }
    public double getInterestRate(){
        return interestRate;
    }
    public void setInterestRate(double interestRate){
        this.interestRate=interestRate;
    }
    @Override
    public double calculateInterest(){
        return 0.0
    }
    @Override
    public void addInterest(){

    }
}