package com.self.egoboard.global.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

  @CreatedDate
  @Column(name = "register_time", nullable = false, updatable = false)
  private LocalDateTime registerTime;

  @LastModifiedDate
  @Column(name = "update_time", nullable = false)
  private LocalDateTime updateTime;
}
