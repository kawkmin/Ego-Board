package com.self.egoboard.domain.post.entity.embedded;

import jakarta.persistence.Embeddable;

@Embeddable
public class Board {

  private String title;
  private String content;
  private String uploadFileName;
  private String storeFileName;
}
