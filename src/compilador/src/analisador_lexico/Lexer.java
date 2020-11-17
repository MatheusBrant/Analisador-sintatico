package compilador.src.analisador_lexico;

import java.io.*;
import java.util.*;

public class Lexer {

    public static int line = 1; //contador de linhas
    public static int total_de_linhas = 1; //contador de linhas
    private char ch = ' '; //caractere lido do arquivo
    private final FileReader file;
    private final Hashtable<String, Word> words;
    {
        words = new Hashtable<>();
    }

    private void reserve(Word w){
        words.put(w.getLexema(), w);
    }

    public Lexer(String fileName) throws FileNotFoundException {
        try {
            file = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
            throw e;
        }
        reserve(new Word("program",Tag.P1_PROGRAM));
        reserve(new Word("is",Tag.P2_IS));
        reserve(new Word("declare",Tag.P3_DECLARE));
        reserve(new Word("init",Tag.P4_INIT));
        reserve(new Word("end",Tag.P5_END));
        reserve(new Word("int",Tag.P6_INT));
        reserve(new Word("float",Tag.P7_FLOAT));
        reserve(new Word("char",Tag.P8_CHAR));
        reserve(new Word("if",Tag.P9_IF));
        reserve(new Word("then",Tag.P10_THEN));
        reserve(new Word("else",Tag.P11_ELSE));
        reserve(new Word("repeat",Tag.P12_REPEAT));
        reserve(new Word("until",Tag.P13_UNTIL));
        reserve(new Word("while",Tag.P14_WHILE));
        reserve(new Word("do",Tag.P15_DO));
        reserve(new Word("in",Tag.P16_IN));
        reserve(new Word("out",Tag.P17_OUT));
        reserve(new Word("end.",Tag.P18_END_FINAL));

        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        total_de_linhas = lines;
    }
    private void readch() throws IOException{
        ch = (char) file.read();
    }

    private boolean readch(char c) throws IOException{
        readch();
        if (ch != c) return false;
        ch = ' ';
        return true;
    }
    public Token scan() throws IOException {
        //Desconsidera delimitadores na entrada
        for (;; readch()) {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') continue;
            else if (ch == '\n') line++; //conta linhas
            else break;
        }
        switch(ch){
            //Operadores
            case '&':
                if (readch('&')) {
                    return Word.e;
                }
            case '|':
                if (readch('|')){
                    return Word.ou;
                }
            case '=':
                if (readch('=')) {
                    return Word.igual_igual;
                }else {
                    return Word.igual;
                }
            case '<':
                if (readch('=')){
                    return Word.menor_ou_igual;
                } else if (ch == '<') {
                    readch();
                    return Word.ler;
                } else {
                    return Word.menor_que;
                }
            case '>':
                if (readch('=')){
                    return Word.maior_ou_igual;
                } else if (ch =='>') {
                    readch();
                    return Word.escrever;
                } else {
                    return Word.maior_que;
                }
            case '!':
                if (readch('=')){
                    return Word.diferente;
                } else {
                    return Word.negacao;
                }
            case '+':
                readch();
                return Word.mais;
            case '-':
                readch();
                return Word.menos;
            case '*':
                readch();
                return Word.vezes;
            case '/':
                if(readch('*')){
                    readch();
                    lerComentario();
                } else {
                    readch();
                    return Word.divisao;
                }
            case '.':
                readch();
                return Word.ponto;
            case '(':
                readch();
                return Word.abre_par;
            case ')':
                readch();
                return Word.fecha_par;
            case ',':
                readch();
                return Word.virgula;
            case ';':
                readch();
                return Word.ponto_e_virgula;
            case ':':
                readch();
                return Word.dois_pontos;

        }
        //Números
        if (Character.isDigit(ch)){
            int value=0;
            boolean isFloat = false;
            do{
                value = 10*value + Character.digit(ch,10);
                readch();
            }while(Character.isDigit(ch));

            if(ch == '.') {
                isFloat = true;
                do {
                    value = 10 * value + Character.digit(ch, 10);
                    readch();
                } while (Character.isDigit(ch));
            }

            if(isFloat) {
                return new FloatConst(Tag.RESTO4_FLOAT_CONST, Float.valueOf(value));
            } else {
                return new IntegerConst(Tag.RESTO3_INTEGER_CONST, value);
            }

        }

        //Identificadores
        if (Character.isLetter(ch)){
            StringBuilder sb = new StringBuilder();
            do{
                sb.append(ch);
                readch();
            }while(Character.isLetterOrDigit(ch));
            String s = sb.toString();
            Word w = words.get(s);
            if (w != null) return w; //palavra já existe na HashTable
            readch();
            w = new Word (s, Tag.RESTO1_IDENTIFICADOR);
            words.put(s, w);
            return w;
        }

        if(ch == '"') {
            StringBuilder sb = new StringBuilder();

            do{
                sb.append(ch);
                readch();
            }while(ch != '"');
            sb.append(ch);
            String s = sb.toString();
            readch();
            return new Literal (Tag.RESTO2_LITERAL, s);
        }

        if(ch == '\'') {
            readch();
            char s =  ch;
            readch();
            if(ch == '\'') {
                readch();
                return new CharConst (Tag.RESTO5_CHAR_CONST, s);

            }
        }

        if(line >= total_de_linhas) {
            //Caracteres não especificados
            Token t = Word.fim_do_arquivo;
            return t;
        }
        //Caracteres não especificados
        Token t = Word.invalido;
        ch = ' ';
        return t;

    }

    private void lerComentario() throws IOException {
        boolean pararLoop = true;
        do {
            readch();
            if(ch == '*') {
                readch();
                if(ch == '/') {
                    do {
                        readch();
                    }while(!Character.isLetterOrDigit(ch));
                }
                pararLoop = false;
            }
        } while(pararLoop);
    }

    public void imprimirTabeladeSimbolos() {
        words.entrySet().forEach( entry -> {
            System.out.println( entry.getKey() + "->" + entry.getValue() );
        });
    }
}

