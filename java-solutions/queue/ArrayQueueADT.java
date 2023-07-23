package queue;

import java.util.Objects;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// let immutable(n): [a'[1], a[2]', ..., a[n]'] == [a[1], a[2], ..., a[n]]
public class ArrayQueueADT {
    private Object[] elements = new Object[2];
    private int head = 0;
    private int tail = -1;
    private int size = 0;

    public ArrayQueueADT() {
    }

    // Pred: true
    // Post: return n = 0
    public static ArrayQueueADT create() {
        ArrayQueueADT queue = new ArrayQueueADT();
        queue.elements = new Object[2];
        return queue;
    }

    // Pre: queue != null && element != null
    // Post: [a'[1], a'[2], ..., a'[n]] = [element, a[1], ..., a[n]]  && n' = n+1
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        if (queue.size == queue.elements.length) {
            ensureCapacity(queue);
        }
        queue.tail = (queue.tail + 1) % queue.elements.length;
        queue.elements[queue.tail] = element;
        queue.size++;
    }

    // Pred: queue != null &&  n > 0
    // Post: return a[1] && [a[1]', ...,a[n-1]'] = [a[2], ..., a[n]] && n'= n-1
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object element = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return element;
    }

    // Pred: queue != null && n > 0
    // Post: return a[1] && immutable(n)
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.head];
    }

    // Pred: queue != null
    // Post: return n == 0
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // Pred: queue != null
    // Post: return n && immutable(n)
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // Pred: queue != null
    // Post: n' = n && immutable(n)
    private static void ensureCapacity(ArrayQueueADT queue) {
        Object[] temp = new Object[queue.elements.length * 2];
        if(queue.elements.length>=queue.head) {
            System.arraycopy(queue.elements, queue.head, temp, 0, queue.size - queue.head);
            System.arraycopy(queue.elements, 0, temp,
                        queue.elements.length - queue.head,
                        queue.size + queue.head - queue.elements.length);
        } else{
            System.arraycopy(queue.elements, 0,
                    temp, 0, queue.size);
        }
        queue.elements = temp;
        queue.head = 0;
        queue.tail = queue.size - 1;
    }

    // Pred: queue != null
    // Post: [a[1]', a[2]', ..., a[n]'] -> empty
    public static void clear(ArrayQueueADT queue) {
        for (int i = 0; i < queue.size; i++) {
            queue.elements[i] = null;
        }
        queue.elements = new Object[2];
        queue.head = 0;
        queue.tail = -1;
        queue.size = 0;
    }

    // Pred: queue != null && x != null
    // Post: [a[1]',a[2]', ..., a[n+1]'] = [a[x], a[1], ..., a[n]], n' = n + 1
    public static void push(ArrayQueueADT queue, Object x) {
        if (queue.size == queue.elements.length) {
            ensureCapacity(queue);
        }
        queue.head = (queue.head - 1 + queue.elements.length) % queue.elements.length;
        queue.elements[queue.head] = x;
        queue.size++;
    }

    // Pred: queue != null && n > 0
    // Post: return a[n]
    public static Object peek(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[(queue.tail + queue.elements.length) % queue.elements.length];
    }

    // Pred: queue != null
    // Post: return a[n], n' = n - 1
    public static Object remove(ArrayQueueADT queue) {
        assert queue.size > 0;
        queue.tail = (queue.tail + queue.elements.length) % queue.elements.length;
        Object element = queue.elements[queue.tail];
        queue.elements[queue.tail] = null;
        queue.size--;
        queue.tail = (queue.tail - 1 + queue.elements.length) % queue.elements.length;
        return element;
    }

    // Pred: queue != null
    // Post: return a && immutable(n)
    public static Object[] toArray(ArrayQueueADT queue) {
        Object[] newElements = new Object[queue.size];
        if (queue.head + queue.size > queue.elements.length) {
            int rightCount = queue.elements.length - queue.head;
            System.arraycopy(queue.elements, queue.head, newElements, 0, rightCount);
            System.arraycopy(queue.elements, 0, newElements, rightCount, queue.size - rightCount);
        } else {
            System.arraycopy(queue.elements, queue.head, newElements, 0, queue.size);
        }
        return newElements;
    }
}
