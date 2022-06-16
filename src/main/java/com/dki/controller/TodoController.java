package com.dki.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dki.entity.ResultInfo;
import com.dki.entity.Todo;
import com.dki.repository.TodoJpaRepo;
import com.dki.repository.MemberJpaRepo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/todo")
public class TodoController {

	@Autowired
	private TodoJpaRepo todoJpaRepo;
	@Autowired
	private MemberJpaRepo memberJpaRepo;

	@GetMapping(value = "/all")
	public List<Todo> findAllTodo() {
		log.info("전체 회원 할 일 목록 출력");
		return todoJpaRepo.findAll();
	}
	
	@PostMapping(value = "/list")	
	public Map<String, Object> listTodo(@RequestBody Todo todo){
		log.info("할 일 목록 출력");
		Map<String, Object> map = new HashMap<>();
		if(todo.getId().isEmpty()) {
			map.put("resultInfo", new ResultInfo(false, CommonErrorCodeStatus.NO_PARAMETER.getCode(), CommonErrorCodeStatus.NO_PARAMETER.getMsg()));
		}else {
			List<Todo> t = todoJpaRepo.listTodo(todo.getId());
			if(t.isEmpty()) {
				int userIdCnt =memberJpaRepo.countUserId(todo.getId());
				if(userIdCnt == 0){
					map.put("resultInfo", new ResultInfo(false, CommonErrorCodeStatus.NO_ID.getCode(), CommonErrorCodeStatus.NO_ID.getMsg()));
				}else {
					map.put("resultInfo", new ResultInfo(false, CommonErrorCodeStatus.NO_TODO.getCode(), CommonErrorCodeStatus.NO_TODO.getMsg()));
				}
			}else {
				map.put("todoInfo", t);
				map.put("resultInfo", new ResultInfo(true, "S", "출력이 완료되었습니다."));
		}
		}
		return map;
	}

	@PostMapping
	public Map<String, Object> registerTodo(@RequestBody Todo todo) {
		log.info("할 일 추가");
		Map<String, Object> map = new HashMap<>();
		if(todo.getId().isEmpty()) {
			map.put("resultInfo", new ResultInfo(false, CommonErrorCodeStatus.NO_PARAMETER.getCode(), CommonErrorCodeStatus.NO_PARAMETER.getMsg()));
		}else {
			int userIdCnt = memberJpaRepo.countUserId(todo.getId());
			if(userIdCnt == 0) {
				map.put("resultInfo", new ResultInfo(false, CommonErrorCodeStatus.NO_ID.getCode(), CommonErrorCodeStatus.NO_ID.getMsg()));
			}else {
				Todo t = todoJpaRepo.save(todo);
				map.put("todoInfo", t);
				map.put("resultInfo", new ResultInfo(true, "S", "등록이 완료되었습니다."));
			}
		}
		return map;
	}

	@DeleteMapping
	public Map<String, Object> deleteTodo(@RequestParam (value="num") long num, @RequestParam (value="id") String id) {
		log.info("할 일 삭제");
		Map<String, Object> map = new HashMap<>(); 
		int userIdCnt = memberJpaRepo.countUserId(id);
		if(userIdCnt == 0) {
			map.put("resultInfo", new ResultInfo(false, CommonErrorCodeStatus.NO_ID.getCode(), CommonErrorCodeStatus.NO_ID.getMsg()));
		}else {
			int todoCnt = todoJpaRepo.countTodoItem(num);
			if(todoCnt == 0) {
				map.put("resultInfo", new ResultInfo(false, CommonErrorCodeStatus.NO_TODO.getCode(), CommonErrorCodeStatus.NO_TODO.getMsg()));
			}else {
				todoJpaRepo.deleteTodo(num, id);
				List<Todo> t = todoJpaRepo.listTodo(id);
				map.put("todoInfo", t);
				map.put("resultInfo", new ResultInfo(true, "S", "삭제가 완료되었습니다."));
			}
		}
		return map;
	}

	@PutMapping
	public Map<String, Object> toggleTodo(@RequestBody Todo todo) {
		log.info("할 일 체크");
		Map<String, Object> map = new HashMap<>();
		int todoCnt = todoJpaRepo.countTodoItem(todo.getNum());
		if(todoCnt == 0) {
			map.put("resultInfo", new ResultInfo(false, CommonErrorCodeStatus.NO_TODO.getCode(), CommonErrorCodeStatus.NO_TODO.getMsg()));
		}else {
			todoJpaRepo.toggleTodo(todo.getNum(), todo.isCompleted());
			Todo t = todoJpaRepo.getTodo(todo.getNum());
			map.put("todoInfo", t);
			map.put("resultInfo", new ResultInfo(true, "S", "체크가 완료되었습니다."));
		}
		return map;
	}
	
	// 상세보기 수정
	@PutMapping(value = "/detail")
	public Map<String, Object> updateTodo(@RequestBody Todo todo){
		log.info("할 일 수정");
		Map<String, Object> map = new HashMap<>();
		int userIdCnt = memberJpaRepo.countUserId(todo.getId());
		if(userIdCnt == 0) {
			map.put("resultInfo", new ResultInfo(false, CommonErrorCodeStatus.NO_ID.getCode(), CommonErrorCodeStatus.NO_ID.getMsg()));
		}else {
			int todoCnt = todoJpaRepo.countTodoItem(todo.getNum());
			if(todoCnt == 0) {
				map.put("resultInfo", new ResultInfo(false, CommonErrorCodeStatus.NO_TODO.getCode(), CommonErrorCodeStatus.NO_TODO.getMsg()));
			}else {
				todoJpaRepo.updateTodo(todo.getNum(), todo.getTitle(), todo.getContent());
				Todo t = todoJpaRepo.getTodo(todo.getNum());
				map.put("todoInfo", t);
				map.put("resultInfo", new ResultInfo(true, "S", "수정이 완료되었습니다."));
			}
		}
		return map;
	}
	
	@GetMapping
	public Map<String,Object> clearAllTodo() {
		log.info("할 일 전체 삭제");
		Map<String, Object> map = new HashMap<>();
		todoJpaRepo.deleteAll();
		map.put("resultInfo", new ResultInfo(true, "S", "전체 삭제가 완료되었습니다."));
		return map;
	}
}
