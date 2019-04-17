package utilities;

import java.util.ArrayList;

/**
 * @author Vigneet Sompura
 *
 * Container for result
 */
public class Result implements Comparable<Result>{
	/**
	 * Template resulting in the best match
	 */
	private Template temp; 
	/**
	 * Score of the best match
	 */
	private double score;
	/**
	 * NBest List
	 */
	private ArrayList<String> nBestList;
	

	
	/**
	 * @return the str
	 */
	public ArrayList<String> getStr() {
		return nBestList;
	}

	/**
	 * @param str the str to set
	 */
	public void setStr(ArrayList<String> str) {
		this.nBestList = str;
	}

	/**
	 * @param temp Template
	 * @param score Score
	 */
	public Result(Template temp, double score) {
		super();
		this.temp = temp;
		this.score = score;
	}

	/**
	 * @param temp Template
	 * @param score Score
	 * @param str NBest List
	 */
	public Result(Template temp, double score, ArrayList<String> str) {
		super();
		this.temp = temp;
		this.score = score;
		this.nBestList = str;
	}

	/**
	 * @return the temp
	 */
	public Template getTemp() {
		return temp;
	}

	/**
	 * @param temp the temp to set
	 */
	public void setTemp(Template temp) {
		this.temp = temp;
	}

	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public int compareTo(Result o) {
		Double a = this.getScore();
		Double b = o.getScore();
		return a.compareTo(b);
	}
	
	
}
