package com.allar.kodune;

import javafx.collections.FXCollections;
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

    public String getSqlQueryCondition() {
        return sqlQueryCondition;
    }

    private String sqlQueryCondition;
    private SelectionModel selection;

    public ChoiceBoxCustom(final String name, final ChoiceBoxCustom next, final String table, boolean disable){
        this.name = name;
        this.next = next;
        this.sqlTable = table;
        this.setDisable(disable);
        setSelection();
    }

    private String getName() {
        return name;
    }

    private String getSqlQuery() {
        return this.sqlQuery;
    }

    private void setSqlQuery() {
        this.sqlQuery = "SELECT DISTINCT " + this.name + " FROM " + this.sqlTable;
        if (this.selection != null) {
            if (this.previousSQLCondition == null) {
                this.sqlQueryCondition = " WHERE " + name + "='" + selection.getSelectedItem() + "'";
            } else {
                this.sqlQuery = this.sqlQuery + this.previousSQLCondition;
                this.sqlQueryCondition = this.previousSQLCondition + " AND " + this.name + "='" + this.selection.getSelectedItem() + "'";
            }

        }
        if (next != null){
            next.previousSQLCondition = this.sqlQueryCondition;
        } else {
            System.out.println(this.sqlQuery);
        }
    }

    private void setSetItemsWithDefaultItemAdded(ChoiceBoxCustom choiceBoxCustom) {
        ArrayList<String> items = new ArrayList<>();
        if (!choiceBoxCustom.isDisabled()) {
            Database db = new Database();
            items = db.select(choiceBoxCustom.getName(), choiceBoxCustom.getSqlQuery());
        }
        items.add(0, "Vali");
            choiceBoxCustom.setItems(FXCollections.observableArrayList(items));
        if(choiceBoxCustom.getSelectionModel().getSelectedIndex() != 0) {
            choiceBoxCustom.getSelectionModel().select(0);
        }
    }

    private void setSelection(){
        setSqlQuery();
        setSetItemsWithDefaultItemAdded(this);
        this.selection = this.getSelectionModel();
        this.setOnAction( event -> {
            if(!this.isDisabled()) {
                System.out.println("setOnAction: " + this.getName());
                setSqlQuery();
                if (this.next != null) {

                    if (this.selection.getSelectedIndex() <= 0) {
                        this.next.setDisable(true);
                        setSetItemsWithDefaultItemAdded(this.next);
                    } else {
                        this.next.setDisable(false);
                    }
                    if (!this.next.isDisabled()) {
                        this.next.selection.clearSelection();
                        setSetItemsWithDefaultItemAdded(this.next);
                    }
                    resetSelection(this.next);
                }
            }
        });
    }

    private void resetSelection(ChoiceBoxCustom choiceBoxCustom){
        if (choiceBoxCustom.next != null) {
            choiceBoxCustom.next.setDisable(true);
            setSetItemsWithDefaultItemAdded(choiceBoxCustom.next);
            resetSelection(choiceBoxCustom.next);
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
