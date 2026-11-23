package banking.model;
public class InvestmentAccount extends Account implements InterestBearing{
    private double interestRate;
    private double minimumInitialDeposit;

    public InvestmentAccount(int customerID,String accountNumber,double balance,String branch,String accountType,String status,double interestRate,double minimumInitialDeposit){
         super(customerID,accountNumber,balance,branch,accountType,status);
         this.interestRate=interestRate;
         this.minimumInitialDeposit=minimumInitialDeposit;

    }

    public double getInterestRate(){
        return interestRate;
    }

    public void setInterestRate(double interestRate){
        this.interestRate=interestRate;
    }

    public double getMinimumInitialDeposit(){
        return minimumInitialDeposit;
    }

    public void setMinimumInitialDeposit(double minimumInitialDeposite){
        this.minimumInitialDeposit=minimumInitialDeposite;
    }
    @Override
    public double calculateInterest(){
        return 0.0;
    }
    @Override
    public void addInterest(){

    }
}