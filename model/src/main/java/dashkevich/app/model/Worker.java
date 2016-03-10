package dashkevich.app.model;

import java.sql.Date;

public class Worker {
    private int id;
    private int departmentID;
    private String name;
    private Date birthday;
    private int salary;

    public Worker(){

    }

    public Worker(int departmentID, String name, Date birthday, int salary){
        this.departmentID = departmentID;
        this.name = name;
        this.birthday = birthday;
        this.salary = salary;
    }

    public long getDepartmentID() {
        return departmentID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;

        Worker worker = (Worker) o;

        if (getId() != worker.getId()) return false;
        if (getDepartmentID() != worker.getDepartmentID()) return false;
        if (getSalary() != worker.getSalary()) return false;
        if (!getName().equals(worker.getName())) return false;
        return getBirthday().equals(worker.getBirthday());

    }

    public static boolean isValid(Worker worker){

        if(worker.getId() < 0) return false;
        if(worker.getName() == null) return false;
        if(worker.getName().length() == 0) return false;
        if(worker.getSalary() <= 0) return false;
        if(worker.getBirthday() == null) return false;
        if(worker.getBirthday().compareTo(new java.util.Date()) > 0) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", departmentID=" + departmentID +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", salary=" + salary +
                '}';
    }
}
