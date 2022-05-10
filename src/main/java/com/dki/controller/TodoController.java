package com.dki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dki.entity.Todo;
import com.dki.entity.TodoJpaRepo;

@CrossOrigin
@RestController
@RequestMapping(value = "/todo")
public class TodoController {

	@Autowired
	private TodoJpaRepo todoJpaRepo;

	@GetMapping(value = "/list")
	public List<Todo> listTodo() {
		return todoJpaRepo.findAll();
	}

	@PostMapping
	public Todo registerTodo(@RequestBody Todo todo) {
		return todoJpaRepo.save(todo);
	}

	@DeleteMapping
	public String deleteTodo(@RequestParam (value="item") String item) {
		System.out.println(item);
		todoJpaRepo.deleteTodo(item);
		return "할 일 삭제 완료";
	}

	@PutMapping
	public String toggleTodo(@RequestBody Todo todo) {
		return "할일 체크 완료";
	}

}
