package com.tnsif.customerservice.service;

import com.tnsif.customerservice.entity.Customer;
import com.tnsif.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        customer.setId(0);
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(int id) {
        Optional<Customer> opt = customerRepository.findById(id);
        return opt.orElse(null);
    }

    public Customer updateCustomer(int id, Customer updatedCustomer) {
        return customerRepository.findById(id)
            .map(existing -> {
                existing.setName(updatedCustomer.getName());
                existing.setEmail(updatedCustomer.getEmail());
                existing.setPhone(updatedCustomer.getPhone());
                existing.setPassword(updatedCustomer.getPassword());
                // handle new field
                existing.setAddress(updatedCustomer.getAddress());
                return customerRepository.save(existing);
            }).orElse(null);
    }

    // Now performs hard delete since isActive no longer exists
    public void deleteCustomer(int id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        }
    }
}
