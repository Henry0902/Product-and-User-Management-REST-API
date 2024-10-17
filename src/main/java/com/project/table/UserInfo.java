package com.project.table;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import lombok.Data;

@Entity
@Table(name = "USER_INFO")
@Data
@Getter
@Setter
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Tên đăng nhập không được để trống")
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String username;
	
	@NotEmpty(message = "Mật khẩu không được để trống")
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String password;
	
	@NotEmpty(message = "Họ và tên không được để trống")
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String fullName;
	
	Integer status;
	
	long groupId;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date createTime;
	
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String createBy;
	
	@Nationalized
	@Size(max = 255, message = "Độ dài không quá 255 kí tự")
	String email;
}
