package com.formation.appli.mypocketbride;

/**
 * Created by student on 03-07-17.
 */

public class Messages {
    private String text;
    private int adressContext, behaviour,id;//Context will be attribute int 1 for home, 2 for work, etc...

    //region getter/setter

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAdressContext() {
        return adressContext;
    }

    public void setAdressContext(int adressContext) {
        this.adressContext = adressContext;
    }

    public int getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(int behaviour) {
        this.behaviour = behaviour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //endregion


    public Messages(String text, int adressContext, int behaviour) {
        this.text = text;
        this.adressContext = adressContext;
        this.behaviour = behaviour;
    }
    public Messages(int id,String text, int adressContext, int behaviour) {
        this.id=id;
        this.text = text;
        this.adressContext = adressContext;
        this.behaviour = behaviour;
    }
}
