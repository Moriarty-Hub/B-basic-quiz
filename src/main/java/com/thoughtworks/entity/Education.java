package com.thoughtworks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Education {
    private Long userId;
    private Long year;
    @Size(min = 1, max = 256, message = "The length of title is invalid, it must within the range from 1 to 256")
    private String title;
    @Size(min = 1, max = 4096, message = "The length of description is invalid, it must within the range from 1 to 4096")
    private String description;
}
