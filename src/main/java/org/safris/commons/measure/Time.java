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

public final class Time extends Dimension.Scalar<Dimension.Unit> {
  public static class Unit extends Dimension.Unit {
    public static final Unit SEC = new Unit("sec", 1, null);
    public static final Unit MIN = new Unit("min", 60, Unit.SEC);
    public static final Unit HR = new Unit("hr", 60, Unit.MIN);
    public static final Unit DAY = new Unit("day", 24, Unit.HR);
    public static final Unit WK = new Unit("wk", 7, Unit.DAY);
    
    protected Unit(final String name, final double factor, final Dimension.Unit basis) {
      super(name, factor, basis);
    }
  }
  
  public Time(final double value, final Unit unit) {
    super(value, unit);
  }
}