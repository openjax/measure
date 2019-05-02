/* Copyright (c) 2018 OpenJAX
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

import static org.junit.Assert.*;

import org.junit.Test;

public class MetricPrefixTest {
  @Test
  public void test() {
    assertNull(MetricPrefix.of(-27));
    assertEquals(MetricPrefix.YOCTO, MetricPrefix.of(-24));
    assertEquals(MetricPrefix.ATTO, MetricPrefix.of(-18));
    assertNull(MetricPrefix.of(0));
    assertEquals(MetricPrefix.MEGA, MetricPrefix.of(6));
    assertEquals(MetricPrefix.GIGA, MetricPrefix.of(9));
    assertEquals(MetricPrefix.YOTTA, MetricPrefix.of(24));
    assertNull(MetricPrefix.of(27));
  }
}