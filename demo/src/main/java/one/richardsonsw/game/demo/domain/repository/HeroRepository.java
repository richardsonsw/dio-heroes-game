package one.richardsonsw.game.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.richardsonsw.game.demo.domain.model.Hero;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {
	
	boolean existsByArmorId(long id);

}
