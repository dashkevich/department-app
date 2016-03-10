package dashkevich.app.dao;

import dashkevich.app.model.Worker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.Date;
import java.util.List;

/**
 *  This class implements WorkerDAO interface and using SQL for data access.
 */
public class JdbcWorkerDAO extends JdbcDaoSupport implements WorkerDAO {

    private final static Logger log = LogManager.getLogger(JdbcWorkerDAO.class);

    public List<Worker> getWorkers() {

        log.debug("get all workers from db");

        return getJdbcTemplate().query("SELECT * FROM `workers`",
                new WorkerRowMapper());
    }

    public List<Worker> getWorkersByDepartmentId(int did) {

        log.debug("get workers from db with departmentID \"{}\"", did);

        return getJdbcTemplate().query("SELECT * FROM `workers` WHERE `did` = ?",
                new WorkerRowMapper(), did);
    }

    public List<Worker> getWorkers(Date dateFrom, Date dateTo) {

        log.debug("get workers from db by date DateFrom: \"{}\", DateTo: \"{}\"", dateFrom, dateTo);

        return getJdbcTemplate().query("SELECT * FROM `workers` WHERE `birthday` >= ? AND `birthday` <= ?",
                new WorkerRowMapper(), dateFrom, dateTo);
    }

    public boolean addWorker(Worker worker) {

        log.debug("add worker to db: \"{}\"", worker);

        int affectedRows = getJdbcTemplate().update("INSERT INTO `workers` (`did`, `name`, `birthday`, `salary`) VALUES" +
                "(?, ?, ?, ?)", worker.getDepartmentID(), worker.getName(), worker.getBirthday(), worker.getSalary());
        return affectedRows == 1;
    }

    public boolean deleteWorker(int id) {

        log.debug("delete worker from db with id \"{}\"", id);

        int affectedRows = getJdbcTemplate().update("DELETE FROM `workers` WHERE `id` = ? ", id);
        return affectedRows == 1;
    }

    public boolean updateWorker(Worker worker) {

        log.debug("update worker in db: \"{}\"", worker);

        int affectedRows = getJdbcTemplate().update(
                "UPDATE `workers` SET `did` = ?, `name` = ?, `birthday` = ?, `salary` = ? WHERE `id` = ?",
                worker.getDepartmentID(), worker.getName(), worker.getBirthday(), worker.getSalary(), worker.getId());
        return affectedRows == 1;
    }
}