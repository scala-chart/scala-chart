package scalax.chart

import language.implicitConversions

import scala.reflect.ClassTag

import org.specs2._
import org.specs2.matcher._

trait BeViewableAs extends MustExpectations with AnyMatchers {

  implicit class AnyWrapper[A](a: A) {
    def beViewableAs[B](implicit ct: ClassTag[B], ev: A ⇒ B) = implicitly[B](a) must beAnInstanceOf[B]
    def =>=[B](implicit ct: ClassTag[B], ev: A ⇒ B) = implicitly[B](a) must beAnInstanceOf[B]
  }

}
