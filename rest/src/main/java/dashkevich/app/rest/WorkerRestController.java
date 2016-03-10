package dashkevich.app.rest;


import dashkevich.app.model.Worker;
import dashkevich.app.services.WorkerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * Rest controller for Workers.
 */
@Controller
public class WorkerRestController {

    private final static Logger log = LogManager.getLogger(WorkerRestController.class);

    @Autowired
    private WorkerService workerService;


    /**
     * Get workers list.
     * @param did - department id, may be empty.
     * @param dateFrom - date for filtering workers by birthday, may be empty.
     * @param dateTo - date for filtering workers by birthday, may be empty.
     * @return ResponseEntity with List<Worker> and http status OK if getting workers was successful or
     * http status BAD_REQUEST.
     */
    @RequestMapping(value = "/workers", method = RequestMethod.GET,
            headers = {"Accept=application/json"})
    public @ResponseBody
    ResponseEntity<List<Worker>> getWorkers(
            @RequestParam(value="did", required=false) Integer did,
            @RequestParam(value="dateFrom", required=false) Date dateFrom,
            @RequestParam(value="dateTo", required=false) Date dateTo) {

        log.debug("Rest: get workers, request to /workers, method = GET;");

        List<Worker> workers;

        try{
            log.debug("Rest: try to get workers");

            if(dateFrom != null && dateTo != null && did != null){
                workers = workerService.getWorkers(did, dateFrom, dateTo);
            }else if(dateFrom != null && dateTo != null){
                workers = workerService.getWorkers(dateFrom, dateTo);
            }else if(dateFrom != null) {
                workers = workerService.getWorkers(dateFrom, dateFrom);
            }else if(did != null){
                workers = workerService.getWorkers(did);
            }else {
                workers = workerService.getWorkers();
            }
        }catch(Exception e){
            log.debug("Rest: get error: ", e);
            return new ResponseEntity<List<Worker>>(HttpStatus.BAD_REQUEST);
        }

        log.debug("Rest: get workers sucsess.");
        return new ResponseEntity<List<Worker>>(workers, HttpStatus.OK);
    }


    /**
     * Update worker
     * @param worker - Worker to update.
     * @return ResponseEntity with http status NO_CONTENT if the updating was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/workers", method = RequestMethod.PUT,
            headers = "Content-Type=application/json")
    public ResponseEntity updateWorker(@RequestBody Worker worker) {

        log.debug("Rest: update workers, request to /workers, method = PUT;");

        try {
            log.debug("Rest: try to update worker:", worker);

            if(Worker.isValid(worker)) {
                if (workerService.updateWorker(worker)) {
                    log.debug("Rest: update worker sucsess.");
                    return new ResponseEntity(HttpStatus.NO_CONTENT);
                }
            }

            log.debug("Rest: invalid input data");

        }catch (Exception e){
            log.debug("Rest: update error: ", e);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    /**
     * Add new worker
     * @param worker
     * @return ResponseEntity with http status CREATED if the addition was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/workers", method = RequestMethod.POST,
            headers = "Content-Type=application/json")
    public ResponseEntity addWorker(@RequestBody Worker worker) {

        log.debug("Rest: add workers, request to /workers, method = POST;");

        try {
            log.debug("Rest: try to add worker:", worker);

            if(Worker.isValid(worker)) {
                if (workerService.addWorker(worker)) {
                    log.debug("Rest: add worker sucsess.");
                    return new ResponseEntity(HttpStatus.CREATED);
                }
            }

            log.debug("Rest: invalid input data");

        }catch (Exception e){
            log.debug("Rest: add error: ", e);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Delete worker.
     * @param id
     * @return ResponseEntity with http status NO_CONTENT if the deleting was successful,
     * or BAD_REQUEST status otherwise.
     */
    @RequestMapping(value = "/workers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteWorker(@PathVariable("id") int id) {

        log.debug("Rest: delete workers, request to /workers/{}, method = GET;", id);

        try {
            log.debug("Rest: try to delete worker with id: {}", id);

            if(workerService.deleteWorker(id)){
                log.debug("Rest: delete worker sucsess.");
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }

        }catch (Exception e){
            log.debug("Rest: delete error: ", e);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}
