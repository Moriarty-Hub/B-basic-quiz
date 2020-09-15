package com.thoughtworks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Education {
    private Long userId;
    private Long year;
    private String title;
    private String description;
}
