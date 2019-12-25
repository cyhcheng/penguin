package org.penguin.project.tutorial.modelmapper;

import lombok.Data;

@Data
public class OrderDTO {

    private String customerFirstName;

    private String customerLastName;

    private String billingStreet;

    private String billingCity;
}
