package sandbox

object Main extends App {
  import PrintableInstances._
  import PrintableSyntax._

  val cat = Cat("Garfield", 38, "ginger and black")
  // cat: Cat = Cat(Garfield,38,ginger and black)

  cat.print
  // Garfield is a 38 year-old ginger and black cat.
}

final case class Cat(name: String, age: Int, color: String)

//NAME is a AGE year-old COLOR cat.

trait Printable[A] {
  def format(v: A): String
}

object PrintableInstances {
  implicit val printableString: Printable[String] = new Printable[String] {
    def format(_val: String) = _val
  }

  implicit val printableInt: Printable[Int] = new Printable[Int] {
    def format(_val: Int) = _val.toString
  }

  implicit val printableCat: Printable[Cat] = new Printable[Cat] {
    def format(_val: Cat): String = s"${Printable.format(_val.name)} is a ${Printable.format(_val.age)} year old ${Printable.format(_val.color)} cat"
  }
}

object Printable {
  def format[A](a: A)(implicit P: Printable[A]): String = P.format(a)
  def print[A](a: A)(implicit P: Printable[A]): Unit = println(format(a))
}

object PrintableSyntax {
  implicit class PrintableOps[O](instance: O) {
    def format(implicit P: Printable[O]): String = Printable.format(instance)
    def print(implicit P: Printable[O]): Unit = Printable.print(instance)
  }
}