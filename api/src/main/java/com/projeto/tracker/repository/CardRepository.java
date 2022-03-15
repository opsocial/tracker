package com.projeto.tracker.repository;

import com.projeto.tracker.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
