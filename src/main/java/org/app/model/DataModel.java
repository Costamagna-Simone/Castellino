package org.app.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class DataModel {

    //lista delle fatture
    private final ObservableList<Fattura> fattureCorrenti;
    public DataModel()  {
        fattureCorrenti = FXCollections.observableArrayList(new ArrayList<>());
    }

    public ObservableList<Fattura> getFattureCorrenti() {
        return fattureCorrenti;
    }
}
