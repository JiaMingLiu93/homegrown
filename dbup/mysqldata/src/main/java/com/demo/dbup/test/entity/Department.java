package com.demo.dbup.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jam
 * @description
 * @create 2018-01-18
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable{
    private Long id;
    private String name;
    private Date createDate;
}
