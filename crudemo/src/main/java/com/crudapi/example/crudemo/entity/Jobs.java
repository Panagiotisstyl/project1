package com.crudapi.example.crudemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name="jobs")
public class Jobs {

    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name="job_desc")
    private String job_Desc;

    @JsonIgnore
    @OneToOne(mappedBy="job")
    private Evaluation evaluation;

    public Jobs() {}

    public Jobs(String job_Desc) {
        this.job_Desc = job_Desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob_Desc() {
        return job_Desc;
    }

    public void setJob_Desc(String job_Desc) {
        this.job_Desc = job_Desc;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {
        return "Jobs{" +
                "id=" + id +
                ", job_Desc='" + job_Desc + '\'' +
                '}';
    }
}
