package queue;

public class LinkedQueueTest {
    public static void fill(LinkedQueue queue, String prefix) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(prefix + i);
        }
    }

    public static void dump(LinkedQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(
                    queue.size() + " " +
                            queue.element() + " " +
                            queue.dequeue()
            );
        }
    }

    public static void main(String[] args) {
        LinkedQueue queue1 = new LinkedQueue();
        fill(queue1, "s1_");
        dump(queue1);
    }
}
