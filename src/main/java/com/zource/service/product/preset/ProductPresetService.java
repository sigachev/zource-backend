package com.zource.service.product.preset;

import com.zource.exceptions.product.ProductPresetNotFoundException;
import com.zource.model.ProductPreset;
import com.zource.model.User;

import java.util.List;

public interface ProductPresetService {


    ProductPreset getById(Long Id) throws ProductPresetNotFoundException;

    public List<ProductPreset> getByUser(User user);
}
