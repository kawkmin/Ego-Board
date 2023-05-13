package com.self.egoboard.domain.item.entity.subtypes;

import com.self.egoboard.domain.item.entity.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "hat")
public class hat extends Item {

  private String hatImage;
}
