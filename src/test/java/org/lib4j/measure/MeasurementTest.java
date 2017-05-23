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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.lib4j.measure.Dimension.Unit;
import org.lib4j.util.Combinations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeasurementTest {
  private static final Logger logger = LoggerFactory.getLogger(MeasurementTest.class);

  private static Dimension.Unit[] getUnits(final Class<?> unitClass) throws Exception {
    final List<Dimension.Unit> units = new ArrayList<Dimension.Unit>();
    final Field[] fields = unitClass.getDeclaredFields();
    for (final Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers()) && Dimension.Unit.class.isAssignableFrom(field.getType()))
        units.add((Dimension.Unit)field.get(null));
    }

    return units.toArray(new Dimension.Unit[units.size()]);
  }

  private static Object[] toArgs(final Object arg, final Object[] units) {
    final Object[] args = new Object[units.length + 1];
    args[0] = arg;
    System.arraycopy(units, 0, args, 1, units.length);
    return args;
  }

  private static final Map<Class<?>,Method> unitFactoryMethods = new HashMap<Class<?>,Method>();

  private static Method getFactoryMethod(final Class<?> unitClass) {
    if (unitClass.getDeclaringClass() != Dimension.Unit.class)
      return null;

    Method factoryMethod = unitFactoryMethods.get(unitClass);
    if (factoryMethod != null)
      return factoryMethod;

    synchronized (unitFactoryMethods) {
      if ((factoryMethod = unitFactoryMethods.get(unitClass)) != null)
        return factoryMethod;

      for (final Method method : unitClass.getDeclaringClass().getDeclaredMethods()) {
        if (Modifier.isStatic(method.getModifiers()) && method.getReturnType() == unitClass) {
          unitFactoryMethods.put(unitClass, factoryMethod = method);
          break;
        }
      }
    }

    return factoryMethod;
  }

  private static void assertMeasurementUnits(final Class<?> dimensionClass, final Class<?> ... unitClasses) throws Exception {
    Constructor<?> constructor = null;
    Constructor<?>[] constructors = dimensionClass.getConstructors();
    for (int i = 0; i < constructors.length; i++) {
      final Class<?>[] parameterTypes = constructors[i].getParameterTypes();
      if (parameterTypes.length > 1 && parameterTypes[0] == double.class && Dimension.Unit.class.isAssignableFrom(parameterTypes[1])) {
        constructor = constructors[i];
        break;
      }
    }

    final Dimension.Unit[][] allUnits = new Dimension.Unit[unitClasses.length][];
    int i = 0;
    for (final Class<?> unitClass : unitClasses)
      allUnits[i++] = getUnits(unitClass);

    final double value = 100;
    Dimension.Unit[][] combinations = Combinations.<Dimension.Unit>combine(allUnits);
    for (final Dimension.Unit[] from : combinations) {
      final Class<?> unitType = constructor.getParameterTypes()[1];
      final Method factoryMethod = getFactoryMethod(unitType);
      final Object[] fromArgs = factoryMethod != null ? new Object[] {factoryMethod.invoke(null, (Object[])from)} : from;
      final Object measurement = constructor.newInstance(toArgs(value, fromArgs));
      for (final Dimension.Unit[] to : combinations) {
        final Method[] methods = measurement.getClass().getMethods();
        for (final Method method : methods) {
          if ("value".equals(method.getName())) {
            final Object[] toArgs = factoryMethod != null ? new Object[] {factoryMethod.invoke(null, (Object[])to)} : to;
            final double scalar = (double)method.invoke(measurement, toArgs);
            final Object measurement2 = constructor.newInstance(toArgs(scalar, toArgs));
            final double back = (double)method.invoke(measurement2, fromArgs);
            Assert.assertEquals(value, back, 0.000001);
            logger.info(measurement + " = " + measurement2 + " = " + constructor.newInstance(toArgs(back, fromArgs)) + " [OK]");
          }
        }
      }
    }
  }

  @Test
  public void testUnits() throws Exception {
    assertMeasurementUnits(Distance.class, Distance.Unit.class);
    assertMeasurementUnits(Elevation.class, Elevation.Unit.class);
    assertMeasurementUnits(Time.class, Time.Unit.class);
    assertMeasurementUnits(Speed.class, Distance.Unit.class, Time.Unit.class);
    assertMeasurementUnits(Mass.class, Mass.Unit.class);
    assertMeasurementUnits(Angle.class, Angle.Unit.class);
    assertMeasurementUnits(Volume.class, Volume.Unit.class);
    assertMeasurementUnits(Density.class, Mass.Unit.class, Volume.Unit.class);
    final Velocity v = new Velocity(new Angle(45, Angle.Unit.DEG), new Speed(100, Unit.ratio(Distance.Unit.KM, Time.Unit.HR)));
    logger.info(v.value(new Angle(-45, Angle.Unit.DEG)).toString());
  }
}