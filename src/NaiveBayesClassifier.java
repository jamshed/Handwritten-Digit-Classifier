public class NaiveBayesClassifier {
	int exampleCount;
	int featureCount;
	int classCount;
	int[][] F; // Features of the training examples
	int[] Y; // Classifications of the training examples

	double[] P_Y;
	double[][] P_FY;

	public NaiveBayesClassifier(int exampleCount, int featureCount,
			int classCount, int[][] f, int[] y) {
		super();
		this.exampleCount = exampleCount;
		this.featureCount = featureCount;
		this.classCount = classCount;
		F = f;
		Y = y;

		P_Y = new double[classCount];
		P_FY = new double[classCount][featureCount];

	}

	public void train() {
		int[] count = new int[classCount];

		for (int i = 0; i < exampleCount; ++i)
			count[Y[i]]++;

		for (int i = 0; i < classCount; ++i)
			P_Y[i] = (double) count[i] / exampleCount;

		// System.out.println(Arrays.toString(P_Y));

		for (int i = 0; i < exampleCount; ++i)
			for (int j = 0; j < featureCount; ++j)
				if (F[i][j] == 1)
					P_FY[Y[i]][j]++;

		for (int i = 0; i < classCount; ++i)
			for (int j = 0; j < featureCount; ++j)
				P_FY[i][j] /= count[i];
	}

	public int classify(int[] features) {

		double prob;
		double maxProb = 0;
		int bestClass = -1;

		for (int i = 0; i < classCount; ++i) {
			prob = P_Y[i];
			for (int j = 0; j < featureCount; ++j)
				if (features[j] == 1)
					prob *= P_FY[i][j];
				else
					prob *= (1 - P_FY[i][j]);

			if (maxProb < prob) {
				maxProb = prob;
				bestClass = i;
			}
		}

		return bestClass;
	}
}
