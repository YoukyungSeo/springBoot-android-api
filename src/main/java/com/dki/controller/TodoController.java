package com.dki.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dki.entity.Todo;
import com.dki.repository.TodoJpaRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/todo")
public class TodoController {

	@Autowired
	private TodoJpaRepo todoJpaRepo;

	@GetMapping(value = "/list")
	public List<Todo> listTodo() {
		log.info("할 일 목록 출력");
		return todoJpaRepo.findAll();
	}

	@PostMapping
	public Map<String, Object> registerTodo(@RequestBody Todo todo) {
		Map<String, Object> map = new HashMap<>();
		log.info("할 일 추가");
		Todo t = todoJpaRepo.save(todo);
		map.put("todoInfo", t);
		return map;
	}

	@DeleteMapping
	public String deleteTodo(@RequestParam (value="title") String title) {
		log.info("할 일 삭제");
		todoJpaRepo.deleteTodo(title);
		return "할 일 삭제 완료";
	}

	@PutMapping
	public Map<String, Object> toggleTodo(@RequestBody Todo todo) {
		Map<String, Object> map = new HashMap<>();
		todoJpaRepo.toggleTodo(todo.getTitle(), todo.isCompleted());
		Todo t = todoJpaRepo.getTodo(todo.getNum());
		map.put("todoInfo", t);
		log.info("할 일 체크");
		return map;
	}
	
	// 상세보기 수정
	@PutMapping(value = "/detail")
	public Map<String, Object> updateTodo(@RequestBody Todo todo){
		Map<String, Object> map = new HashMap<>();
		todoJpaRepo.updateTodo(todo.getNum(), todo.getTitle(), todo.getContent());
		Todo t = todoJpaRepo.getTodo(todo.getNum());
		map.put("todoInfo", t);
		return map;
	}
	
	@GetMapping
	public String clearAllTodo() {
		log.info("할 일 전체 삭제");
		todoJpaRepo.deleteAll();
		return "전체 삭제 완료";
	}

}
