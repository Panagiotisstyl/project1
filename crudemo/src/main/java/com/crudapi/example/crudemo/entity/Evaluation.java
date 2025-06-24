package com.crudapi.example.crudemo.entity;



import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name="evaluation")
public class Evaluation {

    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private int id;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="employee_id")
    private Employee employee;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="job_id")
    private Jobs job;

    @Column(name="years_of_empl")
    private int years_of_Empl;

    @Column(name="score")
    private int score;



}