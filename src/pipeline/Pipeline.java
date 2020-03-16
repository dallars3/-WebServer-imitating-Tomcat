package pipeline;

import value.Value;

public interface Pipeline {
	/**
	 * Get the first Value of this Pipeline, to start traversing
	 * @return first Value of this Pipeline
	 */
	public Value getFirst();
}
