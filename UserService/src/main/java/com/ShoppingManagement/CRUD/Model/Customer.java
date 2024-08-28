package com.ShoppingManagement.CRUD.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cstId;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$",
            message = "email is not valid")
    private String email;

    @Pattern(regexp = "^[a-zA-Z]{3,12}$",
            message = "first name must be of 3 to 12 length with no special characters")
    private String firstname;

    @Pattern(regexp = "^[a-zA-Z]{3,12}$",
            message = "last name must be of 3 to 12 length with no special characters")
    private String lastname;
    @Pattern(regexp="^[6-9][0-9]{9}",message="Length must be 10 and starts with 6-9")
    private String phone_no;
}
