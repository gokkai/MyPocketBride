package com.formation.appli.mypocketbride;

/**
 * Created by student on 11-07-17.
 */

public class Interact {
    private String name;
    private int id, companionId, userId;

    //region getter/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanionId() {
        return companionId;
    }

    public void setCompanionId(int companionId) {
        this.companionId = companionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    //endregion


    public Interact(String name, int companionId, int userId) {
        this.name = name;
        this.companionId = companionId;
        this.userId = userId;
    }
    public Interact(int id,String name, int companionId, int userId) {
        this.id=id;
        this.name = name;
        this.companionId = companionId;
        this.userId = userId;
    }
}
