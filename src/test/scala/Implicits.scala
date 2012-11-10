package org.sfree.chart

import language.implicitConversions

import java.util.Date

import org.jfree.data.time._

object Implicits extends Implicits

trait Implicits {
  implicit def jdate2jfree(d: Date): RegularTimePeriod = new Minute(d)
}
