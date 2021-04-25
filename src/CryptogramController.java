import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CryptogramController {
	private CryptogramModel model;
//	private CryptogramGUIView GUIView;
//	private CryptogramTextView TextView;

	public CryptogramController() {
		this.model = new CryptogramModel();
	}

	public boolean checkWin(boolean toPrintOrNotToPrint) {
		if (model.getPCmap().equals(model.getUserMap())) {
			if (toPrintOrNotToPrint) {
				System.out.println();
				System.out.println("SUCCESS! YOU GOT IT!\nGame ended.");
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param filename (input
	 * @return returns string encrypted
	 * @throws FileNotFoundException throws if file not found
	 */
	public String readFileAndReturnQuote(String filePath) throws FileNotFoundException {// surround with try catch

		File file = new File(filePath);
		Random rand = new Random();
		int lines = 0;

		Scanner sc = new Scanner(file);

		while (sc.hasNextLine()) {
			lines++;
			sc.nextLine();
		}

		int n = rand.nextInt((lines - 1) + 1) + 1;

		Scanner newsc = new Scanner(file);
		int i = 1;
		String line = "";
		String toEncrypt2 = "";
		while (newsc.hasNextLine()) {
			line = newsc.nextLine();
			if (i == n) {
				toEncrypt2 = line;
			}
			i++;
		}
		// end reading file and getting specific line
		String toEncrypt = "";
		toEncrypt = toEncrypt2.toUpperCase();

		model.setToEncrypt(toEncrypt);
		return toEncrypt;

	}

	/**
	 * 
	 * @param quote to encrypt message
	 * @return returns array of characters for encrypted message
	 */
	public ArrayList encryptMessage(String quote) {// TODO NO SAME LETTER ASSIGNMENT DONE!!!
		// Create PC HashMap that stores quotation encryption
		model.setToEncrypt(quote);
		ArrayList<Character> encryptedList = new ArrayList<Character>(model.getToEncrypt().length());

		for (int j = 0; j < model.getToEncrypt().length(); j++) {
			if (Character.isLetter(model.getToEncrypt().charAt(j))) {
				for (int k = 0; k < model.getGameAlphabet().size(); k++) {
					if (!model.getPCmap().containsKey(model.getGameAlphabet().get(k))
							&& !model.getPCmap().containsValue(model.getToEncrypt().charAt(j))
							&& (model.getGameAlphabet().get(k) != model.getToEncrypt().charAt(j))) {
						model.getPCmap().put(model.getGameAlphabet().get(k), model.getToEncrypt().charAt(j));
						break;
					}

				}
			}

		}

		for (int l = 0; l < model.getToEncrypt().length(); l++) {
			Character key = null;
			boolean flag = false;
			for (ArrayMap.Entry entry : model.getPCmap().entrySet()) {
				if (Character.valueOf(model.getToEncrypt().charAt(l)).equals(entry.getValue())) {
					String key2 = entry.getKey().toString();
					key = key2.charAt(0);
					flag = true;
					break;
				}
			}

			if (flag == true) {
				encryptedList.add(key);
				model.setEncrypted(model.getEncrypted() + key);
				flag = false;
			} else {
				encryptedList.add((model.getToEncrypt().charAt(l)));
				model.setEncrypted(model.getEncrypted() + (model.getToEncrypt().charAt(l)));
			}
		}

		model.setGameArray(encryptedList);

		return model.getGameArray();

	}

	/**
	 * 
	 * @param limit how many chars per line maximum
	 * @return sb.toString() string of correctly sized lines
	 */
	public String wrapFnc(int limit) {
		String str = model.getEncrypted();
		int maxChar = limit;
		StringBuilder sb = new StringBuilder(str);
		int i = 0;
		while (i + maxChar < sb.length() && (i = sb.lastIndexOf(" ", i + maxChar)) != -1) {
			sb.replace(i, i + 1, "\n");
		}
		return sb.toString();
	}

	/**
	 * Void prints the board to console
	 */
	public void printBoard() {
		int counter = 0;
		int firstInt = 0;
		String wrappedStr = wrapFnc(80);
		for (int i = 0; i < wrappedStr.length(); i++) {
			if (wrappedStr.charAt(i) < 32 && i + 1 <= wrappedStr.length()) {
				System.out.println();
				for (int j = firstInt; j < counter; j++) {
					System.out.print(wrappedStr.charAt(j));
				}
				System.out.println("\n");
				firstInt = i + 1;
			} else {
				if (model.getUserMap().containsKey(wrappedStr.charAt(i))) {
					System.out.print(model.getUserMap().get(wrappedStr.charAt(i)));
				} else {
					if (Character.isLetter(wrappedStr.charAt(i))) {
						System.out.print(" ");
					} else {
						System.out.print(wrappedStr.charAt(i));
					}

				}
			}
			counter++;
			if (counter == wrappedStr.length()) {
				break;
			}
		}
		System.out.println();
		for (int j = firstInt; j < counter; j++) {
			System.out.print(wrappedStr.charAt(j));
		}
		System.out.println();
	}

	public void setChars(GridPane grid, CryptogramModel model, BorderPane window) {

		String wrappedStr = wrapFnc(30);

		int posX = 0;
		int posY = 0;
		for (int i = 0; i < wrappedStr.length(); i++) {
			if (wrappedStr.charAt(i) < 32) {
				posY++;
				posX = 0;
			} else {
				VBox vbox1 = new VBox();

				Label lab1 = new Label(Character.toString(wrappedStr.charAt(i)));
				lab1.setId(Character.toString(wrappedStr.charAt(i)));
				TextField tex = new TextField();
				tex.setPrefColumnCount(1);

				tex.setOnAction((keyTyped) -> {
					keyTyped.consume();
					model.replaceCommand(lab1.getText().charAt(0), tex.getText().charAt(0));

					if (checkWin(false)) {
						Alert alert = new Alert(AlertType.INFORMATION, "You won!");
						alert.showAndWait();
						grid.getChildren().forEach(node -> {
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
				});

				if (model.getUserMap().containsKey(lab1.getText().charAt(0))
						&& !model.getUserMap().get(lab1.getText().charAt(0)).equals(null)) {
					tex.setText(Character.toString(model.getUserMap().get(lab1.getText().charAt(0))));
				}
				else {
				}

				vbox1.getChildren().addAll(tex, lab1);
				vbox1.setAlignment(Pos.CENTER);
				vbox1.setId(Character.toString(lab1.getText().charAt(0)));
				grid.add(vbox1, posX, posY);
				posX++;
				if (!Character.isLetter(wrappedStr.charAt(i))) {
					tex.setDisable(true);
					tex.setText(Character.toString(wrappedStr.charAt(i)));
					tex.setAlignment(Pos.CENTER);
				}
			}
		}
	}

	/**
	 * 
	 * @param quote to be encrypted
	 * @return string as flag
	 */
	public String freqCommand(String quote, int x) {
		int lineCounter = 0;
		String toReturn = "";
		char[] arr = quote.toCharArray();
		// Arrays.sort(arr);
		// System.out.println(arr);
		// System.out.println(Alphabet);
		// System.out.println(gameAlphabet);
		// HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
		for (int i = 0; i < model.getAlphabet().size(); i++) {
			int counter = 0;
			for (int j = 0; j < arr.length; j++) {
				if (arr[j] == model.getAlphabet().get(i)) {
					counter++;
				}
			}
			if (lineCounter == x) {
				toReturn += ("\n");
				lineCounter = 0;
			}
			if (x == 7) {
				toReturn += model.getAlphabet().get(i) + ": " + counter + " ";
			} else if (x == 2) {
				toReturn += model.getAlphabet().get(i) + ": " + counter + "      ";
			}
			lineCounter++;
		}
		return toReturn;
	}

	/**
	 * 
	 * @param toReplace   char to be replaced
	 * @param replacement char to replace
	 * @return String as flag
	 */
	public String equalsCommand(char toReplace, char replacement) {
		boolean flag = false;
		if (Character.isUpperCase(toReplace) && Character.isUpperCase(replacement)) {
			model.getUserMap().put(toReplace, replacement);
			flag = true;
		}
		if (flag) {
			return "success!\n";
		}
		return "illegal mapping";
	}

	public CryptogramModel getMod() {
		return model;
	}

	/**
	 * 
	 * @return, returns string of help command listing supported commandsx
	 */
	public String helpCommand() {
		String toReturn = "";
		toReturn += "Supported commands:\n";
		toReturn += "a.\treplace X by Y - replace letter X by Y\n";
		toReturn += "\tX = Y - a shortcut for this same command\n";
		toReturn += "b.\tfreq - Display the letter frequencies in the encrypted quotation (i.e., how many of letter X appear)\n";
		toReturn += "c.\thint - Display one correct mapping that has not yet been guessed\n";
		toReturn += "d.\texit – Ends the game early\n";
		toReturn += "e.\thelp - List these commands";
		return toReturn;
	}

}
