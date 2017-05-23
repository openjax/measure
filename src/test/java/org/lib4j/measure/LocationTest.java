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

import org.junit.Assert;
import org.junit.Test;

public class LocationTest {
  @Test
  public void testLocation() throws Exception {
    final Angle lat = new Angle(38.898556, Angle.Unit.DEG);
    final Location l1 = new Location(lat, new Angle(-77.037852, Angle.Unit.DEG));
    final Location l2 = new Location(lat, new Angle(-77.043934, Angle.Unit.DEG));
    final Distance expected = new Distance(0.5269164586229639, Distance.Unit.KM);
    Assert.assertEquals(expected, l1.distance(l2));

    final Location location = expected.locate(new Location(lat, new Angle(0, Angle.Unit.DEG)), new Angle(90, Angle.Unit.DEG));
    final double dlng = 77.037852 - 77.043934;
    Assert.assertEquals(dlng, location.longitude.value(Angle.Unit.DEG), 0.0000000001);
  }
}