package one.richardsonsw.game.demo.controller.dto;

import one.richardsonsw.game.demo.domain.model.Profession;

public record ProfessionDto(Long id, String profession) {
	public ProfessionDto(Profession model) {
		this(model.getId(), model.getProfession());
	}
	
	public Profession toModel() {
        Profession model = new Profession();
        model.setId(this.id);
        model.setProfession(this.profession);
        return model;
    }

}
