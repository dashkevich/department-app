package dashkevich.app.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dashkevich.app.model.Worker;
import dashkevich.app.web.services.WorkerRestClient;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;



public class WorkerAppControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    WorkerAppController workerAppController;

    @Mock
    private WorkerRestClient workerRestClient;

    @Before
    public void initMocks() {

        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(workerAppController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void testWorkers() throws Exception {

    }

    @Test
    public void testAddWorkers() throws Exception {

        when(workerRestClient.addWorker(any(Worker.class))).thenReturn(true);

        ResultActions result = mockMvc.perform(post("/workers/add")
                .param("workerName", "new worker")
                .param("workerDepartment", "2")
                .param("workerBirthday", "2016-01-01")
                .param("workerSalary", "200")
        );
        result.andExpect(status().is2xxSuccessful())
                .andDo(print());
        verify(workerRestClient).addWorker(any(Worker.class));

    }

    @Test
    public void testUpdateWorkers() throws Exception {
        when(workerRestClient.updateWorker(any(Worker.class))).thenReturn(true);

        ResultActions result = mockMvc.perform(post("/workers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Worker(2,"name", Date.valueOf("2010-10-10"), 123)))
        );
        result.andExpect(status().is2xxSuccessful())
                .andDo(print());
        verify(workerRestClient).updateWorker(any(Worker.class));
    }

    @Test
    public void testDeleteWorkers() throws Exception {

        when(workerRestClient.deleteWorker(1)).thenReturn(true);
        ResultActions result = mockMvc.perform(post("/workers/delete")
            .param("id", "1")
        );
        result.andExpect(status().is2xxSuccessful())
                .andDo(print());
        verify(workerRestClient).deleteWorker(1);

    }
}