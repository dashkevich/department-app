package dashkevich.app.rest;

import dashkevich.app.model.Department;
import dashkevich.app.services.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * Rest controller for Departments.
 */
@Controller
public class DepartmentRestController {

    private final static Logger log = LogManager.getLogger(DepartmentRestController.class);

    @Autowired
    private DepartmentService departmentService;

    /**
     * Get Departments with average salary.
     * @return ResponseEntity with Departments list and http status OK if getting departments was successful or
     * http status BAD_REQUEST.
     */
    @RequestMapping(value = "/departments", method = RequestMethod.GET, headers = {"Accept=application/json"})
    public @ResponseBody
    ResponseEntity< List<HashMap<String, String>> > getDepartments() {

        log.debug("Rest: get departments, request to /departments, method = GET;");

        List<HashMap<String, String>> departments = null;
        try{
            log.debug("Rest: try to get departments");
            departments = departmentService.getDepartments();
        }catch (Exception e){
            log.debug("Rest: get error: ", e);
            new ResponseEntity<List<HashMap<String, String>>>(HttpStatus.BAD_REQUEST);
        }

        log.debug("Rest: get department sucsess.");
        return new ResponseEntity<List<HashMap<String, String>>>(departments, HttpStatus.OK);
    }

    /**
     * Update department
     * @param department - department to update.
     * @return ResponseEntity with http status NO_CONTENT if the updating was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/departments", method = RequestMethod.PUT,
            headers = "Content-Type=application/json")
    public ResponseEntity updateDepartment(@RequestBody Department department) {

        log.debug("Rest: update departments, request to /departments, method = PUT;");

        try{
            log.debug("Rest: try to update departmentt:", department);

            if(departmentService.updateDepartment(department)){
                log.debug("Rest: update department sucsess.");
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            log.debug("Rest: update error: ", e);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    /**
     * Add new department
     * @param name
     * @return ResponseEntity with http status CREATED if the updating was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/departments/{name}", method = RequestMethod.POST)
    public ResponseEntity addDepartment(@PathVariable("name") String name) {

        log.debug("Rest: add departments, request to /departments/{}, method = POST;", name);

        Department department = new Department();
        department.setName(name);

        try{
            log.debug("Rest: try to add department:", department);
            if(Department.isValid(department)){
                if(departmentService.addDepartment(department.getName())){
                    log.debug("Rest: add department sucsess.");
                    return new ResponseEntity(HttpStatus.CREATED);
                }
            }

        }catch (Exception e){
            log.debug("Rest: add error: ", e);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Delete department
     * @param id
     * @return ResponseEntity with http status NO_CONTENT if the deleting was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/departments/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDepartment(@PathVariable("id") int id) {

        log.debug("Rest: delete departments, request to /departments/{}, method = DELETE;", id);

        try{
            log.debug("Rest: try to delete department with id: {}", id);

            if(departmentService.deleteDepartment(id)){
                log.debug("Rest: delete department sucsess.");
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            log.debug("Rest: delete error: ", e);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}