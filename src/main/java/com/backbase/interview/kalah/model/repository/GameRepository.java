package com.backbase.interview.kalah.model.repository;

import com.backbase.interview.kalah.model.domain.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository <GameEntity, Long> {
}
