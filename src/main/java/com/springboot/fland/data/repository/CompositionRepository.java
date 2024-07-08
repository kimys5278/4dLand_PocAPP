package com.springboot.fland.data.repository;

import com.springboot.fland.data.entity.Composition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompositionRepository extends JpaRepository<Composition,Long> {
}
