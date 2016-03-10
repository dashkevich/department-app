package dashkevich.app.dao;

import dashkevich.app.model.Worker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/dao-test-context.xml" })
public class JdbcWorkerDAOTest {

    @Autowired
    JdbcWorkerDAO workerDAO;

    @Test
    public void testGetWorkers() throws Exception {

        List<Worker> workers = workerDAO.getWorkers();
        assertNotNull(workers);
        assertTrue(workers.size() > 0);

        for(int i=0; i<workers.size(); i++){
            Worker worker = workers.get(i);

            assertNotNull(worker);
            assertTrue(worker.getId() > 0);
            assertNotNull(worker.getName());
            assertNotNull(worker.getBirthday());
            assertTrue(worker.getSalary() >= 0);
            assertTrue(worker.getDepartmentID() > 0);
        }

    }

    @Test
    public void testGetWorkersByDepartmentId() throws Exception {

        List<Worker> workers = workerDAO.getWorkersByDepartmentId(1);
        assertNotNull(workers);
        assertTrue(workers.size() > 0);

        for(int i=0; i<workers.size(); i++){
            Worker worker = workers.get(i);

            assertNotNull(worker);
            assertTrue(worker.getDepartmentID() == 1);
            assertTrue(worker.getId() > 0);
            assertNotNull(worker.getName());
            assertNotNull(worker.getBirthday());
            assertTrue(worker.getSalary() >= 0);
        }

    }

    @Test
    public void testGetWorkersByDate() throws Exception {

        Date dateFrom = Date.valueOf("1993-12-08");
        Date dateTo = Date.valueOf("1993-12-08");

        List<Worker> workers = workerDAO.getWorkers(dateFrom, dateTo);
        assertNotNull(workers);
        assertTrue(workers.size() > 0);

        for(int i=0; i<workers.size(); i++) {
            Worker worker = workers.get(i);

            assertNotNull(worker);
            assertTrue(worker.getDepartmentID() > 0);
            assertTrue(worker.getId() > 0);
            assertNotNull(worker.getName());
            assertNotNull(worker.getBirthday());
            assertTrue(worker.getBirthday().equals(Date.valueOf("1993-12-08")));
            assertTrue(worker.getSalary() >= 0);
        }


        dateFrom = Date.valueOf("1990-01-01");
        dateTo = Date.valueOf("1994-12-31");

        workers = workerDAO.getWorkers(dateFrom, dateTo);

        assertNotNull(workers);
        assertTrue(workers.size() > 0);

        for(int i=0; i<workers.size(); i++) {
            Worker worker = workers.get(i);

            assertNotNull(worker);
            assertTrue(worker.getDepartmentID() > 0);
            assertTrue(worker.getId() > 0);
            assertNotNull(worker.getName());
            assertNotNull(worker.getBirthday());
            Date birthday = worker.getBirthday();
            assertTrue( birthday.compareTo(dateFrom) >= 0 &&  birthday.compareTo(dateTo) <= 0);
            assertTrue(worker.getSalary() >= 0);
        }

    }

    @Test
    public void testAddWorker() throws Exception {

        Worker worker = new Worker(10, "test worker", Date.valueOf("2016-01-01"), 5000);
        assertTrue(workerDAO.addWorker(worker));
        List<Worker> workers = workerDAO.getWorkersByDepartmentId(10);
        assertNotNull(workers);
        assertTrue(workers.size() == 1);

        Worker addedWorker = workers.get(0);
        assertNotNull(addedWorker);
        assertTrue(addedWorker.getDepartmentID() == 10);
        assertTrue(addedWorker.getId() > 0);
        assertEquals(addedWorker.getName(), "test worker");
        assertEquals(addedWorker.getBirthday(), Date.valueOf("2016-01-01"));
        assertTrue(addedWorker.getSalary() == 5000);

    }

    @Test
    public void testDeleteWorker() throws Exception {

        List<Worker> workers = workerDAO.getWorkers();
        assertNotNull(workers);
        int workersCountBefore = workers.size();

        assertTrue(workerDAO.deleteWorker(5));

        workers = workerDAO.getWorkers();
        assertNotNull(workers);

        assertEquals(workersCountBefore, workers.size() + 1);

    }

    @Test
    public void testUpdateWorker() throws Exception {

        Worker worker = new Worker(22, "updated worker", Date.valueOf("2016-01-01"), 5000);
        worker.setId(2);
        assertTrue(workerDAO.updateWorker(worker));

        List<Worker> workers = workerDAO.getWorkersByDepartmentId(22);

        assertNotNull(workers);
        assertTrue(workers.size() == 1);

        Worker updatedWorker = workers.get(0);

        assertNotNull(updatedWorker);
        assertTrue(worker.getDepartmentID() == 22);
        assertTrue(worker.getId() > 0);
        assertEquals(worker.getName(), "updated worker");
        assertEquals(worker.getBirthday(), Date.valueOf("2016-01-01"));
        assertTrue(worker.getSalary() == 5000);

    }
}