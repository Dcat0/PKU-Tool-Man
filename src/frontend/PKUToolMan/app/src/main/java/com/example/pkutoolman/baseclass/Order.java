package com.example.pkutoolman.baseclass;

import com.example.pkutoolman.ChatDatabase_sqlite;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    public int id, userID, toolmanID, state;
    public String place, destination, description, startTime, endTime;

    public Order (int _id, int _userID, int _toolmanID, String _place, String _destination, String _description) {
        id = _id;
        userID = _userID;
        toolmanID = _toolmanID;
        place = _place;
        destination = _destination;
        description = _description;
    }

    public Order (int _id, int _userID, int _toolmanID, String _endTime, int _state) {
        id = _id;
        userID = _userID;
        toolmanID = _toolmanID;
        endTime = _endTime.replace('T',' ');
        state = _state;
    }

    public Order (int _id, int _userID, int _toolmanID, String _place, String _destination, String _startTime,
                  String _endTime, String _description, int _state) {
        id = _id;
        userID = _userID;
        toolmanID = _toolmanID;
        place = _place;
        destination = _destination;
        startTime = _startTime.replace('T',' ');
        endTime = _endTime.replace('T',' ');
        description = _description;
        state = _state;
    }

}
