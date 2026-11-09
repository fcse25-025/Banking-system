package banking.model
public class BankEmployee{
    private int employeeID;
    private String firstName;
    private String surName;
    private String phoneNumber;

    public BankEmployee(int employeeID,String firstName,String surName,String phoneNumber){
        this.employeeID=employeeID;
        this.firstName=firstName;
        this.surName=surName;
        this.phoneNumber=phoneNumber;
    }
    public int getEmployeeID(){
        return employeeID;
    }
    public void setEmployeeID(int employeeID){
        this.employeeID=employeeID;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public String getSurName(){
        return surName ;
    }
    public void setSurName(String surName){
        this.surName=surName;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }
}