import entities.DefaultSettings;
import lombok.extern.log4j.Log4j;
import services.launcher.Launcher;
import services.launcher.impl.LauncherImpl;

/**
Entry point class Main
 */
@Log4j
public class Main {
    public static void main(String[]args){
        Launcher launcher = LauncherImpl.getInstance();
        if(args.length > 0){
            launcher.launchReading(args[0], args[1]);
        } else {
            log.info("Program has started without parameters. Launching with default settings...");
            launcher.launchReading(DefaultSettings.path, DefaultSettings.parameter);
        }
    }
}
