package dashkevich.app.dao;

import dashkevich.app.model.Worker;

import java.util.Date;
import java.util.List;

public interface WorkerDAO{
    List<Worker> getWorkers();
    List<Worker> getWorkersByDepartmentId(int did);
    List<Worker> getWorkers(Date dateFrom, Date dateTo);
    boolean addWorker(Worker worker);
    boolean deleteWorker(int id);
    boolean updateWorker(Worker worker);
}