package services.validator;

import exceptions.ValidationException;

public interface Validator {
    void validate(String[] args) throws ValidationException;
}
