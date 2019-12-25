package org.penguin.project.tutorial.modelmapper;

import lombok.Data;

@Data
public class Order {

    private Customer customer;

    private Address billingAddress;
}
