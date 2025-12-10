package com.example.chicken.domain.faq.repository;

import com.example.chicken.domain.faq.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {}
