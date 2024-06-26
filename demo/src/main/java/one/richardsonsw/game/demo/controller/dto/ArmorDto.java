package one.richardsonsw.game.demo.controller.dto;

import one.richardsonsw.game.demo.domain.model.Armor;

public record ArmorDto(Long id, String armor) {
	public ArmorDto(Armor model) {
		this(model.getId(), model.getArmor());
	}
	
	public Armor toModel() {
        Armor model = new Armor();
        model.setId(this.id);
        model.setArmor(this.armor);
        return model;
    }
}
