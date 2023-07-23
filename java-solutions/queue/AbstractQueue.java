package queue;

import java.util.Objects;


public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    public void enqueue(Object element) {
        Objects.requireNonNull(element);
        enqueueImpl(element);
        size++;
    }

    public Object dequeue() {
        assert size > 0;
        Object element = dequeueImpl();
        size--;
        return element;
    }

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public void clear() {
        clearImpl();
        size = 0;
    }

    @Override
    public boolean contains(Object element) {
        return containsImpl(element);
    }

    @Override
    public boolean removeFirstOccurrence(Object element) {
        Objects.requireNonNull(element);
        return removeFirstImpl(element);
    }

    public int size() {
        return size;
    }

    protected abstract boolean containsImpl(Object element);

    protected abstract boolean removeFirstImpl(Object element);

    protected abstract Object dequeueImpl();

    protected abstract Object elementImpl();

    protected abstract void clearImpl();

    protected abstract void enqueueImpl(Object element);
}