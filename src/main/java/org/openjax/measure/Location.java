/* Copyright (c) 2015 OpenJAX
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

import java.util.Objects;

/**
 * Class representing a location on the earth, identified by a
 * {@link #latitude} and a {@link #longitude}.
 */
public class Location {
  public final Angle latitude;
  public final Angle longitude;

  public Location(final Angle latitude, final Angle longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Distance distance(final Location location) {
    final double dLat = location.latitude.value(Angle.Unit.RAD) - latitude.value(Angle.Unit.RAD);
    final double dLon = location.longitude.value(Angle.Unit.RAD) - longitude.value(Angle.Unit.RAD);
    final double a = StrictMath.pow(StrictMath.sin(dLat / 2), 2) + StrictMath.cos(latitude.value(Angle.Unit.RAD)) * StrictMath.cos(location.latitude.value(Angle.Unit.RAD)) * StrictMath.pow(StrictMath.sin(dLon / 2), 2);
    final double d = 2 * Distance.R.value(Distance.Unit.KM) * StrictMath.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return new Distance(d, Distance.Unit.KM);
  }

  @Override
  public boolean equals(final Object obj) {
    return obj == this || (obj instanceof Location && Objects.equals(latitude, ((Location)obj).latitude) && Objects.equals(longitude, ((Location)obj).longitude));
  }

  @Override
  public int hashCode() {
    int hashCode = 1;
    if (latitude != null)
      hashCode = 31 * hashCode + (int)Double.doubleToLongBits(latitude.value(Angle.Unit.RAD));

    if (longitude != null)
      hashCode = 31 * hashCode + (int)Double.doubleToLongBits(longitude.value(Angle.Unit.RAD));

    return hashCode;
  }

  @Override
  public String toString() {
    return "(" + (latitude != null ? latitude.value(Angle.Unit.DEG) : "?") + "N, " + (longitude != null ? longitude.value(Angle.Unit.DEG) : "?") + "E)";
  }
}