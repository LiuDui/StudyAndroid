package com.example.loginandforceoffline;

public class AccountUtil {
    public static boolean isVild(String account, String password){
        return "admin".equals(account) && "123456".equals(password);
    }
}
