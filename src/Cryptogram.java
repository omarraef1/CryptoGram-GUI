import javafx.application.Application;

public class Cryptogram {

	public static void main(String[] args) {
		// TODO check with previous grading

		// check command line arguments
		// and launch game
		int viewFlag = 0;
		if (args.length == 0) { // not specified
			Application.launch(CryptogramGUIView.class, args);// TODO check correctness of args??
			viewFlag = 0;
		}
		else if (args.length > 0 && args[0].equals("-text")) { // check in console TODO
			CryptogramTextView textView = new CryptogramTextView();
			textView.main(args);
			// start textView game
			viewFlag = 1;
		} else if (args.length > 0 && args[0].equals("-window")) {
			Application.launch(CryptogramGUIView.class, args);// TODO check correctness of args??
			viewFlag = 0;
		} else {
			System.out.println("View option not available\n");
			System.exit(-1);
		}

	}

}
