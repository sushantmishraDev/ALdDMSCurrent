package com.dms.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "service_logs")
public class ServiceLogs {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "service_logs_seq_generator"
    )
    @SequenceGenerator(
        name = "service_logs_seq_generator",
        sequenceName = "service_logs_seq",
        allocationSize = 1
    )
    @Column(name = "sl_id")
    private Long id;

    @Column(name = "sl_service_name", length = 255)
    private String serviceName;

    @Column(name = "sl_log_message", columnDefinition = "TEXT")
    private String logMessage;

    @Column(name = "sl_user_id")
    private Long userId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sl_log_date")
    private Date logDate;

    // Constructors
    public ServiceLogs() {
    }
    
    
    

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    @Override
    public String toString() {
        return "ServiceLogs{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", logMessage='" + logMessage + '\'' +
                ", userId=" + userId +
                ", logDate=" + logDate +
                '}';
    }
}
