package com.kundan.EntityClass;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name ="User_Dtls_Entity")
public class UserDtlsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	
	private String name;
	
	private String email;
	
	private String phno;
	
	private String userPwd;
	
	private String userACCStatus;
	
	@OneToMany(mappedBy ="user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<StudentEnqEntity> enquries;
	

}
