package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Category;

public interface CategoryService extends ReadWriteService<Category,Long>{
    Category findById(Long id);
    Category findByName(String name);
}