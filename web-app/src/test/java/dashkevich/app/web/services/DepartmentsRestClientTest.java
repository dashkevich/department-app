package dashkevich.app.web.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dashkevich.app.model.Department;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.*;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class DepartmentsRestClientTest {

    private DepartmentsRestClient departmentsRestClient;

    private MockRestServiceServer server;

    @Before
    public void setUp() throws Exception {
        departmentsRestClient = new DepartmentsRestClient();
        server = MockRestServiceServer.createServer(departmentsRestClient.getRestTemplate());
    }

/*
    @Test
    public void testGetDepartmens() throws Exception {

        List<Department> departments = Arrays.asList(new Department(1, "first"),
                                                     new Department(2, "second")
        );

        server.expect(requestTo("http://localhost:8080/rest/departments"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(new ObjectMapper().writeValueAsString(departments), MediaType.APPLICATION_JSON));

        departmentsRestClient.getDepartmens();

        server.verify();

    }

    @Test
    public void testGetDepartmentsBadRequest() throws Exception{

        server.expect(requestTo("http://localhost:8080/rest/departments"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        departmentsRestClient.getDepartmens();

        server.verify();
    }
*/

    @Test
    public void testGetDepartments() throws Exception {

        List<HashMap<String, String>> departments = new ArrayList<HashMap<String, String>>();
        for(int i=0; i<5; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", "" + i);
            map.put("name", "department " + i);
            map.put("salary", "" + (i+1)*100);
            departments.add(map);
        }

        server.expect(requestTo("http://localhost:8080/rest/departments"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(new ObjectMapper().writeValueAsString(departments), MediaType.APPLICATION_JSON));

        departmentsRestClient.getDepartments();

        server.verify();
    }

    @Test
    public void testGetDepartmentsBadRequest() throws Exception {

        server.expect(requestTo("http://localhost:8080/rest/departments"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        departmentsRestClient.getDepartments();

        server.verify();
    }

    @Test
    public void testAddDepartment() throws Exception {

        server.expect(requestTo("http://localhost:8080/rest/departments/testDepartment"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CREATED));

        assertTrue(departmentsRestClient.addDepartment("testDepartment"));

        server.verify();
    }

    @Test
    public void testAddDepartmentBadRequest() throws Exception {

        server.expect(requestTo("http://localhost:8080/rest/departments/testDepartment"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        assertFalse(departmentsRestClient.addDepartment("testDepartment"));

        server.verify();
    }

    @Test
    public void testDeleteDepartment() throws Exception {

        server.expect(requestTo("http://localhost:8080/rest/departments/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.NO_CONTENT));

        assertTrue(departmentsRestClient.deleteDepartment(1));

        server.verify();
    }

    @Test
    public void testDeleteDepartmentBadRequest() throws Exception {

        server.expect(requestTo("http://localhost:8080/rest/departments/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        assertFalse(departmentsRestClient.deleteDepartment(1));

        server.verify();
    }

    @Test
    public void testUpdateDepartment() throws Exception {

        Department department = new Department(1, "first");
        server.expect(requestTo("http://localhost:8080/rest/departments"))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(department)))
                .andRespond(withStatus(HttpStatus.NO_CONTENT));

        assertTrue(departmentsRestClient.updateDepartment(department));

        server.verify();

    }

    @Test
    public void testUpdateDepartmentBadRequest() throws Exception {

        Department department = new Department(1, "first");
        server.expect(requestTo("http://localhost:8080/rest/departments"))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(department)))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        assertFalse(departmentsRestClient.updateDepartment(department));

        server.verify();

    }
}