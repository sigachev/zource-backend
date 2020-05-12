package com.zource.service.brand;

import com.zource.model.Brand;

import java.util.List;

public interface BrandService {

    Brand saveBrand(Brand brand);

    List<Brand> find(String name);

    List<Brand> getAllBrands();
}
