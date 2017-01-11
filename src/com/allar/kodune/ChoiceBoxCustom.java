package com.allar.kodune;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

/**
 * Created by allar.vendla on 10.01.2017.
 */
public class ChoiceBoxCustom extends ChoiceBox<String>{
    private String name;
    private ChoiceBoxCustom previous;
    private String nextName;
    private String sqlQuery;
    private String sqlTable;
    private ObservableList<String> selectionItems;


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


    private String getSqlTable(){
        if (this.sqlTable.isEmpty() && !this.previous.name.isEmpty()){
            this.setSqlTable(this.previous.getSqlTable());
        }
        return this.sqlTable;
    }
    public void setSqlTable(String sqlTable) {
        this.sqlTable = sqlTable;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery() {
        if (this.nextName == null){
            if (this.previous.getName() == null){
                this.sqlQuery = "SELECT " + this.name + " FROM " + this.sqlTable;
            } else {
                if (this.previous.sqlQuery.contains("WHERE")){
                    this.sqlQuery = this.previous.sqlQuery + " AND " + this.name + "='" + this.getSelectionModel().getSelectedItem() +"'";
                } else {
                    this.sqlQuery = this.previous.sqlQuery + " WHERE " + this.name + "='" + this.getSelectionModel().getSelectedItem() +"'";
                }
            }
        }else{
            if (this.previous.getName() == null){
                this.sqlQuery = "SELECT " + this.nextName + " FROM " + this.sqlTable + " WHERE " + this.name + "='"
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

    private ObservableList<String> getSelectionItems() {
        return selectionItems;
    }

    private void setSelectionItems(ObservableList<String> selectionItems) {

        this.selectionItems = selectionItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChoiceBoxCustom that = (ChoiceBoxCustom) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (previous != null ? !previous.equals(that.previous) : that.previous != null) return false;
        if (nextName != null ? !nextName.equals(that.nextName) : that.nextName != null) return false;
        if (sqlQuery != null ? !sqlQuery.equals(that.sqlQuery) : that.sqlQuery != null) return false;
        return sqlTable != null ? sqlTable.equals(that.sqlTable) : that.sqlTable == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (previous != null ? previous.hashCode() : 0);
        result = 31 * result + (nextName != null ? nextName.hashCode() : 0);
        result = 31 * result + (sqlQuery != null ? sqlQuery.hashCode() : 0);
        result = 31 * result + (sqlTable != null ? sqlTable.hashCode() : 0);
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
                ", sqlTable='" + sqlTable + '\'' +
                '}';
    }
}
