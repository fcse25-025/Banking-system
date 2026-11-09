package banking.model
public class ChequeAccount extends Account{
private String company;
private String companyAddress;

public ChequeAccount(String accountNumber,double balance,String branch,String accountType,String status,String company,String companyAddress){
     super(accountNumber,balance,branch,accountType,status);
     this.company=company;
     this.companyAddress=companyAddress;
}
public String getCompany(){
    return company;
}
public void setCompany(String company){
    this.company=company;
}
public String getCompanyAddress(){
    return companyAddress;
}
public void setCompanyAddress(String companyAddress){
    this.companyAddress=companyAddress
}

}