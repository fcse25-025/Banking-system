package banking.model
public class InvestmentAccount extends Account implements InterestBearing{
    private double interestRate;
    private double minimumInitialDeposite;

    public InvestmentAccount(String accountNumber,double balance,String branch,String accountType,String status,double interestRate,double minimumInitialDeposite){
         super(accountNumber,balance,branch,accountType,status);
         this.interestRate=interestRate;
         this.minimumInitialDeposite-minimumInitialDeposite;

    }

    public double getInterestRate(){
        return interestRate;
    }

    public void setInterestRate(double interestRate){
        this.interestRate=interestRate;
    }

    public double getMinimumInitialDeposit(){
        return minimumInitialDeposite;
    }

    public void setMinimumInitialDeposit(double minimumInitialDeposite){
        this.minimumInitialDeposite=minimumInitialDeposite;
    }
    @Override
    public double calculateInterest(){
        return 0.0
    }
    @Override
    public void addInterest(){

    }
}