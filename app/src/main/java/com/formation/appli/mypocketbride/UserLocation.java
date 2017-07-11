package com.formation.appli.mypocketbride;

/**
 * Created by student on 11-07-17.
 */

public class UserLocation {
    private int addressContext, id, userId,addressId; //Context will be attribute int 1 for home, 2 for work, etc...

    //region getter/setter
    public void setAddressContext(int addressContext) {
        this.addressContext = addressContext;
    }

    public int getAddressContext() {
        return addressContext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    //endregion


    public UserLocation(int addressContext, int userId, int addressId) {
        this.addressContext = addressContext;
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
    }
    public UserLocation(int id, int addressContext, int userId, int addressId) {
        this.id=id;
        this.addressContext = addressContext;
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
    }
}
