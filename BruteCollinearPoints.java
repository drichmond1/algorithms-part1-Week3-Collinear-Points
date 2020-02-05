import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private Point[] points;
	private LineSegment[] segments;
	private int segmentArrayPointer;
	private int size;
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
	   if( points == null) {
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
	   segmentArrayPointer ++;
	   if(segmentArrayPointer == segments.length) {
		   doubleArray();
	   }
	   size ++;
	   
   }
   private void doubleArray() {
	   LineSegment[] segments = new LineSegment[this.segments.length * 2];
	   for (int x = 0 ; x < this.segments.length; x ++) {
		   segments[x] = this.segments[x];
	   }
	   this.segments = segments;	
}

private void findSegments() {
	   for (int i = 0; i < points.length; i ++) {
		
		for (int j = 0; j < points.length; j ++) {
			if(points[j].compareTo(points[i]) < 1) {
				continue;
			}
			double slope = points[i].slopeTo(points[j]);
			for (int k= 0; k < points.length; k ++) {
				if(points[k].compareTo(points[j]) < 1) {
					continue;
				}
				double slope1 = points[j].slopeTo(points[k]);
				if (slope == slope1) {
					for(int l = 0; l<points.length; l ++) {
						if(points[l].compareTo(points[k]) < 1) {
							continue;
						}
						double slope2 = points[j].slopeTo(points[l]);
						if(slope == slope2) {
							addToSegment(new LineSegment(points[i], points[l]));                  	
						}
					}
				}
			}
		}
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

public int numberOfSegments()        // the number of line segments
   {
	   return size;
   }
   public LineSegment[] segments()                // the line segments
   {
	   LineSegment[] segments = new LineSegment[size];
	   for (int x = 0 ; x < size; x ++) {
		   segments[x] = this.segments[x];
	   }
	   return segments;	
   }
   
   public static void main(String [] args) {
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
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
		   
		
	}
 }
