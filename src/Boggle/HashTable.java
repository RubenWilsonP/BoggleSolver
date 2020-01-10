package Boggle;

public abstract class HashTable<T>
{
    //Implementação da Hashtable

    public Elemento[] array;
    private static final int SIZE= 23;
    private int ocupados=0;

    public HashTable(){ array = new Elemento[SIZE]; }

    public HashTable(int n){ array = new Elemento[n]; }

    public int ocupados(){ return ocupados; }

    public float factorCarga(){ return (float)(ocupados)/array.length; }

    protected abstract int procPos(T s);

    public void alocarTabela(int dim)
    {
        array = new Elemento[dim];
        ocupados=0;
    }

    public void tornarVazia()
    {
        for(int i=0;i<array.length;i++)
        {
            array[i]=null;
        }
    }

    public T procurar(T x){

        if(array[procPos(x)]!=null && array[procPos(x)].getValor().equals(x) && array[procPos(x)].isActive())
            return x;

        else
            return null;
    }

    public void remove(T x)
    {
        if(array[procPos(x)]!=null)
            array[procPos(x)].setState(false);
    }

    public Elemento getElement(T x)
    {
        int hash = (x.hashCode()%array.length);

        if (hash<0)
            hash+=array.length;

        try
        {
            if (array[hash].valor==x)
                return array[hash];
        }
        catch (NullPointerException e)
        {
            for (int k=0; k<array.length;k++)
                if (array[k]!=null)
                    if (array[k].valor.equals(x))
                        return array[k];
        }
        for (int k=0; k < SIZE;k++)
            if (array[k]!=null)
                if (array[k].valor.equals(x))
                    return array[k];

        return null;
    }

    public Elemento getElement2(T x, int l, int c)
    {
        int hash = (x.hashCode()%array.length);
        if (hash<0)
            hash+=array.length;

        try
        {
            if (array[hash].valor==x && array[hash].linha==l && array[hash].coluna==c)
                return array[hash];
        }
        catch (NullPointerException e)
        {
            for (int k=0; k<array.length;k++)
                if (array[k]!=null)
                    if (array[k].valor.equals(x) && array[k].linha==l && array[k].coluna==c)
                        return array[k];
        }
        for (int k=0; k < SIZE;k++)
            if (array[k]!=null)
                if (array[k].valor.equals(x) && array[k].linha==l && array[k].coluna==c)
                    return array[k];

        return null;
    }

    public void insere(T x)
    {
        int posiçao = procPos(x);

        if(array[posiçao]!=null && array[posiçao].isActive())
            return;

        else
        {
            array[posiçao]=new Elemento<T>(x);
            ocupados++;
            if(factorCarga() >= 0.5)
                rehash();
        }
    }

    public void inseres(T x, int l, int c)
    {
        int hash = procPos(x);
        Elemento<T> e = new Elemento<T>(hash, x, l, c);
        array[hash] = e;
        ocupados++;
        if (factorCarga() > 0.75f)
            rehash();
    }

    public void inserepo (T x, int l, int c ,Position p)
    {
        array[getElement2(x,l,c).key].words.add(p.toString());
    }

    public void rehash(){

        Elemento<T>[] temp = array;
        alocarTabela(nextPrime(array.length*2));
        ocupados = 0;
        for(int i = 0; i < temp.length; i++)
        {
            if(temp[i] != null && temp[i].isActive())
                insere(temp[i].getValor());
        }
    }

    public void print()
    {
        for(int i=0;i<array.length;i++)
        {
            if(array[i]!=null)
                System.out.println(array[i].getValor());

            else
                System.out.println("null");
        }
    }

    public boolean isPrime(int num)
    {
        if(num==2)
            return true;
        if(num % 2==0)
            return false;

        for(int i=3; i*i<num; i+=2)
        {
            if(num%i==0)
                return false;
        }
        return true;
    }

    public int nextPrime(int num)
    {
        int result=num;
        while(!isPrime(result))
            result++;

        return result;
    }

    public int prevPrime(int num)
    {
        int result =num;
        while(!isPrime(num-1))
            result--;

        return result;
    }

    public class Elemento<T>
    {
        //Implementa o elemento da tabela.
        T valor;
        private boolean state;
        int key, linha, coluna;
        LinkedList<String> words;

        Elemento(T x)
        {
            this.valor=x;
            this.state=true;
        }
        Elemento (int key,  T data, int l, int c)
        {
            this.key = key;
            this.valor = data;
            this.linha = l;
            this.coluna = c;
            this.words = new LinkedList<String>();
        }

        public void setValor(T elemento){ valor = elemento; }

        public void setState(boolean valor){ state = valor; }

        public T getValor(){ return valor; }

        public boolean isActive(){ return state; }

    }
}