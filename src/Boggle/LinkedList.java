package Boggle;

public class LinkedList<T> implements Iterable<T>
{
    public class SingleNode<T>
    {
        T element;
        T[] elem;
        SingleNode<T> next;
        SingleNode<T> current;

        public SingleNode(){ this(null); }

        public SingleNode(T e){ this.element = e; this.next=null; }

        public SingleNode(T e, SingleNode<T> n){ this.element = e; this.next = n; }

        public SingleNode(T[] e, SingleNode<T> n){ this.elem = e; this.next = n; }

        public T element() { return element; }

        public void setElement(T e, int po[][]){ this.element=e;}

        public void setNext(SingleNode<T> n){ this.next = n; }

        public SingleNode<T> getNext(){ return next; }

        public void setPRev(SingleNode<T> p){ this.current = p; }

        public SingleNode<T> getPrev(){ return current; }
    }

    private SingleNode<T> header;

    private int theSize;

    public LinkedList(){ header = new SingleNode<>(); theSize=0; }

    public java.util.Iterator<T> iterator() { return new LinkedListIterator<>(header.next); }

    public int size(){ return theSize; }

    public boolean isEmpty(){ return theSize==0; }

    public SingleNode<T> header(){ return header; }

    public void add(T x){

        if(theSize==0)
            this.add(header,x);
        else
            this.add(this.getNode(theSize-1), x);
    }


    public void add(int ind, T x)
    {
        this.add(this.getNode(ind-1),x);
    }


    void add(SingleNode<T> prev, T x)
    {
        SingleNode<T> newNode = new SingleNode<T>(x,prev.getNext());
        prev.setNext(newNode);
        newNode.setPRev(prev);
        theSize++;
    }

    public void remove(int ind)
    {
        remove(getNode(ind-1));
    }

    void remove(SingleNode<T> prev)
    {
        prev.setNext(prev.getNext().getNext());
        prev.getNext().getNext().setPRev(prev);
        theSize--;
    }

    public SingleNode<T> findPrevious(T x)
    {
        SingleNode<T> p = header();
        for(T v:this)
        {
            if (v.equals(x)) return p;
            else p = p.getNext();
        }
        throw new java.util.NoSuchElementException( );
    }


    public void remove(T x)
    {
        try
        {
            remove(findPrevious(x));
        }
        catch(java.util.NoSuchElementException e){};
    }

    public void deleteLastNode() // função para eliminar o último nó da lista
    {
        SingleNode currentNode = this.header;
        if(theSize == 1)
        {
            header = null;
            theSize = 0;
        }
        else
            {
                SingleNode prevNode = null;
                while (currentNode.next != null)
                {
                    prevNode = currentNode;
                    currentNode = currentNode.next;
                }
                prevNode.next = null;
                this.theSize--;
            }
    }

    public void set(int indx, T x, int po[][])
    {
        getNode(indx).setElement(x, po);
    }

    public T get(int ind)
    {
        if (ind >=0 && ind<=size()-1)
            return getNode(ind).element();
        else
            throw new IndexOutOfBoundsException( "getNodeindex: " + ind + "; size: " + size( ) );

    }

    public SingleNode<T> getNode(int i)
    {
        int ind=-1;
        SingleNode<T> s = header();
        while(ind++<i)
            s=s.getNext();

        return s;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( T x : this )
            sb.append("(" + x + ")-->");

        sb.append( "]" );

        return new String( sb );
    }

    public String toString2() // função que irá dar print de toda a lista contendo todas as posiçoes dos caracteres que constroem a palavra
    {
        String retStr = "[ ";
        SingleNode<T> current = header;
        current = current.getNext();
        int i = 0 ;
        while(current!=null)
        {
            if(i == 0)
            {
                retStr += current.element ;
                current = current.getNext();
                ++i;
            }
            else if(i == size())
            {
                retStr += " --> " + current.element ;
                current = current.getNext();}

            else
                {
                    retStr += " --> " + current.element ;
                    current = current.getNext();
                }

            ++i;
        }
        retStr += " ]";
        return retStr;
    }


    public class LinkedListIterator <E> implements java.util.Iterator<E>
    {
        private SingleNode<E> current;

        public LinkedListIterator(SingleNode<E> c)
        {
            current=c;
        }

        public boolean hasNext()
        {
            return current!=null;
        }

        public E next()
        {
            if(!hasNext())
                throw new java.util.NoSuchElementException( );

            E nextItem = current.element();
            current = current.getNext();
            return nextItem;
        }
    }
}

