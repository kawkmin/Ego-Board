package com.self.egoboard.domain.petItem.dao;

import com.self.egoboard.domain.petItem.entity.PetItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetItemRepository extends JpaRepository<PetItem, Long> {


}
