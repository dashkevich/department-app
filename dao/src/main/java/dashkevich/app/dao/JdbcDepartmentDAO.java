package dashkevich.app.dao;

import dashkevich.app.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *  This class implements DepartmentDAO interface and using SQL for data access.
 */
@Component
public class JdbcDepartmentDAO extends JdbcDaoSupport implements DepartmentDAO {

    private final static Logger log = LogManager.getLogger(JdbcDepartmentDAO.class);

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${if_exist_department}')).inputStream)}")
    public String IF_EXIST_DEPARTMENT;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_departments}')).inputStream)}")
    public String GET_DEPARTMENTS;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_department}')).inputStream)}")
    public String UPDATE_DEPARTMENT;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${add_department}')).inputStream)}")
    public String ADD_DEPARTMENT;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${delete_department}')).inputStream)}")
    public String DELETE_DEPARTMENT;

    public boolean ifExistDepartment(String name) {
        log.debug("check department with name \"{}\"", name);
        try{
            Department department = getJdbcTemplate().queryForObject(IF_EXIST_DEPARTMENT,
                    new DepartmentRowMapper(), name);
            return department.getName().equals(name);
        }catch (IncorrectResultSizeDataAccessException e){
            log.debug("No department with name \"" + name + "\"");
        }
        return false;
    }

    public List<HashMap<String, String>> getDepartments() {

        log.debug("get departments from db");

        return getJdbcTemplate().query(GET_DEPARTMENTS, new RowMapper<HashMap<String, String>>() {
                    public HashMap<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
                        HashMap<String, String> data = new HashMap<String, String>();
                        data.put("id", resultSet.getString(1));
                        data.put("name", resultSet.getString(2));
                        data.put("salary", resultSet.getString(3));
                        return data;
                    }
                });
    }

    public boolean addDepartment(String departmentName) {

        log.debug("add department to db with name \"{}\"", departmentName);

        int affectedRows = getJdbcTemplate().update(ADD_DEPARTMENT, departmentName);
        return affectedRows == 1;

    }

    public boolean deleteDepartment(int id) {

        log.debug("delete department from db with id \"{}\"", id);

        int affectedRows = getJdbcTemplate().update(DELETE_DEPARTMENT, id);
        return affectedRows == 1;
    }

    public boolean updateDepartment(Department department) {

        log.debug("update department in db:", department);

        int affectedRows = getJdbcTemplate().update(UPDATE_DEPARTMENT,
                department.getName(), department.getId());
        return affectedRows == 1;
    }
}
