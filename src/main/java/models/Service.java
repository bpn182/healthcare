package models;

/**
 *
 * @author bipin
 */
public class Service  {

    private int serviceID;
    private String serviceName;
    private String department;
    private String duration;
    private String cost;

    public Service() {
    }

    public Service(String serviceName, String department, String duration, String cost) {
        this.serviceName = serviceName;
        this.department = department;
        this.duration = duration;
        this.cost = cost;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return this.serviceName;
    }

}
