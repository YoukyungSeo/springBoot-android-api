package com.dki.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dki.entity.Todo;

public interface TodoJpaRepo extends JpaRepository<Todo, Long> {

	@Query(value = "DELETE FROM Todo t WHERE t.title = :title")
	@Modifying
	@Transactional
	public void deleteTodo(@Param(value = "title") String title);

	@Query(value = "UPDATE Todo t SET t.completed = :completed WHERE t.title = :title")
	@Modifying
	@Transactional
	public void toggleTodo(@Param(value = "title") String title,
			@Param(value = "completed") Boolean completed);
	
	@Query(value = "UPDATE Todo t SET t.title = :title, t.content = :content WHERE t.num = :num")
	@Modifying
	@Transactional
	public void updateTodo(@Param(value = "num") long num,
			@Param(value = "title") String title, @Param(value = "content") String content);
	
	@Query(value = "SELECT t FROM Todo t WHERE t.num = :num")
	public Todo getTodo(@Param(value = "num") long num);
}
