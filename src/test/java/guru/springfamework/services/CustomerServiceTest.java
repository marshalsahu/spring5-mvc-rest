package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceTest extends TestCase {

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    CustomerMapper customerMapper=CustomerMapper.INSTANCE;


    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository,customerMapper);
    }

    @Test
    public void testgetCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Adam");
        customer1.setLastName("Levine");
        customer1.setId(1L);

        Customer customer2 = new Customer();
        customer2.setFirstName("Enrique");
        customer2.setLastName("Igleasis");
        customer2.setId(2L);

        List<Customer> customerList = Arrays.asList(customer1,customer2);

        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerDTO> customerDTOS = customerService.getCustomers();

        assertEquals(2, customerDTOS.size());
    }

  @Test
    public void testCreateNewCustomer(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Adam");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setId(1L);
        savedCustomer.setLastName(customerDTO.getLastName());

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO returnedCustomerDTO = customerService.addNewCustomer(customerDTO);

        assertEquals(customerDTO.getFirstName(),returnedCustomerDTO.getFirstName());
        assertEquals(savedCustomer.getId(),returnedCustomerDTO.getId());
    }
   @Test
    public void testdeleteCustomerById(){
        Long id =1L;
        customerRepository.deleteById(id);
        verify(customerRepository,times(1)).deleteById(anyLong());
    }


}