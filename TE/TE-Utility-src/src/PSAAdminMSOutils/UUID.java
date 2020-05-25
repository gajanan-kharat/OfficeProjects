/**********************************************************************************************************
 *
 * FILE NAME	  : UUID
 *
 * SOCIETE        : PSA
 * PROJET         : 164AB CO-CONception Multi-Plaques
 * VERSION        : V1.0
 * DATE           : 22/08/2011
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    :  Class used to generate a unique 32 bit number.
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.PSAAdminMSOutils;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * UUID is a DCE UUID, that is, a 128-bit universally unique identifier.
 * 
 * The UUID has the following fields:
 * 
 * 
 * 
 * timeLow(ui4) - the low 32 bits of the current time
 * 
 * timeMid(ui2) - the next 16 bits of the current time
 * 
 * timeHiAndVersion(ui2) - the high 12 bits of the current time, or'ed with UUID
 * version information, which occupies the top 4 bits.
 * 
 * clockSeqHiAndReserved(ui1) - the top 6 bits of the 'Clock Sequence', which is
 * a random number. The top two bits of this field must be '10' to be a valid
 * variant 1 UUID.
 * 
 * clockSeqLow(ui1) - the low 8 bits of the Clock Sequence
 * 
 * nodeId (u_i1[6]) - intended to be the Ethernet address, which is guaranteed
 * unique in the world.
 * 
 * 
 * Time is expressed as in VMS, i.e. 100's of nanoseconds since a base date, but
 * the base dat is 10/15/1582, rather than VMS's 1858. Since 100 nanoseconds is
 * probably much smaller than the system clock resolution, implementations are
 * allowed to construct many UUIDs per clock tick by keeping track of a 'time
 * adjustment', which is bumped for each UUID constructed during a clock tick
 * and added into the UUID's time fields.
 * 
 * The intent of the UUID algorithm is to generate a globally unique ID. The
 * reasoning goes:
 * 
 * 
 * 
 * First of all, the nodeId is globally unique, so the only possible
 * duplications will come from the same machine.
 * 
 * Second, the time value will be unique for each producer of UUIDs on a given
 * machine.
 * 
 * Third (not explicitly stated anywhere I can find), different producers on a
 * single machine will have different Clock Sequence values. The implmentations
 * of Clock Sequence I've seen use pid as an input to the random number
 * generator.
 * 
 * 
 * Java has one large problem using this logic: the Ethernet address is not
 * findable in any documented way. The DCE implementation on SunOS evidently has
 * had the same problem, since they use the Internet address instead of the
 * Ethernet address as four bytes of their node ID (they set the fifth and sixth
 * bytes not to conflict with any true Ethernet address.) Forte's version of
 * UUID generation, implemented in the UUID.UUIDGenerator class, is:
 * 
 * 
 * 
 * nodeId: Generate a 4-byte random number. Like DCE on SunOS, set bytes 5 and 6
 * to values illegal for an Ethernet address.
 * 
 * time: Get a 32-bit time value from System.currentTimeMills(), and convert it
 * to VMS-like format. Since the conversion from milliseconds to hundreds of
 * nanoseconds is a multiplication by 10**4, this leaves the result always 0 mod
 * 10**4. We add a randomly generated 12-bit number to this, which leaves room
 * to generate at least 10**4 - 2**12 or about 6000 UUIDs in the current
 * millisecond.
 * 
 * clock sequence: A 14-bit random number
 * 
 * 
 */

@SuppressWarnings({ "serial", "rawtypes" })
public class UUID implements Comparable, Serializable {
	/** the length of a UUID converted to a string */
	public static final int STRING_LENGTH = 36;

	/* fields of the UUID, as described above */
	private int timeLow;
	private short timeMid;
	private short timeHiAndVersion;
	private byte clockSeqHiAndReserved;
	private byte clockSeqLow;
	private byte nodeId[];

	/**
	 * Generate a new UUID
	 */
	public UUID() {
		nodeId = new byte[6];
		this.generate();
	}

	/**
	 * Create a UUID from a string
	 * 
	 * @param str
	 *            A string in standard UUID format
	 * @exception java.lang.NumberFormatException
	 *                Thrown if the string isn't a valid UUID.
	 */
	public UUID(String str) throws NumberFormatException {
		StringTokenizer st = new StringTokenizer(str, "-", true);
		nodeId = new byte[6];
		try {
			timeLow = (int) Long.parseLong(st.nextToken(), 16);
			if (!st.nextToken().equals("-"))
				throw new NumberFormatException();
			timeMid = (short) Integer.parseInt(st.nextToken(), 16);
			if (!st.nextToken().equals("-"))
				throw new NumberFormatException();
			timeHiAndVersion = (short) Integer.parseInt(st.nextToken(), 16);
			if (!st.nextToken().equals("-"))
				throw new NumberFormatException();
			short clock = (short) Integer.parseInt(st.nextToken(), 16);
			clockSeqHiAndReserved = (byte) ((clock >> 8) & 0xFF);
			clockSeqLow = (byte) (clock & 0xFF);
			if (!st.nextToken().equals("-"))
				throw new NumberFormatException();
			String node = st.nextToken();
			for (int i = 0; i < 6; i++) {
				nodeId[i] = (byte) Integer.parseInt(
						node.substring(i * 2, i * 2 + 2), 16);
			}
		} catch (Exception ex) {
			throw new NumberFormatException();
		}
	}

	/**
	 * Get a UUIDGenerator and generate into this
	 */
	public void generate() {
		UUIDGenerator gener = UUIDGenerator.create();
		gener.generate(this);
	}

	/**
	 * Convert a UUID to a string in standard form:
	 * 
	 * XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
	 * 
	 * timeLow-timeMid-timeHiAndVersion-clockSeqAndReserved-nodeId
	 * 
	 * @return String version of UUID
	 */
	public String toString() {
		Object nodeArgs[] = { toHex(nodeId[0]), toHex(nodeId[1]),
				toHex(nodeId[2]), toHex(nodeId[3]), toHex(nodeId[4]),
				toHex(nodeId[5]) };
		String nodeString = MessageFormat
				.format("{0}{1}{2}{3}{4}{5}", nodeArgs);

		Object args[] = { toHex(timeLow), toHex(timeMid),
				toHex(timeHiAndVersion), toHex(clockSeqHiAndReserved),
				toHex(clockSeqLow), nodeString };

		// return MessageFormat.format("{0}-{1}-{2}-{3}{4}-{5}", args);
		return MessageFormat.format("{0}{1}{2}{3}{4}{5}", args);
	}

	/**
	 * Two UUIDs are equal if all of their fields are equal
	 */
	public boolean equals(Object o) {
		if (!(o instanceof UUID))
			return false;

		UUID other = (UUID) o;
		return (other.timeLow == this.timeLow && other.timeMid == this.timeMid
				&& other.timeHiAndVersion == this.timeHiAndVersion
				&& other.clockSeqHiAndReserved == this.clockSeqHiAndReserved
				&& other.clockSeqLow == this.clockSeqLow
				&& other.nodeId[0] == this.nodeId[0]
				&& other.nodeId[1] == this.nodeId[1]
				&& other.nodeId[2] == this.nodeId[2]
				&& other.nodeId[3] == this.nodeId[3]
				&& other.nodeId[4] == this.nodeId[4] && other.nodeId[5] == this.nodeId[5]);
	}

	/**
	 * Orders UUIDs by their field values
	 */
	public int compareTo(Object o) {
		if (!(o instanceof UUID))
			throw new ClassCastException();

		UUID other = (UUID) o;
		if (other.timeLow != this.timeLow)
			return this.timeLow - other.timeLow;
		if (other.timeMid != this.timeMid)
			return this.timeMid - other.timeMid;
		if (other.timeHiAndVersion != this.timeHiAndVersion)
			return this.timeHiAndVersion - other.timeHiAndVersion;
		if (other.clockSeqHiAndReserved != this.clockSeqHiAndReserved)
			return this.clockSeqHiAndReserved - other.clockSeqHiAndReserved;
		if (other.clockSeqLow != this.clockSeqLow)
			return this.clockSeqLow - other.clockSeqLow;
		for (int i = 0; i < 6; i++) {
			if (other.nodeId[i] != this.nodeId[i])
				return this.nodeId[i] - other.nodeId[i];
		}
		return 0;
	}

	/**
	 * Use timeLow for the hashCode, so equal UUIDs hash equal
	 */
	public int hashCode() {
		return (int) timeLow;
	}

	/**
	 * Convert a byte to XX in hex, with leading zeros if need be
	 */
	private static String toHex(byte num) {
		return toHex(((int) num & 0xFF), 2);
	}

	/**
	 * Convert a short to XXXX in hex, with leading zeros if need be
	 */
	private static String toHex(short num) {
		return toHex(((int) num & 0xFFFF), 4);
	}

	/**
	 * Convert an int to XXXXXXX in hex, with leading zeros if need be
	 */
	private static String toHex(int num) {
		return toHex(num, 8);
	}

	/**
	 * Convert an int to as many hex digits as specified, with leading zeros if
	 * need be
	 */
	private static String toHex(int num, int digits) {
		String hex = Integer.toHexString(num);
		if (hex.length() < digits) {
			StringBuffer sb = new StringBuffer();
			int toAdd = digits - hex.length();
			for (int i = 0; i < toAdd; i++) {
				sb.append('0');
			}
			sb.append(hex);
			hex = new String(sb);
		}
		return hex.toUpperCase();
	}

	/**
	 * Generate a unique UUID. Only one instance of this class is ever created.
	 * 
	 * Random number generators used:
	 * 
	 * nodeId is generated once, based on the time the UUIDGenerator is created
	 * and the hashCode of a Random created at that time.
	 * 
	 * clockSequence is generated when the UUIDGenerator is created and again if
	 * the system time is ever reset to a time before the previous UUID
	 * generation. It is generated based on the hashcode of the UUIDGenerator.
	 * 
	 * timeAdjust is generated for the first UUID generation in each
	 * millisecond, based on the hashCode of the Thread which generated the
	 * UUID.
	 */
	private static class UUIDGenerator {
		/** The unique instance of this class */
		private static UUIDGenerator theGenerator;

		/** random number generators */
		private Random random1;
		private Random random2;

		/** The last time a UUID was generated */
		private long lastTime;

		/**
		 * An adjustment to allow mulitple UUIDs to be generated within the same
		 * millisecond.
		 */
		private long timeAdjust;

		/**
		 * An adjustment to allow UUIDs to be generated if the system clock is
		 * moved backwards.
		 */
		private int clockSequence;

		/**
		 * The node Id for this instance.
		 */
		private byte nodeId[];

		/** The clock sequence is 14 bits */
		private static final int CLOCK_SEQ_MASK = 0x3FFF;

		/** The UUID version is one bit */
		private static final int VERSION_BITS = 0x1000;

		/** One bit is reserved */
		private static final int RESERVED_BITS = 0x0080;

		/** The time adjust is 12 bits */
		private static final int TIME_ADJUST_MASK = 0x0FFF;

		/** convesion from milliseconds to 10s of nanoseconds */
		private static final long N100NS_PER_MILLI = 10000;

		/** convert from 1/1/70 to 10/15/1582 */
		private static final long EPOCH_CVT = 0x1B21DD213814000L;

		/** return the singleton UUID generator */
		public synchronized static UUIDGenerator create() {
			if (theGenerator == null)
				theGenerator = new UUIDGenerator();
			return theGenerator;
		}

		/** Intitialize the generator's fields */
		private UUIDGenerator() {
			random1 = new Random(this.hashCode());
			random2 = new Random(Thread.currentThread().hashCode());
			nodeId = new byte[6];
			initializeNodeId();
			getClockSequence();
		}

		/** Generate a new UUID. */
		public synchronized void generate(UUID uuid) {
			long now = System.currentTimeMillis();

			while (true) {
				if (now > lastTime) {
					/* The easiest case */
					getTimeAdjust();
					break;
				} else if (now < lastTime) {
					/*
					 * Time moved backward! Get a new clock sequence to avid
					 * generating duplicates.
					 */
					getClockSequence();
					getTimeAdjust();
					break;
				} else {
					/* increment time adjustment to avois a duplicate. */
					timeAdjust++;
					if (timeAdjust < N100NS_PER_MILLI) {
						break;
					} else {
						/*
						 * ran out of time adjustments -- wait for the clock
						 * to tick again. Note this can only happen if we
						 * try to generate > 6000 UUIDs per millisecond.
						 */
						Thread.yield();
					}
				}
			}

			lastTime = now;
			long result = (lastTime * N100NS_PER_MILLI) + EPOCH_CVT
					+ timeAdjust;

			uuid.timeLow = (int) (result & 0xFFFFFFFF);
			uuid.timeMid = (short) ((result >> 32) & 0xFFFF);
			uuid.timeHiAndVersion = (short) (((result >> 48) & 0xFFFF) | VERSION_BITS);
			uuid.clockSeqHiAndReserved = (byte) (((clockSequence >> 8) & 0xFF) | RESERVED_BITS);
			uuid.clockSeqLow = (byte) (clockSequence & 0xFF);
			for (int i = 0; i < 6; i++) {
				uuid.nodeId[i] = nodeId[i];
			}
		}

		/** Randomly generate a time adjustment */
		private void getTimeAdjust() {
			timeAdjust = random2.nextInt() & TIME_ADJUST_MASK;
		}

		/** Randomly generate a clock sequence */
		private void getClockSequence() {
			clockSequence = random1.nextInt() & CLOCK_SEQ_MASK;
			if (clockSequence == 0)
				clockSequence++;
		}

		/**
		 * randomly generate a node ID. make the last two bytes 0xAA77, which
		 * cannot conflict with a real Ethernet address
		 */
		private void initializeNodeId() {
			byte barr[] = new byte[2];

			Random r1 = new Random();
			Random r2 = new Random(r1.hashCode());
			r1.nextBytes(barr);
			nodeId[0] = barr[0];
			nodeId[1] = barr[1];

			r2.nextBytes(barr);
			nodeId[2] = barr[0];
			nodeId[3] = barr[1];

			nodeId[4] = (byte) 0xaa;
			nodeId[5] = 0x77;
		}
	}
}
