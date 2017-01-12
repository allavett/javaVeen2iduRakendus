package com.allar.kodune;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;

import java.util.ArrayList;

/**
 * Created by allar.vendla on 10.01.2017.
 */
public class ChoiceBoxCustom extends ChoiceBox<String>{
    private final String name;
    private final String nextName;
    private final ChoiceBoxCustom previous;
    private final String sqlTable;
    private String sqlQuery;
    private String sqlQueryCondition;
    private ObservableList<String> setItemsWithDefaultSet;
    SelectionModel selection;

    public ChoiceBoxCustom(final String name, final String nextName, final ChoiceBoxCustom previous, final String table){
        this.name = name;
        this.nextName = nextName;
        this.previous = previous;
        this.sqlTable = table;
    }

    public String getName() {
        return name;
    }

    public ChoiceBoxCustom getPrevious() {
        return previous;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery() {

        this.sqlQuery = "SELECT DISTINCT " + name + " FROM " + this.sqlTable;
        if (selection != null) {
            if (previous != null) {
                if (previous.sqlQueryCondition == null) {
                    sqlQueryCondition = " WHERE " + name + "='" + selection.getSelectedItem() + "'";
                } else {
                    sqlQueryCondition = previous.sqlQueryCondition + " AND " + name + "='" + selection.getSelectedItem() + "'";
                }
                sqlQuery = sqlQuery + sqlQueryCondition;
            }
        }
    }

    public void setSetItemsWithDefaultItemAdded(ArrayList<String> items, String defaultItem) {
        items.add(0, defaultItem);
        this.setItems(FXCollections.observableArrayList(items));
    }

    public void resetSelection(){
        this.selection = this.getSelectionModel();
        this.setOnAction(event -> {

            System.out.println(selection.getSelectedItem());
            if (previous != null && previous.selection != null && previous.selection.getSelectedIndex() > 0){

                previous.selection.select(0);
            }
        });
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
