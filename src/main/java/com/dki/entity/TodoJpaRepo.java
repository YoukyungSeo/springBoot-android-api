package com.dki.entity;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoJpaRepo extends JpaRepository<Todo, Long> {

	@Query(value = "DELETE FROM Todo t WHERE t.item = :item")
	@Modifying
	@Transactional
	public void deleteTodo(@Param(value = "item") String item);

//	@Query(value = "UPDATE Todo t SET t.completed = :completed")
//	@Modifying
//	@Transactional
//	public void toggleTodo(@Param(value = "completed") Boolean completed);
}
