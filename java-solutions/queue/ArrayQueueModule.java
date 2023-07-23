package queue;

import java.util.Objects;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// let immutable(n): [a[1]', a[2]', ..., a[n]'] == [a[1], a[2], ..., a[n]]
public class ArrayQueueModule {
    private static Object[] elements = new Object[2];
    private static int head = 0;
    private static int tail = -1;
    private static int size = 0;


    // Pre: element != null
    // Post: [a'[1], a'[2], ..., a'[n]] = [element, a[1], ..., a[n]]  && n' = n+1
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        if (size == elements.length) {
            ensureCapacity();
        }
        tail = (tail + 1 + elements.length) % elements.length;
        elements[tail] = element;
        size++;
    }

    // Pred: n > 0
    // Post: return a[1] && [a[1]', ...,a[n-1]'] = [a[2], ..., a[n]] && n'= n-1
    public static Object dequeue() {
        assert size > 0;
        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return element;
    }

    // Pred: n > 0
    // Post: return a[1] && immutable(n)
    public static Object element() {
        assert size > 0;
        return elements[head];
    }

    // Pred: true
    // Post: return n == 0
    public static boolean isEmpty() {
        return size == 0;
    }

    // Pred: true
    // Post: return n && immutable(n)
    public static int size() {
        return size;
    }

    // Pred: elements != null
    // Post: n' = n && immutable(n)
    private static void ensureCapacity() {
        Object[] temp = new Object[elements.length * 2];
        if(elements.length>=head) {
            System.arraycopy(elements, head, temp, 0, size - head);
            System.arraycopy(elements, 0, temp,
                    elements.length - head, size + head - elements.length);
        } else{
            System.arraycopy(elements, 0, temp, 0, size);
        }
        elements = temp;
        head = 0;
        tail = size - 1;
    }

    // Pred: true
    // Post: [a[1]', a[2]', ..., a[n]'] -> empty
    public static void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        elements = new Object[2];
        head = 0;
        tail = -1;
        size = 0;
    }

    // Pred: x != null
    // Post: [a[1]',a[2]', ..., a[n+1]'] = [a[x], a[1], ..., a[n]], n' = n + 1
    public static void push(Object x) {
        if (size == elements.length) {
            ensureCapacity();
        }
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = x;
        size++;
    }

    // Pred: n > 0
    // Post: return a[n]
    public static Object peek() {
        assert size > 0;
        return elements[(tail + elements.length) % elements.length];
    }

    // Pred: n > 0
    // Post: return a[n], n' = n - 1
    public static Object remove() {
        assert size > 0;
        tail = (tail + elements.length) % elements.length;
        Object element = elements[tail];
        elements[tail] = null;
        size--;
        tail = (tail - 1 + elements.length) % elements.length;
        return element;
    }

    // Pred: true
    // Post: return a && immutable(n)
    public static Object[] toArray() {
        Object[] newElements = new Object[size];
        if (head + size > elements.length) {
            int rightCount = elements.length - head;
            System.arraycopy(elements, head, newElements, 0, rightCount);
            System.arraycopy(elements, 0, newElements, rightCount, size - rightCount);
        } else {
            System.arraycopy(elements, head, newElements, 0, size);
        }
        return newElements;
    }
}