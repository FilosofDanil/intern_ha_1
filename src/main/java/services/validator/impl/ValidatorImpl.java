package services.validator.impl;

import entities.Employee;
import exceptions.ValidationException;
import services.validator.Validator;

import java.io.File;
/**
 * Class, which useful for validation of input parameters
 */
public class ValidatorImpl implements Validator {
    private static ValidatorImpl validator;

    /**
     * Method for validation of input args
     */
    @Override
    public void validate(String[] args) throws ValidationException {
        if (args.length == 0){
            throw new ValidationException("Program has started without parameters. Launching with default settings...");
        } else if(args.length < 2){
            throw new ValidationException("Not enough parameters specified. Launching with default settings...");
        } else if(args.length > 3){
            throw new ValidationException("Too more parameters specified. Launching with default settings...");
        }
        else {
            File directory = new File(args[0]);
            if (!directory.exists() || !directory.isDirectory()) {
                throw new ValidationException("No such directory found. Launching with default settings...");
            }
            Class<?> employeeClass = Employee.class;
            try {
                employeeClass.getDeclaredField(args[1]);
            } catch (NoSuchFieldException e) {
                throw new ValidationException("No such field in class Employee present. Launching with default settings...");
            }
        }
        if(args.length==3){
            try {
                int threadNumber = Integer.parseInt(args[2]);
                if(threadNumber < 0 || threadNumber > 8){
                    throw new ValidationException("Not valid thread value, it should be between 1 and 8, " +
                            "Try again with other value, or launch it only with 2 parameters" +
                            "Launching with default settings...");
                }
            } catch (NumberFormatException e){
                throw new ValidationException("Program is not able to parse number of threads specified. Try again, using " +
                        " number or launch it only with 2 parameters " +
                        "Launching with default settings...");
            }
        }
    }

    public static ValidatorImpl getInstance() {
        if(validator==null){
            validator = new ValidatorImpl();
        }
        return validator;
    }
}
