package compilador.src.main_lexico;

import compilador.src.analisador_lexico.Lexer;
import compilador.src.analisador_lexico.Tag;
import compilador.src.analisador_lexico.Token;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "teste1.txt";
        Lexer lutor = new Lexer(fileName);

        System.out.println("Lista de tokens:");
        boolean pararLoop = false;
        while(pararLoop == false) {
            Token t = lutor.scan();
            System.out.println("<"+ t + "," + t.tagToken + ">");
            if(t.tagToken == Tag.RESTO9_FIM_ARQUIVO) {
                pararLoop = true;
            }
        }

        System.out.println("\n");
        System.out.println("Tabela de símbolos:");
        lutor.imprimirTabeladeSimbolos();
    }
}
