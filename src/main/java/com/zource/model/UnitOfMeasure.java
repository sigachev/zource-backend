package com.zource.model;

import lombok.Data;
import org.hibernate.annotations.Tuplizer;
import org.springframework.data.annotation.Id;

import javax.persistence.*;


public enum UnitOfMeasure{
    each,
    sqft,
    box,
    sheet
}
