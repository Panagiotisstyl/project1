package com.crudapi.example.crudemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name="employee")
public class Employee {

    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @JsonIgnore
    @OneToOne(mappedBy="employee")
    private Evaluation evaluation;

    @Builder
    public Employee(int id, String firstName, String lastName, String email, Evaluation evaluation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.evaluation = evaluation;
    }
}
