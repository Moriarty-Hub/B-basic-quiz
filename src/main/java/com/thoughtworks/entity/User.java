package com.thoughtworks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String name;
    private long age;
    private String avatar;
    private String description;
}
