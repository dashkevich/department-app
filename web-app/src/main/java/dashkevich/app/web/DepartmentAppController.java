package dashkevich.app.web;


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
 *  Controller for Departments.
 */
@Controller
public class DepartmentAppController {

    private final static Logger log = LogManager.getLogger(DepartmentAppController.class);

    @Autowired
    private DepartmentService departmentsRestClient;


    /**
     * Show page with departments list
     * @param model
     * @return view name.
     */
    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String departments(HashMap<String, Object> model) {

        log.debug("Web: get departments, request to /departments, method = GET;");

        List<HashMap<String, String>> departments = departmentsRestClient.getDepartments();

        model.put("departments", departments);

        model.put("pageTitle", "Departments");
        model.put("pageHeading", "Departments page");

        return "departments";
    }


    /**
     * Ajax method.
     * Add new Department
     * @param name - name for new department
     * @return ResponseEntity with http status CREATED if the addition was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/departments/add", method = RequestMethod.POST)
    public ResponseEntity addDepartment(@RequestParam("name") String name){

        log.debug("Web: add departments, request to /departments/add, method = POST;");

        Department department = new Department();
        department.setName(name);

        try {
            log.debug("Web: try to add department: {}", department);

            if(Department.isValid(department)) {
                if (departmentsRestClient.addDepartment(department.getName())) {
                    log.debug("Web: add department - sucsess.");
                    return new ResponseEntity(HttpStatus.CREATED);
                }
            }

            log.debug("Web: invalid input data");

        }catch (Exception e) {
            log.debug("Web: add error: ", e);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    /**
     * Ajax method.
     * Update department.
     * @param department - Department for update.
     * @return ResponseEntity with http status NO_CONTENT if the updating was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/departments/update", method = RequestMethod.POST,
            headers = "Content-Type=application/json")
    public ResponseEntity updateDepartment(@RequestBody Department department){

        log.debug("Web: update departments, request to /departments/update, method = POST;");

        try {
            log.debug("Web: try to update department: {}", department);

            if(Department.isValid(department)) {
                if (departmentsRestClient.updateDepartment(department)) {
                    log.debug("Web: update department - sucsess.");
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                }
            }

            log.debug("Web: invalid input data");

        }catch (Exception e){
            log.debug("Web: update error: ", e);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Ajax method.
     * Delete department.
     * @param id
     * @return ResponseEntity with http status NO_CONTENT if the deleting was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/departments/delete", method = RequestMethod.POST)
    public ResponseEntity deleteDepartment(@RequestParam("id") Integer id){

        log.debug("Web: delete departments, request to /departments/delete, method = POST;");

        try {
            log.debug("Web: try to delete department with id: {}", id);

            if(departmentsRestClient.deleteDepartment(id)){
                log.debug("Web: delete department - sucsess.");
                return  new ResponseEntity(HttpStatus.NO_CONTENT);
            }

        }catch (Exception e){
            log.debug("Web: delete error: ", e);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
