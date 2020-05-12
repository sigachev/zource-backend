package com.zource.service.brand;

import com.zource.model.Brand;
import com.zource.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> find(String name) {
        List<Brand>  brands = brandRepository.findAll().stream().filter(b -> b.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        return brands;
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

}
