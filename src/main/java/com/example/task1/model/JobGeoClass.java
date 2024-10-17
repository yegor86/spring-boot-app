package com.example.task1.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class JobGeoClass {
    
    @EmbeddedId
    private JobGeoClassId id;

    public JobGeoClass() {

    }
    
    public JobGeoClass(Long jobId, Long geoClassId) {
        this.id = new JobGeoClassId(jobId, geoClassId);
    }


    public JobGeoClassId getJobId() {
        return id;
    }

    public void setJobGeoClassId(JobGeoClassId id) {
        this.id = id;
    }
}
