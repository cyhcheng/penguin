package org.penguin.project.tutorial;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.penguin.project.tutorial.modelmapper.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MainApplicationTests {

    private static ModelMapper modelMapper;

    @BeforeAll
    static void init() {
        modelMapper = new ModelMapper();
    }

    @Test
    void testModelMapper(){
        Order order = new Order();

        Address address =new Address();
        address.setCity("城市");
        address.setStreet("街道");
        order.setBillingAddress(address);

        Customer customer = new Customer();
        Name name = new Name();
        name.setFirstName("名字");
        name.setLastName("姓");
        customer.setName(name);
        order.setCustomer(customer);

        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        assertEquals(order.getCustomer().getName().getFirstName(), orderDTO.getCustomerFirstName());
        assertEquals(order.getCustomer().getName().getLastName(), orderDTO.getCustomerLastName());
        assertEquals(order.getBillingAddress().getStreet(), orderDTO.getBillingStreet());
        assertEquals(order.getBillingAddress().getCity(), orderDTO.getBillingCity());
    }
}
