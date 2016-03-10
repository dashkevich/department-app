package dashkevich.app.dao;

import dashkevich.app.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *  This class implements DepartmentDAO interface and using SQL for data access.
 */
public class JdbcDepartmentDAO extends JdbcDaoSupport implements DepartmentDAO {

    private final static Logger log = LogManager.getLogger(JdbcDepartmentDAO.class);

    public boolean ifExistDepartment(String name) {
        log.debug("check department with name \"{}\"", name);
        try{
            Department department = getJdbcTemplate().queryForObject("SELECT * FROM `departments` WHERE `name` = ?",
                    new DepartmentRowMapper(), name);
            return department.getName().equals(name);
        }catch (IncorrectResultSizeDataAccessException e){
            log.debug("No department with name \"" + name + "\"");
        }
        return false;
    }

    public List<HashMap<String, String>> getDepartments() {

        log.debug("get departments from db");

        return getJdbcTemplate().query("SELECT `id`, `name`, IFNULL(`salary`, 0) FROM `departments` LEFT JOIN \n" +
                        "(SELECT `did`, IFNULL(avg(salary),0) AS `salary` FROM `workers` GROUP BY `did`) AS S " +
                "ON departments.id = S.did GROUP BY `id`;",
                new RowMapper<HashMap<String, String>>() {
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

        int affectedRows = getJdbcTemplate().update("INSERT INTO `departments` (`name`) VALUES (?)", departmentName);
        return affectedRows == 1;

    }

    public boolean deleteDepartment(int id) {

        log.debug("delete department from db with id \"{}\"", id);

        int affectedRows = getJdbcTemplate().update("DELETE FROM `departments` WHERE `id` = ? ", id);
        return affectedRows == 1;
    }

    public boolean updateDepartment(Department department) {

        log.debug("update department in db:", department);

        int affectedRows = getJdbcTemplate().update("UPDATE `departments` SET `name` = ? WHERE `id` = ?",
                department.getName(), department.getId());
        return affectedRows == 1;
    }
}
