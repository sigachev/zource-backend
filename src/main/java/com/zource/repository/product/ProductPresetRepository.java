package com.zource.repository.product;

import com.zource.model.Product;
import com.zource.model.ProductPreset;
import com.zource.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductPresetRepository extends JpaRepository<ProductPreset, Long>  {

List<ProductPreset> findAllByUser(User user);


}
