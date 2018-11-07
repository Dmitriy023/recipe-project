package com.recipeproject.converters;

import com.recipeproject.commands.UnitOfMeasureCommand;
import com.recipeproject.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure,UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public  UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if(source == null){
            return null;
        }
        UnitOfMeasureCommand uomCmd = new UnitOfMeasureCommand();
        uomCmd.setId(source.getId());
        uomCmd.setDescription(source.getDescription());
        return uomCmd;
    }
}
