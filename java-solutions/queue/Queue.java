package queue;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// let immutable(n): [a[1]', a[2]', ..., a[n]'] == [a[1], a[2], ..., a[n]]
public interface Queue {

    // Pre: element != null
    // Post: [a'[1], a'[2], ..., a'[n]] = [element, a[1], ..., a[n]]  && n' = n+1
    void enqueue(Object element);

    // Pred: n > 0
    // Post: return a[1] && [a[1]', ...,a[n-1]'] = [a[2], ..., a[n]] && n'= n-1
    Object dequeue();

    // Pred: n > 0
    // Post: return a[1] && immutable(n) && n' == n
    Object element();

    // Pred: true
    // Post: return n == 0
    boolean isEmpty();

    // Pred: true
    // Post: return n && immutable(n) && n' == n
    int size();

    // Pred: true
    // Post: [a[1]', a[2]', ..., a[n]'] -> empty
    void clear();

    //Pred: true
    //Post: immutable(n) && n' == n
    //      return (exists k that a[k] == element)
    boolean contains(Object element);

    // :NOTE: element != null?
    //Pred: element != null
    //Post: return (exists k that a[k] == element)
    //      if (exists k that a[k] == element):
    //          n' = n-1
// :NOTE: informal
    //          let k be the index of the first occurrence of element in a:
    //          [a[1], a[2] ... a[n'] = [a[0] ... a[k-1], a[k+1] ... a[n]]
    //      else:
    //        immutable(n) && n' = n
    boolean removeFirstOccurrence(Object element);
}
