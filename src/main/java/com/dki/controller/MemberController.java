package com.dki.controller;
import com.dki.entity.MemberJpaRepo;
import com.dki.entity.Member;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event.ID;

import java.util.*;

//@RequiredArgsConstructor
@RestController // 결과 JSON
@RequestMapping(value = "/dki")
public class MemberController {
	
	@Autowired
	private MemberJpaRepo memberJpaRepo;
	
	@GetMapping(value="/all")
	public List<Member> findAllMember(){
		return memberJpaRepo.findAll();
	}
	
	@PostMapping(value="/add")
	public Member save() {
		Member member = Member.builder()
				.id("dki123")
				.pw("dki123!")
				.name("신지원")
				.phone("010-3203-9810")
				.build();
		
		return memberJpaRepo.save(member);
	}
}
