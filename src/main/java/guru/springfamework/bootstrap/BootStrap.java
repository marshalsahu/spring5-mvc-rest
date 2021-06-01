package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;

    private CustomerRepository customerRepository;

    public BootStrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(nuts);

        System.out.println("Categories Loaded = "+categoryRepository.count());

        Customer customer1 = new Customer();
        customer1.setFirstName("Adam");
        customer1.setLastName("Levine");
        Customer customer2 = new Customer();
        customer2.setFirstName("chille");
        customer2.setLastName("Doble");
        Customer customer3 = new Customer();
        customer3.setFirstName("Anam");
        customer3.setLastName("zang");
        Customer customer4 = new Customer();
        customer4.setFirstName("Adrian");
        customer4.setLastName("Gangtok");
        Customer customer5 = new Customer();
        customer5.setFirstName("Aaahan");
        customer5.setLastName("Losto");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer4);
        customerRepository.save(customer3);
        customerRepository.save(customer5);

        System.out.println("Customers loaded:  "+customerRepository.count());

    }
}
