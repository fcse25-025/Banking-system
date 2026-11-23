package banking.model;
public class IndividualCustomer extends Customer{

    private String firstName;
    private String surName;
    private String nationalID;

    public IndividualCustomer(int customerID,String address,String phoneNumber,String email,String firstName,String surName,String nationalID){
        super(customerID,address,phoneNumber,email);

        this.firstName=firstName;
        this.surName=surName;
        this.nationalID=nationalID;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getSurName(){
        return surName;
    }
    public String getNationalID(){
        return nationalID;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public void setSurName(String surName){
        this.surName=surName;
    }
    public void setNationalID(String nationalID){
        this.nationalID=nationalID;
    }
}