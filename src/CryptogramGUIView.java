import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CryptogramGUIView extends Application implements Observer {

	private CryptogramController control;
	private GridPane gridPane;
	private BorderPane window;

	@Override
	public void start(Stage primaryStage) throws Exception {
		/////// create instances

		control = new CryptogramController();

		window = new BorderPane();

		VBox rightSection = new VBox();

		Button newPuzz = new Button();
		newPuzz.setText("New Puzzle");
		newPuzz.setMinWidth(97);
		newPuzz.setId("newPuzz");
		Button hintButt = new Button();
		hintButt.setText("Hint");
		CheckBox checkbox = new CheckBox("Show Freq");
		Label freqSection = new Label();

		EventHandler<ActionEvent> newPuzzEvent = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				control = new CryptogramController();
				try {
					start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		newPuzz.setOnAction(newPuzzEvent);

		control.readFileAndReturnQuote("src/quotes.txt");
		control.encryptMessage(control.getMod().getToEncrypt());

		String freq = control.freqCommand(control.getMod().getEncrypted(), 2);

		EventHandler<ActionEvent> freqBoxEvent = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (checkbox.isSelected()) {
					freqSection.setText(freq);
				} else {
					freqSection.setText("");
				}

			}

		};

		checkbox.setOnAction(freqBoxEvent);

		EventHandler<ActionEvent> hintEvent = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				control.getMod().hintCommand();
				if (control.checkWin(false)) {
					Alert alert = new Alert(AlertType.INFORMATION, "You won!");

					alert.showAndWait();
					// System.out.println(window.getChildren().toString());
					gridPane.getChildren().forEach(node -> {
						node.setDisable(true);
					});
					window.getChildren().forEach(node -> {
						if (node instanceof VBox) {
							for (int u = 1; u < 3; u++) {
								((VBox) node).getChildren().get(u).setDisable(true);
							}
						}
					});
				}

			}

		};

		hintButt.setOnAction(hintEvent);

		// gridpane///////////////////////////

		gridPane = new GridPane();
		control.getMod().addObserver(this);
		control.setChars(gridPane, control.getMod(), window); // add word parameter

		//////////////////////////////////////

		rightSection.getChildren().addAll(newPuzz, hintButt, checkbox, freqSection);
		rightSection.setId("rightSec");
		window.setCenter(gridPane);
		window.setRight(rightSection);

		Scene scene = new Scene(window, 900, 400);
		primaryStage.setTitle("Cryptograms");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void update(Observable o, Object arg) {
		for (int l = 0; l < gridPane.getChildren().size(); l++) {
			if (control.getMod().getUserMap().containsKey(gridPane.getChildren().get(l).getId().toString().charAt(0))) {
				gridPane.getChildren().get(l).setAccessibleText(Character.toString(
						control.getMod().getUserMap().get(gridPane.getChildren().get(l).getId().toString().charAt(0))));
			}
		}
		gridPane.getChildren().removeAll(gridPane.getChildren());

		control.setChars(gridPane, control.getMod(), window);

	}

}