package com.project.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class ProductInfoDto {
    @Id
    private Long id;

    String productName;

    String productDesc;

    String productOrigin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate productDate;

    Integer status;

    long groupId;

    Date createTime;

    String createBy;

}
