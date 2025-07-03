package com.crudapi.example.crudemo.entity;



import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name="evaluation")
public class Evaluation {

    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private int id;

    @OneToOne
    @JoinColumn(name="employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name="job_id")
    private Jobs job;

    @Column(name="yearsOfEmpl")
    private int yearsOfEmpl;

    @Column(name="score")
    private int score;


    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", employeeId=" + employee.getId() +
                ", jobId=" + job.getId() +
                ", yearsOfEmpl=" + yearsOfEmpl +
                ", score=" + score +
                '}';
    }
}