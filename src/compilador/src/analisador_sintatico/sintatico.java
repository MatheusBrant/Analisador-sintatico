package compilador.src.analisador_sintatico;

import compilador.src.analisador_lexico.*;
import java.io.IOException;

public class sintatico {

    public Lexer lexema;
    public Token tagToken;

    public sintatico(Lexer lexema) throws IOException {
        this.lexema = lexema;
        this.tagToken = lexema.scan();
    }

    private Exception tokenDesconhecido(){
        return new Exception("Token desconhecido");
    }

    void scanner(){
        try {
            tagToken = lexema.scan();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void eat(Tag tagName) throws Exception {
        if (tagToken.match(tagName)) {
            try {
                scanner();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw tokenDesconhecido();
        }
    }
    
    void start() throws Exception{
        programNovo();
        System.out.println("\nFim da análise.\n");
    }

    private void programNovo() throws Exception {
        if(tagToken.match(Tag.P1_PROGRAM)){
            program_main();
            body();
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void program_main() throws Exception{
        if (tagToken.match(Tag.P1_PROGRAM)) {
            eat(Tag.P1_PROGRAM);
            eat(Tag.RESTO1_IDENTIFICADOR);
            eat(Tag.P2_IS);
            body();
        } else {
            throw tokenDesconhecido();
        }
    }

    private void body() throws Exception{
        if (tagToken.match(Tag.P3_DECLARE)) {
            eat(Tag.P3_DECLARE);
            decl_list();
            eat(Tag.P4_INIT);
            stmt_list();
            eat(Tag.P18_END_FINAL);
        }
        else if(tagToken.match(Tag.P4_INIT)){
            stmt_list();
            eat(Tag.P18_END_FINAL);
        } else {
            throw tokenDesconhecido();
        }
    }

    private void decl_list() throws Exception{
        if (tagToken.match(Tag.RESTO1_IDENTIFICADOR)){
            decl();
            decl_novo();
        } else {
            throw tokenDesconhecido();
        }
    }

    private void decl() throws Exception{
        if (tagToken.match(Tag.RESTO1_IDENTIFICADOR)){
            ident_list();
            eat(Tag.PONT6_DOIS_PONTOS);
            type();
        } else {
            throw tokenDesconhecido();
        }
    }

    private void type() throws Exception{
        if(tagToken.match(Tag.P6_INT)){
            eat(Tag.P6_INT);
        } else if(tagToken.match(Tag.P7_FLOAT)){
            eat(Tag.P7_FLOAT);
        } else if(tagToken.match(Tag.P8_CHAR)){
            eat(Tag.P8_CHAR);
        } else{
            throw tokenDesconhecido();
        }

    }

    private void ident_list() throws Exception{
        if (tagToken.match(Tag.RESTO1_IDENTIFICADOR)){
            eat(Tag.RESTO1_IDENTIFICADOR);
            ident_novo();
        } else {
            throw tokenDesconhecido();
        }
    }

    private void ident_novo() throws Exception{
        if(tagToken.match(Tag.PONT4_VIRGULA)){
            eat(Tag.PONT4_VIRGULA);
            eat(Tag.RESTO1_IDENTIFICADOR);
            ident_novo();
        } else{
            throw tokenDesconhecido();
        }
    }

    private void decl_novo() throws Exception{
        if(tagToken.match(Tag.PONT5_PONTO_E_VIRGULA)){
            eat(Tag.PONT5_PONTO_E_VIRGULA);
            decl_novo();
        } else{
            throw tokenDesconhecido();
        }
    }

    private void stmt_list() throws Exception{
        if (tagToken.match(Tag.RESTO1_IDENTIFICADOR)){
            stmt();
            stmt_novo();
        } else {
            throw tokenDesconhecido();
        }
    }

    private void stmt_novo() throws Exception{
    }

    private void stmt() throws Exception{

    }

}
