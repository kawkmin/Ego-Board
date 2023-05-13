package com.self.egoboard.domain.item.dao;

import com.self.egoboard.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
