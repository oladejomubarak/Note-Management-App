package com.example.notemanagement.validators;

public class UserValidator {
    public static boolean isValidEmail(String email){
        return email.contains("@");
    }
    public static boolean isValidPassword(String password){
        return password.matches("[a-zA-Z0-9+-£$%^&*()?~@#+]{8,20}");
    }
    public static boolean isValidPhoneNumber(String phoneNumber){
        for (int i = 0; i < phoneNumber.length(); i++) {
            if(!Character.isDigit(phoneNumber.charAt(i))) throw new RuntimeException("phone number can only be digits");
        }
        return phoneNumber.length() == 11;
    }
}
