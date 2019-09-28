package com.nukkpointer.animalbar.repository;
import com.nukkpointer.animalbar.domain.Wrapper;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Wrapper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WrapperRepository extends JpaRepository<Wrapper, Long> {

}
