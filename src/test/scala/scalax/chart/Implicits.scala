package scalax.chart

import language.implicitConversions

import java.util.Date

import org.jfree.data.time.{ RegularTimePeriod, Second }

object Implicits extends Implicits

trait Implicits {
  implicit def jdate2jfree(d: Date): RegularTimePeriod = new Second(d)
}
