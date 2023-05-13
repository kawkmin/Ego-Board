package com.self.egoboard.domain.item.entity.subtypes;

import com.self.egoboard.domain.item.entity.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clothes")
public class clothes extends Item {

  private String clothesImage;
}
