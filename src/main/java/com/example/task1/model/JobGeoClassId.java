package com.example.task1.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JobGeoClassId implements Serializable {

    private Long jobId;
    private Long geoClassId;

    public JobGeoClassId() {}

    public JobGeoClassId(Long jobId, Long geoClassId) {
        this.jobId = jobId;
        this.geoClassId = geoClassId;
    }

    // Getters and Setters
    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getGeoClassId() {
        return geoClassId;
    }

    public void setGeoClassId(Long geoClassId) {
        this.geoClassId = geoClassId;
    }

    // Override equals and hashCode for composite key comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobGeoClassId that = (JobGeoClassId) o;
        return Objects.equals(jobId, that.jobId) && Objects.equals(geoClassId, that.geoClassId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, geoClassId);
    }
}

