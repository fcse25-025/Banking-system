package banking.controller;

public class LoginController {

    public boolean validateLogin(int userID,String password){
        if(userID <= 0||password == null|| password.isEmpty()){
            return false;
        }
        return true;
    }
}
