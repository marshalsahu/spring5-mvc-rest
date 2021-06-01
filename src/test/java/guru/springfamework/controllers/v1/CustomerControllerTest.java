package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.domain.Customer;
import guru.springfamework.services.CustomerService;
import guru.springfamework.services.ResourceNotFoundException;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {
    private static String BASE_URL="/api/v1/customers";

    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;

    MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getAllCustomers() throws Exception {

        CustomerDTO customerDTO1 = new CustomerDTO();
        CustomerDTO customerDTO2 = new CustomerDTO();
        List<CustomerDTO> customerDTOList = Arrays.asList(customerDTO1,customerDTO2);

        when(customerService.getCustomers()).thenReturn(customerDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers",hasSize(2)));
    }

    @Test
    void getCustomerById() {
        //todo
    }

    @Test
    void createNewCustomerTest() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Adam");
        customerDTO.setLastName("Levine");
        customerDTO.setId(1L);

        CustomerDTO savedCustomerDTO = new CustomerDTO();
        savedCustomerDTO.setFirstName(customerDTO.getFirstName());
        savedCustomerDTO.setLastName(customerDTO.getLastName());
        savedCustomerDTO.setId(customerDTO.getId());
        savedCustomerDTO.setCustomerUrl("/api/v1/customers/"+savedCustomerDTO.getId());

        when(customerService.addNewCustomer(customerDTO)).thenReturn(savedCustomerDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",equalTo("Adam")));
    }
    @Test
    void testPatchCustomer() throws Exception{
        String updatedName= "Malnick";
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedName);

        CustomerDTO updatedCustomerDTO = new CustomerDTO();
        updatedCustomerDTO.setFirstName(customerDTO.getFirstName());
        updatedCustomerDTO.setLastName("Flinstone");
        updatedCustomerDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.patchCustomer(anyLong(),any(CustomerDTO.class))).thenReturn(updatedCustomerDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/customers/1").content(asJsonString(customerDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.firstName",equalTo("Malnick")))
                        .andExpect(jsonPath("$.lastName",equalTo("Flinstone")))
                        .andExpect(jsonPath("$.customer_url",equalTo("/api/v1/customers/1")));
    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(customerService,times(1)).deleteCustomerById(anyLong());
    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}