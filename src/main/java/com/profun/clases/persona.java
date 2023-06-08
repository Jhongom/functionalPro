package com.profun.clases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class persona {
    private Integer id;
    private String nombre;
    private Integer edad;


}
