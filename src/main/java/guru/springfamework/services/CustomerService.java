package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;
import javassist.NotFoundException;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO addNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomer(Long id,CustomerDTO customerDTO);

    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id) throws NotFoundException;
}
