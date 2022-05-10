package com.dki.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dki.entity.Member;
import com.dki.entity.ResultInfo;
import com.dki.repository.MemberJpaRepo;

@RestController
@RequestMapping(value = "/member")
public class MemberController {

	@Autowired
	private MemberJpaRepo memberJpaRepo;

	@GetMapping(value = "/list")
	public List<Member> memberList() {
		return memberJpaRepo.findAll();
	}

	@GetMapping(value = "/info")
	public Map<String, Object> memberInfo(@RequestParam(value = "id") String id) {
		Map<String, Object> map = new HashMap<>();
		int cnt = memberJpaRepo.countUserId(id);
		try {
			if (cnt == 1) {
				Member m = memberJpaRepo.findUser(id);
				map.put("userInfo", m);
			} else {
				map.put("resultInfo", new ResultInfo(false, "E-001", "등록되지 않은 아이디입니다."));
			}
		} catch (Exception e) {
			map.put("resultInfo", new ResultInfo(false, "E-999", "일시적인 오류로 서비스 접속에 실패했습니다. 잠시 후 다시 시도해 주세요."));
		}
		return map;
	}

	@PostMapping
	public Map<String, Object> memberJoin(@RequestBody Member member) {
		Map<String, Object> map = new HashMap<>();
		try {
			ResultInfo result = (ResultInfo) memberIdCheck(member).get("resultInfo");
			if (result.getResult() == true) {
				memberJpaRepo.save(member);
				map.put("resultInfo", new ResultInfo(true, "S", "회원가입을 완료했습니다."));
			} else {
				map.put("resultInfo", new ResultInfo(false, "E-003", "이미 사용중인 아이디입니다."));
			}
		} catch (ConstraintViolationException e) {
			map.put("resultInfo", new ResultInfo(false, "E-004", "필수 입력 사항이 누락되었습니다."));
		}
		return map;
	}

	@PostMapping(value = "/idcheck")
	public Map<String, Object> memberIdCheck(@RequestBody Member member) {
		Map<String, Object> map = new HashMap<>();
		int cnt = memberJpaRepo.countUserId(member.getId());
		if (cnt == 1) {
			map.put("resultInfo", new ResultInfo(false, "E-003", "이미 사용중인 아이디입니다."));
		} else {
			map.put("resultInfo", new ResultInfo(true, "S", "사용할 수 있는 아이디입니다."));
		}
		return map;
	}

	@GetMapping(value = "/login")
	public Map<String, Object> memberLoginGet(@RequestParam(value = "id") String id,
			@RequestParam(value = "pw") String pw) {
		Map<String, Object> map = memberCheckLogin(id, pw);
		return map;
	}

	@PostMapping(value = "/login")
	public Map<String, Object> memberLoginPost(@RequestBody Member member) {

		String id = member.getId();
		String pw = member.getPw();
		System.out.println(id + " " + pw);
		Map<String, Object> map = memberCheckLogin(id, pw);
		return map;

	}

	private Map<String, Object> memberCheckLogin(String id, String pw) {
		Map<String, Object> map = new HashMap<>();
		int cnt = memberJpaRepo.countUserId(id);
		if (cnt == 0) {
			map.put("resultInfo", new ResultInfo(false, "E-001", "등록되지 않은 아이디입니다."));
		} else {
			Member m = memberJpaRepo.isLogin(id, pw);
			if (m != null) {
				map.put("userInfo", m);
				map.put("resultInfo", new ResultInfo(true, "S", "로그인에 성공했습니다."));
			} else {
				map.put("resultInfo", new ResultInfo(false, "E-002", "비밀번호를 잘못 입력했습니다."));
			}
		}
		return map;
	}

	@PutMapping
	public Map<String, Object> memberUpdate(@RequestBody Member member) {
		Map<String, Object> map = new HashMap<>();
		int cnt = memberJpaRepo.countUserId(member.getId());
		try {
			if (cnt == 0) {
				map.put("resultInfo", new ResultInfo(false, "E-005", "존재하지 않는 사용자입니다."));
			} else {
				memberJpaRepo.updateUser(member.getId(), member.getPw(), member.getName(), member.getPhone());
				map.put("resultInfo", new ResultInfo(true, "S", "회원 정보 수정이 완료되었습니다."));
			}
		} catch (Exception e) {
			map.put("resultInfo", new ResultInfo(false, "E-999", "일시적인 오류로 서비스 접속에 실패했습니다. 잠시 후 다시 시도해 주세요."));
		}
		return map;
	}

	@DeleteMapping
	public Map<String, Object> memberDelete2(@RequestBody Member member) {
		Map<String, Object> map = new HashMap<>();
		int cnt = memberJpaRepo.countUserId(member.getId());
		try {
			if (cnt == 0) {
				map.put("resultInfo", new ResultInfo(false, "E-005", "존재하지 않는 사용자입니다."));
			} else {
				memberJpaRepo.deleteUser(member.getId());
				map.put("resultInfo", new ResultInfo(true, "S", "회원 탈퇴가 완료되었습니다. 이용해 주셔서 감사합니다."));
			}
		} catch (Exception e) {
			map.put("resultInfo", new ResultInfo(false, "E-999", "일시적인 오류로 서비스 접속에 실패했습니다. 잠시 후 다시 시도해 주세요."));
		}
		return map;
	}
}
