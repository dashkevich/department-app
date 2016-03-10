package dashkevich.app.web.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dashkevich.app.model.Worker;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;


public class WorkerRestClientTest {

    private WorkerRestClient workerRestClient;

    private MockRestServiceServer server;

    @Before
    public void setUp() throws Exception {
        workerRestClient = new WorkerRestClient();
        server = MockRestServiceServer.createServer(workerRestClient.getRestTemplate());
    }


    @Test
    public void testGetWorkers() throws Exception {

        List<Worker> workers = new ArrayList<Worker>();
        workers.add(new Worker(1, "worker 1", Date.valueOf("2010-10-10"), 100));
        workers.add(new Worker(2, "worker 2", Date.valueOf("2010-10-11"), 200));

        server.expect(requestTo("http://localhost:8080/rest/workers"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(new ObjectMapper().writeValueAsString(workers), MediaType.APPLICATION_JSON));

        workers = workerRestClient.getWorkers();
        assertNotNull(workers);
        assertTrue(workers.size() == 2);

        server.verify();
    }

    @Test
    public void testGetWorkersBadRequest() throws Exception {

        server.expect(requestTo("http://localhost:8080/rest/workers"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        assertNotNull(workerRestClient.getWorkers());

        server.verify();
    }

    @Test
    public void testGetWorkers1() throws Exception {
        List<Worker> workers = new ArrayList<Worker>();
        workers.add(new Worker(1, "worker 1", Date.valueOf("2010-10-10"), 100));
        workers.add(new Worker(2, "worker 2", Date.valueOf("2010-10-11"), 200));

        server.expect(requestTo("http://localhost:8080/rest/workers?dateFrom=2010-10-10&dateTo=2010-10-11"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(new ObjectMapper().writeValueAsString(workers), MediaType.APPLICATION_JSON));

        workers = workerRestClient.getWorkers(Date.valueOf("2010-10-10"), Date.valueOf("2010-10-11"));
        assertNotNull(workers);
        assertTrue(workers.size() == 2);

        server.verify();
    }

    @Test
    public void testGetWorkers1BadRequest() throws Exception {

        server.expect(requestTo("http://localhost:8080/rest/workers?dateFrom=2010-10-10&dateTo=2010-10-11"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        assertNotNull(workerRestClient.getWorkers(Date.valueOf("2010-10-10"), Date.valueOf("2010-10-11")));

        server.verify();
    }

    @Test
    public void testGetWorkers2() throws Exception {

        List<Worker> workers = new ArrayList<Worker>();
        workers.add(new Worker(1, "worker 1", Date.valueOf("2010-10-10"), 100));
        workers.add(new Worker(2, "worker 2", Date.valueOf("2010-10-11"), 200));

        server.expect(requestTo("http://localhost:8080/rest/workers?did=1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(new ObjectMapper().writeValueAsString(workers), MediaType.APPLICATION_JSON));

        workers = workerRestClient.getWorkers(1);
        assertNotNull(workers);
        assertTrue(workers.size() == 2);

        server.verify();
    }

    @Test
    public void testGetWorkers2BadRequest() throws Exception {

        server.expect(requestTo("http://localhost:8080/rest/workers?did=1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        assertNotNull(workerRestClient.getWorkers(1));

        server.verify();
    }

    @Test
    public void testGetWorkers3() throws Exception {
        List<Worker> workers = new ArrayList<Worker>();
        workers.add(new Worker(1, "worker 1", Date.valueOf("2010-10-10"), 100));
        workers.add(new Worker(2, "worker 2", Date.valueOf("2010-10-11"), 200));

        server.expect(requestTo("http://localhost:8080/rest/workers?did=1&dateFrom=2010-10-10&dateTo=2010-10-11"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(new ObjectMapper().writeValueAsString(workers), MediaType.APPLICATION_JSON));

        workers = workerRestClient.getWorkers(1, Date.valueOf("2010-10-10"), Date.valueOf("2010-10-11"));
        assertNotNull(workers);
        assertTrue(workers.size() == 2);

        server.verify();
    }

    @Test
    public void testGetWorkers3BadRequest() throws Exception {

        server.expect(requestTo("http://localhost:8080/rest/workers?did=1&dateFrom=2010-10-10&dateTo=2010-10-11"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        assertNotNull(workerRestClient.getWorkers(1, Date.valueOf("2010-10-10"), Date.valueOf("2010-10-11")));

        server.verify();
    }

    @Test
    public void testAddWorker() throws Exception {

        Worker worker = new Worker(1, "test", Date.valueOf("2013-12-20"), 500);
        server.expect(requestTo("http://localhost:8080/rest/workers"))
                .andExpect(method(HttpMethod.POST))
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(worker)))
                .andRespond(withStatus(HttpStatus.CREATED));

        assertTrue(workerRestClient.addWorker(worker));

        server.verify();
    }

    @Test
    public void testAddWorkerBadRequest() throws Exception {

        Worker worker = new Worker(1, "test", Date.valueOf("2013-12-20"), 500);
        server.expect(requestTo("http://localhost:8080/rest/workers"))
                .andExpect(method(HttpMethod.POST))
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(worker)))
                .andRespond(withBadRequest());

        assertFalse(workerRestClient.addWorker(worker));

        server.verify();
    }

    @Test
    public void testDeleteWorker() throws Exception {

        server.expect(requestTo("http://localhost:8080/rest/workers/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.NO_CONTENT));

        assertTrue(workerRestClient.deleteWorker(1));

        server.verify();
    }

    @Test
    public void testDeleteWorkerBadRequest() throws Exception {

        server.expect(requestTo("http://localhost:8080/rest/workers/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withBadRequest());

        assertFalse(workerRestClient.deleteWorker(1));

        server.verify();
    }

    @Test
    public void testUpdateWorker() throws Exception {

        Worker worker = new Worker(1, "test", Date.valueOf("2013-12-20"), 500);
        server.expect(requestTo("http://localhost:8080/rest/workers"))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(worker)))
                .andRespond(withStatus(HttpStatus.NO_CONTENT));

        assertTrue(workerRestClient.updateWorker(worker));

        server.verify();
    }

    @Test
    public void testUpdateWorkerBadRequest() throws Exception {

        Worker worker = new Worker(1, "test", Date.valueOf("2013-12-20"), 500);
        server.expect(requestTo("http://localhost:8080/rest/workers"))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(worker)))
                .andRespond(withBadRequest());

        assertFalse(workerRestClient.updateWorker(worker));

        server.verify();
    }
}