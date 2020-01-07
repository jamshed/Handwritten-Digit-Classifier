import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class kNN_Classifier {
	int exampleCount;
	int featureCount;
	int classCount;
	int[][] F; // Features of the training examples
	int[] Y; // Classifications of the training examples

	int k;

	public kNN_Classifier(int exampleCount, int featureCount, int classCount,
			int[][] f, int[] y, int k) {
		super();
		this.exampleCount = exampleCount;
		this.featureCount = featureCount;
		this.classCount = classCount;
		F = f;
		Y = y;
		this.k = k;
	}

	int distance(int[] features1, int[] features2) {
		int d = 0;

		for (int i = 0; i < featureCount; ++i)
			if (features1[i] != features2[i])
				d++;

		return d;
	}

	int classify(int[] features) {
		ArrayList<Neighbor> N = new ArrayList<Neighbor>();
		int[] classFrequency = new int[classCount];
		int maxFrequency = 0;
		int bestClass = -1;

		for (int i = 0; i < exampleCount; ++i)
			N.add(new Neighbor(Y[i], distance(features, F[i])));

		Collections.sort(N);

		for (int i = 0; i < k; ++i)
			classFrequency[N.get(i).label]++;

		// System.out.println(Arrays.toString(classFrequency));

		for (int i = 0; i < classCount; ++i)
			if (maxFrequency < classFrequency[i]) {
				maxFrequency = classFrequency[i];
				bestClass = i;
			}

		return bestClass;
	}

	class Neighbor implements Comparable<Neighbor> {
		int label;
		int distance;

		public Neighbor(int label, int distance) {
			super();
			this.label = label;
			this.distance = distance;
		}

		@Override
		public int compareTo(Neighbor n) {
			return this.distance - n.distance;
		}

	}

}
