/* Copyright (c) 2014 OpenJAX
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

package org.openjax.measure;

/**
 * A scalar dimension representing distance.
 */
public final class Distance extends Dimension.Scalar<Dimension.Unit> {
  /** Equatorial radius of earth. */
  public static final Distance R = new Distance(6378.137, Unit.KM);

  public static class Unit extends Dimension.Unit {
    public static final Unit M = new Unit("m", 1, null);
    public static final Unit FT = new Unit("ft", 12 * 0.0254, Unit.M);
    public static final Unit MI = new Unit("mi", 5280, Unit.FT);
    public static final Unit KM = new Unit("km", 1000, Unit.M);
    public static final Unit NM = new Unit("nm", 1852, Unit.M);

    protected Unit(final String name, final double factor, final Dimension.Unit basis) {
      super(name, factor, basis);
    }
  }

  public Distance(final double value, final Unit unit) {
    super(value, unit);
  }

  public Location locate(final Location location, final Angle bearing) {
    final double d = value(Unit.KM) / R.value(Unit.KM);
    final double bearing1 = bearing.value(Angle.Unit.RAD);
    final double lat1 = location.latitude.value(Angle.Unit.RAD);
    final double lon1 = location.longitude.value(Angle.Unit.RAD);

    final double lat2 = StrictMath.asin(StrictMath.sin(lat1) * StrictMath.cos(d) + StrictMath.cos(lat1) * StrictMath.sin(d) * StrictMath.cos(bearing1));
    double lon2 = StrictMath.atan2(StrictMath.sin(bearing1) * StrictMath.sin(d) * StrictMath.cos(lat1), StrictMath.cos(d) - StrictMath.sin(lat1) * StrictMath.sin(lat2));
    lon2 = ((lon1 - lon2 + Math.PI) % (2 * Math.PI)) - Math.PI;
    return new Location(new Angle(lat2, Angle.Unit.RAD), new Angle(lon2, Angle.Unit.RAD));
  }
}