package com.allar.kodune;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionModel;

import java.util.ArrayList;

/**
 * Created by allar.vendla on 10.01.2017.
 */
public class ChoiceBoxCustom extends ChoiceBox<String>{
    private final String name;
    private final ChoiceBoxCustom next;
    private final String sqlTable;
    private String previousSQLCondition;
    private String sqlQuery;
    private String sqlQueryCondition;
    private ObservableList<String> setItemsWithDefaultSet;
    SelectionModel selection;

    public ChoiceBoxCustom(final String name, final ChoiceBoxCustom next, final String table){
        this.name = name;
        this.next = next;
        this.sqlTable = table;
    }

    public String getName() {
        return name;
    }

    public ChoiceBoxCustom getNext() {
        return next;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery() {
        this.sqlQuery = "SELECT DISTINCT " + name + " FROM " + this.sqlTable;
        if (selection != null) {
            //if (next != null) {
                if (previousSQLCondition == null) {
                    sqlQueryCondition = " WHERE " + name + "='" + selection.getSelectedItem() + "'";
                } else {
                    this.sqlQuery = this.sqlQuery + previousSQLCondition;
                    sqlQueryCondition = previousSQLCondition + " AND " + name + "='" + selection.getSelectedItem() + "'";
                }
                //sqlQuery = sqlQuery + sqlQueryCondition;
            //}
        }
        setCurrentSQLQueryConditionForNext(sqlQueryCondition);
    }

    private void setCurrentSQLQueryConditionForNext(String sqlQueryCondition){
        if (next != null){
            next.previousSQLCondition = sqlQueryCondition;
        }
    }

    public void setSetItemsWithDefaultItemAdded(ChoiceBoxCustom choiceBoxCustom) {
        Database db = new Database();
        ArrayList<String> items = new ArrayList<>();
        if (previousSQLCondition == null || choiceBoxCustom.getSelectionModel().getSelectedIndex() > 0) {
            items = db.select(choiceBoxCustom.getName(), choiceBoxCustom.getSqlQuery());
        }
        items.add(0, "Vali");
        choiceBoxCustom.setItems(FXCollections.observableArrayList(items));
        choiceBoxCustom.getSelectionModel().select(0);
    }

    public void resetSelection(){
        setSqlQuery();
        setSetItemsWithDefaultItemAdded(this);
        this.selection = this.getSelectionModel();
        this.setOnAction(event -> {
            setSqlQuery();
            System.out.println(this.selection.getSelectedItem());
            if (this.next != null /* && next.selection != null &&next.selection.getSelectedIndex() > 0*/){
                this.next.setSqlQuery();
                setSetItemsWithDefaultItemAdded(this.next);
                //next.selection.select(0);
                //next.setDisabled(true);
            }
            if (this.next == null){
                setSetItemsWithDefaultItemAdded(this);
            }
        });
        if(this.next != null) {
            this.next.resetSelection();
        }
    }
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChoiceBoxCustom that = (ChoiceBoxCustom) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (next != null ? !next.equals(that.next) : that.next != null) return false;
        return sqlQuery != null ? sqlQuery.equals(that.sqlQuery) : that.sqlQuery == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (next != null ? next.hashCode() : 0);
        result = 31 * result + (sqlQuery != null ? sqlQuery.hashCode() : 0);
        return result;
    }

    // TODO!!! Not sure if I need this????
    @Override
    public String toString() {
        return "ChoiceBoxCustom{" +
                "name='" + name + '\'' +
                ", previousSQLCondition=" + previousSQLCondition +
                ", next=" + next +
                ", sqlQuery='" + sqlQuery + '\'' +
                '}';
    }
    */
}
