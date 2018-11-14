package com.recipeproject.services;

import com.recipeproject.commands.UnitOfMeasureCommand;
import com.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.recipeproject.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UomServiceImpl implements UomService {

    private UnitOfMeasureRepository uomRepository;
    private UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public UomServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomRepository = uomRepository;
        this.uomConverter = uomConverter;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(uomRepository.findAll().spliterator(),false)
                .map(uomConverter::convert)
                .collect(Collectors.toSet());
    }
}
