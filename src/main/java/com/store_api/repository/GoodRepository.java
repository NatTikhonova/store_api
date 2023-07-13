package com.store_api.repository;

import com.store_api.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodRepository extends JpaRepository<Good, Long> {
    List<Good> findAllByCategory_Id (Long id);
}
