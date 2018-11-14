package com.recipeproject.services;

import com.recipeproject.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UomService {
    Set<UnitOfMeasureCommand> listAllUoms();
}
