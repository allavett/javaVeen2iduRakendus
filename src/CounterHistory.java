import com.allar.kodune.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AllarVendla on 27.01.2017.
 */
public class CounterHistory {
    private Error error = new Error();
    ObservableList<Data<String, Number>> data = FXCollections.observableArrayList();

    public ObservableList<Data<String, Number>> getData() {
        return data;
    }

    public String getErrorText(){
        return error.getError();
    }
    public void getCounters(LocalDate startDate, LocalDate endDate, String username){
        error.setError("");
        if (startDate != null && endDate != null) {

            if (startDate.isBefore(endDate)) {
                String sql = "SELECT date, counter FROM counters WHERE user_id=(SELECT user_id FROM users WHERE username='"
                        + username + "') AND DateTime(date) >= '" + startDate + "' AND DateTime(date) <= '" + endDate
                        + "' ORDER BY date";
                Database db = new Database();
                ArrayList<String> dates = new ArrayList<>(db.select("date", sql));
                ArrayList<String> counters = new ArrayList<>(db.select("counter", sql));
                if (!dates.isEmpty() && dates.size() == counters.size()) {
                    HashMap<String, Number> test = new HashMap<>();
                    for (int i = 0; i < dates.size(); i++) {
                        String date = dates.get(i);
                        int counter;
                        if (i > 0) {
                            counter = Integer.parseInt(counters.get(i)) - Integer.parseInt(counters.get(i-1));
                        } else {
                            counter = Integer.parseInt(counters.get(i));
                        }

                        // Järgmine osa on võetud: http://stackoverflow.com/questions/34529956/sorting-x-axis-in-chart-by-the-dates-javafx
                        // Väga täpselt ei saa aru, mis siin tehakse :\
                        Data<String, Number> dataAtDate = data.stream()
                                .filter(data -> data.getXValue().equals(date))
                                .findAny()
                                .orElseGet(()->{
                                   Data<String, Number> newData = new Data<>(date, 0);
                                    data.add(newData);
                                    return newData;
                                });
                        dataAtDate.setYValue(dataAtDate.getYValue().doubleValue() + counter);
                    }
                    System.out.println(data);
                } else {
                    error.setError("Sellest ajavahemikust ei leitud ühtegi näidu sisestust!");
                }
            } else {
                error.setError("Lõppkuupäev enne alguskuupäeva!");
            }
        } else {
            error.setError("Vali ajavahemik!");
        }
    }
}
