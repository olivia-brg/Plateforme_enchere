package fr.eni.encheres.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StringToLocaldate implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return null;
    }
}
