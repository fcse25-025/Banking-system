package banking.model;
public class Manager{
    private int managerID;
    private String firstName;
    private String surName;
    private String branch;

    public Manager(int managerID,String firstName,String surName,String branch){
        this.managerID=managerID;
        this.firstName=firstName;
        this.surName=surName;
        this.branch=branch;
    }
    public int getManagerID(){
        return managerID;
    }
    public void setManagerID(int managerID){
        this.managerID=managerID;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName=firstName;
    }
    public String getSurName(){
        return surName;
    }
    public void setSurName(String surName){
        this.surName=surName;
    }
    public String getBranch(){
        return branch;
    }
    public void setBranch(String branch){
        this.branch=branch;
    }
}