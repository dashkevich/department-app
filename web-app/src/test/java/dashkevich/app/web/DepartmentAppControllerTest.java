package dashkevich.app.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dashkevich.app.model.Department;
import dashkevich.app.web.services.DepartmentsRestClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class DepartmentAppControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    DepartmentAppController departmentAppController;

    @Mock
    private DepartmentsRestClient departmentsRestClient;

    @Before
    public void initMocks() {

        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(departmentAppController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }


    @Test
    public void testDepartments() throws Exception {

        when(departmentsRestClient.getDepartments()).thenReturn(new ArrayList<HashMap<String, String>>());
        assertEquals(departmentAppController.departments(new HashMap<String, Object>()), "departments");
        verify(departmentsRestClient).getDepartments();

    }

    @Test
    public void testAddDepartment() throws Exception {

        when(departmentsRestClient.addDepartment(any(String.class))).thenReturn(true);

        ResultActions result = mockMvc.perform(post("/departments/add").param("name", "new department"));
        result.andExpect(status().is2xxSuccessful())
                .andDo(print());
        verify(departmentsRestClient).addDepartment("new department");

    }

    @Test
    public void testUpdateDepartment() throws Exception {

        Department department = new Department(1, "department 1");
        when(departmentsRestClient.updateDepartment(any(Department.class))).thenReturn(true);

        ResultActions result = mockMvc.perform(post("/departments/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(department))
        );
        result.andExpect(status().is2xxSuccessful())
                .andDo(print());
        verify(departmentsRestClient).updateDepartment(any(Department.class));

    }

    @Test
    public void testDeleteDepartment() throws Exception {
        when(departmentsRestClient.deleteDepartment(1)).thenReturn(true);

        mockMvc.perform(post("/departments/delete").param("id", "1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        verify(departmentsRestClient).deleteDepartment(1);
    }
}