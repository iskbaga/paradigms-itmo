package queue;

// Model: a[1]..a[n]
// Inv: n >= 0 && forall i=1..n: a[i] != null
// let immutable(n): [a[1]', a[2]', ..., a[n]'] == [a[1], a[2], ..., a[n]]
public class LinkedQueue extends AbstractQueue {
    private Node head = null;
    private Node tail = null;

    protected void enqueueImpl(Object element) {
        Node temp = new Node(element);
        if (isEmpty()) {
            head = temp;
        } else {
            tail.next = temp;
        }
        tail = temp;
    }

    @Override
    protected boolean containsImpl(Object element) {
        return getNode(element) != null;
    }


    // :NOTE: common code
    @Override
    protected boolean removeFirstImpl(Object element) {
        Node current = getNode(element);
        if (current == null) {
            return false;
        }
        if (head == current) {
            head = head.next;
        } else {
            Node prev = head;
            while (prev.next != current) {
                prev = prev.next;
            }
            prev.next = current.next;
            if (tail == current) {
                tail = prev;
            }
        }
        size--;
        return true;
    }

    private Node getNode(Object element) {
        Node current = head;
        while (current != null) {
            if (current.element.equals(element)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    protected Object dequeueImpl() {
        Object temp = head.element;
        head = head.next;
        tail = size > 1 ? tail : null;
        return temp;
    }

    public void clearImpl() {
        head = null;
        tail = null;
    }

    protected Object elementImpl() {
        return head.element;
    }

    private static class Node {
        private final Object element;
        private Node next;

        Node(Object element) {
            this.element = element;
            this.next = null;

        }
    }
}
