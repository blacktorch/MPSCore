package com.chidiebere.nodes;

public enum Participants {

    SUBSCRIBER(0x231),
    PUBLISHER(0x232),
    MPS_BUS(0x233),
    ;

    private final int id;

    Participants(int idNumber){
        this.id = idNumber;
    }

    public int getId() {
        return id;
    }
}
