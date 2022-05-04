package com.dki.entity;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name= "MEMBER")
// AccessLevel.Private
@Getter
@Setter
// 생성자 
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	
	// PK 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue: 각각 다른 Database의 Id 생성 전략을 유연하게 대응
	private long num;
	
	@Column(unique = true)
	private String id;
	
	@NotEmpty
	private String pw;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String phone;
}
