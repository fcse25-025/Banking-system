package banking.model
public class CompanyCustomer extends Customer{
    private String companyName;
    private String registrationNumber;

    public CompanyCustomer(int customerID,String address,String phoneNumber,String email,String companyName,String registrationNumber){
         super(customerID,address,phoneNumber,email);

         this.companyName=companyName;
         this.registrationNumber=registrationNumber;
    }
    public String getCompanyName(){
        return companyName;
    }
    public String getRegistrationNumber(){
        return registrationNumber;
    }
    public void setCompanyName(String companyName){
        this.companyName=companyName;
    }
    public void setRegistrationNumber(String registrationNumber){
        this.registrationNumber=registrationNumber;
    }
}