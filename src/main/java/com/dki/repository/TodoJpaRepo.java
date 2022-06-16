package com.dki.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dki.entity.Todo;

public interface TodoJpaRepo extends JpaRepository<Todo, Long> {

	// 삭제
	@Query(value = "DELETE FROM Todo t WHERE t.num = :num AND t.id = :id")
	@Modifying
	@Transactional
	public void deleteTodo(@Param(value = "num") long num, @Param(value = "id") String id);

	// 체크
	@Query(value = "UPDATE Todo t SET t.completed = :completed WHERE t.num = :num")
	@Modifying
	@Transactional
	public void toggleTodo(@Param(value = "num") long num,
			@Param(value = "completed") Boolean completed);
	
	// 수정
	@Query(value = "UPDATE Todo t SET t.title = :title, t.content = :content WHERE t.num = :num")
	@Modifying
	@Transactional
	public void updateTodo(@Param(value = "num") long num,
			@Param(value = "title") String title, @Param(value = "content") String content);
	
	// 아이디로 리스트 조회
	@Query(value = "SELECT t FROM Todo t WHERE t.id = :id")
	public List<Todo> listTodo(@Param(value = "id") String id);
	
	
	@Query(value = "SELECT t FROM Todo t WHERE t.num = :num")
	public Todo getTodo(@Param(value = "num") long num);
	
	// 아이디 확인
	@Query(value = "SELECT COUNT(t) FROM Todo t WHERE t.id = :id")
	public int countUserId(@Param(value = "id") String id);
	
	@Query(value = "SELECT COUNT(t) FROM Todo t WHERE t.num = :num")
	public int countTodoItem(@Param(value = "num") long num);
}
