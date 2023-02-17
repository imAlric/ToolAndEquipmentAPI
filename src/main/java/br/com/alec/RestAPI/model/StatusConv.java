package br.com.alec.RestAPI.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusConv implements AttributeConverter<Status, Character> {

    @Override
    public Character convertToDatabaseColumn(Status status) {
        if (status == null)
            return null;

        return switch (status) {
            case Pending -> 'P';
            case Closed -> 'C';
            case Interrupted -> 'I';
            case Active -> 'A';
            case Inactive -> 'N';
            case Deleted -> 'X';
        };
    }

    @Override
    public Status convertToEntityAttribute(Character character) {
        if (character == null)
            return null;

        return switch (character) {
            case 'P' -> Status.Pending;
            case 'C' -> Status.Closed;
            case 'I' -> Status.Interrupted;
            case 'A' -> Status.Active;
            case 'N' -> Status.Inactive;
            case 'X' -> Status.Deleted;
            default -> throw new IllegalArgumentException(character + " inválido.");
        };
    }
}