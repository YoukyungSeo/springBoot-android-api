package com.dki.entity;
import lombok.*;
import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "member")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long num;
	
	@Column(unique = true)
	private String id;
	
	@Column
	private String pw;
	private String name;
	private String phone;
}
