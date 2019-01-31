package sandbox
import cats.Eval

class EvalExample {

  def foldRightStackUnsafe[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    as match {
      case head :: tail =>
        fn(head, foldRightStackUnsafe(tail, acc)(fn))

      case Nil =>
        acc
    }

  def foldRightStackSafe[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    foldRightEval(as, Eval.now(acc))((a, evalB) => {
      evalB.map(fn(a, _))
    }).value

  def foldRightEval[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case head :: tail =>
        Eval.defer(fn(head, foldRightEval(tail, acc)(fn)))
      case Nil =>
        acc
    }
}
