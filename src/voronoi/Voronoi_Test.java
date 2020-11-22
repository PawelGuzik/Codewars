package voronoi;

import org.junit.Test;

import voronoi.Voronoi.Edge;
import voronoi.Voronoi.Line;
import voronoi.Voronoi.Point2D;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.*;

public class Voronoi_Test {
	@Test
	public void perpendicularBisectorTestXequalsY() {
		Voronoi.Point2D p1 = new Point2D(0.0, 0.0);
		Voronoi.Point2D p2 = new Point2D(4.0, 4.0);
		Line testLine = Voronoi.prependicularBisector(p1, p2);
		BigDecimal big1 = BigDecimal.valueOf(1L);
	
		 assertEquals(BigDecimal.valueOf(-1L), testLine.a.stripTrailingZeros());
		assertEquals(BigDecimal.valueOf(0), testLine.b.stripTrailingZeros());
		assertNull(testLine.x);
		assertNull(testLine.y);
	}

	@Test
	public void perpendicularBisectorTestXconstans() {
		Voronoi.Point2D p1 = new Point2D(10.0, 2.0);
		Voronoi.Point2D p2 = new Point2D(10.0, 4.0);
		Line testLine = Voronoi.prependicularBisector(p1, p2);
		assertEquals(BigDecimal.valueOf(3), testLine.y.stripTrailingZeros());
		assertNull(testLine.x);
		assertNull(testLine.b);
		assertNull(testLine.a);
	}

	@Test
	public void perpendicularBisectorTestYconstans() {
		Voronoi.Point2D p1 = new Point2D(5.0, 4.0);
		Voronoi.Point2D p2 = new Point2D(10.0, 4.0);
		Line testLine = Voronoi.prependicularBisector(p1, p2);
		assertEquals(BigDecimal.valueOf(7.5), testLine.x.stripTrailingZeros());
		assertNull(testLine.y);
		assertNull(testLine.b);
		assertNull(testLine.a);
	}

	@Test
	public void perpendicularBisectorTestAB() {
		Voronoi.Point2D p1 = new Point2D(0.0, 3.0);
		Voronoi.Point2D p2 = new Point2D(3.0, 12.0);
		Line testLine = Voronoi.prependicularBisector(p1, p2);
		assertEquals(BigDecimal.valueOf(-0.3333333333), testLine.a.stripTrailingZeros());
		assertEquals(BigDecimal.valueOf(3), testLine.b.stripTrailingZeros());
		assertNull(testLine.x);
		assertNull(testLine.y);
	}
	
	@Test
	public void pointLineDistanceXconstans() {
		Voronoi.Point2D p1 = new Point2D(10.0, 2.0);
		Voronoi.Point2D p2 = new Point2D(10.0, 4.0);
		Line testLine = new Line(p1, p2);
		Voronoi.Point2D p3 = new Point2D(8.0, 4.0);
		assertEquals(BigDecimal.valueOf(2), Voronoi.pointLineDistance(p3, testLine).stripTrailingZeros());
	}
	
	@Test
	public void pointLineDistanceYconstans() {
		Voronoi.Point2D p1 = new Point2D(5.0, 4.0);
		Voronoi.Point2D p2 = new Point2D(10.0, 4.0);
		Line testLine = new Line(p1, p2);
		Voronoi.Point2D p3 = new Point2D(8.0, 16.5);
		assertEquals(BigDecimal.valueOf(12.5), Voronoi.pointLineDistance(p3, testLine).stripTrailingZeros());
	}
	
	@Test
	public void pointLineDistanceNormalAB() {
		Voronoi.Point2D p1 = new Point2D(10.0,10.0);
		Voronoi.Point2D p2 = new Point2D(20.0, 20.0);
		Line testLine = new Line(p1, p2);
		Voronoi.Point2D p3 = new Point2D(0.0, 2.0);
		assertEquals(BigDecimal.valueOf(1.4142135623), Voronoi.pointLineDistance(p3, testLine).stripTrailingZeros());
	}
	
	@Test 
	public void edgeBisecIntersectionEachParallelToXaxis() {
		Voronoi.Point2D p1 = new Point2D(1.0, 2.0);
		Voronoi.Point2D p2 = new Point2D(1.0, 20.0);
		Voronoi.Edge edge = new Edge(p1, p2);
		Voronoi.Point2D p3 = new Point2D(4.0, 2.0);
		Voronoi.Point2D p4 = new Point2D(4.0, 20.0);
		Line line = new Line(p3, p4);
		
		assertNull(Voronoi.calculateEdgeBisecIntersection(edge, line));
	}
	@Test 
	public void edgeBisecIntersectionEachParallelToYaxis() {
		Voronoi.Point2D p1 = new Point2D(3.0, 20.0);
		Voronoi.Point2D p2 = new Point2D(1.0, 20.0);
		Voronoi.Edge edge = new Edge(p1, p2);
		Voronoi.Point2D p3 = new Point2D(4.0, 2.0);
		Voronoi.Point2D p4 = new Point2D(10.0, 2.0);
		Line line = new Line(p3, p4);
		
		assertNull(Voronoi.calculateEdgeBisecIntersection(edge, line));
	}
	@Test 
	public void edgeBisecIntersectionEachParallelToDifferentAxis() {
		Voronoi.Point2D p1 = new Point2D(3.0, 20.0);
		Voronoi.Point2D p2 = new Point2D(1.0, 20.0);
		Voronoi.Edge edge = new Edge(p1, p2);
		Voronoi.Point2D p3 = new Point2D(10.0, 6.0);
		Voronoi.Point2D p4 = new Point2D(10.0, 2.0);
		Line line = new Line(p3, p4);
		
		assertNull(Voronoi.calculateEdgeBisecIntersection(edge, line));
		
	}
	
	@Test 
	public void edgeBisecIntersectionEachParallelToDifferentAxis2() {
		Voronoi.Point2D p1 = new Point2D(10.0, 0.0);
		Voronoi.Point2D p2 = new Point2D(10.0, 21.0);
		Voronoi.Edge edge = new Edge(p1, p2);
		Voronoi.Point2D p3 = new Point2D(1.0, 20.0);
		Voronoi.Point2D p4 = new Point2D(10.0, 20.0);
		Line line = new Line(p3, p4);
		Point2D point = Voronoi.calculateEdgeBisecIntersection(edge, line);
		
		assertEquals(BigDecimal.valueOf(10.0), point.x);
		assertEquals(BigDecimal.valueOf(20.0), point.y);		
	}
	
	
	@Test 
	public void edgeBisecIntersection() {
		Voronoi.Point2D p1 = new Point2D(0.0, 0.0);
		Voronoi.Point2D p2 = new Point2D(10.0, 10.0);
		Voronoi.Edge edge = new Edge(p1, p2);
		Voronoi.Point2D p3 = new Point2D(10.0, 0.0);
		Voronoi.Point2D p4 = new Point2D(0.0, 10.0);
		Line line = new Line(p3, p4);
		Point2D point = Voronoi.calculateEdgeBisecIntersection(edge, line);
		
		assertEquals(BigDecimal.valueOf(5), point.x.stripTrailingZeros());
		assertEquals(BigDecimal.valueOf(5), point.y.stripTrailingZeros());		
	}
	
	

	@Test
	public void calculatingDistancesTest() {
		Point[] p = { new Point(0.0, 0.0), new Point(2.0, 0.0), new Point(-2.0, 0.0), new Point(0.0, 2.0),
				new Point(0.0, -2.0) };
		Voronoi.voronoi_areas(p);
		// Voronoi.calculateDistances(p);
	}

	@Test
	public void Square_Cell() throws Exception {
		Point[] p = { new Point(0.0, 0.0), new Point(2.0, 0.0), new Point(-2.0, 0.0), new Point(0.0, 2.0),
				new Point(0.0, -2.0) };
		double[] ans = { 4.0, -1, -1, -1, -1 };
		assertEquals("", answer_matches(Voronoi.voronoi_areas(p), ans), true);
	}
	
	@Test
	public void Square_Cell36() throws Exception {
		Point p1 = new Point(5, -5);
		Point p2 = new Point(5, -3);
		Point p3 = new Point(5, -1);
		Point p4 = new Point(5, 1);
		Point p5 = new Point(5, 3);
		Point p6 = new Point(5, 5);
		Point p7 = new Point(3, 5);
		Point p8 = new Point(1, 5);
		Point p9 = new Point(-1, 5);
		Point p10 = new Point(-3, 5);
		Point p11 = new Point(-5, 5);
		Point p12 = new Point(-5, 3);
		Point p13 = new Point(-5, 1);
		Point p14 = new Point(-5, -1);
		Point p15 = new Point(-5, -3);
		Point p16 = new Point(-5, -5);
		Point p17 = new Point(-3, -5);
		Point p18 = new Point(-1, -5);
		Point p19 = new Point(1, -5);
		Point p20 = new Point(3, -5);
		Point p21 = new Point(3, -3);
		Point p22 = new Point(3, -1);
		Point p23 = new Point(1, -3);
		Point p24 = new Point(1, -1);
		Point p25 = new Point(3, 1);
		Point p26 = new Point(3, 3);
		Point p27 = new Point(1, 1);
		Point p28 = new Point(1, 3);
		Point p29 = new Point(-1, -3);
		Point p30 = new Point(-1, -1);
		Point p31 = new Point(-3, -3);
		Point p32 = new Point(-3, -1);
		Point p33 = new Point(-1, 1);
		Point p34 = new Point(-1, 3);
		Point p35 = new Point(-3, 1);
		Point p36 = new Point(-3, 3);
		Point[] p = { p1, p2, p3, p4, p5, p6,p20, p21, p22, p25, p26, p7, p19, p23, p24, p27, p28, p8, p18, p29, p30, p33, p34, p9, p17, p31, p32, p35, p36, p10, p16, p15, p14, p13, p12, p11};
		double[] ans = { -1, -1, -1, -1, -1, -1, -1, 4, 4, 4, 4, -1, -1, 4, 4, 4, 4, -1, -1, 4, 4, 4, 4, -1, -1, 4, 4, 4,  4, -1, -1, -1, -1, -1, -1, -1  };
		assertEquals("", answer_matches(Voronoi.voronoi_areas(p), ans), true);
	}
	
	@Test
	public void Square_Cell4() throws Exception {
		Point p1 = new Point(1, -1);
		Point p2 = new Point(1, 1);
		Point p3 = new Point(-1, 1);
		Point p4 = new Point(-1, -1);
		
		Point[] p = { p1, p2, p3, p4};
		double[] ans = { -1, -1, -1, -1 };
		assertEquals("", answer_matches(Voronoi.voronoi_areas(p), ans), true);
	}
	
	@Test
	public void Square_CellR() throws Exception {
		Point p1 = new Point(-7.955511527656113, -0.13193854525662974);
		Point p2 = new Point(-4.927047990300888, 0.14332760562837693);
		Point p3 = new Point(0.5408153676095281, 1.382819465581412);
		Point p4 = new Point(-1.91423704962216, -5.785540957090698);
		Point p5 = new Point(-4.23031342669296, -4.619802119395407);
		Point p6 = new Point(-4.543908505873914, 3.2973751020993727);	
		Point p7 = new Point(-6.461476220390311, 1.0869448638558412);
		Point p8 = new Point(-0.4313738842972808, -6.360988459842292);
		Point p9 = new Point(3.690849818850984, -0.13838650688091772);
		Point p10 = new Point(-0.7744311579153694, -7.693875684884814);	
		Point p11 = new Point(7.52480969688092, 9.347579313690089);
		Point p12 = new Point(-4.775752889821644, 11.008732185649727);
		Point p13 = new Point(-11.433699224878925, 3.6428727722778174);
		Point p14 = new Point(-10.384024370879978, -6.0143194016422745);
		Point p15 = new Point(-1.4122125135401946, -11.916612598242862);
		Point p16 = new Point(9.28870634262006, -7.597363653305658);
		Point p17 = new Point(10.889864539304686, 5.040917606507207);
		Point p18 = new Point(2.159759806488442, 11.804043272467151);
		Point p19 = new Point(-9.551768889455984, 7.263863371679068);
		Point[] p = { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19};
		double[] ans = { -1, -1, -1, -1 };
		assertEquals("", answer_matches(Voronoi.voronoi_areas(p), ans), true);
	}


	
	@Test
	public void Triangular_Cells() throws Exception {
		Point[] p = { new Point(2.0, 1.0), new Point(2.0, -1.0), new Point(4.4, 2.2), new Point(4.4, -2.2),
				new Point(-0.4, 2.2), new Point(-0.4, -2.2) };
		double[] ans = { 8, 8, -1, -1, -1, -1 };
		assertEquals("", answer_matches(Voronoi.voronoi_areas(p), ans), true);
	}

	/*
	 * -----------------------------------------------------------------------------
	 * ------------
	 */
	// Match expected results. If an 'expected' value is:
	// positive - must match 'actual' value to within a relative tolerance;
	// -1.0 - 'actual' can be either -1.0 or a very large positive value.
	private static boolean answer_matches(double[] actual, double[] expected) {
		if (actual.length != expected.length) {
			System.out.format("Wrong vector size (%d, expected %d)\n\n", actual.length, expected.length);
			return false;
		}
		for (int i = 0; i < expected.length; i++) {
			if (expected[i] == -1.0 && !(actual[i] == -1.0 || actual[i] > 1e9)) {
				System.out.format("Wrong value for cell %d (%f) -- this Voronoi cell has infinite area\n\n", i,
						actual[i]);
				return false;
			}
			if (expected[i] > 0 && Math.abs(actual[i] - expected[i]) > 1e-6 * expected[i]) {
				System.out.format("Wrong value for cell %d (%.12f, expected %.12f)\n\n", i, actual[i], expected[i]);
				return false;
			}
		}
		return true;
	}
} // end of class