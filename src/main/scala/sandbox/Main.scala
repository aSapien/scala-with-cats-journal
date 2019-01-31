package sandbox

object Main extends App {
  println("started...")

  type Absurd[A] = A => Nothing

  def never[T](t: T)(implicit evidence: Absurd[T]) = t
  //never(1)

  sealed trait TopType
  case class SubType() extends TopType

  def always[T](t:T)(implicit evidence: Absurd[TopType] <:< Absurd[SubType]) = t

  def add5[T](t: T)(implicit evidence: Absurd[Int] <:< Absurd[T]): T = { 
    case t: Int => k + 5 
    case _ => -1
    }

  add5(1)

  val canConvert = implicitly[SubType <:< TopType] //works

  // val canConvertAbsurd = implicitly[Absurd[SubType] <:< Absurd[TopType]] //doesn't work

  // val canConvertAbsurd = implicitly[Absurd[TopType] <:< Absurd[SubType]] //works

  // def alwaysDoubleNegative[T](t:T)(implicit evidence: Absurd[Absurd[T]] =:= T) = t

  // sealed trait A
  // sealed trait B

  // new A with B {} // Logical "AND"

  // def always[T](t:T)(implicit evidence: Absurd[Absurd[T]]) = t

  // alwaysDoubleNegative(1)

  // println("SUCCESS!!! " + fooNever(1)) //not compiling


  // type Always[A] = Never[Never[A]]

  // def fooAlways[T: Always](t: T): T = t

  // println(fooAlways(1))
}
