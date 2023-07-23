package queue;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// let immutable(n): [a[1]', a[2]', ..., a[n]'] == [a[1], a[2], ..., a[n]]
public class ArrayQueue extends AbstractQueue {
    private Object[] elements = new Object[2];
    private int head = 0;
    private int tail = -1;

    protected void enqueueImpl(final Object element) {
        if (size == elements.length) {
            ensureCapacity();
        }
        tail = (tail + 1) % elements.length;
        elements[tail] = element;

    }

    @Override
    protected boolean containsImpl(final Object element) {
        return getFirstInd(element) != -1;
    }

    @Override
    protected boolean removeFirstImpl(final Object element) {
        if (!containsImpl(element)) {
            return false;
        }
        final int deletePos = head + getFirstInd(element);
        final int end = head + size - 1;
        final int len = elements.length;
        final int startPos = Math.max(0, deletePos - len);
        final Object firstElement = elements[0];
        // :NOTE: simplify
        if (startPos < end - len) {
            System.arraycopy(elements, startPos + 1, elements,
                    startPos, end - len - startPos);
        }
        final int length = Math.min(len - 1, end) - deletePos;
        if (0 <= length) {
            System.arraycopy(elements, deletePos + 1, elements, deletePos, length) ;
            elements[len - 1] = firstElement;
        }
        elements[end % len] = null;
        tail = (tail - 1 + len) % len;
        size--;
        return true;
    }

    private int getFirstInd(final Object element) {
        for (int i = 0; i < size; i++) {
            if (elements[(head + i) % elements.length].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    protected Object dequeueImpl() {
        final Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return element;
    }

    public Object elementImpl() {
        return elements[head];
    }

    private void ensureCapacity() {
        final Object[] temp = new Object[elements.length * 2];
        if (elements.length >= head) {
            System.arraycopy(elements, head, temp, 0, size - head);
            System.arraycopy(elements, 0, temp,
                    elements.length - head, size + head - elements.length);
        } else {
            System.arraycopy(elements, 0, temp, 0, size);
        }
        elements = temp;
        head = 0;
        tail = size - 1;
    }

    public void clearImpl() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        elements = new Object[2];
        head = 0;
        tail = -1;
    }

    // Pred: x != null
    // Post: [a[1]',a[2]', ..., a[n+1]'] = [a[x], a[1], ..., a[n]], n' = n + 1
    // Неявный this
    public void push(final Object x) {
        if (size == elements.length) {
            ensureCapacity();
        }
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = x;
        size++;
    }

    // Pred: n > 0
    // Post: return a[n]
    // Неявный this
    public Object peek() {
        assert size > 0;
        return elements[(tail + elements.length) % elements.length];
    }

    // Pred: n > 0
    // Post: return a[n], n' = n - 1
    // Неявный this
    public Object remove() {
        assert size > 0;
        tail = (tail + elements.length) % elements.length;
        final Object element = elements[tail];
        elements[tail] = null;
        size--;
        tail = (tail - 1 + elements.length) % elements.length;
        return element;
    }

    // Pred: true
    // Post: return a && immutable(n)
    // Неявный this
    public Object[] toArray() {
        final Object[] newElements = new Object[size];
        if (head + size > elements.length) {
            final int rightCount = elements.length - head;
            System.arraycopy(elements, head, newElements, 0, rightCount);
            System.arraycopy(elements, 0, newElements, rightCount, size - rightCount);
        } else {
            System.arraycopy(elements, head, newElements, 0, size);
        }
        return newElements;
    }
}
