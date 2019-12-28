package com.example.adk37_daovannamproject02.view.main;

import com.example.adk37_daovannamproject02.model.ObjectACity;

import java.util.ArrayList;

public interface InterfaceMain {
    void loadfullCity(ArrayList<ObjectACity> objectACity);
    void loadACityForSearch(ObjectACity objectACity, int vitri);
    void loadByGPS(ObjectACity objectACity);
    void onMessenger(String mes);
}
