package com.example.Reactive_Programming.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "bookinfo")
@AllArgsConstructor
@NoArgsConstructor
public class BookInfo {
    @Id
    private Integer id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String ISBN;
}
