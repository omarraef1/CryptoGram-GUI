import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

public class CryptogramModel extends Observable {
	private String toEncrypt;
	private String encrypted = ""; // might not need TODO
	private ArrayList<Character> originalAlphabet = new ArrayList<Character>(); // might not need TODO
	private ArrayList<Character> Alphabet = new ArrayList<Character>();
	private ArrayList<Character> gameAlphabet = new ArrayList<Character>();
	private String board;// might not need TODO
	private ArrayMap<Character, Character> PCmap = new ArrayMap<Character, Character>();
	private ArrayMap<Character, Character> Usermap = new ArrayMap<Character, Character>();
	private ArrayList<Character> gameArray = new ArrayList<>();

	public CryptogramModel() {
		getGameAlphabet().add('A');
		getGameAlphabet().add('B');
		getGameAlphabet().add('C');
		getGameAlphabet().add('D');
		getGameAlphabet().add('E');
		getGameAlphabet().add('F');
		getGameAlphabet().add('G');
		getGameAlphabet().add('H');
		getGameAlphabet().add('I');
		getGameAlphabet().add('J');
		getGameAlphabet().add('K');
		getGameAlphabet().add('L');
		getGameAlphabet().add('M');
		getGameAlphabet().add('N');
		getGameAlphabet().add('O');
		getGameAlphabet().add('P');
		getGameAlphabet().add('Q');
		getGameAlphabet().add('R');
		getGameAlphabet().add('S');
		getGameAlphabet().add('T');
		getGameAlphabet().add('U');
		getGameAlphabet().add('V');
		getGameAlphabet().add('W');
		getGameAlphabet().add('X');
		getGameAlphabet().add('Y');
		getGameAlphabet().add('Z');

		Collections.shuffle(getGameAlphabet());

		getAlphabet().add('A');
		getAlphabet().add('B');
		getAlphabet().add('C');
		getAlphabet().add('D');
		getAlphabet().add('E');
		getAlphabet().add('F');
		getAlphabet().add('G');
		getAlphabet().add('H');
		getAlphabet().add('I');
		getAlphabet().add('J');
		getAlphabet().add('K');
		getAlphabet().add('L');
		getAlphabet().add('M');
		getAlphabet().add('N');
		getAlphabet().add('O');
		getAlphabet().add('P');
		getAlphabet().add('Q');
		getAlphabet().add('R');
		getAlphabet().add('S');
		getAlphabet().add('T');
		getAlphabet().add('U');
		getAlphabet().add('V');
		getAlphabet().add('W');
		getAlphabet().add('X');
		getAlphabet().add('Y');
		getAlphabet().add('Z');

	}

	/**
	 * 
	 * @param toReplace   char to be replaced
	 * @param replacement letter for later use
	 * @return string as flag
	 */
	@SuppressWarnings("deprecation")
	public String replaceCommand(char toReplace, char replacement) {
		boolean flag = false;
		if (Character.isUpperCase(toReplace) && Character.isUpperCase(replacement)) {
			Usermap.put(toReplace, replacement);
			flag = true;

		}
		setChanged();
		notifyObservers();
		if (flag) {
			return "success!\n";
		}
		return "illegal mapping";
	}

	/**
	 * 
	 * @return
	 * @return null, the method processes the hint and alters the user's map
	 */
	public void hintCommand() {
		boolean flag = false;
		for (ArrayMap.Entry entry : getPCmap().entrySet()) {
			if (!Usermap.containsKey(entry.getKey())) {
				Usermap.put(entry.getKey().toString().charAt(0), entry.getValue().toString().charAt(0));
				flag = true;
				break;
			}
		}
		if (!flag) {
			for (ArrayMap.Entry entry : getPCmap().entrySet()) {
				if (Usermap.containsKey(entry.getKey())) {
					if (!Usermap.get(entry.getKey()).equals(entry.getValue())) {
						Usermap.put(entry.getKey().toString().charAt(0), entry.getValue().toString().charAt(0));
						flag = true;
						break;
					}
				}
			}
		}

		setChanged();
		notifyObservers();
	}

	public String getToEncrypt() {
		return toEncrypt;
	}

	public void setToEncrypt(String toEncrypt) {
		this.toEncrypt = toEncrypt;
	}

	public String getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}

	public ArrayList<Character> getOriginalAlphabet() {
		return originalAlphabet;
	}

	public void setOriginalAlphabet(ArrayList<Character> originalAlphabet) {
		this.originalAlphabet = originalAlphabet;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public ArrayMap<Character, Character> getUserMap() {
		return Usermap;
	}

	public ArrayList<Character> getGameArray() {
		return gameArray;
	}

	public void setGameArray(ArrayList<Character> gameArray) {
		this.gameArray = gameArray;
	}

	public ArrayMap<Character, Character> getPCmap() {
		return PCmap;
	}

	public ArrayList<Character> getGameAlphabet() {
		return gameAlphabet;
	}

	public ArrayList<Character> getAlphabet() {
		return Alphabet;
	}

}
