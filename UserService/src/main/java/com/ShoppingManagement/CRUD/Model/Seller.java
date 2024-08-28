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
@Table(name="seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sellerId;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$",
            message = "email is not valid")
    private String email;

    @Pattern(regexp = "^[a-zA-Z]{3,12}$",
            message = "first name must be of 3 to 12 length with no special characters")
    private String firstname;

    @Pattern(regexp = "^[a-zA-Z]{3,12}$",
            message = "last name must be of 3 to 12 length with no special characters")
    private String lastname;
}
