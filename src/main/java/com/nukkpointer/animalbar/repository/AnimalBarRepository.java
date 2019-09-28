package com.nukkpointer.animalbar.repository;
import com.nukkpointer.animalbar.domain.AnimalBar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AnimalBar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnimalBarRepository extends JpaRepository<AnimalBar, Long> {

}
