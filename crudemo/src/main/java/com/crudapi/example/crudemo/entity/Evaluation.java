package com.crudapi.example.crudemo.entity;

import jakarta.persistence.*;

@Entity
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

    public Evaluation() {}


    public Evaluation(Employee employee, Jobs job, int years_of_Empl, int score) {
        this.employee = employee;
        this.job = job;
        this.years_of_Empl = years_of_Empl;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Jobs getJob() {
        return job;
    }

    public void setJob(Jobs job) {
        this.job = job;
    }

    public int getYears_of_Empl() {
        return years_of_Empl;
    }

    public void setYears_of_Empl(int years_of_Empl) {
        this.years_of_Empl = years_of_Empl;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", employee=" + employee +
                ", job=" + job +
                ", years_of_Empl=" + years_of_Empl +
                ", score=" + score +
                '}';
    }
}