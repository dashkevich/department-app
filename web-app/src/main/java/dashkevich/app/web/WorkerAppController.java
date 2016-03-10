package dashkevich.app.web;


import dashkevich.app.model.Worker;
import dashkevich.app.services.DepartmentService;
import dashkevich.app.services.WorkerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Controller for Workers.
 */
@Controller
public class WorkerAppController {

    private final static Logger log = LogManager.getLogger(WorkerAppController.class);

    @Autowired
    private DepartmentService departmentsRestClient;
    @Autowired
    private WorkerService workerRestClient;

    /**
     * Show page with workers list.
     * @param did - Department id, may be empty.
     * @param dateFrom - date for filtering workers by birthday, may be empty.
     * @param dateTo - date for filtering workers by birthday, may be empty.
     * @param model
     * @return view name.
     */
    @RequestMapping(value = "/workers", method = RequestMethod.GET)
    public String workers(
            @RequestParam(value="did", required=false) Integer did,
            @RequestParam(value="dateFrom", required=false) Date dateFrom,
            @RequestParam(value="dateTo", required=false) Date dateTo,
            Model model)
    {

        log.debug("Web: get workers, request to /workers, method = GET;");

        List<Worker> workers = null;
        List<HashMap<String, String>> departments = null;

        try {
            log.debug("Web: try to get workers");

            if(dateFrom != null && dateTo != null && did != null){
                workers = workerRestClient.getWorkers(did, dateFrom, dateTo);
            }else if(dateFrom != null && dateTo != null){
                workers = workerRestClient.getWorkers(dateFrom, dateTo);
            }else if(dateFrom != null) {
                workers = workerRestClient.getWorkers(dateFrom, dateFrom);
            }else if(did != null){
                workers = workerRestClient.getWorkers(did);
            }else {
                workers = workerRestClient.getWorkers();
            }
            departments = departmentsRestClient.getDepartments();

        }catch (Exception e){
            log.debug("Web: get error: ", e);
        }

        model.addAttribute("workers", workers);
        model.addAttribute("departments", departments);

        model.addAttribute("pageTitle", "Workers");
        model.addAttribute("pageHeading", "Workers page");

        log.debug("Web: get workers - sucsess.");

        return "workers";
    }

    /**
     * Ajax method.
     * Add new Worker.
     * @param name - Worker name.
     * @param did - Worker department id.
     * @param birthday - Worker birthday
     * @param salary - Worker salary.
     * @return ResponseEntity with http status CREATED if the addition was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/workers/add", method = RequestMethod.POST)
    public ResponseEntity addWorkers(@RequestParam(value = "workerName") String name,
                                     @RequestParam(value = "workerDepartment") Integer did,
                                     @RequestParam(value = "workerBirthday") Date birthday,
                                     @RequestParam(value = "workerSalary") Integer salary)
    {
        log.debug("Web: add worker, request to /workers/add, method = POST;");

        Worker worker = new Worker(did, name, birthday, salary);

        try{
            log.debug("Web: try to add worker: {}", worker);

            if(Worker.isValid(worker)){
                if(workerRestClient.addWorker(worker)) {
                    log.debug("Web: add worker - sucsess.");
                    return new ResponseEntity(HttpStatus.CREATED);
                }
            }

            log.debug("Web: invalid input data");

        }catch (Exception e){
            log.debug("Web: add error: ", e);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    /**
     * Ajax method.
     * Update worker.
     * @param worker
     * @return ResponseEntity with http status NO_CONTENT if the updating was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/workers/update", method = RequestMethod.POST,
            headers = "Content-Type=application/json")
    public ResponseEntity updateWorkers(@RequestBody Worker worker){

        log.debug("Web: update worker, request to /workers/update, method = POST;");

        try {
            log.debug("Web: try to update worker: {}", worker);

            if(Worker.isValid(worker)){
                if (workerRestClient.updateWorker(worker)) {
                    log.debug("Web: update worker - sucsess.");
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
     * Delete worker.
     * @param id
     * @return ResponseEntity with http status NO_CONTENT if the deleting was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/workers/delete", method = RequestMethod.POST)
    public ResponseEntity deleteWorkers(@RequestParam("id") Integer id){

        log.debug("Web: delete worker, request to /workers/delete, method = POST;");

        try {
            log.debug("Web: try to delete worker: with id {}", id);

            if (workerRestClient.deleteWorker(id)) {
                log.debug("Web: delete worker - sucsess.");
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            log.debug("Web: delete error: ", e);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
