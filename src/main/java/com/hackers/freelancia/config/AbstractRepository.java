package com.hackers.freelancia.config;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hackers.freelancia.entity.User;

public interface AbstractRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID>{
    List<T> findByStatut(Statut statut);
    List<T> findByStatutOrderByCreatedAtDesc(Statut statut);
    default List<T> findAllActive(){return findByStatut(Statut.ACTIVE);}
    default List<T> finAllActiveDesc(){return findByStatutOrderByCreatedAtDesc(Statut.ACTIVE);}
    default boolean isActive(ID id){return (boolean) findById(id).map(BaseEntity::isActive).orElse(false);}
    Optional<T> findTop1ByStatutOrderByCreatedAtDesc(Statut statut);
    Optional<T> findByIdAndStatut(ID id, Statut statut);
    List<T> findByCreatedAtBetweenAndStatut(Instant start, Instant end, Statut statut);
    boolean existsByIdAndStatut(ID id, Statut statut);
    List<T> findByStatutAndLastModifiedBy(Statut statut, String lastModifiedBy);
    List<T> findByStatutAndCreatedBy(Statut statut, User createdBy);
    Page<T> findByStatut(Statut statut, Pageable page);
    long countByStatut(Statut statut);
    default long countActives(){return this.countByStatut(Statut.ACTIVE);}

    @Modifying
    @Query(
        "UPDATE #{#entityName} e SET e.statut = ?2 WHERE e.id IN ?1"
    )
    int updateStatutInBulk(List<ID> ids, Statut newStatut);

}
