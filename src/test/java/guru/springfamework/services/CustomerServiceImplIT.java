package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.BootStrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    VendorRepository vendorRepository;
    @Before
    public void setUp() throws Exception {
        System.out.println("Loading Customer Data: ");
        System.out.println(customerRepository.findAll().size());
        BootStrap bootStrap = new BootStrap(categoryRepository,customerRepository,vendorRepository);
        bootStrap.run(); //load data
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerUpdateFirstName() throws Exception{
        String updatedName = "UpdatedName";
        Long id = getCustomerIdValue();

        Customer customer = customerRepository.getOne(id);
        assertNotNull(customer);
        String originalFirstName = customer.getFirstName();
        String originalLastName  = customer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedName);
        customerService.patchCustomer(id,customerDTO);
        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertThat(updatedName,equalTo(updatedCustomer.getFirstName()));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
    }

    public Long getCustomerIdValue(){
        List<Customer> customerList = customerRepository.findAll();
        System.out.println("Customers found : "+customerList.size());
        return customerList.get(0).getId();
    }
}
