package com.example.deploydocker.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
@EntityListeners(AuditingEntityListener.class)
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "description")
    private String description;
    @Column(name = "create_at")
    @CreatedDate
    private Instant createAt;
    @Column(name = "update_at")
    @LastModifiedDate
    private Instant updateAt;


}