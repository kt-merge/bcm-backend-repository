package com.example.chicken.domain.qna.repository;

import com.example.chicken.domain.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {}
