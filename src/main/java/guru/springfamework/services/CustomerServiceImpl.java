package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll().stream()
                .map(customer ->{
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
                    return customerDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(!customerOptional.isPresent()){
            throw new ResourceNotFoundException();
        }
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerOptional.get());
        customerDTO.setCustomerUrl(getCustomerUrl(customerDTO.getId()));
        return customerDTO;
    }

    @Override
    public CustomerDTO addNewCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.INSTANCE.customerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = customerMapper.INSTANCE.customerToCustomerDTO(savedCustomer);
        savedCustomerDTO.setCustomerUrl(getCustomerUrl(savedCustomerDTO.getId()));
        return savedCustomerDTO;
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            if(customerDTO.getFirstName()!= null){
                customer.setFirstName(customerDTO.getFirstName());
            }
            if(customerDTO.getLastName()!=null){
                customer.setLastName(customerDTO.getLastName());
            }
            CustomerDTO customerDTO1 = customerMapper.INSTANCE.customerToCustomerDTO(customerRepository.save(customer));
            customerDTO1.setCustomerUrl(getCustomerUrl(customerDTO.getId()));
            return customerDTO1;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) throws NotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isPresent()){
            customerRepository.deleteById(id);
        }
        else {
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }

    @Override
    public CustomerDTO saveCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.INSTANCE.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        Customer returnedCustomer = customerRepository.save(customer);
        CustomerDTO returnedCustomerDTO = customerMapper.INSTANCE.customerToCustomerDTO(returnedCustomer);
        returnedCustomerDTO.setCustomerUrl(getCustomerUrl(returnedCustomerDTO.getId()));
        return returnedCustomerDTO;

    }

    public String getCustomerUrl(Long id){
        return CustomerController.BASE_URL+id;
    }
}
