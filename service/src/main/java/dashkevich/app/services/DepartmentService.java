package dashkevich.app.services;

import dashkevich.app.model.Department;

import java.util.HashMap;
import java.util.List;

public interface DepartmentService{

    List<HashMap<String, String>> getDepartments();
    boolean addDepartment(String departmentName);
    boolean deleteDepartment(int id);
    boolean updateDepartment(Department department);

}
