package FootballLeague.FootballLeagueFrontend.Content;

import FootballLeague.FootballLeagueBackend.GameState;
import FootballLeague.FootballLeagueBackend.Match;
import FootballLeague.FootballLeagueBackend.Team;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static FootballLeague.FootballLeagueBackend.GameState.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static FootballLeague.FootballLeagueBackend.GameState.readSaveName;
import static FootballLeague.FootballLeagueBackend.GameState.readGameWeek;
import static FootballLeague.FootballLeagueBackend.Match.readAllMatches;
import static FootballLeague.FootballLeagueBackend.Team.readAllTeams;

public class LeagueResultsContent extends VBox {

    public static Logger logger = LogManager.getLogger("com.josh");

    ComboBox<Integer> gameWeek;
    ComboBox<Team> team;
    VBox results;

    public LeagueResultsContent(){
        //Sets some spacing to make the screen look better
        setPadding(new Insets(0, 10, 0, 10));

        gameWeek = new ComboBox<>();
        team = new ComboBox<>();
        results = new VBox();
        updateContent();

        //This is the action listener for the combo box, so when a game week is selected then it will display the corresponding results on the screen
        gameWeek.setOnAction(e -> {
            if(gameWeek.getValue() != null){
                results.getChildren().clear();
                for(Match result : readAllMatches("WHERE date='" + gameWeek.getValue() + "';")){
                    String labelContents = padRight(result.getHomeTeamName(), 20) + result.getScore() +  " " + padRight(result.getAwayTeamName(), 20);
                    logger.info(labelContents);
                    Label label = new Label(labelContents);
                    if(Team.readTeam(result.getHomeTeamCode()).getLeague().equals(readTeamLeague())){
                        results.getChildren().add(label);
                    }
                }
                team.setValue(null);
            }

        });

        //This is the action listener for the combo box, so when a team is selected it will display the corresponding results on the screen
        team.setOnAction(e -> {
            if(team.getValue() != null){
                results.getChildren().clear();
                System.out.println("WHERE homeTeamCode='" + team.getValue().getTeamCode() + "' AND score <> 'null' OR awayTeamCode='" + team.getValue().getTeamCode() + "' AND score <> 'null' ORDER BY date ASC;");
                for(Match result : readAllMatches("WHERE homeTeamCode='" + team.getValue().getTeamCode() + "' AND score <> 'null' OR awayTeamCode='" + team.getValue().getTeamCode() + "' AND score <> 'null' ORDER BY date ASC;")){
                    String labelContents = result.getDate() + " : " + padRight(result.getHomeTeamName(), 20) + result.getScore() + " " + padRight(result.getAwayTeamName(), 20);
                    Label label = new Label(labelContents);
                    if(team.getValue().getLeague().equals(readTeamLeague())){
                        results.getChildren().add(label);
                    }
                }
                gameWeek.setValue(null);
            }
        });



        HBox resultSelector = new HBox();

        Label gameWeekLabel = new Label("Game week:  ");

        Label teamLabel = new Label("  or Team:  ");

        resultSelector.getChildren().addAll(gameWeekLabel, gameWeek, teamLabel, team);

        this.getChildren().add(resultSelector);
        this.getChildren().add(results);
    }

    //this function updates the variables in the combo boxes and clears the screen
    public void updateContent(){
        //Remove the existing numbers from the combo box
        gameWeek.getItems().clear();
        //Remove the existing teams from the combo box
        team.getItems().clear();
        //Remove the existing results from the screen
        results.getChildren().clear();
        //Fill the combo box with all of the game weeks containing results
        for(int i = 1; i < Integer.parseInt(readGameWeek(readSaveName())); i++){
            gameWeek.getItems().add(i);
        }
        gameWeek.getItems().add(null);
        //Fill the team combo box with all of the teams
        for(Team t : readAllTeams("WHERE league='" + GameState.readTeamLeague() + "';")){
            team.getItems().add(t);
        }
        team.getItems().add(null);
    }

    //TODO the padding doesn't work currently, changes are needed to this method
    public static String padRight(String s, int n) {
        String str = String.format("%1$-" + n + "s", s);
        return str;
    }
}
