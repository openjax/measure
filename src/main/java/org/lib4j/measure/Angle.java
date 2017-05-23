/* Copyright (c) 2014 lib4j
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.lib4j.measure;

public final class Angle extends Dimension.Scalar<Dimension.Unit> {
  public static class Unit extends Dimension.Unit {
    public static final Unit RAD = new Unit("rad", 1, null);
    public static final Unit DEG = new Unit("deg", Math.PI / 180, Unit.RAD);

    protected Unit(final String name, final double factor, final Dimension.Unit basis) {
      super(name, factor, basis);
    }
  }

  private static final int[] factors = new int[] {1, 60, 3600};

  private static double parseDMS(final String dms) {
    final int factor = dms.endsWith("S") || dms.endsWith("W") ? -1 : 1;
    final String[] parts = dms.split("[^\\.0-9]");
    double deg = 0;
    for (int i = 0; i < factors.length && i < parts.length; i++)
      deg += Double.parseDouble(parts[i]) / factors[i];

    return factor * deg;
  }

  public Angle(final double value, final Unit unit) {
    super(value, unit);
  }

  public Angle(final String dms) {
    super(parseDMS(dms), Unit.DEG);
  }

  public String toDMS() {
    final double deg = value(Unit.DEG);
    if (Double.isNaN(deg))
      return null;

    final int d = (int)deg;
    final int m = (int)((deg - d) * 60) % 60;
    final float s = (float)((deg - d) * 3600 - m * 60) % 60;
    return d + "˚" + m + "'" + s + "\"";
  }
}