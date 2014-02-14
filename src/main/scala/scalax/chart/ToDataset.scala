package scalax.chart

import language.higherKinds

import org.jfree.data.general.Dataset

abstract class ToDataset[A] protected () {
  type X <: Dataset
  def toDataset(a: A): X
}

abstract class ToDatasetCompanion[D <: Dataset, TD[X] <: ToDataset[X]] protected () {
  def apply[A,B <: D](f: A => B): TD[A]

  final implicit def Identity[A <: D]: TD[A] =
    apply(identity)
}
