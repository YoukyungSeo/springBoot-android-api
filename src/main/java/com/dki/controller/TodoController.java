package com.dki.controller;

import java.util.List;

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
	public Todo registerTodo(@RequestBody Todo todo) {
		log.info("할 일 추가");
		return todoJpaRepo.save(todo);
	}

	@DeleteMapping
	public String deleteTodo(@RequestParam (value="item") String item) {
		log.info("할 일 삭제");
		todoJpaRepo.deleteTodo(item);
		return "할 일 삭제 완료";
	}

	@PutMapping
	public String toggleTodo(@RequestBody Todo todo) {
		log.info("할 일 체크");
		todoJpaRepo.toggleTodo(todo.getItem(), todo.isCompleted());
		return "할 일 체크 완료";
	}
	
	@GetMapping
	public String clearAllTodo() {
		log.info("할 일 전체 삭제");
		todoJpaRepo.deleteAll();
		return "전체 삭제 완료";
	}

}
