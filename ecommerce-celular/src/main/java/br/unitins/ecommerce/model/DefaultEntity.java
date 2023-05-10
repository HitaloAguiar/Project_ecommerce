package br.unitins.ecommerce.model;

import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PreUpdate;

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
