package com.the43appmart.nfc.kfc;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class AddMoneyData {
    private  String userId;
    private  String Name;
    private String CardNumber;
    private String CVV;
    private String Expiry;
    private String Brand;

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return Name;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public String getCVV() {
        return CVV;
    }

    public String getExpiry() {
        return Expiry;
    }

    public String getBrand() {
        return Brand;
    }

    public AddMoneyData(String userId, String name, String cardNumber, String cvv, String expiry, String brand) {
        this.userId=userId;
        this.Name=name;
        this.CardNumber=cardNumber;
        this.CVV=cvv;

        this.Expiry=expiry;
        this.Brand=brand;



    }


}