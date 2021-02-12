package isha.ishop.repository;

import isha.ishop.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepo extends JpaRepository<Subcategory, Long> {

    List<Subcategory> findAll();

    Subcategory findByName(String category);
    Subcategory findByUrl(String category);
}
