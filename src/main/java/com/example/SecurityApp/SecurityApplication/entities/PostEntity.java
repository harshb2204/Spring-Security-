package com.example.SecurityApp.SecurityApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Data


public class PostEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String title;


    private String description;

    @PrePersist
    void beforeSave(){

    }

    @PreUpdate
    void beforeUpdate(){

    }
    @PreRemove
    void beforeDelete(){

    }


}
