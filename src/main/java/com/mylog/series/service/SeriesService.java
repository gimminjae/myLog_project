package com.mylog.series.service;

import com.mylog.base.dto.DtoUt;
import com.mylog.member.dto.MemberDto;
import com.mylog.series.dto.SeriesDto;
import com.mylog.series.entity.Series;
import com.mylog.series.repository.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesService {
    private final SeriesRepository seriesRepository;

    public void create(String subject, MemberDto memberDto) {
        Series series = Series.builder()
                .createDate(LocalDateTime.now())
                .member(DtoUt.toEntity(memberDto))
                .subject(subject)
                .build();
        seriesRepository.save(series);
    }

    public List<SeriesDto> getAll() {
        return seriesRepository.findAll().stream().map(i -> DtoUt.toDto(i)).toList();
    }

    public SeriesDto getBySubject(String subject) {
        return DtoUt.toDto(seriesRepository.findBySubject(subject).orElse(null));
    }

    public SeriesDto getByMember(MemberDto memberDto) {
        return DtoUt.toDto(seriesRepository.findByMemberId(memberDto.getId()).orElse(null));
    }

    public void modify(String subject, SeriesDto seriesDto) {
        Series series = seriesRepository.findById(seriesDto.getId()).orElse(null);

        series.setSubject(subject);
        seriesRepository.save(series);
    }

    public void delete(SeriesDto seriesDto) {
        Series series = seriesRepository.findById(seriesDto.getId()).orElse(null);

        seriesRepository.delete(series);
    }
}
