import entities.DefaultSettings;
import exceptions.ValidationException;
import lombok.extern.log4j.Log4j;
import services.launcher.Launcher;
import services.launcher.impl.LauncherImpl;
import services.validator.Validator;
import services.validator.impl.ValidatorImpl;

/**
 * Entry point class Main
 */
@Log4j
public class Main {
    public static void main(String[] args) {
        Launcher launcher = LauncherImpl.getInstance();
        Validator validator = ValidatorImpl.getInstance();
        try {
            validator.validate(args);
            if(args.length==3){
                launcher.setThreadCount(args[2]);
            }
            launcher.launchReading(args[0], args[1]);
        } catch (ValidationException e){
            log.warn(e.getMessage());
            launcher.launchReading(DefaultSettings.path, DefaultSettings.parameter);
        }
    }
}
