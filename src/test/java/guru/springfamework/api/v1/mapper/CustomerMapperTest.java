package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import junit.framework.TestCase;
import org.junit.Test;

public class CustomerMapperTest extends TestCase {

    CustomerMapper customerMapper;


    @Test
    public void testCustomerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setFirstName("Adam");
        customer.setLastName("Levine");

        CustomerDTO customerDTO = CustomerMapper.INSTANCE.customerToCustomerDTO(customer);

        assertNotNull(customerDTO);
        assertEquals("Adam",customerDTO.getFirstName());
    }
}