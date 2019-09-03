package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;



/**
 * @author Vigneet Sompura
 * Contains utilities and static methods for recognizing gestures using $P recognizer.
 *
 */
public class DollarPOriginal {

	
	
	/**
	 * Reamples the given template to result in specified number of points.
	 * 
	 * @param template Gesture Template to be resampled
	 * @param N: sampling rate 
	 * @return resampled template 
	 */
	public static Template resample(Template template, int N) {
		Template t = template.clone();
		double I = pathLength(t)/(N-0.5);
		double D = 0;
		Template temp = new Template(t.getType(), t.getID());
		ArrayList<Point> p = t.getPoints();
		temp.addPoint(p.get(0));
		for(int i=1; i<p.size(); i++) {
			if(p.get(i-1).getStrokeID()==p.get(i).getStrokeID()) {
				double d = distance(p.get(i-1),p.get(i));
				if((D+d)>=I) {
					double x = p.get(i-1).getX()+((I-D)/d)*(p.get(i).getX()-p.get(i-1).getX());
					double y = p.get(i-1).getY()+((I-D)/d)*(p.get(i).getY()-p.get(i-1).getY()); 
					temp.addPoint(new Point(x,y,p.get(i).getStrokeID()));
					p.add(i, new Point(x,y,p.get(i).getStrokeID()));
					D=0;
				}else {
					D = D+d;
				}
			}
		}
		return temp;
	}
	
	/**
	 * Computes the path length of a gesture
	 * 
	 * @param t  Template 
	 * @return path length
	 */
	public static double pathLength(Template t) {
		double d = 0;
		ArrayList<Point> p = t.getPoints();
		for(int i=1; i<p.size(); i++) {
			if(p.get(i-1).getStrokeID()==p.get(i).getStrokeID()) {
				d += distance(p.get(i-1), p.get(i));
			}
		}
		return d;
	}
	
	/**
	 * @param p1: Point 1
	 * @param p2: Point 2
	 * @return euclidian distance between two points
	 */
	public static double distance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.getX()-p2.getX(), 2)+Math.pow(p1.getY()-p2.getY(), 2));
	}
	
	/**
	 * Performs uniform scaling on given template.
	 * 
	 * @param temp Template
	 * @return scaled template
	 */
	public static Template scale(Template temp) {
		double xmin = 9999999, ymin = 9999999;
		double xmax = 0, ymax = 0;
		ArrayList<Point> points = temp.getPoints();
		Template t = new Template(temp.getType(),temp.getID());
		for(Point p : points) {
			xmin = Math.min(xmin, p.getX());
			ymin = Math.min(ymin, p.getY());
			xmax = Math.max(xmax, p.getX());
			ymax = Math.max(ymax, p.getY());
		}
		double scale = Math.max(xmax-xmin, ymax-ymin);
		for(Point p: points) {
			t.addPoint(new Point((p.getX()-xmin)/scale,(p.getY()-ymin)/scale,p.getStrokeID()));
		}
		
		return t;
	}
	
	/**
	 * Translates the template(centroid) to origin.
	 * 
	 * @param temp Template
	 * @return template translated to origin
	 */
	public static Template translateToOrigin(Template temp) {
		double x=0, y=0;
		Template t = new Template(temp.getType(), temp.getID());
		for(Point p: temp.getPoints()) {
			x = x+p.getX();
			y = y+p.getY();
		}
		x = x/temp.getPoints().size();
		y = y/temp.getPoints().size();
		for(Point p: temp.getPoints()) {
			t.addPoint(new Point(p.getX()-x, p.getY()-y,p.getStrokeID()));
		}
		return t;
	}
	
	/**
	 * Returns normalized template after performing resampling-scale-translation(to Origin)
	 * 
	 * @param temp Template
	 * @param N: sampling rate 
	 * @return Normalized template
	 */
	public static Template normalize(Template temp, int N) {
		Template p = temp.clone();
		Template q = resample(p,N);
		Template r = scale(q);
		Template s = translateToOrigin(r);
		//System.out.println(temp.getPoints().size()+":"+p.getPoints().size()+":"+q.getPoints().size()+":"+r.getPoints().size()+":"+s.getPoints().size());
		return s;
	}
	
	/**
	 * Calculates the min diatance between candidate and template 
	 * with sampling rate N considering start as starting index.
	 * 
	 * @param candidate gesture 
	 * @param temp Template gesture
	 * @param N: sampling rate
	 * @param start starting point
	 * @return sum of min distance over all points
	 */
	public static double cloudDistance(Template candidate, Template temp, int N, int start) {
		ArrayList<Point> t = temp.clone().getPoints();
		ArrayList<Point> c = candidate.getPoints();
		double sum = 0;
		int i = start;
		do {
			double min = 99999999;
			int index=0;
			for(int j=0; j<t.size();j++) {
				double d = distance(c.get(i), t.get(j));
				if (d<min) {
					min = d;
					index = j;
				}
			}
			t.remove(index);
			double weight = 1 - ((i-start+N)%N)/N;
			sum = sum + weight*min;
			i = (i+1)%N;
		}while(i!=start);
		return sum;
	}
	
	/**
	 *  Calculates min distance between candidate and gesture considering all 
	 *         possible starting points
	 * 
	 * @param candidate gesture 
	 * @param temp Template gesture
	 * @param N sampling rate
	 * @return min distance 
	 */
	public static double greedyCloudMatch(Template candidate, Template temp, int N) {
		double min = 99999999;
		int step = (int) Math.floor(Math.pow(N, 0.5));
		for(int i=0; i<N; i+=step) {
			double d1 = cloudDistance(candidate, temp, N, i);
			double d2 = cloudDistance(temp, candidate, N, i);
			min = Math.min(min, Math.max(d1, d2));
		}
		return min;
	}
	
	
	/**
	 * @param candidate gesture 
	 * @param templates set of training templates 
	 * @return Result object containing the best Match and NBest List
	 */
	public static Result recognize(Template candidate, ArrayList<Template> templates) {
		int N = templates.get(0).getPoints().size();
		Template c = normalize(candidate, N);
		double score = 99999999;
		
		// holds NBest List
		
		ArrayList<Result> NB = new ArrayList<Result>(); 
		Template r = null;
		for(Template t: templates) {
			double d = greedyCloudMatch(c, t, N);
			
			if(score>d) {
				score = d;
				r = t.clone();
			}
			NB.add(new Result(t.clone(),d));
		}
		Collections.sort(NB);
		ArrayList<String> nBest = new ArrayList<String>();
		int i = 0;
		while(i<NB.size() && i<20) {
			Result rs = NB.get(i);
			nBest.add(rs.getTemp().getTypeEnum()+"-"+rs.getTemp().getID()+":"+String.format("%.2f", rs.getScore()));
			i++;
		}
		//score = Math.max((2-score)/2, 0);
		return new Result(r, score, nBest);
	}
}
