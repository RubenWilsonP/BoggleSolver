package Boggle;

import java.io.*;
import java.util.Scanner;
import java.util.*;

public class Boggle
{
    public static int MaxLinha = 4;
    public static int MaxColuna = 4;

    public Boggle() throws FileNotFoundException
    {
        resolveBoggle(lerBoggle(), Dicionario(), Prefix());
    }

    public void resolveBoggle(char[][] m, LinearHashTable<String> tab, LinearHashTable<String> prefix) // função que irá criar uma Hashtable onde vao ser guardadas as soluções
    {
        print2D(m);

        LinearHashTable<String> solutions = new LinearHashTable<String>(33);

        procuraPal(m, tab, solutions, prefix);
    }

    public static void adicionaSol( String str, String temp, int i, int j, int al, int ac, LinearHashTable solutions) // função que irá introduzir no buffer as soluções
    {
        if(temp == "" && (solutions.getElement2(str,al,ac) == null || solutions.getElement2(str,al,ac).words.size() == 0))
        {
            solutions.inseres(str, al, ac);
            Position p = new Position(str.charAt(0), i, j);
            solutions.inserepo(str, al, ac, p);
        }

        else if(solutions.getElement2(temp,al,ac) == null)
        {
            solutions.getElement2(temp+str.substring(str.length()-1),al,ac).valor = temp;
            solutions.getElement2(temp,al,ac).words.deleteLastNode();
        }
        else
        {
            if(temp == "")
                temp = str;

            solutions.getElement2(temp, al, ac).valor = str;
            Position p = new Position(str.charAt(str.length() - 1), i, j);
            solutions.inserepo(str, al, ac, p);
        }
    }

    public static boolean contains(StringBuilder sb, String findString) // função para saber se o buffer contem uma determinada palavra
    {
        return sb.indexOf(findString) > -1;
    }

    static void procuraPalRec(StringBuilder sb, String str, char boggle[][], boolean visitados[][], int i, int j, int al, int ac, LinearHashTable<String> dictionary, LinearHashTable<String> solutions, LinearHashTable<String> prefx) // função que irá apartir de uma posição da matriz (posição esta proveniente da função procuraPal()) construir todas as palavras existentes desde essa mesma posição
    {
        visitados[i][j] = true;
        String temp = str;
        str = str + boggle[i][j];

        if(temp == "")
        {
            al = i;
            ac = j;
        }

        adicionaSol(str, temp, i, j, al, ac, solutions);

        if (dictionary.procurar(str) == str)
        {
            if(contains(sb," " + str + " ") == false)
            {
                System.out.println(str + " " + solutions.getElement(str).words.toString2());
                sb.append(" " + str + " ");
            }
        }

        if(prefx.procurar(str) == str)
        {
            for (int linha = i - 1; linha <= i + 1 && linha < MaxLinha; linha++)
                for (int col = j - 1; col <= j + 1 && col < MaxColuna; col++)
                    if (linha >= 0 && col >= 0 && !visitados[linha][col])
                        procuraPalRec(sb, str, boggle, visitados, linha, col, al, ac, dictionary, solutions, prefx);
        }

        str = "" + str.charAt(str.length() - 1);
        visitados[i][j] = false;
        adicionaSol(str, temp, i, j, al, ac, solutions);
    }


    static void procuraPal(char boggle[][], LinearHashTable<String> dictionary, LinearHashTable<String> solutions, LinearHashTable<String> prefx) // função que irá iniciar e percorrer todas as posições da matriz para construir as palavras
    {
        boolean visitados[][] = new boolean[MaxLinha][MaxColuna];

        String str = ""; // vai se introduzindo os caracteres ao longo que é percorrida a matriz
        StringBuilder sb = new StringBuilder(); // vai conter as palavras encontradas que existem no dicionário e que já foram dadas no stdop

        for (int i = 0; i < MaxLinha; i++)
            for (int j = 0; j < MaxColuna; j++)
                procuraPalRec(sb, str, boggle, visitados, i, j, i, j, dictionary, solutions, prefx);
    }
    
    public static void print2D(char mat[][]) // função para dar print da matrix no standard output
    {
        for (char[] row : mat)
            System.out.println(Arrays.toString(row));
    }

    public class CreateFile {
    public void main(String[] args) {
        try {
            File file = new File("filename.txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

    public char[][] lerBoggle() throws FileNotFoundException // função que le um ficheiro para construir a matriz returnando a mesma no final
    {
        File textfile = new File("C:\\Users\\ruben pinheiro\\Desktop\\Universidade\\2º Ano - Engenharia Informática\\1º Semestre\\EDA I\\Boggle2\\src\\Boggle/matriz.txt");
        Scanner scn = new Scanner(textfile);

        char [][] m = new char [MaxLinha][MaxColuna];

        while (scn.hasNextLine())
            for (int i = 0 ; i < MaxLinha ; i++)
                for (int j = 0 ; j < MaxColuna ; j++)
                    m[i][j]= scn.next().charAt(0);

        scn.close();
        return m;
    }

    public LinearHashTable<String> Prefix()  throws FileNotFoundException // função que irá introduzir todos os prefixos das palavras numa Hashtable , dando o return da mesma no final
    {
        LinearHashTable<String> prefx = new LinearHashTable<String>(116239);
        File file = new File("C:\\Users\\ruben pinheiro\\Desktop\\Universidade\\2º Ano - Engenharia Informática\\1º Semestre\\EDA I\\Boggle2\\src\\Boggle\\dicionario.txt");
        Scanner sc = new Scanner(file);
        String word, prefix;
        while (sc.hasNextLine())
        {
            word =(sc.nextLine());
            for(int i = 0; i < word.length(); ++i)
            {
                prefix = word.substring(0, i);
                if(prefx.procurar(prefix)== null)
                    prefx.insere(prefix);
            }
        }
        sc.close();
        return prefx;
    }

    public LinearHashTable<String> Dicionario() throws FileNotFoundException // função que irá introduzir todas as palavras numa Hashtable , dando o return da mesma no final
    {
        LinearHashTable<String> tab = new LinearHashTable<String>(116239);
        File file = new File("C:\\Users\\ruben pinheiro\\Desktop\\Universidade\\2º Ano - Engenharia Informática\\1º Semestre\\EDA I\\Boggle2\\src\\Boggle\\dicionario.txt");
        Scanner sc = new Scanner(file);
        String word;
        while (sc.hasNextLine())
        {
            word =(sc.nextLine());
            if(tab.procurar(word)== null)
                tab.insere(word);
        }
        sc.close();
        return tab;
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        Boggle boggle = new Boggle();
    }
}
