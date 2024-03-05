package com.angular.practicejava.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name ="post_data")
public class PostData {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String content;
}
