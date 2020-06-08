package com.zource.service.product.preset;

import com.zource.exceptions.product.ProductPresetNotFoundException;
import com.zource.model.ProductPreset;
import com.zource.model.User;
import com.zource.repository.product.ProductPresetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductPresetServiceImpl implements ProductPresetService {

    @Autowired
    private ProductPresetRepository productPresetRepository;

    public List<ProductPreset> getByUser(User user) {
        return this.productPresetRepository.findAllByUser(user);
    }

    @Override
    public ProductPreset getById(Long id) throws ProductPresetNotFoundException {
        return this.productPresetRepository.findById(id).orElseThrow(() -> new ProductPresetNotFoundException("No product preset found with id = " + id));
    }
}
