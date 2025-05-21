package com.taskmanagementsystem.model;

import java.sql.Timestamp;
import java.util.Date;

public class Project {
    private int id;
    private String name;
    private String description;
    private int managerId;
    private String pStatus;
    private Date startDate;
    private Date endDate;
    private String priority;
    private int progressPercentage;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    
    private String managerName;
    public void setManagerName(String name)
    {
    	this.managerName=name;
    }
    
    public String getManagerName()
    {
    	return managerName;
    }
    

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getManagerId() {
        return managerId;
    }
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
    public String getpStatus() {
        return pStatus;
    }
    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public int getProgressPercentage() {
        return progressPercentage;
    }
    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

