package one.richardsonsw.game.demo.service.impl;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import one.richardsonsw.game.demo.domain.model.Hero;
import one.richardsonsw.game.demo.domain.repository.HeroRepository;
import one.richardsonsw.game.demo.service.HeroService;
import one.richardsonsw.game.demo.service.exception.BusinessException;
import static java.util.Optional.ofNullable;

@Service
public class HeroServiceImpl implements HeroService {
	
	private final HeroRepository heroRepository;
	
	public HeroServiceImpl(HeroRepository heroRepository) {
		this.heroRepository = heroRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Hero> findAll() {
		// TODO Auto-generated method stub
		return this.heroRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Hero findById(Long id) {
		// TODO Auto-generated method stub
		try {
			return this.heroRepository.findById(id).orElseThrow(NotFoundException::new);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public Hero create(Hero heroToCreate) {
		// TODO Auto-generated method stub
		ofNullable(heroToCreate).orElseThrow(() -> new BusinessException("Hero to create must not be null."));
		ofNullable(heroToCreate.getArmor()).orElseThrow(() -> new BusinessException("Hero armor must not be null."));
		ofNullable(heroToCreate.getProfession()).orElseThrow(() -> new BusinessException("Hero profession must not be null."));

        if (heroRepository.findById(heroToCreate.getId()) != null) {
            throw new BusinessException("This hero already exists.");
        }
        
        return this.heroRepository.save(heroToCreate);
	}

	@Override
	@Transactional
	public Hero update(Long id, Hero heroToUpdate) {
		// TODO Auto-generated method stub
		Hero dbHero = this.findById(id);
        if (!(dbHero.getId() == heroToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbHero.setName(heroToUpdate.getName());
        dbHero.setArmor(heroToUpdate.getArmor());
        dbHero.setProfession(heroToUpdate.getProfession());
        
        return this.heroRepository.save(dbHero);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
        Hero dbHero = this.findById(id);
        this.heroRepository.delete(dbHero);		
	}

}
