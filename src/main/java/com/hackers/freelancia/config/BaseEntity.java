package com.hackers.freelancia.config;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.time.Instant;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    protected String id;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    protected Instant createdAt;
    @Column(name = "updated_at")
    @LastModifiedDate
    protected Instant updatedAt;
    @Column(name = "created_by", updatable = false)
    @CreatedBy
    protected String createdBy;
    @Column(name = "last_modified_by")
    @LastModifiedBy
    protected String lastModifiedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    protected Statut statut;
    @Column(name = "version")
    protected Long version;

    public BaseEntity() {
        this.statut = Statut.ACTIVE;
        this.version = 1L;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
    public Statut getStatut() {
        return statut;
    }
    public void setStatut(Statut statut) {
        this.statut = statut;
    }
    public Long getVersion() {
        return version;
    }
    public void setVersion(Long version) {
        this.version = version;
    }
    public boolean isActive(){
        return this.statut == Statut.ACTIVE;
    }
    public boolean isDeleted(){
        return this.statut == Statut.DELETED;
    }
    public boolean isSuspended(){
        return this.statut == Statut.SUSPENDED;
    }
    public boolean isInactive(){
        return this.statut == Statut.INACTIVE;
    }
    
}
