package dashkevich.app.services;


import dashkevich.app.dao.WorkerDAO;
import dashkevich.app.model.Worker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class WorkerServiceImpl implements WorkerService{

    private final static Logger log = LogManager.getLogger(WorkerServiceImpl.class);

    private WorkerDAO workerDAO;

    @Autowired
    public WorkerServiceImpl(WorkerDAO workerDAO) {
        this.workerDAO = workerDAO;
    }


    public List<Worker> getWorkers() {
        log.debug("Service: get all workers");
        return workerDAO.getWorkers();
    }

    public List<Worker> getWorkers(int did) {
        log.debug("Service: get workers by departmentID \"{}\"", did);
        return workerDAO.getWorkersByDepartmentId(did);
    }

    /**
     * Search workers by departmentID and birthday.
     * This method takes from database all records with departmentID = did and
     * filtering them by date.
     */
    public List<Worker> getWorkers(int did, Date dateFrom, Date dateTo) {

        log.debug("Service: get workers with params: departmentID = \"{}\", dateFrom = \"{}\", dateTo = \"{}\"",
                did, dateFrom, dateTo);

        List<Worker> workerList = workerDAO.getWorkersByDepartmentId(did);
        List<Worker> workers = new ArrayList<Worker>();

        for(int i=0; i<workerList.size(); i++){
            Date birthday = workerList.get(i).getBirthday();
            if(birthday.compareTo(dateFrom) >=0 && birthday.compareTo(dateTo) <=0){
                workers.add(workerList.get(i));
            }
        }

        return workers;
    }

    public List<Worker> getWorkers(Date dateFrom, Date dateTo) {
        log.debug("Service: get workers FromDate: {}, ToDate: {}", dateFrom, dateTo);
        return workerDAO.getWorkers(dateFrom, dateTo);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean addWorker(Worker worker) {
        log.debug("Service: add worker:", worker);
        return workerDAO.addWorker(worker);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean deleteWorker(int id) {
        log.debug("Service: delete worker with id \"{}\":", id);
        return workerDAO.deleteWorker(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean updateWorker(Worker worker) {
        log.debug("Service: update worker:", worker);
        return workerDAO.updateWorker(worker);
    }
}
