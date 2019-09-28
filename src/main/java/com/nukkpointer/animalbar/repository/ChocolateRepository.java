package com.nukkpointer.animalbar.repository;
import com.nukkpointer.animalbar.domain.Chocolate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Chocolate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChocolateRepository extends JpaRepository<Chocolate, Long> {

}
