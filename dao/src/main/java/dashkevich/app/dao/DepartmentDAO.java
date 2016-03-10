package dashkevich.app.dao;

import dashkevich.app.model.Department;

import java.util.HashMap;
import java.util.List;

public interface DepartmentDAO {

    boolean ifExistDepartment(String name);
    List<HashMap<String, String>> getDepartments();
    boolean addDepartment(String departmentName);
    boolean deleteDepartment(int id);
    boolean updateDepartment(Department department);

}
