import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class CryptogramTextView {

	public static void main(String[] args) {
		CryptogramController control = new CryptogramController();

		// prompt user for file
		String filePath = "src/quotes.txt";
		int flag = 0;
		String toEncrypt = "";
		try {

			toEncrypt = control.readFileAndReturnQuote(filePath);
			flag = 1;

		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			flag = 0;
			e.printStackTrace();
		}
		int turnCount = 0;
		if (flag == 0) {
			System.out.println("Rerun program with right filepath in main.");
		}

		else if (flag == 1) {
			// rest of program and game

			ArrayList encrypted = control.encryptMessage(toEncrypt);

			control.printBoard();
			System.out.print("Enter a command (help to see commands): ");

//					
			String encryptedAsString = "";
			for (int u = 0; u < encrypted.size(); u++) {
				encryptedAsString += (encrypted.get(u));
			}

			boolean gameFlag = true;
			Scanner usersc = new Scanner(System.in);

			while (gameFlag == true) {
				String userInput = usersc.nextLine();
				// print board first // no letter should map to itself

				if (userInput.compareTo("help") == 0) {
					// command printing method
					System.out.println(control.helpCommand());
					System.out.println();
				} else if (userInput.compareTo("freq") == 0) {
					// letter frequency method
					System.out.println(control.freqCommand(encryptedAsString, 7));
					System.out.println();

				} else if (userInput.compareTo("hint") == 0) {
					// display one correct mapping that has not yet been guessed
					// hinting method
					control.getMod().hintCommand();
					System.out.println();

				} else if (userInput.length() >= 13 && userInput.substring(0, 7).compareTo("replace") == 0
						&& userInput.substring(10, 12).compareTo("by") == 0) {
					// replace stuff and check letters
					// letter replacing method
					control.getMod().replaceCommand(userInput.charAt(8), userInput.charAt(13));
					System.out.println();

				} else if (userInput.length() > 1 && Character.toString(userInput.charAt(2)).compareTo("=") == 0) {
					// replace stuff and check letters
					// letter replacing method
					control.equalsCommand(userInput.charAt(0), userInput.charAt(4));
					System.out.println();

				} else if (userInput.compareTo("exit") == 0) {
					gameFlag = false;
					System.out.println("Game exited early, sad to see you go\n");
					break;
				} else {
					System.out.println("Command not found, try again");
					System.out.println();
				}

				// check if hashmaps are equal and end game if true
				if (control.checkWin(true)) {
					control.printBoard();
					break;
				}
				control.printBoard();
				System.out.print("Enter a command (help to see commands): ");
			}
		}
	}
}
