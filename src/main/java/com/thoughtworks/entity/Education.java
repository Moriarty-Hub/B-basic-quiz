package com.thoughtworks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    private Long userId;

    @ManyToOne
    @JsonIgnore
    private User user;

    private Long year;
    @Size(min = 1, max = 256, message = "The length of title is invalid, it must within the range from 1 to 256")
    private String title;
    @Size(min = 1, max = 4096, message = "The length of description is invalid, it must within the range from 1 to 4096")
    private String description;
}
