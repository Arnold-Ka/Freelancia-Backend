package com.hackers.freelancia.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class VersionListening {

    private static final Logger log = LoggerFactory.getLogger(VersionListening.class);

    @PrePersist
    @PreUpdate
    public void beforeAnyUpdat(final BaseEntity entity){
        log.debug("Updating class: {}, from :{} to :{}",entity.getClass(), entity.getVersion(), entity.getVersion()+1L);
        entity.setVersion(entity.getVersion() + 1L);
    }
}
