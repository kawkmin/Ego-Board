package com.self.egoboard.domain.pet.dao;

import com.self.egoboard.domain.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
