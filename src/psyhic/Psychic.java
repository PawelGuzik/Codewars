package psyhic;

import java.util.concurrent.atomic.AtomicLong;

public class Psychic {
	AtomicLong seedF = new AtomicLong(66167621253841L);
	private static final double DOUBLE_UNIT = 0x1.0p-53;
	private static long multiplier = 0x5DEECE66DL;
	private static long addend = 0xBL;
	private static long mask = (1L << 48) - 1;

	public static double guess() {
		Double dob = Math.random();
		double dobDiv = dob / DOUBLE_UNIT;
		long l = (long) dobDiv;
		long secSeed = (l << 21) & mask;
		long firstSeed = (l >> 27) << 22 & mask;
		long checker = firstSeed + (1L << 22);

		long myNewSeed = 0;
		for (long i = firstSeed; i < checker; i++) {
			long seedTry = (i * multiplier + addend) & mask;
			if ((seedTry > secSeed) && seedTry < secSeed + ((1L << 21) - 1)) {
				myNewSeed = i;
			}
		}
		Psychic p = new Psychic();
		p.myNextDouble(myNewSeed);
		return p.myNextDouble(0L);
	}

	public double myNextDouble(long newSeed) {
		double asdouble = (((long) (myNext(26, newSeed)) << 27) + myNext(27, 0L)) * DOUBLE_UNIT;
		return asdouble;
	}

	protected int myNext(int bits, long newSeed) {
		long oldseed, nextseed;
		AtomicLong seedF = this.seedF;
		if (newSeed != 0L) {
			oldseed = seedF.get();
			nextseed = newSeed;
			seedF.compareAndSet(oldseed, nextseed);
			return (int) (nextseed >>> (48 - bits));
		} else {
			do {
				oldseed = seedF.get();
				nextseed = (oldseed * multiplier + addend) & mask;
			} while (!seedF.compareAndSet(oldseed, nextseed));

			return (int) (nextseed >>> (48 - bits));
		}
	}
}
