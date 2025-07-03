package com.crudapi.example.crudemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Entity
@ToString(exclude = "evaluation")
@Table(name="jobs")
public class Jobs {

    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name="job_desc")
    private String job_Desc;

   /* @JsonIgnore
    @OneToOne(mappedBy="job")
    private Evaluation evaluation;*/

    @Builder
    public Jobs(int id,String job_Desc) {
        this.id = id;
        this.job_Desc = job_Desc;
        //this.evaluation = evaluation;
    }
}
