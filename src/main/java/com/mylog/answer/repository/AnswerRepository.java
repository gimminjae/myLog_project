package com.mylog.answer.repository;

import com.mylog.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Optional<Answer> findByPostId(long id);

    Optional<Answer> findByMemberId(long id);
}
