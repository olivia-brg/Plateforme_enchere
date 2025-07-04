package fr.eni.encheres.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StringToLocaldate implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(@NonNull String source) {
        return null;
    }
}
