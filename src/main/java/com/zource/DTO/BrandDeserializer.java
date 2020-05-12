package com.zource.DTO;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.zource.model.Brand;
import com.zource.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandDeserializer extends StdDeserializer<Brand> {
    private static final long serialVersionUID = 1L;

    @Autowired
    private BrandRepository brandRepository;

    public BrandDeserializer() {
        this(Brand.class);
    }

    protected BrandDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Brand deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        TreeNode tn = p.readValueAsTree();
        Long brandId;


        if (tn != null) {
            brandId = Long.parseLong(tn.get(0).toString());
        } else {
            brandId = Long.valueOf(1);
        }

        Optional<Brand> brand = brandRepository.findById(brandId);

        if (brand.isPresent())
            return brand.get();
        else
            return null;
    }
}
