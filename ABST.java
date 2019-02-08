import tester.Tester;

// 
interface IComparator<T> {
  int compare(T t1, T t2);
}

// function object which compares two books by their title
class BooksByTitle implements IComparator<Book> {
  /*
   * returns a negative number if t1 comes before t2 in alphabetical order,
   * positive if t2 comes before, and 0 if they are equal
   */
  public int compare(Book t1, Book t2) {
    return t1.title.compareTo(t2.title);
  }
}

// function object which compares two books by their authors
class BooksByAuthor implements IComparator<Book> {
  /*
   * returns a negative number if t1 comes before t2 in alphabetical order,
   * positive if t2 comes before, and 0 if they are equal
   */
  public int compare(Book t1, Book t2) {
    return t1.author.compareTo(t2.author);
  }
}

// function object which compares two books by their price
class BooksByPrice implements IComparator<Book> {
  /*
   * returns a negative number if the price of t1 is less than that of t2,
   * positive if t2 is more than t1, and 0 if they are equal
   */
  public int compare(Book t1, Book t2) {
    return t1.price - t2.price;
  }
}

// abstract class representing a binary search tree
abstract class ABST<T> {

  // comparator which determines how the search tree is ordered
  IComparator<T> order;

  // Constructor
  ABST(IComparator<T> order) {
    this.order = order;
  }

  // inserts the given item at the proper place in this tree
  abstract ABST<T> insert(T item);

  // returns the leftmost item in the this tree
  abstract T getLeftmost();

  // helper which returns the leftmost item in this tree
  abstract T getLeftmostHelp(T previous);

  // returns all but the leftmost item in this tree
  abstract ABST<T> getRight();

  /*
   * helper which removes the leftmost item from this tree by accumulating the
   * part of the tree to be kept
   */
  abstract ABST<T> removeLeft(T data, ABST<T> right);

  // is this tree the same as the given tree?
  abstract boolean sameTree(ABST<T> other);

  // is this leaf the same as the given leaf?
  boolean sameLeaf(Leaf<T> other) {
    return false;
  }

  // is this node the same as the given node?
  boolean sameNode(Node<T> other) {
    return false;
  }

  // does this tree have the sameData in the same order as the given tree?
  abstract boolean sameData(ABST<T> other);

  // does this tree contain the same books as the given list of books, in the same
  // order
  abstract boolean sameAsList(IList<T> list);

  // adds all of the items, leftmost to rightmost, in this list to the given list.
  abstract IList<T> buildList(IList<T> list);
}

// class representing leaf
class Leaf<T> extends ABST<T> {
  // Constructor
  Leaf(IComparator<T> order) {
    super(order);
  }

  /*  Template
   * @formatter:off
   * ----------
   *  Fields:
   *  IComparator<T>              order
   *  
   *  Methods:
   *  insert(T)                   ABST<T>
   *  getLeftmost()               T
   *  getLeftmostHelp(T)          T
   *  getRight()                  ABST<T>
   *  removeLeft(T, ABST<T>)      ABST<T>
   *  sameTree(ABST<T>)           boolean
   *  sameLeaf(Leaf<T>            boolean
   *  sameData(ABST<T>)           boolean
   *  sameAsList(IList<T>)        boolean
   *  buildList(IList<T>)         IList<T>
   *  
   *  Methods on fields:
   *  order.compare(Book, Book)   int
   *  @formatter:on
   */

  // inserts the given item at this leaf
  ABST<T> insert(T item) {
    return new Node<T>(this.order, item, this, this);
  }

  // throws an exception because there is no left item on this leaf
  T getLeftmost() {
    throw new RuntimeException("No leftmost item of an empty tree");
  }

  // helper which returns the accumulated item from the previous node
  T getLeftmostHelp(T previous) {
    return previous;
  }

  // throws an exception because there is no right item on this leaf
  ABST<T> getRight() {
    throw new RuntimeException("No right of an empty tree");
  }

  // removes the left item in this empty tree
  ABST<T> removeLeft(T data, ABST<T> right) {
    return right;
  }

  // is this leaf the same as the given tree?
  boolean sameTree(ABST<T> other) {
    return other.sameLeaf(this);
  }

  // is this leaf the same as the given leaf?
  @Override
  public boolean sameLeaf(Leaf<T> other) {
    return true;
  }

  // does this leaf contain the same data as the given tree?
  boolean sameData(ABST<T> other) {
    return other.sameLeaf(this);
  }

  // does this leaf contains the same data in the same order as the given list
  boolean sameAsList(IList<T> list) {
    return new MtList<T>().sameList(this.order, list);
  }

  // returns a list containing all of the items in this leaf appended to the given
  // list, in order
  IList<T> buildList(IList<T> list) {
    return list;
  }
}

// class representing a node
class Node<T> extends ABST<T> {
  T data;
  ABST<T> left;
  ABST<T> right;

  // Constructor
  Node(IComparator<T> order, T data, ABST<T> left, ABST<T> right) {
    super(order);
    this.data = data;
    this.left = left;
    this.right = right;
  }

  /*  Template
   * @formatter:off
   * ----------
   *  Fields:
   *  IComparator<T>              order
   *  data                        T
   *  ABST<T>                     left
   *  ABST<T>                     right
   *  
   *  Methods:
   *  insert(T)                   ABST<T>
   *  getLeftmost()               T
   *  getLeftmostHelp(T)          T
   *  getRight()                  ABST<T>
   *  removeLeft(T, ABST<T>)      ABST<T>
   *  sameTree(ABST<T>)           boolean
   *  sameNode(Leaf<T>)           boolean
   *  sameData(ABST<T>)           boolean
   *  sameAsList(IList<T>)        boolean
   *  buildList(IList<T>)         IList<T>
   *  
   *  Methods on fields:
   *  order.compare(Book, Book)   int
   *    //Methods on this.left and this.right//
   *  insert(T)                   ABST<T>
   *  getLeftmost()               T
   *  getLeftmostHelp(T)          T
   *  getRight()                  ABST<T>
   *  removeLeft(T, ABST<T>)      ABST<T>
   *  sameTree(ABST<T>)           boolean
   *  sameLeaf(ABST<T>)           boolean
   *  sameNode(Leaf<T>)           boolean
   *  sameData(ABST<T>)           boolean
   *  sameAsList(IList<T>)        boolean
   *  buildList(IList<T>)         IList<T>
   *  
   *  @formatter:on
   */

  // inserts the given item at the correct position in this tree
  ABST<T> insert(T item) {
    if (this.order.compare(item, this.data) < 0) {
      return new Node<T>(this.order, this.data, this.left.insert(item), this.right);
    }
    else {
      return new Node<T>(this.order, this.data, this.left, this.right.insert(item));
    }
  }

  // returns the leftmost item in this tree
  T getLeftmost() {
    return this.left.getLeftmostHelp(this.data);
  }

  // helper that accumulates the next leftmost item in this tree
  T getLeftmostHelp(T previous) {
    return this.left.getLeftmostHelp(this.data);
  }

  // returns all but the leftmost item in this tree
  ABST<T> getRight() {
    return this.left.removeLeft(this.data, this.right);
  }

  // helper which removes the leftmost item in this list by accumulating the right
  // tree
  ABST<T> removeLeft(T data, ABST<T> right) {
    return new Node<T>(this.order, data, this.left.removeLeft(this.data, this.right), right);
  }

  // is this tree the same as the given tree?
  boolean sameTree(ABST<T> other) {
    return other.sameNode(this);
  }

  // is this node the same as the given node?
  @Override
  public boolean sameNode(Node<T> other) {
    return this.order.compare(this.data, other.data) == 0 && this.left.sameTree(other.left)
        && this.right.sameTree(other.right);
  }

  // does this tree contain the same data as the given tree?
  boolean sameData(ABST<T> other) {
    return this.order.compare(this.getLeftmost(), other.getLeftmost()) == 0
        && this.getRight().sameData(other.getRight());
  }

  // does this tree contain the same books as the given list of books, in the same
  // order
  boolean sameAsList(IList<T> list) {
    return this.buildList(new MtList<T>()).reverse().sameList(this.order, list);
  }

  // adds all of the items, leftmost to rightmost, in this list to the given list.
  IList<T> buildList(IList<T> list) {
    return this.getRight().buildList(new ConsList<T>(this.getLeftmost(), list));
  }
}

// class representing a book
class Book {
  String title; // title of book
  String author; // name of author
  int price; // price in cents

  Book(String title, String author, int price) {
    this.title = title;
    this.author = author;
    this.price = price;
  }
}

interface IList<T> {
  <U> U foldl(IFunc2<U, T, U> func, U base);

  IList<T> reverse();

  IList<T> reverseHelp(IList<T> acc);

  boolean sameList(IComparator<T> comp, IList<T> other);

  boolean sameCons(IComparator<T> comp, ConsList<T> other);

  boolean sameMT(IComparator<T> comp, MtList<T> other);

  ABST<T> buildTree(ABST<T> tree);
}

interface IFunc2<Arg1, Arg2, Ret> {
  Ret apply2(Arg1 ifunc1, Arg2 ifunc2);
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  public <U> U foldl(IFunc2<U, T, U> func, U base) {
    return this.rest.foldl(func, func.apply2(base, this.first));
  }

  @Override
  public IList<T> reverse() {
    return this.reverseHelp(new MtList<T>());
  }

  @Override
  public IList<T> reverseHelp(IList<T> acc) {
    return this.rest.reverseHelp(new ConsList<T>(this.first, acc));
  }

  @Override
  public boolean sameList(IComparator<T> comp, IList<T> other) {
    return other.sameCons(comp, this);
  }

  @Override
  public boolean sameCons(IComparator<T> comp, ConsList<T> other) {
    return comp.compare(this.first, other.first) == 0;
  }

  @Override
  public boolean sameMT(IComparator<T> comp, MtList<T> other) {
    return false;
  }

  @Override
  public ABST<T> buildTree(ABST<T> tree) {
    return this.rest.buildTree(tree.insert(this.first));
  }
}

class MtList<T> implements IList<T> {
  public <U> U foldl(IFunc2<U, T, U> func, U base) {
    return base;
  }

  @Override
  public IList<T> reverse() {
    return this;
  }

  @Override
  public IList<T> reverseHelp(IList<T> acc) {
    return acc;
  }

  @Override
  public boolean sameList(IComparator<T> comp, IList<T> other) {
    return other.sameMT(comp, this);
  }

  @Override
  public boolean sameCons(IComparator<T> comp, ConsList<T> other) {
    return false;
  }

  @Override
  public boolean sameMT(IComparator<T> comp, MtList<T> other) {
    return true;
  }

  @Override
  public ABST<T> buildTree(ABST<T> tree) {
    return tree;
  }
}

class ExamplesABST {
  IComparator<Book> byTitle = new BooksByTitle();
  IComparator<Book> byAuthor = new BooksByAuthor();
  IComparator<Book> byPrice = new BooksByPrice();

  Book mybook = new Book("My Book", "Henry", 10);
  Book macbook = new Book("MacBook", "Apple", 1000);
  Book gatsby = new Book("Great Gatsby", "Fitzgerald", 20);
  Book mocking = new Book("To Kill a Mockingbird", "Lee", 15);

  ABST<Book> leaf1 = new Leaf<Book>(byTitle);
  ABST<Book> leaf2 = new Leaf<Book>(byAuthor);
  ABST<Book> leaf3 = new Leaf<Book>(byPrice);
  Leaf<Book> leaf4 = new Leaf<Book>(byTitle);

  ABST<Book> node1 = new Node<Book>(byTitle, mybook, leaf1, leaf1);
  Node<Book> node11 = new Node<Book>(byTitle, mybook, leaf1, leaf1);
  ABST<Book> node2 = new Node<Book>(byTitle, macbook, node1, leaf1);
  ABST<Book> node3 = new Node<Book>(byTitle, gatsby, node2, leaf1);
  ABST<Book> node31 = new Node<Book>(byTitle, gatsby, leaf1, node2);

  ABST<Book> node4 = new Node<Book>(byAuthor, mybook, leaf1, leaf1);
  ABST<Book> node5 = new Node<Book>(byAuthor, macbook, node1, leaf1);
  ABST<Book> node6 = new Node<Book>(byAuthor, gatsby, node2, leaf1);

  ABST<Book> node7 = new Node<Book>(byPrice, mybook, leaf1, leaf1);
  ABST<Book> node8 = new Node<Book>(byPrice, macbook, node1, leaf1);
  ABST<Book> node9 = new Node<Book>(byPrice, gatsby, node2, leaf1);

  boolean testComparators(Tester t) {
    /* by title */
    return t.checkExpect(node1.order.compare(mybook, mybook), 0)
        && t.checkExpect(node2.order.compare(gatsby, macbook), -6)
        && t.checkExpect(node2.order.compare(macbook, gatsby), 6)

        /* by author */
        && t.checkExpect(node4.order.compare(mybook, gatsby), 2)
        && t.checkExpect(node4.order.compare(mybook, mybook), 0)
        && t.checkExpect(node4.order.compare(gatsby, mocking), -6)

        /* by price */
        && t.checkExpect(node7.order.compare(mybook, gatsby), -10)
        && t.checkExpect(node7.order.compare(mybook, mybook), 0)
        && t.checkExpect(node7.order.compare(macbook, mybook), 990);
  }

  boolean testInsert(Tester t) {
    return t.checkExpect(node1.insert(mocking),
        new Node<Book>(byTitle, mybook, leaf1, new Node<Book>(byTitle, mocking, leaf1, leaf1)))
        && t.checkExpect(node2.insert(gatsby),
            new Node<Book>(byTitle, macbook,
                new Node<Book>(byTitle, mybook, new Node<Book>(byTitle, gatsby, leaf1, leaf1),
                    leaf1),
                leaf1))
        && t.checkExpect(node1.insert(mocking),
            new Node<Book>(byTitle, mybook, leaf1, new Node<Book>(byTitle, mocking, leaf1, leaf1)))
        && t.checkExpect(node3.insert(mocking),
            new Node<Book>(byTitle, gatsby, node2, new Node<Book>(byTitle, mocking, leaf1, leaf1)))
        && t.checkExpect(leaf1.insert(mocking), new Node<Book>(byTitle, mocking, leaf1, leaf1));
  }

  boolean testGetLeftmost(Tester t) {
    return t.checkException(new RuntimeException("No leftmost item of an empty tree"),
        new Leaf<Book>(byTitle), "getLeftmost") && t.checkExpect(node1.getLeftmost(), mybook)
        && t.checkExpect(node2.getLeftmost(), mybook)
        && t.checkExpect(node31.getLeftmost(), gatsby);
  }

  boolean testGetLeftmostHelp(Tester t) {
    return t.checkExpect(node1.getLeftmostHelp(mybook), mybook)
        && t.checkExpect(node31.getLeftmostHelp(mybook), gatsby)
        && t.checkExpect(node2.getLeftmostHelp(macbook), mybook)
        && t.checkExpect(leaf1.getLeftmostHelp(macbook), macbook);
  }

  boolean testGetRight(Tester t) {
    return t.checkExpect(node1.getRight(), leaf1)
        && t.checkExpect(node2.getRight(), new Node<Book>(byTitle, macbook, leaf1, leaf1))
        && t.checkExpect(node31.getRight(), node2)
        && t.checkExpect(node3.getRight(),
            new Node<Book>(byTitle, gatsby, new Node<Book>(byTitle, macbook, leaf1, leaf1), leaf1))
        && t.checkException(new RuntimeException("No right of an empty tree"),
            new Leaf<Book>(byTitle), "getRight");
  }

  boolean testRemoveLeft(Tester t) {
    return t.checkExpect(node1.removeLeft(mybook, leaf1), node1)
        && t.checkExpect(node2.removeLeft(macbook, leaf1),
            new Node<Book>(byTitle, macbook, new Node<Book>(byTitle, macbook, leaf1, leaf1), leaf1))
        && t.checkExpect(leaf1.removeLeft(mybook, leaf1), leaf1);
  }

  boolean testSameTree(Tester t) {
    return t.checkExpect(node1.sameTree(node1), true) && t.checkExpect(node1.sameTree(node2), false)
        && t.checkExpect(node1.sameTree(node4), true) && t.checkExpect(leaf1.sameTree(node4), false)
        && t.checkExpect(leaf1.sameTree(leaf1), true);
  }

  boolean testSameLeaf(Tester t) {
    return t.checkExpect(leaf4.sameLeaf(leaf4), true) && t.checkExpect(leaf1.sameLeaf(leaf4), true)
        && t.checkExpect(node1.sameLeaf(leaf4), false)
        && t.checkExpect(node2.sameLeaf(leaf4), false);
  }

  boolean testSameNode(Tester t) {
    return t.checkExpect(node1.sameNode(node11), true)
        && t.checkExpect(node2.sameNode(node11), false)
        && t.checkExpect(leaf1.sameNode(node11), false);
  }
}