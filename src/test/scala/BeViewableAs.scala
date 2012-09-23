package org.sfree.chart

import org.specs2._
import org.specs2.matcher._

object BeViewableAs extends MustExpectations with AnyMatchers {

  implicit def anyWrapper[A](a: A) = new AnyWrapper(a)

  class AnyWrapper[A](a: A) {
    def beViewableAs[B](implicit cm: ClassManifest[B], ev: A ⇒ B) = implicitly[B](a) must beAnInstanceOf[B]
    def =>=[B](implicit cm: ClassManifest[B], ev: A ⇒ B) = implicitly[B](a) must beAnInstanceOf[B]
  }

}
