package org.crazymages.springmastertelegrambot.repository;

import org.crazymages.springmastertelegrambot.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Integer> {
}
