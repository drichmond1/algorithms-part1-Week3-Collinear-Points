import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private Point[] points;
	private LineSegment[] segments;
	private int segmentArrayPointer;
	private int size;

	public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
	{
		if (points == null) {
			throw new IllegalArgumentException("points array cannot be null");
		}
		Point[] pointsCopy = points.clone();
		checkForRepeatedAndNullElements(pointsCopy);

		this.points = pointsCopy;

		segments = new LineSegment[1];
		segmentArrayPointer = 0;
		findSegments();
	}

	private void addToSegment(LineSegment line) {
		segments[segmentArrayPointer] = line;
		segmentArrayPointer++;
		if (segmentArrayPointer == segments.length) {
			doubleArray();
		}
		size++;

	}

	private void doubleArray() {
		LineSegment[] segments = new LineSegment[this.segments.length * 2];
		for (int x = 0; x < this.segments.length; x++) {
			segments[x] = this.segments[x];
		}
		this.segments = segments;
	}

	private void findSegments() {
		Point[][] temp = new Point[points.length][points.length - 1];
		for (int i = 0; i < points.length; i++) {
			int pointer = 0;
			for (int x = 0; x < points.length; x++) {
				if (x == i) {

					continue;
				}
				temp[i][pointer] = points[x];
				pointer++;
			}
			Arrays.sort(temp[i], points[i].slopeOrder());


		}
		findCommonSlopes(temp);
	}

	private void findCommonSlopes(Point[][] sortedBySlope) {
		for (int x = 0; x < points.length; x++) {

			double currentSlope = 0;

			int count = 0;
			Point[] temp = new Point[sortedBySlope[x].length + 1];
			temp[0] = sortedBySlope[x][0];

			for (int i = 0; i < sortedBySlope[x].length; i++) {
				if(sortedBySlope[x].length == 0 ) {
					return;
				}
				double slope = points[x].slopeTo(sortedBySlope[x][i]);

				if (i == 0 || slope != currentSlope) {
					currentSlope = slope;

					if (count > 2) {
						addToSegments(temp, count, points[x]);
					}
					temp[0] = sortedBySlope[x][i];
					count = 1;
				} else {
					temp[count] = sortedBySlope[x][i];
					count++;
				}
			}
			if(count > 2) {
				addToSegments(temp, count, points[x]);
			}
		}
	}

	private void addToSegments(Point[] temp, int count, Point point) {
		
	if(point.compareTo(temp[0]) <= 0) {
		addToSegment(new LineSegment(point, temp[count - 1]));
	}
	}
	
	private void checkForRepeatedAndNullElements(Point[] points) {
		for (int x = 0; x < points.length; x++) {
			if(points[x] == null) {
				throw new IllegalArgumentException("cannot have null points");
			}		
		}
		Arrays.sort(points);
		
		Point currentPoint = points[0];
		
		for (int x = 1; x < points.length; x++) {
			
			if (currentPoint.compareTo(points[x]) == 0) {
				throw new IllegalArgumentException("cannot have duplicate points");
			}
			currentPoint = points[x];
		}

	}

	public int numberOfSegments() // the number of line segments
	{
		return size;
	}

	public LineSegment[] segments() // the line segments
	{
		LineSegment[] segments = new LineSegment[size];

		for (int x = 0; x < size; x++) {
			segments[x] = this.segments[x];
		}
		return segments;
	}

	public static void main(String[] args) {
		// read the n points from a file
		In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();

	}
}