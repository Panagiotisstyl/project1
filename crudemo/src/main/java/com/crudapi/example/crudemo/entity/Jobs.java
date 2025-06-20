package com.crudapi.example.crudemo.entity;

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

    @Override
    public String toString() {
        return "Jobs{" +
                "id=" + id +
                ", job_Desc='" + job_Desc + '\'' +
                '}';
    }
}
