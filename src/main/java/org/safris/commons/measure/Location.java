/* Copyright (c) 2015 lib4j
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

package org.safris.commons.measure;

public class Location {
  public final Angle longitude;
  public final Angle latitude;

  public Location(final Angle latitude, final Angle longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Distance distance(final Location location) {
    final double dLat = location.latitude.value(Angle.Unit.RAD) - latitude.value(Angle.Unit.RAD);
    final double dLon = location.longitude.value(Angle.Unit.RAD) - longitude.value(Angle.Unit.RAD);
    final double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(latitude.value(Angle.Unit.RAD)) * Math.cos(location.latitude.value(Angle.Unit.RAD)) * Math.pow(Math.sin(dLon / 2), 2);
    final double d = 2 * Distance.R.value(Distance.Unit.KM) * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return new Distance(d, Distance.Unit.KM);
  }

  @Override
  public boolean equals(final Object obj) {
    return obj == this || (obj instanceof Location && (latitude != null ? latitude.equals(((Location)obj).latitude) : ((Location)obj).latitude == null) && (longitude != null ? longitude.equals(((Location)obj).longitude) : ((Location)obj).longitude == null));
  }

  @Override
  public int hashCode() {
    return (latitude != null ? (int)Double.doubleToLongBits(latitude.value(Angle.Unit.RAD)) : -1) + (longitude != null ? (int)Double.doubleToLongBits(longitude.value(Angle.Unit.RAD)) : -1);
  }

  @Override
  public String toString() {
    return "(" + (latitude != null ? latitude.value(Angle.Unit.DEG) : "?") + "N, " + (longitude != null ? longitude.value(Angle.Unit.DEG) : "?") + "E)";
  }
}