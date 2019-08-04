package com.mike4christ.storexapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mike4christ.storexapp.Constant;

public class UserPreferences {
    private SharedPreferences sharedPreferences;
    private Editor editor;
    private Context _context;

    @SuppressLint({"CommitPrefEdits"})
    public UserPreferences(Context _context) {
        this._context = _context;
        sharedPreferences = _context.getSharedPreferences(Constant.USER_PREF, Constant.PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

   

    public void setUserLogged(boolean usLg) {
        editor.putBoolean(Constant.IS_USER_LOGGED, usLg);
        editor.commit();
    }

    public boolean isUserLogged() {
        return sharedPreferences.getBoolean(Constant.IS_USER_LOGGED, false);
    }

    public void setFacebookToken(String facebookToken) {
        editor.putString(Constant.FACEBOOK_TOKEN, facebookToken);
        editor.commit();
    }

    public String getColorAtrribute() {
        return sharedPreferences.getString(Constant.COLOR_ATTR, "");
    }

    public void setColorAtrribute(String color) {
        editor.putString(Constant.COLOR_ATTR, color);
        editor.commit();
    }

    public String getTotalAmount() {
        return sharedPreferences.getString("Total_Amount", "");
    }

    public void setTotalAmount(String totalAmount) {
        editor.putString("Total_Amount", totalAmount);
        editor.commit();
    }

    public String getSizeAttribute() {
        return sharedPreferences.getString(Constant.SIZE_ATTR, "");
    }

    public void setSizeAttribute(String size) {
        editor.putString(Constant.SIZE_ATTR, size);
        editor.commit();
    }


    public String getFacebookToken() {
        return sharedPreferences.getString(Constant.FACEBOOK_TOKEN, "");
    }


    public String getString(String key, String defaultValue) {
        sharedPreferences = _context.getSharedPreferences(Constant.USER_PREF, 0);
        return sharedPreferences.getString(key, defaultValue);
    }
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(Constant.IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(Constant.IS_FIRST_TIME_LAUNCH, true);
    }

    public void setSentSuccess(boolean isSentSuccess) {
        editor.putBoolean(Constant.IS_SENT_SUCCESS, isSentSuccess);
        editor.commit();
    }

    public boolean isSentSuccess() {
        return sharedPreferences.getBoolean(Constant.IS_SENT_SUCCESS, false);
    }

    public void saveString(String key, String value) {
        sharedPreferences = _context.getSharedPreferences(Constant.USER_PREF, 0);
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public void setCustomerId(int customer_id) {
        editor.putInt("customer_id", customer_id);
        editor.commit();
    }

    public Integer getCustomerId() {
        return sharedPreferences.getInt("customer_id", 0);
    }

    public void setUserName(String name) {
        editor.putString("name", name);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString("name", "");
    }

    public void setUserEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    public String getUserEmail() {
        return sharedPreferences.getString("email", "");
    }

    public void setUserAddr1(String addr1) {
        editor.putString("address_1", addr1);
        editor.commit();
    }

    public String getUserAddr1() {
        return sharedPreferences.getString("address_1", "");
    }


    public void setUserFirstname(String firstname) {
        editor.putString("firstname", firstname);
        editor.commit();
    }

    public String getUserFirstname() {
        return sharedPreferences.getString("firstname", "");
    }

    public void setUserLastname(String lastname) {
        editor.putString("lastname", lastname);
        editor.commit();
    }

    public String getUserLastname() {
        return sharedPreferences.getString("lastname", "");
    }

    public void setUserState(String userState) {
        editor.putString("userState", userState);
        editor.commit();
    }

    public String getUserState() {
        return sharedPreferences.getString("userState", "");
    }


    public void setUserAddr2(String addr2) {
        editor.putString("address_2", addr2);
        editor.commit();
    }

    public String getUserAddr2() {
        return sharedPreferences.getString("address_2", "");
    }


    public void setUserCity(String city) {
        editor.putString("city", city);
        editor.commit();
    }

    public String getUserCity() {
        return sharedPreferences.getString("city", "");
    }



    public void setUserRegion(String region) {
        editor.putString("region", region);
        editor.commit();
    }

    public String getUserRegion() {
        return sharedPreferences.getString("region", "");
    }

    public void setUserPostalCode(String postalCode) {
        editor.putString("postal_code", postalCode);
        editor.commit();
    }


    public String getUserPostalCode() {
        return sharedPreferences.getString("postal_code", "");
    }


    public void setUserCountry(String country) {
        editor.putString("country", country);
        editor.commit();
    }

    public String getUserCountry() {
        return sharedPreferences.getString("country", "");
    }

    public void setUserShippingRegionId(int shippingRegionId) {
        editor.putInt("shipping_region_id", shippingRegionId);
        editor.commit();
    }

    public Integer getUserShippingRegionId() {
        return sharedPreferences.getInt("shipping_region_id", 0);
    }

    public void setUserDayPhone(String dayPhone) {
        editor.putString("day_phone", dayPhone);
        editor.commit();
    }

    public String getUserDayPhone() {
        return sharedPreferences.getString("day_phone", "");
    }

    public void setUserEvePhone(String evePhone) {
        editor.putString("eve_phone", evePhone);
        editor.commit();
    }

    public String getUserEvePhone() {
        return sharedPreferences.getString("eve_phone", "");
    }

    public void setUserMobPhone(String mobPhone) {
        editor.putString("mob_phone", mobPhone);
        editor.commit();
    }

    public String getUserMobPhone() {
        return sharedPreferences.getString("mob_phone", "");
    }

    public void setUserAccessToken(String accessToken) {
        editor.putString("accessToken", accessToken);
        editor.commit();
    }

    public String getUserAccessToken() {
        return sharedPreferences.getString("accessToken", "");
    }

    public void setUserExpiresIn(String expiresIn) {
        editor.putString("expires_in", expiresIn);
        editor.commit();
    }

    public String getUserExpiresIn() {
        return sharedPreferences.getString("expires_in", "");
    }


    public void setUserOrderId(String orderId) {
        editor.putString("orderId", orderId);
        editor.commit();
    }

    public String getUserOrderId() {
        return sharedPreferences.getString("orderId", "");
    }

    public void setUserOrderSize(int orderSize) {
        editor.putInt("orderSize", orderSize);
        editor.commit();
    }

    public String getUserLastOrder() {
        return sharedPreferences.getString("lastOrder", "");
    }


    public void setUserLastOrder(String lastOrder) {
        editor.putString("lastOrder", lastOrder);
        editor.commit();
    }

    public String getUserLastOrderId() {
        return sharedPreferences.getString("lastOderId", "");
    }


    public void setUserLastOrderId(String lastOderId) {
        editor.putString("lastOderId", lastOderId);
        editor.commit();
    }

    public int getUserOrderSize() {
        return sharedPreferences.getInt("orderSize", 0);
    }


    public void setUserCartId(String userCartId) {
        editor.putString("cart_id", userCartId);
        editor.commit();
    }

    public String getUserCartId() {
        return sharedPreferences.getString("cart_id", "");
    }


    public void setUserCartSize(int cartSize) {
        editor.putInt("cartSize", cartSize);
        editor.commit();
    }

    public int getUserCartSize() {
        return sharedPreferences.getInt("cartSize", 0);
    }


    public void setShippingCost(String shippingCost) {
        editor.putString("shippingCost", shippingCost);
        editor.commit();
    }

    public String getShippingCost() {
        return sharedPreferences.getString("shippingCost", "");
    }
















}
