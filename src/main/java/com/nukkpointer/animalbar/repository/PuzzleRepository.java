package com.nukkpointer.animalbar.repository;
import com.nukkpointer.animalbar.domain.Puzzle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Puzzle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle, Long> {

}
