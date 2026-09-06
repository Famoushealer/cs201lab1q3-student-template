// Define a generic Doubly Linked List.
// <E> means the list can store any type of data.
public class DoublyLinkedList<E> {

    // --------------------------------------------------
    // NODE CLASS
    // --------------------------------------------------

    // A Node stores:
    // 1. The element/data
    // 2. A reference to the previous node
    // 3. A reference to the next node
    //
    // static means the Node class does not need
    // an instance of DoublyLinkedList to exist.
    private static class Node<E> {

        // Stores the actual data
        private E element;

        // Points to the previous node
        private Node<E> prev;

        // Points to the next node
        private Node<E> next;


        // Node constructor
        // Creates a node with an element,
        // previous node, and next node.
        public Node(E e, Node<E> p, Node<E> n) {

            // Store the element
            element = e;

            // Store the reference to the previous node
            prev = p;

            // Store the reference to the next node
            next = n;
        }


        // Returns the element stored in this node
        public E getElement() {
            return element;
        }


        // Returns the next node
        public Node<E> getNext() {
            return next;
        }


        // Returns the previous node
        public Node<E> getPrev() {
            return prev;
        }


        // Changes the next node
        public void setNext(Node<E> n) {
            next = n;
        }


        // Changes the previous node
        public void setPrev(Node<E> p) {
            prev = p;
        }
    }


    // --------------------------------------------------
    // DOUBLY LINKED LIST VARIABLES
    // --------------------------------------------------

    // Header is a special node at the beginning.
    // It does NOT store actual data.
    private Node<E> header;

    // Trailer is a special node at the end.
    // It does NOT store actual data.
    private Node<E> trailer;

    // Keeps track of the number of actual elements.
    private int size = 0;


    // --------------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------------

    // Creates an empty doubly linked list.
    public DoublyLinkedList() {

        // Create the header node.
        // It initially has no previous or next node.
        header = new Node<>(null, null, null);

        // Create the trailer node.
        // Its previous node is header.
        // Its next node is null.
        trailer = new Node<>(null, header, null);

        // Make header point to trailer.
        header.setNext(trailer);
    }


    // --------------------------------------------------
    // size()
    // --------------------------------------------------

    // Returns the number of actual elements in the list.
    public int size() {
        return size;
    }


    // --------------------------------------------------
    // isEmpty()
    // --------------------------------------------------

    // Checks whether the list contains no elements.
    public boolean isEmpty() {
        return size == 0;
    }


    // --------------------------------------------------
    // first()
    // --------------------------------------------------

    // Returns the first element in the list.
    public E first() {

        // If there are no elements, return null.
        if (isEmpty()) {
            return null;
        }

        // header.getNext() gives the first actual node.
        // getElement() gets the data stored in that node.
        return header.getNext().getElement();
    }


    // --------------------------------------------------
    // last()
    // --------------------------------------------------

    // Returns the last element in the list.
    public E last() {

        // If there are no elements, return null.
        if (isEmpty()) {
            return null;
        }

        // trailer.getPrev() gives the last actual node.
        // getElement() gets the data stored there.
        return trailer.getPrev().getElement();
    }


    // --------------------------------------------------
    // addFirst()
    // --------------------------------------------------

    // Adds a new element at the beginning.
    public void addFirst(E e) {

        // Add the new node between:
        // header and the current first node.
        addBetween(e, header, header.getNext());
    }


    // --------------------------------------------------
    // addLast()
    // --------------------------------------------------

    // Adds a new element at the end.
    public void addLast(E e) {

        // Add the new node between:
        // the current last node and trailer.
        addBetween(e, trailer.getPrev(), trailer);
    }


    // --------------------------------------------------
    // removeFirst()
    // --------------------------------------------------

    // Removes and returns the first element.
    public E removeFirst() {

        // If empty, there is nothing to remove.
        if (isEmpty()) {
            return null;
        }

        // Remove the first actual node.
        return remove(header.getNext());
    }


    // --------------------------------------------------
    // removeLast()
    // --------------------------------------------------

    // Removes and returns the last element.
    public E removeLast() {

        // If empty, there is nothing to remove.
        if (isEmpty()) {
            return null;
        }

        // Remove the last actual node.
        return remove(trailer.getPrev());
    }


    // --------------------------------------------------
    // addBetween()
    // --------------------------------------------------

    // Adds a new node between two existing nodes.
    //
    // predecessor = node before the new node
    // successor   = node after the new node
    private void addBetween(
            E e,
            Node<E> predecessor,
            Node<E> successor) {

        // Create the new node.
        // Its previous pointer points to predecessor.
        // Its next pointer points to successor.
        Node<E> newest =
                new Node<>(e, predecessor, successor);

        // Make predecessor point forward to the new node.
        predecessor.setNext(newest);

        // Make successor point backwards to the new node.
        successor.setPrev(newest);

        // Increase the number of elements.
        size++;
    }


    // --------------------------------------------------
    // remove()
    // --------------------------------------------------

    // Removes a specific node from the list.
    private E remove(Node<E> node) {

        // Get the node before the node being removed.
        Node<E> predecessor = node.getPrev();

        // Get the node after the node being removed.
        Node<E> successor = node.getNext();

        // Make predecessor skip over node
        // and point directly to successor.
        predecessor.setNext(successor);

        // Make successor skip over node
        // and point directly back to predecessor.
        successor.setPrev(predecessor);

        // Decrease the size.
        size--;

        // Return the element that was removed.
        return node.getElement();
    }


    // --------------------------------------------------
    // toString()
    // --------------------------------------------------

    // Converts the linked list into a String.
    public String toString() {

        // StringBuilder is used to construct the output.
        StringBuilder sb = new StringBuilder();

        // Start at the first actual node.
        Node<E> current = header.getNext();

        // Continue until we reach the trailer.
        while (current != trailer) {

            // Add the current node's element to the String.
            sb.append(current.getElement());

            // Add a space between elements.
            sb.append(" ");

            // Move to the next node.
            current = current.getNext();
        }

        // Return the completed String.
        return sb.toString();
    }


    // --------------------------------------------------
    // group()
    // --------------------------------------------------

    // Moves all nodes containing null elements
    // toward the beginning of the list.
    public void group() {

        // nullTail keeps track of the end of the group
        // containing null elements.
        Node<E> nullTail = header;

        // Start checking from the first actual node.
        Node<E> current = header.getNext();


        // Continue until we reach the trailer.
        while (current != trailer) {

            // Save the next node BEFORE changing any links.
            // This is important because current may be moved.
            Node<E> next = current.getNext();


            // Check whether the current node contains null.
            if (current.getElement() == null) {


                // Check whether the current null node
                // is already directly after the null group.
                if (current != nullTail.getNext()) {

                    // ------------------------------------------
                    // REMOVE CURRENT FROM ITS CURRENT POSITION
                    // ------------------------------------------

                    // Make the previous node point to current's next node.
                    current.getPrev().setNext(current.getNext());

                    // Make the next node point back to current's previous node.
                    current.getNext().setPrev(current.getPrev());


                    // ------------------------------------------
                    // INSERT CURRENT AFTER THE NULL GROUP
                    // ------------------------------------------

                    // Get the node currently after nullTail.
                    Node<E> afterNulls = nullTail.getNext();

                    // Make nullTail point to current.
                    nullTail.setNext(current);

                    // Make current point back to nullTail.
                    current.setPrev(nullTail);

                    // Make current point forward to afterNulls.
                    current.setNext(afterNulls);

                    // Make afterNulls point back to current.
                    afterNulls.setPrev(current);
                }


                // Current null node is now the end
                // of the group of null nodes.
                nullTail = current;
            }


            // Move to the next node we saved earlier.
            current = next;
        }
    }
}