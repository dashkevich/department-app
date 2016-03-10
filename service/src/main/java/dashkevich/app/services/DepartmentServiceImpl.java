package dashkevich.app.services;

import dashkevich.app.dao.DepartmentDAO;
import dashkevich.app.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final static Logger log = LogManager.getLogger(DepartmentServiceImpl.class);

    private DepartmentDAO departmentDAO;

    @Autowired
    public DepartmentServiceImpl(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    public List<HashMap<String, String>> getDepartments() {
        log.debug("Service: get departments");
        return departmentDAO.getDepartments();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean addDepartment(String departmentName) {

        log.debug("Service: add department with name \"{}\"", departmentName);

        if(departmentDAO.ifExistDepartment(departmentName)){
            log.debug("Service: department with name\"{}\" already exist", departmentName);
            return false;
        }
        return departmentDAO.addDepartment(departmentName);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean deleteDepartment(int id) {
        log.debug("Service: delete department with id \"{}\"", id);
        return departmentDAO.deleteDepartment(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean updateDepartment(Department department) {
        log.debug("Service: update department:", department);
        return departmentDAO.updateDepartment(department);
    }
}
