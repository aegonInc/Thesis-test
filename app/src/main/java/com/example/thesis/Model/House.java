package com.example.thesis.Model;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Danielle T. Quilang on 26/02/2018.
 */

public class House {
    public String user;
    public String author;

    public String housename;
    public String houseType;
    public String description;
    public String addressLat;
    public String addressLong;
    public String addressBarangay;
    public String permit;
    public String roomNumber;
    public String bathroomNumber;
    public  String houseRule;
    public String payment;
    public String paymentType;
    public String housePaymentRule;
    public String ameneties;
    public String kitchenNumber;
    public String bedNumber;

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public House() {
    }

    public House(String _user, String _author, String _housename, String _houseType, String _description, String _addressLat, String _addressLong, String _addressBarangay, String _permit, String _roomNumber, String _bathroomNumber, String _ameneties, String _houseRule, String _payment, String _paymentType, String _housePaymentRule, String _kitchenNumber, String _bedNumber) {
            user = _user;
            author = _author;

            housename = _housename;
            houseType = _houseType;
            description = _description;
            addressLat = _addressLat;
            addressLong = _addressLong;
            addressBarangay = _addressBarangay;
            permit = _permit;
            roomNumber = _roomNumber;
            bathroomNumber = _bathroomNumber;
            ameneties = _ameneties;
            houseRule = _houseRule;
            payment = _payment;
            paymentType = _paymentType;
            housePaymentRule = _housePaymentRule;
            kitchenNumber = _kitchenNumber;
            bedNumber = _bedNumber;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String _user) {
        user = _user;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String _author) {
        author = _author;
    }

    public String getHousename() {
        return housename;
    }
    public void setHousename(String _housename) {
        housename = _housename;
    }

    public String getHouseType() {
        return houseType;
    }
    public void setHouseType(String _houseType) {
        houseType = _houseType;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String _description) {
        description = _description;
    }

    public String getAddressLat() {
        return addressLat;
    }
    public void setAddressLat(String _addressLat) {
        addressLat = _addressLat;
    }

    public String getAddressLong() {
        return addressLong;
    }
    public void setAddressLong(String _addressLong) {
        addressLong = _addressLong;
    }

    public String getAddressBarangay() {
        return addressBarangay;
    }
    public void setAddressBarangay(String _addressBarangay) {
        addressBarangay = _addressBarangay;
    }

    public String getPermit() {
        return permit;
    }
    public void setPermit(String _permit) {
        permit = _permit;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String _roomNumber) {
        roomNumber = _roomNumber;
    }

    public String getBathroomNumber() {
        return bathroomNumber;
    }
    public void setBathroomNumber(String _bathroomNumber) {
        bathroomNumber = _bathroomNumber;
    }

    public String getHouseRule() {
        return houseRule;
    }
    public void setHouseRule(String _houseRule) {
        houseRule = _houseRule;
    }

    public String getAmeneties() {
        return ameneties;
    }
    public void setAmeneties(String _ameneties) {
        ameneties = _ameneties;
    }

    public String getPayment() {
        return payment;
    }
    public void setPayment(String _payment) {
        payment = _payment;
    }

    public String getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(String _paymentType) {
        paymentType = _paymentType;
    }

    public String getHousePaymentRule() {
        return housePaymentRule;
    }
    public void setHousePaymentRule(String _housePaymentRule) {
        housePaymentRule = _housePaymentRule;
    }
    public String getKitchenNumber() {
        return kitchenNumber;
    }
    public void setKitchenNumber(String _kitchenNumber) {
        kitchenNumber = _kitchenNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }
    public void setBedNumber(String _bedNumber) {
        bedNumber = _bedNumber;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("author", author);

        result.put("housename", housename);
        result.put("housetype", houseType);
        result.put("description", description);
        result.put("addressLat", addressLat);
        result.put("addressLong", addressLong);
        result.put("addressBarangay", addressBarangay);
        result.put("permit", permit);
        result.put("roomNumber", roomNumber);
        result.put("bathroomNumber", bathroomNumber);
        result.put("ameneties", ameneties);
        result.put("houseRule", houseRule);
        result.put("payment", payment);
        result.put("paymentType", paymentType);
        result.put("housePaymentRule", housePaymentRule);
        result.put("kitchenNumber", kitchenNumber);
        result.put("bedNumber", bedNumber);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }

}
