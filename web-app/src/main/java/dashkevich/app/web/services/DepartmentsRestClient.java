package dashkevich.app.web.services;

import dashkevich.app.model.Department;
import dashkevich.app.services.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *  DepartmentsRestClient - service for Departments controller.
 *  This class implements interface "DepartmentService" and using RESTful web service for data access.
 *  Access to the data through the object of RestTemplate class.
 */
@Component
public class DepartmentsRestClient implements DepartmentService {

    private final static Logger log = LogManager.getLogger(DepartmentsRestClient.class);

    private RestTemplate restTemplate;

    public DepartmentsRestClient() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * This method using for mock testing
     * @return
     */
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

/*
    public List<Department> getDepartmens() {

        log.debug("RestClient: get list departments ");

        ResponseEntity<Department[]> responseEntity;
        try {
            log.debug("RestClient: try send request to:  http://localhost:8080/rest/departments ");
            responseEntity = restTemplate.getForEntity("http://localhost:8080/rest/departments", Department[].class);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return new ArrayList<Department>();
        }

        log.debug("RestClient: get list departments - sucsess.");
        return Arrays.asList(responseEntity.getBody());

    }
*/

    /**
     * The method returns the data as List<HashMap<String, String>>.
     * HashMap<key, value> contains the following keys:
     *  id => Department id;
     *  name => Department name;
     *  salary => Department average salary;
     *
     * @return
     */
    public List<HashMap<String, String>> getDepartments() {

        log.debug("RestClient: get list departments with average salary ");

        ParameterizedTypeReference<List<HashMap<String, String>>> typeRef =
                new ParameterizedTypeReference<List<HashMap<String, String>>>() {};
        ResponseEntity<List<HashMap<String, String>>> response;
        try {
            log.debug("RestClient: try send request to:  http://localhost:8080/rest/departments ");
            response = restTemplate.exchange("http://localhost:8080/rest/departments", HttpMethod.GET, null, typeRef);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return  new ArrayList<HashMap<String, String>>();
        }

        log.debug("RestClient: get departments with average salary - sucsess.");
        return response.getBody();
    }

    public boolean addDepartment(String departmentName) {

        log.debug("RestClient: add department with name \"{}\" ", departmentName);

        ResponseEntity<String> response;
        try {
            log.debug("RestClient: try send request to:  http://localhost:8080/rest/departments/" + departmentName);
            response = restTemplate.postForEntity("http://localhost:8080/rest/departments/{name}",
                    null, String.class, departmentName);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return false;
        }

        if(response.getStatusCode() == HttpStatus.CREATED) {
            log.debug("RestClient: add department - sucsess.");
            return true;
        }

        log.debug("RestClient: add department - fail: HttpStatusCode {}", response.getStatusCode());
        return false;
    }

    public boolean deleteDepartment(int id) {

        log.debug("RestClient: delete department with id \"{}\" ", id);

        ResponseEntity responseEntity;
        try {
            log.debug("RestClient: try send request to:  http://localhost:8080/rest/departments/" + id);
            responseEntity = restTemplate.exchange(
                    "http://localhost:8080/rest/departments/{id}",
                    HttpMethod.DELETE, null, ResponseEntity.class, id);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return  false;
        }

        if(responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            log.debug("RestClient: delete department - sucsess.");
            return true;
        }

        log.debug("RestClient: delete department - fail: HttpStatusCode {}", responseEntity.getStatusCode());
        return false;

    }

    public boolean updateDepartment(Department department) {

        log.debug("RestClient: update department: {}", department);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity responseEntity;
        try {
            log.debug("RestClient: try send request to:  http://localhost:8080/rest/departments");
            responseEntity = restTemplate.exchange(
                    "http://localhost:8080/rest/departments",
                    HttpMethod.PUT, new HttpEntity<Object>(department, headers), ResponseEntity.class);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return  false;
        }

        if(responseEntity.getStatusCode() == HttpStatus.NO_CONTENT){
            log.debug("RestClient: update department - sucsess.");
            return  true;
        }

        log.debug("RestClient: update department - fail: HttpStatusCode {}", responseEntity.getStatusCode());
        return false;

    }


}