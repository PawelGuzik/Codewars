package voronoi;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Voronoi

{
	public static double[] voronoi_areas(Point[] pts) {
		if (pts == null || pts.length == 0) {
			return new double[0];
		}
		Point2D[] points = convertPointToPoint2D(pts);

		Point startingPoint = pts[0];
		double startX = startingPoint.x;
		double startY = startingPoint.y;

		List<Cell> cells = new ArrayList<>();

		Point2D northEast = new Point2D(startX + 100, startY + 100d);
		Point2D northWest = new Point2D(startX + 100d, startY - 100d);
		Point2D southEast = new Point2D(startX - 100d, startY + 100d);
		Point2D southWest = new Point2D(startX - 100d, startY - 100d);

		Edge north = new Edge(northEast, northWest);
		Edge east = new Edge(northEast, southEast);
		Edge south = new Edge(southEast, southWest);
		Edge west = new Edge(northWest, southWest);
		north.setBoundary();
		east.setBoundary();
		south.setBoundary();
		west.setBoundary();

		Cell centralCell = new Cell(new Point2D(startX, startY));
		centralCell.edges.add(west);
		centralCell.edges.add(north);
		centralCell.edges.add(east);
		centralCell.edges.add(south);

		cells.add(centralCell);

		for (int i = 0; i < points.length; i++) {
			Point2D site = points[i];
			// System.out.println(site);
			Cell newCell = new Cell(site);
			Iterator<Cell> iterCell = cells.iterator();
			while (iterCell.hasNext()) {
				Cell c = iterCell.next();
				// System.out.println(c.toString());
				Line pb = prependicularBisector(site, c.site);
				List<Point2D> cutEdge = new ArrayList<>();
				// [7] Create a data structure X to hold the critical points
				if (!site.equals(c.site)) {
					for (int n = 0; n < c.edges.size(); n++) {
						Edge e = c.edges.get(n);
						BigDecimal aDist = pointLineDistance(e.a, pb);
						BigDecimal bDist = pointLineDistance(e.b, pb);
						BigDecimal siteDist = pointLineDistance(site, pb);
						Point2D intersect = calculateEdgeBisecIntersection(e, pb);

						if ((aDist.signum() <= 0 && bDist.signum() <= 0 && siteDist.signum() <= 0)
								|| (aDist.signum() >= 0 && bDist.signum() >= 0 && siteDist.signum() >= 0)) {

							if (e.isBoundary) {
								c.edges.remove(n);
								newCell.edges.add(e);
								n--;
							} else {
								c.edges.remove(n);
								n--;
							}
						}

						if (intersect != null) {
							if ((aDist.setScale(14, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) == 0
									|| bDist.setScale(14, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) == 0)) {
								if (!cutEdge.contains(intersect)) {
									cutEdge.add(intersect);
								}
							} else {
								if (aDist.signum() == siteDist.signum()) {
									if (e.isBoundary) {
										Edge boundaryEdge = new Edge(intersect, e.a);
										boundaryEdge.setBoundary();
										newCell.edges.add(boundaryEdge);
									}
									e.a = intersect;
								} else {
									if (e.isBoundary) {
										Edge boundaryEdge = new Edge(intersect, e.b);
										boundaryEdge.setBoundary();
										newCell.edges.add(boundaryEdge);
									}
									e.b = intersect;
								}
								cutEdge.add(intersect);
							}
						}
					}
				}
				if (cutEdge.size() > 1) {
					Edge nextEdge = new Edge(cutEdge.get(0), cutEdge.get(1));
					c.edges.add(nextEdge);
					newCell.edges.add(nextEdge);
				}
			}
			if (newCell.edges.size() != 0) {
				cells.add(newCell);
			}
		}
		double[] areas = new double[cells.size()];
		for (int i = 0; i < cells.size(); i++) {
			areas[i] = cells.get(i).area();
		}
		return areas;
	}

	protected static BigDecimal pointLineDistance(Point2D point, Line line) {
		if (line.x != null) {
			return line.x.subtract(point.x);
		} else if (line.y != null) {
			return line.y.subtract(point.y);
		} else {// (a*X0)/sqrt(a^2 + 1)
			return line.a.multiply(point.x).subtract(point.y).add(line.b) // a*x0
					.divide(line.a.pow(2).add(BigDecimal.ONE).sqrt(new MathContext(19)), 18, RoundingMode.HALF_UP);
		}
	}

	protected static Point2D calculateEdgeBisecIntersection(Edge e, Line l1) {
		Line l2 = new Line(e);
		Point2D intersection;
		BigDecimal resX;
		BigDecimal resY;
		if (l2.y != null && l1.y == null && l1.x == null) {
			resX = l2.y.subtract(l1.b).divide(l1.a, 18, RoundingMode.HALF_UP);
			intersection = new Point2D(resX, l2.y);
		} else if (l2.x != null && l1.x == null && l1.y == null) {
			resY = l1.a.multiply(l2.x).add(l1.b);
			intersection = new Point2D(l2.x, resY);
		} else if (l1.x != null) {
			if (l2.x != null) { // l1 and l2 are parallel to x axis and to each other
				return null;
			} else if (l2.y != null) {
				intersection = new Point2D(l1.x, l2.y);
			} else {
				resY = l2.a.multiply(l1.x).add(l2.b);
				intersection = new Point2D(l1.x, resY);
			}
		} else if (l1.y != null) {
			if (l2.y != null) { // l1 and l2 are parallel to y axis and to each other
				return null;
			} else if (l2.x != null) {
				intersection = new Point2D(l2.x, l1.y);
			} else {
				resX = l1.y.subtract(l2.b).divide(l2.a, 18, RoundingMode.HALF_UP);
				intersection = new Point2D(resX, l1.y);
			}
		} else if (l1.a.compareTo(l2.a) == 0) { // l1 and l2 are parallel to each other
			return null;
		} else {

			resX = l2.b.subtract(l1.b).divide(l1.a.subtract(l2.a), 18, RoundingMode.HALF_UP);
			resY = l1.a.multiply(resX).add(l1.b);
			intersection = new Point2D(resX, resY);
		}
		if (e.belongsToEdge(intersection)) {
			return intersection;
		} else {
			return null;
		}
	}

	public static Line prependicularBisector(Point2D a, Point2D b) {
		Point2D hlafwayAB = new Point2D(
				a.x.add(b.x.subtract(a.x).divide(BigDecimal.valueOf(2), 18, RoundingMode.HALF_UP)),
				a.y.add(b.y.subtract(a.y).divide(BigDecimal.valueOf(2), 18, RoundingMode.HALF_UP)));

		BigDecimal coeA;
		BigDecimal constB;
		if (a.x.compareTo(b.x) == 0) {
			return new Line(null, null, null, hlafwayAB.y);
		} else if (a.y.compareTo(b.y) == 0) {
			return new Line(null, null, hlafwayAB.x, null);
		} else {

			coeA = BigDecimal.ONE.divide(b.y.subtract(a.y).divide(b.x.subtract(a.x), 18, RoundingMode.HALF_UP), 18,
					RoundingMode.HALF_UP).negate();
			constB = hlafwayAB.y.subtract(coeA.multiply(hlafwayAB.x)).setScale(18, RoundingMode.HALF_UP);

			return new Line(coeA, constB, null, null);
		}
	}

	private static Point2D[] convertPointToPoint2D(Point[] points) {
		Point2D[] result = new Point2D[points.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Point2D(points[i]);
		}
		return result;
	}

	static class Edge {

		Point2D a;
		Point2D b;
		boolean isBoundary = false;

		Edge(Point2D a, Point2D b) {
			this.a = a;
			this.b = b;
		}

		public void setBoundary() {
			isBoundary = true;
		}

		public void reverse() {
			Point2D temp = this.b;
			this.b = this.a;
			this.a = temp;
		}

		public BigDecimal getMinX() {
			if (a.x.compareTo(b.x) == -1) {
				return a.x;
			} else {
				return b.x;
			}
		}

		public BigDecimal getMinY() {
			if (a.y.compareTo(b.y) == -1) {
				return a.y;
			} else {
				return b.y;
			}
		}

		public BigDecimal getMaxX() {
			if (a.x.compareTo(b.x) == 1) {
				return a.x;
			} else {
				return b.x;
			}
		}

		public BigDecimal getMaxY() {
			if (a.y.compareTo(b.y) == 1) {
				return a.y;
			} else {
				return b.y;
			}
		}

		public boolean belongsToEdge(Point2D point) {

			if (point.x.setScale(14, RoundingMode.HALF_UP).compareTo(getMinX().setScale(14, RoundingMode.HALF_UP)) >= 0
					&& point.x.setScale(14, RoundingMode.HALF_UP)
							.compareTo(getMaxX().setScale(14, RoundingMode.HALF_UP)) <= 0
					&& point.y.setScale(14, RoundingMode.HALF_UP)
							.compareTo(getMinY().setScale(14, RoundingMode.HALF_UP)) >= 0
					&& point.y.setScale(14, RoundingMode.HALF_UP)
							.compareTo(getMaxY().setScale(14, RoundingMode.HALF_UP)) <= 0) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((a == null) ? 0 : a.hashCode());
			result = prime * result + ((b == null) ? 0 : b.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Edge other = (Edge) obj;
			if (a == null) {
				if (other.a != null)
					return false;
			} else if (!a.equals(other.a))
				return false;
			if (b == null) {
				if (other.b != null)
					return false;
			} else if (!b.equals(other.b))
				return false;
			return true;
		}

		public String toString() {
			return "Point a: x = " + a.x + ", y = " + a.y + "Point b: x = " + b.x + ", y = " + b.y;
		}

	}

	static class Cell {

		Point2D site;
		List<Edge> edges;

		Cell(Point2D site) {
			this.site = site;
			this.edges = new ArrayList<Voronoi.Edge>(20);
		}

		public String toString() {
			String result = "SITE X = " + site.x + ", Y = " + site.y + "\n";
			for (Edge edge : edges) {
				result = result + edge.toString() + "\n";
			}
			return result;
		}

		private List<Point2D> sortPoints() {
			if (edges == null || edges.size() == 0) {
				return null;
			}
			List<Point2D> pointsSort = new ArrayList<>();
			Edge current = edges.get(0);
			pointsSort.add(current.a);

			while (pointsSort.size() < edges.size()) {
				for (int i = 1; i < edges.size(); i++) {
					Edge check = edges.get(i);
					if ((current.b.x.setScale(14, RoundingMode.HALF_UP)
							.compareTo(check.a.x.setScale(14, RoundingMode.HALF_UP)) == 0)
							&& (current.b.y.setScale(14, RoundingMode.HALF_UP)
									.compareTo(check.a.y.setScale(14, RoundingMode.HALF_UP)) == 0)) {
						pointsSort.add(current.b);
						current = check;
					} else if ((current.b.x.setScale(14, RoundingMode.HALF_UP)
							.compareTo(check.b.x.setScale(14, RoundingMode.HALF_UP)) == 0)
							&& (current.b.y.setScale(14, RoundingMode.HALF_UP)
									.compareTo(check.b.y.setScale(14, RoundingMode.HALF_UP)) == 0)) {
						check.reverse();
						pointsSort.add(current.b);
						current = check;
					}
				}
			}
			return pointsSort;
		}

		public boolean isBorderCell() {
			if (edges == null || edges.size() == 0) {
				return false;
			}
			for (Edge edge : edges) {
				if (edge.isBoundary) {
					return true;
				}
			}
			return false;
		}

		public double area() {
			List<Point2D> sorted = sortPoints();
			if (sorted != null && sorted.size() != 0 && !isBorderCell()) {

				sorted.add(sorted.get(0));
				sorted.add(sorted.get(1));

				BigDecimal result = BigDecimal.ZERO;
				for (int i = 1; i < sorted.size() - 1; i++) {
					result = result.add(sorted.get(i + 1).y.subtract(sorted.get(i - 1).y).multiply(sorted.get(i).x));
				}
				result = result.divide(BigDecimal.valueOf(2.0)).abs();
				return result.doubleValue();
			}
			return -1.0;
		}
	}

	static class Line {
		BigDecimal a, b, x, y;

		Line(Edge e) {
			this(e.a, e.b);
		}

		Line(BigDecimal a, BigDecimal b, BigDecimal x, BigDecimal y) {
			this.a = a;
			this.b = b;
			this.x = x;
			this.y = y;
		}

		Line(Point2D a, Point2D b) {
			if (a.x.compareTo(b.x) == 0) {
				this.x = a.x;
			} else if (a.y.compareTo(b.y) == 0) {
				this.y = a.y;
			} else {
				this.a = b.y.subtract(a.y).divide(b.x.subtract(a.x), 18, RoundingMode.HALF_UP);
				this.b = a.y.subtract(this.a.multiply(a.x)).setScale(18, RoundingMode.HALF_UP);
			}
		}

	}

	static class Point2D implements Comparable<Object> {

		BigDecimal x;
		BigDecimal y;

		Point2D(Point p) {
			this.x = BigDecimal.valueOf(p.x);
			this.y = BigDecimal.valueOf(p.y);
		}

		Point2D(BigDecimal x, BigDecimal y) {
			this.x = x;
			this.y = y;
		}

		Point2D(double x, double y) {
			this.x = BigDecimal.valueOf(x);
			this.y = BigDecimal.valueOf(y);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((x == null) ? 0 : x.hashCode());
			result = prime * result + ((y == null) ? 0 : y.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point2D other = (Point2D) obj;
			if (x == null) {
				if (other.x != null)
					return false;
			} else if (x.compareTo(other.x) != 0)
				return false;
			if (y == null) {
				if (other.y != null)
					return false;
			} else if (y.compareTo(other.y) != 0)
				return false;
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Point2D [");
			if (x != null) {
				builder.append("x=");
				builder.append(x);
				builder.append(", ");
			}
			if (y != null) {
				builder.append("y=");
				builder.append(y);
			}
			builder.append("]");
			return builder.toString();
		}

		@Override
		public int compareTo(Object a) {
			Point2D o = (Point2D) a;
			return Double.compare(o.x.doubleValue(), this.x.doubleValue());
		}
	}
}