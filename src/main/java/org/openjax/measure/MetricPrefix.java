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

/**
 * Class representing the majority of common prefixes used in the metric system.
 */
public final class MetricPrefix {
  private static final MetricPrefix[] prefixes = new MetricPrefix[49];

  /** The {@link MetricPrefix} constant for "yocto": {@code 10^-24} */
  public static final MetricPrefix YOCTO = new MetricPrefix(-24, "yocto", "y");

  /** The {@link MetricPrefix} constant for "zepro": {@code 10^-21} */
  public static final MetricPrefix ZEPRO = new MetricPrefix(-21, "zepto", "z");

  /** The {@link MetricPrefix} constant for "atto": {@code 10^-18} */
  public static final MetricPrefix ATTO = new MetricPrefix(-18, "atto", "a");

  /** The {@link MetricPrefix} constant for "femto": {@code 10^-15} */
  public static final MetricPrefix FEMTO = new MetricPrefix(-15, "femto", "f");

  /** The {@link MetricPrefix} constant for "pico": {@code 10^-12} */
  public static final MetricPrefix PICO = new MetricPrefix(-12, "pico", "p");

  /** The {@link MetricPrefix} constant for "nano": {@code 10^-9} */
  public static final MetricPrefix NANO = new MetricPrefix(-9, "nano", "n");

  /** The {@link MetricPrefix} constant for "micro": {@code 10^-6} */
  public static final MetricPrefix MICRO = new MetricPrefix(-6, "micro", "μ");

  /** The {@link MetricPrefix} constant for "milli": {@code 10^-3} */
  public static final MetricPrefix MILLI = new MetricPrefix(-3, "milli", "m");

  /** The {@link MetricPrefix} constant for "centi": {@code 10^-2} */
  public static final MetricPrefix CENTI = new MetricPrefix(-2, "centi", "c");

  /** The {@link MetricPrefix} constant for "deci": {@code 10^-1} */
  public static final MetricPrefix DECI = new MetricPrefix(-1, "deci", "d");

  /** The {@link MetricPrefix} constant for "deca": {@code 10^-1} */
  public static final MetricPrefix DECA = new MetricPrefix(1, "deca", "da");

  /** The {@link MetricPrefix} constant for "hecto": {@code 10^-2} */
  public static final MetricPrefix HECTO = new MetricPrefix(2, "hecto", "h");

  /** The {@link MetricPrefix} constant for "kilo": {@code 10^-3} */
  public static final MetricPrefix KILO = new MetricPrefix(3, "kilo", "k");

  /** The {@link MetricPrefix} constant for "mega": {@code 10^-6} */
  public static final MetricPrefix MEGA = new MetricPrefix(6, "mega", "m");

  /** The {@link MetricPrefix} constant for "giga": {@code 10^-9} */
  public static final MetricPrefix GIGA = new MetricPrefix(9, "giga", "G");

  /** The {@link MetricPrefix} constant for "tera": {@code 10^-12} */
  public static final MetricPrefix TERA = new MetricPrefix(12, "tera", "T");

  /** The {@link MetricPrefix} constant for "peta": {@code 10^-15} */
  public static final MetricPrefix PETA = new MetricPrefix(15, "peta", "P");

  /** The {@link MetricPrefix} constant for "exa": {@code 10^-18} */
  public static final MetricPrefix EXA = new MetricPrefix(18, "exa", "E");

  /** The {@link MetricPrefix} constant for "zetta": {@code 10^-21} */
  public static final MetricPrefix ZETTA = new MetricPrefix(21, "zetta", "Z");

  /** The {@link MetricPrefix} constant for "yotta": {@code 10^-24} */
  public static final MetricPrefix YOTTA = new MetricPrefix(24, "yotta", "Y");

  /**
   * Returns the {@link MetricPrefix} constant for the specified power, or {@code null} if a {@link MetricPrefix} for the specified
   * power is not defined.
   *
   * @param power The power of the desired {@link MetricPrefix}.
   * @return The {@link MetricPrefix} constant for the specified power, or {@code null} if a {@link MetricPrefix} for the specified
   *         power is not defined.
   */
  public static MetricPrefix of(final int power) {
    return prefixes[0].power <= power && power <= prefixes[prefixes.length - 1].power ? prefixes[power - prefixes[0].power] : null;
  }

  private final int power;
  private final String prefix;
  private final String symbol;

  private MetricPrefix(final int power, final String prefix, final String symbol) {
    final int offset = prefixes[0] != null ? prefixes[0].power : power;
    prefixes[power - offset] = this;
    this.power = power;
    this.prefix = prefix;
    this.symbol = symbol;
  }

  /**
   * Returns the power represented by this {@link MetricPrefix}.
   *
   * @return The power represented by this {@link MetricPrefix}.
   */
  public int getPower() {
    return this.power;
  }

  /**
   * Returns the prefix represented by this {@link MetricPrefix}.
   *
   * @return The prefix represented by this {@link MetricPrefix}.
   */
  public String getPrefix() {
    return this.prefix;
  }

  /**
   * Returns the symbol represented by this {@link MetricPrefix}.
   *
   * @return The symbol represented by this {@link MetricPrefix}.
   */
  public String getSymbol() {
    return this.symbol;
  }

  @Override
  public boolean equals(final Object obj) {
    return obj == this || obj instanceof MetricPrefix && power == ((MetricPrefix)obj).power;
  }

  @Override
  public int hashCode() {
    return power;
  }

  @Override
  public String toString() {
    return prefix;
  }
}