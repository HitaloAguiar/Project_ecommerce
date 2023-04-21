package br.unitins.ecommerce.model;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataInsert;

    private LocalDateTime dataUpdate;

    @PostPersist
    private void gerarDataInsert() {

        dataInsert = LocalDateTime.now();
    }

    @PreUpdate
    private void gerarDataUpdate() {

        dataUpdate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataInsert() {
        return dataInsert;
    }

    public LocalDateTime getDataUpdate() {
        return dataUpdate;
    }
}
