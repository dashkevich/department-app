package dashkevich.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dashkevich.app.model.Department;
import dashkevich.app.services.DepartmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


public class DepartmentRestControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    DepartmentRestController restController;

    @Mock
    private DepartmentService departmentService;

    @Before
    public void initMocks() {

        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(restController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void testGetDepartments() throws Exception {

        List<HashMap<String, String>> departments = new LinkedList<HashMap<String, String>>();

        for(int i=0; i<5; i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", Integer.valueOf(i).toString());
            map.put("name", "department " + Integer.valueOf(i).toString());
            map.put("salary", Integer.valueOf((i+1)*100).toString());
            departments.add(map);
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(departments);


        when(departmentService.getDepartments()).thenReturn(departments);

        ResultActions result = mockMvc.perform(get("/departments").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(jsonString));

        verify(departmentService).getDepartments();
    }

    @Test
    public void testUpdateDepartment() throws Exception {

        when(departmentService.updateDepartment(any(Department.class))).thenReturn(true);

        Department department = new Department(1, "NewDepartmentName");
        String jsonString = new ObjectMapper().writeValueAsString(department);

        mockMvc.perform( put("/departments")
                            .content(jsonString)
                            .contentType(MediaType.APPLICATION_JSON)
                        )
                            .andDo(print())
                            .andExpect(status().is2xxSuccessful());

        verify(departmentService).updateDepartment(any(Department.class));
    }

    @Test
    public void testAddDepartment() throws Exception {

        when(departmentService.addDepartment("NewDepartment")).thenReturn(true);

        mockMvc.perform(post("/departments/NewDepartment"))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(departmentService).addDepartment(any(String.class));
    }

    @Test
    public void testDeleteDepartment() throws Exception {

        when(departmentService.deleteDepartment(1)).thenReturn(true);

        mockMvc.perform(delete("/departments/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(departmentService).deleteDepartment(1);

    }
}