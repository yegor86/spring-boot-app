package com.example.task1.model;

public class RegisterJobResult {
    
    private Long jobId;

    private String status;

    public RegisterJobResult() {

    }

    public RegisterJobResult(Long jobId, String status) {
        this.jobId = jobId;
        this.status = status;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJob(Long jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
