package com.tnsif.customerservice.service;

import com.tnsif.customerservice.entity.Customer;
import com.tnsif.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
        return customerRepository.findById(id).get();
    }

    public Customer updateCustomer(int id, Customer updatedCustomer) {
        return customerRepository.findById(id)
            .map(existing -> {
                existing.setName(updatedCustomer.getName());
                existing.setEmail(updatedCustomer.getEmail());
                existing.setPhone(updatedCustomer.getPhone());
                existing.setPassword(updatedCustomer.getPassword());
                return customerRepository.save(existing);
            }).orElse(null);
    }

    public void softDelete(int id) {
        customerRepository.findById(id).ifPresent(c -> {
            c.setIsActive(false);
            customerRepository.save(c);
        });
    }
}
