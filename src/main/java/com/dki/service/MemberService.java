package com.dki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dki.entity.*;

@Service
public class MemberService {
	
	private MemberJpaRepo memberJpaRepo;
	
	public MemberService(MemberJpaRepo memberJpaRepo) {
		this.memberJpaRepo = memberJpaRepo;
	}
	
}
