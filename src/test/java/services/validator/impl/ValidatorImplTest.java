package services.validator.impl;

import exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.validator.Validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorImplTest {
    private Validator validator;
    @BeforeEach
    void setUp(){
        validator = ValidatorImpl.getInstance();
    }

    @Test
    void validateParametersSuccess() {
        //given
        String[] args = new String[]{"src/test/resources", "name"};
        //when
        validator.validate(args);
        //then
        //What are you doing? Me? Nothing, just hanging around....
    }

    @Test
    void validateParametersEmptyParams() {
        //given
        String[] args = new String[]{};
        //when
        Exception exception = assertThrows(ValidationException.class, ()
                -> validator.validate(args));
        //then
        String expectedMessage = "Program has started without parameters.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void validateParametersNotEnoughParams() {
        //given
        String[] args = new String[]{"src/test/resources"};
        //when
        Exception exception = assertThrows(ValidationException.class, ()
                -> validator.validate(args));
        //then
        String expectedMessage = "Not enough parameters specified.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void validateParametersRedundantParams() {
        //given
        String[] args = new String[]{"src/test/resources", "name", "3", "param" , "param"};
        //when
        Exception exception = assertThrows(ValidationException.class, ()
                -> validator.validate(args));
        //then
        String expectedMessage = "Redundant parameters specified.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void validateParametersInvalidDirectory() {
        //given
        String[] args = new String[]{"src/test/failure", "name"};
        //when
        Exception exception = assertThrows(ValidationException.class, ()
                -> validator.validate(args));
        //then
        String expectedMessage = "No such directory found.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void validateParametersInvalidField() {
        //given
        String[] args = new String[]{"src/test/resources", "failure"};
        //when
        Exception exception = assertThrows(ValidationException.class, ()
                -> validator.validate(args));
        //then
        String expectedMessage = "No such field in class Employee present.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void validateParametersInvalidThreadParseError() {
        //given
        String[] args = new String[]{"src/test/resources", "name", "grgrgrerg"};
        //when
        Exception exception = assertThrows(ValidationException.class, ()
                -> validator.validate(args));
        //then
        String expectedMessage = "Program is not able to parse number of threads specified.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void validateParametersInvalidThreadBoundaryError() {
        //given
        String[] args = new String[]{"src/test/resources", "name", "9"};
        //when
        Exception exception = assertThrows(ValidationException.class, ()
                -> validator.validate(args));
        //then
        String expectedMessage = "Not valid thread value";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}