package com.hackers.freelancia.entity;

import java.util.Objects;

import com.hackers.freelancia.config.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "skills")

public class Skills extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "icon", nullable = true)
    private String icon;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Skills other = (Skills) obj;
        return Objects.equals(getId(), other.getId());

    }
    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
