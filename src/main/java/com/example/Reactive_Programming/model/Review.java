package com.example.Reactive_Programming.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    private Integer id;
    @Column
    private Integer bookId;
    @Column
    private Integer rating;
    @Column
    private String comments;
}
