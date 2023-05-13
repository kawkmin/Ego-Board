package com.self.egoboard.domain.user.dao;

import com.self.egoboard.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User, Long> {

}
