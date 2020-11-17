package compilador.src.main_sintatico;

import compilador.src.analisador_lexico.*;
import compilador.src.analisador_sintatico.*;

import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {

        String fileName = "teste1.txt";

        Lexer lutor = new Lexer(fileName);
        sintatico analisadorSintatico = new sintatico(lutor);

        analisadorSintatico.scanner();

    }

}
