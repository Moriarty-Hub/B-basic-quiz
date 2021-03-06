package com.thoughtworks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    @Size(min = 1, max = 128, message = "The length of name is invalid, it must within the range from 1 to 128")
    private String name;
    @Min(value = 17, message = "The value of age is invalid, it must be greater than 16")
    private long age;
    @Size(min = 8, max = 512, message = "The length of avatar URL is invalid, it must within the range from 8 to 512")
    private String avatar;
    @Size(max = 1024, message = "The length of description is invalid, it cannot exceed 1024 bytes")
    private String description;
}
