package com.mylog.series.repository;

import com.mylog.answer.entity.Answer;
import com.mylog.series.dto.SeriesDto;
import com.mylog.series.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {

    Optional<Series> findBySubject(String subject);

    Optional<Series> findByMemberId(long id);
}
