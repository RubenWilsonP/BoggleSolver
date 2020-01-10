package Boggle;

public class Position{

    char letra;
    int linha, coluna;

    public Position()
    {
        this.letra = letra;
        this.linha = linha;
        this.coluna = coluna;
    }

    public Position(char letra, int l, int c)
    {
        this.letra = letra;
        this.linha = l;
        this.coluna = c;
    }

    public String getChar(){
        return String.valueOf(this.letra);
    }

    public int getLinha(){
        return this.linha;
    }

    public int getColuna(){
        return this.coluna;
    }

    public String toString(){
        return letra + ":" + "(" + linha + "," + coluna + ")";
    }
}


