package Boggle;

public class LinearHashTable<T> extends HashTable<T>
{
    //Hastable Linear

    LinearHashTable(){ super(); }

    LinearHashTable(int size){ super(size); }

    public int procPos(T s)
    {
        int position = Math.abs(s.hashCode()) % array.length;

        if(array[position]==null)
            return position;

        else
        {
            while(!(array[position]==null))
            {
                if(!array[position].isActive())
                    return position;

                if(array[position].isActive())
                {
                    if(array[position].getValor().equals(s))
                        return position;

                    if(position==array.length-1)
                        position=0;

                    else
                        position++;
                }
            }
            return position;
        }
    }
}
