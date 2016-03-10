package dashkevich.app.web.services;

import dashkevich.app.model.Worker;
import dashkevich.app.services.WorkerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  WorkerRestClient - service for Workers controller.
 *  This class implements interface "WorkerService" and using RESTful web service for data access.
 *  Access to the data through the object of RestTemplate class.
 */
@Component
public class WorkerRestClient implements WorkerService {

    private final static Logger log = LogManager.getLogger(WorkerRestClient.class);

    private RestTemplate restTemplate;

    public WorkerRestClient() {
        restTemplate = new RestTemplate();
    }

    /**
     * This method using for mock testing
     * @return
     */
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    /**
     *  Get all workers.
     * @return List<Worker>, may be empty.
     */
    public List<Worker> getWorkers() {

        log.debug("RestClient: get all workers ");

        try{
            log.debug("RestClient: try send request to:  http://localhost:8080/rest/workers ");
            return restTemplate.getForObject("http://localhost:8080/rest/workers",List.class);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return  new ArrayList<Worker>();
        }
    }

    /**
     *  Get workers by birthday date. The date should be between dateFrom and dateTo params.
     *  Search expression( date >= dateFrom and date <= dateTo ).
     * @param dateFrom
     * @param dateTo
     * @return List<Worker>, may be empty.
     */
    public List<Worker> getWorkers(Date dateFrom, Date dateTo) {

        log.debug("RestClient: get workers FromDate: {}, ToDate: {}", dateFrom, dateTo);

        try {
            log.debug("RestClient: try send request to: http://localhost:8080/rest/workers?dateFrom={}&dateTo={} ", dateFrom, dateTo);
            return restTemplate.getForObject(
                    "http://localhost:8080/rest/workers?dateFrom={dateFrom}&dateTo={dateTo}",
                    List.class, dateFrom, dateTo);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return new ArrayList<Worker>();
        }
    }

    /**
     * Get workers from selected department.
     * @param did - department id.
     * @return List<Worker>, may be empty.
     */
    public List<Worker> getWorkers(int did) {

        log.debug("RestClient: get workers by departmentID: {}", did);

        try {
            log.debug("RestClient: try send request to: http://localhost:8080/rest/workers?did={} ", did);
            return restTemplate.getForObject("http://localhost:8080/rest/workers?did={did}", List.class, did);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return new ArrayList<Worker>();
        }
    }

    /**
     * Get workers by department id and birthday date.
     * @param did - department id.
     * @param dateFrom
     * @param dateTo
     * @return List<Worker>, may be empty.
     */
    public List<Worker> getWorkers(int did, Date dateFrom, Date dateTo) {

        log.debug("RestClient: get workers FromDate: {}, ToDate: {} by departmentID: {}", dateFrom, dateTo, did);

        try {
            log.debug("RestClient: try send request to: http://localhost:8080/rest/workers?did={}&dateFrom={}&dateTo={}",
                    did, dateFrom, dateTo);
            return restTemplate.getForObject(
                    "http://localhost:8080/rest/workers?did={did}&dateFrom={dateFrom}&dateTo={dateTo}",
                    List.class, did, dateFrom, dateTo);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return new ArrayList<Worker>();
        }
    }

    public boolean addWorker(Worker worker) {

        log.debug("RestClient: add worker: {}", worker);

        ResponseEntity<Worker> response;
        try {
            log.debug("RestClient: try send request to: http://localhost:8080/rest/workers");
            response = restTemplate.postForEntity("http://localhost:8080/rest/workers", worker, Worker.class);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return false;
        }
        if(response.getStatusCode() == HttpStatus.CREATED){
            log.debug("RestClient: add worker - sucsess.");
            return true;
        }

        log.debug("RestClient: add worker - fail: HttpStatusCode {}", response.getStatusCode());
        return false;
    }

    public boolean deleteWorker(int id) {

        log.debug("RestClient: delete worker with id: {}", id);

        ResponseEntity responseEntity;
        try {
            log.debug("RestClient: try send request to: http://localhost:8080/rest/workers/{}", id);
            responseEntity = restTemplate.exchange(
                    "http://localhost:8080/rest/workers/{id}",
                    HttpMethod.DELETE, null, ResponseEntity.class, id);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return false;
        }

        if(responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            log.debug("RestClient: delete worker - sucsess.");
            return true;
        }

        log.debug("RestClient: delete worker - fail: HttpStatusCode {}", responseEntity.getStatusCode());
        return false;

    }

    public boolean updateWorker(Worker worker) {

        log.debug("RestClient: update worker: {}", worker);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity responseEntity;
        try {
            log.debug("RestClient: try send request to: http://localhost:8080/rest/workers");
            responseEntity = restTemplate.exchange(
                    "http://localhost:8080/rest/workers",
                    HttpMethod.PUT, new HttpEntity<Object>(worker, headers), ResponseEntity.class);
        }catch (HttpClientErrorException e){
            log.debug("RestClient error: ", e);
            return  false;
        }

        if(responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            log.debug("RestClient: update worker - sucsess.");
            return true;
        }

        log.debug("RestClient: update worker - fail: HttpStatusCode {}", responseEntity.getStatusCode());
        return false;

    }
}
