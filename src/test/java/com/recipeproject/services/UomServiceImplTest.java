package com.recipeproject.services;

import com.recipeproject.commands.UnitOfMeasureCommand;
import com.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.recipeproject.domain.UnitOfMeasure;
import com.recipeproject.repositories.UnitOfMeasureRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class UomServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureCommand;
    UomService service;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        service = new UomServiceImpl(unitOfMeasureRepository,unitOfMeasureCommand);


    }

    @Test
    public void listAllUoms() {

        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(1L);
        unitOfMeasureSet.add(uom);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom.setId(2L);
        unitOfMeasureSet.add(uom2);

        Mockito.when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureSet);

        Set<UnitOfMeasureCommand> commands = service.listAllUoms();

        Assert.assertEquals(2,commands.size());

        Mockito.verify(unitOfMeasureRepository,Mockito.times(1)).findAll();

    }
}