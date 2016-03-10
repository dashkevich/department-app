package dashkevich.app.services;

import dashkevich.app.dao.WorkerDAO;
import dashkevich.app.model.Worker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class WorkerServiceImplTest {

    @InjectMocks
    private WorkerServiceImpl workerService;

    @Mock
    private WorkerDAO workerDAO;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetWorkers() throws Exception {

        List<Worker> workerList = Arrays.asList(
                new Worker(1,"worker 1", Date.valueOf("2000-01-01"), 100),
                new Worker(1,"worker 2", Date.valueOf("2000-01-02"), 200),
                new Worker(1,"worker 3", Date.valueOf("2000-01-03"), 300),
                new Worker(1,"worker 4", Date.valueOf("2000-01-04"), 400),
                new Worker(1,"worker 5", Date.valueOf("2000-01-05"), 500)
                );

        when(workerDAO.getWorkersByDepartmentId(anyInt())).thenReturn(workerList);

        List<Worker> workers = workerService.getWorkers(1, Date.valueOf("2000-01-02"), Date.valueOf("2000-01-04"));
        assertNotNull(workers);
        assertTrue(workers.size() == 3);

        verify(workerDAO).getWorkersByDepartmentId(1);
    }
}