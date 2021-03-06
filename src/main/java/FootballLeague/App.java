package FootballLeague;/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import FootballLeague.FootballLeagueFrontend.GameMenu;
import FootballLeague.FootballLeagueFrontend.MainGame;
import FootballLeague.FootballLeagueFrontend.MainMenu;
import javafx.application.Application;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static Logger logger = LogManager.getLogger("com.josh");
    public static Marker DEFAULT = MarkerManager.getMarker("DEFAULT");
    public static Marker EXCEPTION = MarkerManager.getMarker("EXCEPTION");

    public static void main(String[] args) {

        System.out.println(new App().getGreeting());

        logger.info(DEFAULT, "App started.");
        logger.debug(EXCEPTION, "exception");
        logger.error("No key.");

        Application.launch(GameMenu.class, null);


    }
}
