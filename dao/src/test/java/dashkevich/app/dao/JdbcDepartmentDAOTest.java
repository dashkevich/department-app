package dashkevich.app.dao;

import dashkevich.app.model.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/dao-test-context.xml" })
public class JdbcDepartmentDAOTest {

    @Autowired
    JdbcDepartmentDAO departmentDAO = null;

    @Test
    public void testIfExistDepartment() throws Exception {

        assertTrue(departmentDAO.ifExistDepartment("department 1"));
        assertFalse(departmentDAO.ifExistDepartment("record does not exist"));

    }

    @Test
    public void testGetDepartments() throws Exception {

        List<HashMap<String, String>> departments = departmentDAO.getDepartments();

        assertNotNull(departments);
        assertTrue(departments.size() > 0);

        for(int i=0; i<departments.size(); i++) {
            HashMap<String, String> map = departments.get(i);
            assertNotNull(map);
            assertNotNull(map.get("id"));
            assertNotNull(map.get("name"));
            assertNotNull(map.get("salary"));
        }

    }

    @Test
    public void testAddDepartment() throws Exception {
        assertTrue(departmentDAO.addDepartment("test department"));
        assertTrue(departmentDAO.ifExistDepartment("test department"));
    }

    @Test
    public void testDeleteDepartment() throws Exception {

        assertTrue(departmentDAO.ifExistDepartment("department 1"));
        assertTrue(departmentDAO.deleteDepartment(1));
        assertFalse(departmentDAO.ifExistDepartment("department 1"));

    }

    @Test
    public void testUpdateDepartment() throws Exception {

        Department updateDepartment = new Department(2, "updated");
        assertTrue(departmentDAO.updateDepartment(updateDepartment));
        assertTrue(departmentDAO.ifExistDepartment("updated"));

    }
}