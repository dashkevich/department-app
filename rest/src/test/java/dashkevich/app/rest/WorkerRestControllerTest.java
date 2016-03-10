package dashkevich.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dashkevich.app.model.Worker;
import dashkevich.app.services.WorkerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


public class WorkerRestControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    WorkerRestController restController;

    @Mock
    private WorkerService workerService;

    @Before
    public void initMocks() {

        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(restController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }


    @Test
    public void testGetWorkers() throws Exception {

        List<Worker> workers = new ArrayList<Worker>();
        for(int i=0; i<10; i++){
            Worker worker = new Worker();

            worker.setId(i);
            worker.setName("worker " + i);
            worker.setDepartmentID(i);
            worker.setBirthday(Date.valueOf("2016-03-02"));
            worker.setSalary((i+1)*100);

            workers.add(worker);
        }

        when(workerService.getWorkers()).thenReturn(workers);
        when(workerService.getWorkers(1)).thenReturn(workers);
        when(workerService.getWorkers(any(Date.class), any(Date.class))).thenReturn(workers);
        when(workerService.getWorkers(anyInt(), any(Date.class), any(Date.class))).thenReturn(workers);

        ResultActions result = mockMvc.perform(get("/workers").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(new ObjectMapper().writeValueAsString(workers)));
        verify(workerService).getWorkers();

        result = mockMvc.perform(get("/workers").accept(MediaType.APPLICATION_JSON)
                .param("did", "1"));
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(workerService).getWorkers(1);

        result = mockMvc.perform(get("/workers").accept(MediaType.APPLICATION_JSON)
                .param("dateFrom", "2016-03-03"));
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(workerService).getWorkers(Date.valueOf("2016-03-03"), Date.valueOf("2016-03-03") );

        result = mockMvc.perform(get("/workers").accept(MediaType.APPLICATION_JSON)
                .param("dateFrom", "2016-03-05")
                .param("dateTo", "2016-03-09"));
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(workerService).getWorkers(Date.valueOf("2016-03-05"), Date.valueOf("2016-03-09") );

        result = mockMvc.perform(get("/workers").accept(MediaType.APPLICATION_JSON)
                .param("did", "1")
                .param("dateFrom", "2016-03-01")
                .param("dateTo", "2016-03-05"));
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        verify(workerService).getWorkers(1, Date.valueOf("2016-03-01"), Date.valueOf("2016-03-05") );

    }

    @Test
    public void testUpdateWorker() throws Exception {

        Worker worker = new Worker(1, "updated", Date.valueOf("2001-01-01"), 100);

        when(workerService.updateWorker(any(Worker.class))).thenReturn(true);

        mockMvc.perform( put("/workers")
                .content(new ObjectMapper().writeValueAsString(worker))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(workerService).updateWorker(any(Worker.class));
    }

    @Test
    public void testAddWorker() throws Exception {

        Worker worker = new Worker(2, "added", Date.valueOf("2010-10-20"), 200);

        when(workerService.addWorker(any(Worker.class))).thenReturn(true);

        mockMvc.perform( post("/workers")
                .content(new ObjectMapper().writeValueAsString(worker))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(workerService).addWorker(any(Worker.class));

    }

    @Test
    public void testDeleteWorker() throws Exception {

        when(workerService.deleteWorker(1)).thenReturn(true);

        mockMvc.perform( delete("/workers/1") )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(workerService).deleteWorker(1);

    }
}