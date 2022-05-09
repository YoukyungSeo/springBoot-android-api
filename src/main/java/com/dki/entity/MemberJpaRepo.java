package com.dki.entity;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpaRepo extends JpaRepository<Member, Long> {

	// 회원 조회
	@Query(value = "SELECT m.id, m.pw, m.name, m.phone FROM Member m WHERE m.id = :id")
	public Member findUser(@Param(value = "id") String id);

	// 로그인
	@Query(value = "SELECT m FROM Member m WHERE m.id = :id AND m.pw = :pw")
	public Member isLogin(@Param(value = "id") String id, @Param(value = "pw") String pw);

	// 아이디 확인
	@Query(value = "SELECT COUNT(m) FROM Member m WHERE m.id = :id")
	public int countUserId(@Param(value = "id") String id);

	// 비밀번호 확인
	// @Query(value = "SELECT pw FROM Member m WHERE m.id = :id")
	// public String getUserPw(@Param(value = "id") String id);

	// 회원 탈퇴
	@Query(value = "DELETE FROM Member m WHERE m.id = :id")
	@Modifying
	@Transactional
	public void deleteUser(@Param(value = "id") String id);

	// 회원 수정
	@Query(value = "UPDATE Member SET pw=:pw,name=:name, phone=:phone where id=:id ")
	@Modifying
	@Transactional
	public void updateUser(@Param(value = "id") String id, @Param(value = "pw") String pw,
			@Param(value = "name") String name, @Param(value = "phone") String phone);

}