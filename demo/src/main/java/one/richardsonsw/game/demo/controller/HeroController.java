package one.richardsonsw.game.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import one.richardsonsw.game.demo.controller.dto.HeroDto;
import one.richardsonsw.game.demo.service.HeroService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/heroes")
@Tag(name = "heroes Controller", description = "RESTful API for managing heroes.")
public record HeroController(HeroService heroService) {

    @GetMapping
    @Operation(summary = "Get all heroes", description = "Retrieve a list of all registered heroes")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<HeroDto>> findAll() {
        var heroes = heroService.findAll();
        var heroesDto = heroes.stream().map(HeroDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(heroesDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a hero by ID", description = "Retrieve a specific hero based on its ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<HeroDto> findById(@PathVariable Long id) {
        var hero = heroService.findById(id);
        return ResponseEntity.ok(new HeroDto(hero));
    }

    @PostMapping
    @Operation(summary = "Create a new hero", description = "Create a new hero and return the created hero's data")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "201", description = "Hero created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid hero data provided")
    })
    public ResponseEntity<HeroDto> create(@RequestBody HeroDto heroDto) {
        var hero = heroService.create(heroDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(hero.getId())
                .toUri();
        return ResponseEntity.created(location).body(new HeroDto(hero));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a hero", description = "Update the data of an existing hero based on its ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Hero updated successfully"),
            @ApiResponse(responseCode = "404", description = "Hero not found"),
            @ApiResponse(responseCode = "422", description = "Invalid hero data provided")
    })
    public ResponseEntity<HeroDto> update(@PathVariable Long id, @RequestBody HeroDto heroDto) {
        var hero = heroService.update(id, heroDto.toModel());
        return ResponseEntity.ok(new HeroDto(hero));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a hero", description = "Delete an existing hero based on its ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "204", description = "Hero deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Hero not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        heroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}