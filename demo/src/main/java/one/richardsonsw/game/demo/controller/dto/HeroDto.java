package one.richardsonsw.game.demo.controller.dto;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.util.List;

import one.richardsonsw.game.demo.domain.model.Hero;

public record HeroDto(Long id, String name, List<ArmorDto> armors, List<ProfessionDto> professions) {
	public HeroDto(Hero model) {
		this(
				model.getId(), 
				model.getName(), 
				ofNullable(model.getArmor()).orElse(emptyList()).stream().map(ArmorDto::new).collect(toList()),
				ofNullable(model.getProfession()).orElse(emptyList()).stream().map(ProfessionDto::new).collect(toList()));
	}
	
	public Hero toModel() {
        Hero model = new Hero();
        model.setId(this.id);
        model.setName(this.name);
        model.setArmor(ofNullable(this.armors).orElse(emptyList()).stream().map(ArmorDto::toModel).collect(toList()));
        model.setProfession(ofNullable(this.professions).orElse(emptyList()).stream().map(ProfessionDto::toModel).collect(toList()));
        return model;
    }
}
