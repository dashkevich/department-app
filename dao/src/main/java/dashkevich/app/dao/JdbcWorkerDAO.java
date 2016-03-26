package dashkevich.app.dao;

import dashkevich.app.model.Worker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 *  This class implements WorkerDAO interface and using SQL for data access.
 */
@Component
public class JdbcWorkerDAO extends JdbcDaoSupport implements WorkerDAO {

    private final static Logger log = LogManager.getLogger(JdbcWorkerDAO.class);

    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_all_workers}')).inputStream)}")
    public String GET_ALL_WORKERS;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_by_department_id}')).inputStream)}")
    public String GET_BY_DEPARTMENT_ID;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${get_by_birthday}')).inputStream)}")
    public String GET_BY_BIRTHDAY;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${add_worker}')).inputStream)}")
    public String ADD_WORKER;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${delete_worker}')).inputStream)}")
    public String DELETE_WORKER;
    @Value("#{T(org.apache.commons.io.IOUtils).toString((new org.springframework.core.io.ClassPathResource('${update_worker}')).inputStream)}")
    public String UPDATE_WORKER;

    public List<Worker> getWorkers() {

        log.debug("get all workers from db");

        return getJdbcTemplate().query(GET_ALL_WORKERS,
                new WorkerRowMapper());
    }

    public List<Worker> getWorkersByDepartmentId(int did) {

        log.debug("get workers from db with departmentID \"{}\"", did);

        return getJdbcTemplate().query(GET_BY_DEPARTMENT_ID, new WorkerRowMapper(), did);
    }

    public List<Worker> getWorkers(Date dateFrom, Date dateTo) {

        log.debug("get workers from db by date DateFrom: \"{}\", DateTo: \"{}\"", dateFrom, dateTo);

        return getJdbcTemplate().query(GET_BY_BIRTHDAY, new WorkerRowMapper(), dateFrom, dateTo);
    }

    public boolean addWorker(Worker worker) {

        log.debug("add worker to db: \"{}\"", worker);

        int affectedRows = getJdbcTemplate().update(ADD_WORKER, worker.getDepartmentID(), worker.getName(), worker.getBirthday(), worker.getSalary());
        return affectedRows == 1;
    }

    public boolean deleteWorker(int id) {

        log.debug("delete worker from db with id \"{}\"", id);

        int affectedRows = getJdbcTemplate().update(DELETE_WORKER, id);
        return affectedRows == 1;
    }

    public boolean updateWorker(Worker worker) {

        log.debug("update worker in db: \"{}\"", worker);

        int affectedRows = getJdbcTemplate().update(UPDATE_WORKER,
                worker.getDepartmentID(), worker.getName(), worker.getBirthday(), worker.getSalary(), worker.getId());
        return affectedRows == 1;
    }
}