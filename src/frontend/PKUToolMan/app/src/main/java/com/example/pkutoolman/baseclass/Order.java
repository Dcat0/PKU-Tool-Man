package com.example.pkutoolman.baseclass;

public class Order {
    public static int id;
    public static int userID;
    public static int toolmanID;
    public static int state;
    public static String place, destination, description;
    public Order (int _id, int _userID, int _toolmanID, String _place, String _destination, String _description) {
        id = _id;
        userID = _userID;
        toolmanID = _toolmanID;
        place = _place;
        destination = _destination;
        description = _description;
    }

}
