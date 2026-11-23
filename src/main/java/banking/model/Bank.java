package banking.model;
public class Bank{
    private int bankID;
    private String name;
    private String branch;
    private String address;

    public Bank(int bankID,String name,String branch,String address){
        this.bankID=bankID;
        this.name=name;
        this.branch=branch;
        this.address=address;
    }
    
    public int getBankID(){
        return bankID;
    }
    public void setBankID(int bankID){
        this.bankID=bankID;
    }
    public String  getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getBranch(){
        return branch;
    }
    public void setBranch(String branch){
        this.branch=branch;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address=address;
    }
}