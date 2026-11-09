package banking.model;
public abstract class customer{
    private int customerID:
    private String address;
    private String  phoneNumber;
    private String email;

    public Customer(int customerID,String address,String phoneNumber, String email ){
        this.customerID=customerID;
        this.address=address;
        this.phoneNumber=phoneNumber;
        this.email=email;
    }

public int getCustomerID(){
    return customerID;
}
public String getAddress(){
    return address;
}
public String getPhoneNumber(){
    return phoneNumber;
}
public String getEmail(){
    return email;
}
public void setCustomerID(String customerID){
    this.customerID=customerID; 
}
public void setAddress(String address){
    this.address=address;
}
public void setPhoneNumber(String phoneNumber){
    this.phoneNumber=phoneNumber;
}
public void setEmail(String email){
    this.email=email;
}
} 