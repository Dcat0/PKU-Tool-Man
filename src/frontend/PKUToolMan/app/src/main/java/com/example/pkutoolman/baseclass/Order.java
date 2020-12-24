package com.example.pkutoolman.baseclass;

public class Order {
    public int id, userID, toolmanID, state;
    public String place, destination, description;
    public Order (int _id, int _userID, int _toolmanID, String _place, String _destination, String _description) {
        id = _id;
        userID = _userID;
        toolmanID = _toolmanID;
        place = _place;
        destination = _destination;
        description = _description;
    }

}
