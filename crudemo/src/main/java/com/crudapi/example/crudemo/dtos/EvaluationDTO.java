package com.crudapi.example.crudemo.dtos;

import com.crudapi.example.crudemo.entity.Evaluation;

public class EvaluationDTO {

    private int id;
    private int employeeId;
    private int jobId;
    private int years_of_empl;
    private int score;

    public EvaluationDTO(Evaluation evaluation) {

        this.id = evaluation.getId();
        this.employeeId = evaluation.getEmployee().getId();
        this.jobId = evaluation.getJob().getId();
        this.years_of_empl=evaluation.getYears_of_Empl();
        this.score=evaluation.getScore();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getYears_of_empl() {
        return years_of_empl;
    }

    public void setYears_of_empl(int years_of_empl) {
        this.years_of_empl = years_of_empl;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
