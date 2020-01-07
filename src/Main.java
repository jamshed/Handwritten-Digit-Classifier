import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class Main {

	final static int EXAMPLES = 5000;
	final static int FEATURES = 400;
	final static int CLASSES = 10;
	final static int K = 50;

	public static int X[][] = new int[EXAMPLES][FEATURES];
	public static int y[] = new int[EXAMPLES];

	public static void read_data() throws Exception {
		BufferedReader featureReader = new BufferedReader(new FileReader(
				"data//X.csv"));
		BufferedReader outputReader = new BufferedReader(new FileReader(
				"data//Y.csv"));

		String featureStr = null;
		String outputStr = null;

		int examplesCount = 0;
		while (true) {
			featureStr = featureReader.readLine();
			outputStr = outputReader.readLine();
			if (featureStr == null)
				break;

			int featureCount = 0;

			String[] features = featureStr.split(",");
			for (String f : features) {
				X[examplesCount][featureCount] = Integer.parseInt(f);
				featureCount++;
			}

			y[examplesCount] = Integer.parseInt(outputStr);
			examplesCount++;

		}

		featureReader.close();
		outputReader.close();
	}

	public static void main(String[] args) throws Exception {
		read_data();

		Random random = new Random();
		int randomExampleNumber = random.nextInt(EXAMPLES);

		new DigitDisplay(X[randomExampleNumber]);

		NaiveBayesClassifier naiveBayes = new NaiveBayesClassifier(EXAMPLES,
				FEATURES, CLASSES, X, y);

		kNN_Classifier kNN = new kNN_Classifier(EXAMPLES, FEATURES, CLASSES, X,
				y, K);

		naiveBayes.train();

		System.out.println("Predicted digit (Naive Bayes): "
				+ naiveBayes.classify(X[randomExampleNumber]));

		System.out.println("Predicted digit (k-NN): "
				+ kNN.classify(X[randomExampleNumber]));

		System.out.println("\nActual digit: " + y[randomExampleNumber]);

		measure_accuracy(naiveBayes, kNN);
	}

	static void measure_accuracy(NaiveBayesClassifier naiveBayes,
			kNN_Classifier kNN) {

		int RUNS = 100;
		double correctNaiveBayes = 0;
		double correct_kNN = 0;

		for (int i = 0; i < RUNS; ++i) {

			int ex = new Random().nextInt(EXAMPLES);

			if (naiveBayes.classify(X[ex]) == y[ex])
				correctNaiveBayes++;

			if (kNN.classify(X[ex]) == y[ex])
				correct_kNN++;
		}

		System.out
				.println("Naive Bayes Accuracy = " + correctNaiveBayes / RUNS);
		System.out.println("kNN Accuracy = " + correct_kNN / RUNS);
	}
}

@SuppressWarnings("serial")
class DigitDisplay extends JFrame {
	int[][] data = new int[20][20];

	public DigitDisplay(int[] d) {
		super("Digit Display");
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 20; j++)
				data[i][j] = d[i * 20 + j];
		setSize(400, 400);
		setVisible(true);
	}

	public void paint(Graphics g) {
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 20; j++) {
				if (data[i][j] == 1)
					g.fillRect(i * 20, j * 20, 20, 20);
			}
	}
}
