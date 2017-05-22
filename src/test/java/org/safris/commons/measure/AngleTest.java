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

package org.safris.commons.measure;

import org.junit.Assert;
import org.junit.Test;

public class AngleTest {
  @Test
  public void testDMS() {
    final Angle latitude = new Angle(3.58324, Angle.Unit.DEG);
    final Angle longitude = new Angle(4.59202, Angle.Unit.DEG);
    Assert.assertEquals("3˚34'59.664\"", latitude.toDMS());
    Assert.assertEquals("4˚35'31.272\"", longitude.toDMS());
    Assert.assertEquals(latitude, new Angle(latitude.toDMS()));
    Assert.assertEquals(longitude, new Angle(longitude.toDMS()));
  }
}