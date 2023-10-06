package models;

/**
 *
 * @author bipin
 */
import java.util.ArrayList;
import java.util.List;

public class Report {

    private int reportID;
    private List<String> data;

    // Constructor
    public Report(int reportID) {
        this.reportID = reportID;
        this.data = new ArrayList<>();
    }

    // Accessor and Mutator methods
    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Report [ID: " + reportID + ", Data Entries: " + data.size() + "]";
    }
}
