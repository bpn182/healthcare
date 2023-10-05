
package models;

/**
 *
 * @author bipin
 */
public class Staff {

    private int staffID;
    private String fullName;
    private String licence;
    private String password;
    private String phone;
    private String role;
    private String department;
    private String username;

    public Staff() {
    }

    public Staff(String fullName, String licence, String password, String phone, String role, String department, String username) {
        this.fullName = fullName;
        this.licence = licence;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.department = department;
        this.username = username;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return this.fullName;
    }

  
}
