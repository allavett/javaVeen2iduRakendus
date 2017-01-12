package com.allar.kodune;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

/**
 * Created by allar.vendla on 10.01.2017.
 */
public class ChoiceBoxCustom extends ChoiceBox<String>{
    private String name;
    private ChoiceBoxCustom previous;
    private String nextName;
    private String sqlQuery;
    private ObservableList<String> setItemsWithDefaultSet;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ChoiceBoxCustom getPrevious() {
        return previous;
    }
    public void setPrevious(ChoiceBoxCustom previous) {
        this.previous = previous;
    }
    public String getNextName() {
        return nextName;
    }
    public void setNextName(String nextName) {
        this.nextName = nextName;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String table) {
        if (this.nextName == null){
            if (this.previous == null){
                this.sqlQuery = "SELECT " + this.name + " FROM " + table;
            } else {
                if (this.previous.sqlQuery.contains("WHERE")){
                    this.sqlQuery = this.previous.sqlQuery + " AND " + this.name + "='" + this.getSelectionModel().getSelectedItem() +"'";
                } else {
                    this.sqlQuery = this.previous.sqlQuery + " WHERE " + this.name + "='" + this.getSelectionModel().getSelectedItem() +"'";
                }
            }
        }else{
            if (this.previous == null){
                this.sqlQuery = "SELECT " + this.nextName + " FROM " + table + " WHERE " + this.name + "='"
                        + this.getSelectionModel().getSelectedItem() +"'";
            } else {
                if (this.previous.sqlQuery.contains("WHERE")){
                    this.sqlQuery = this.previous.sqlQuery + " AND " + this.name + "='" + this.getSelectionModel().getSelectedItem() +"'";
                } else {
                    this.sqlQuery = this.previous.sqlQuery + " WHERE " + this.name + "='" + this.getSelectionModel().getSelectedItem() +"'";
                }
            }
        }
    }

    public void setSetItemsWithDefaultItemAdded(ArrayList<String> items, String defaultItem) {
        items.add(0, defaultItem);
        this.setItems(FXCollections.observableArrayList(items));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChoiceBoxCustom that = (ChoiceBoxCustom) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nextName != null ? !nextName.equals(that.nextName) : that.nextName != null) return false;
        return sqlQuery != null ? sqlQuery.equals(that.sqlQuery) : that.sqlQuery == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (nextName != null ? nextName.hashCode() : 0);
        result = 31 * result + (sqlQuery != null ? sqlQuery.hashCode() : 0);
        return result;
    }

    // TODO!!! Not sure if I need this????
    @Override
    public String toString() {
        return "ChoiceBoxCustom{" +
                "name='" + name + '\'' +
                ", previous=" + previous +
                ", nextName=" + nextName +
                ", sqlQuery='" + sqlQuery + '\'' +
                '}';
    }
}
