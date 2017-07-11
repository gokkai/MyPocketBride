package com.formation.appli.mypocketbride;

/**
 * Created by student on 03-07-17.
 */

public class Companion {
    //private String name;
    private int sex,behaviour,id; // Sex is 1 for male and 2 for female in all the app,
                                  //Behaviour will be coming and deal with using int
                                  //id will be used when the DB will be implement

    //region getter/setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(int behaviour) {
        this.behaviour = behaviour;
    }

   /* public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    //endregion


    public Companion( int sex,int behaviour) {
        //this.name = name;
        this.sex = sex;
        this.behaviour=behaviour;
    }
    public Companion(int id, int sex,int behaviour) {
        //this.name = name;
        this.id=id;
        this.sex = sex;
        this.behaviour=behaviour;
    }
}
