package dashkevich.app.services;


import dashkevich.app.model.Worker;

import java.util.Date;
import java.util.List;

public interface WorkerService {

    List<Worker> getWorkers();
    List<Worker> getWorkers(int did);
    List<Worker> getWorkers(int did, Date dateFrom, Date dateTo);
    List<Worker> getWorkers(Date dateFrom, Date dateTo);
    boolean addWorker(Worker worker);
    boolean deleteWorker(int id);
    boolean updateWorker(Worker worker);
}
