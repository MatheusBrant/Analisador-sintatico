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

    public void scanner(){
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
        if (tagToken.match(Tag.RESTO1_IDENTIFICADOR)
        ||  tagToken.match(Tag.P9_IF)
        ||  tagToken.match(Tag.P14_WHILE)
        ||  tagToken.match(Tag.P12_REPEAT)
        ||  tagToken.match(Tag.P16_IN)
        ||  tagToken.match(Tag.P17_OUT)){
            stmt();
            stmt_novo();
        } else {
            throw tokenDesconhecido();
        }
    }

    private void stmt_novo() throws Exception{
        if(tagToken.match(Tag.PONT5_PONTO_E_VIRGULA)){
            eat(Tag.PONT5_PONTO_E_VIRGULA);
            stmt();
            stmt_novo();
        } else {
            throw tokenDesconhecido();
        }
    }

    private void stmt() throws Exception{
        if(tagToken.match(Tag.RESTO1_IDENTIFICADOR)){
            assign_stmt();
        } else if(tagToken.match(Tag.P9_IF)){
            if_stmt();
        } else if(tagToken.match(Tag.P14_WHILE)){
            while_stmt();
        } else if(tagToken.match(Tag.P12_REPEAT)){
            repeat_stmt();
        } else if(tagToken.match(Tag.P16_IN)){
            read_stmt();
        } else if(tagToken.match(Tag.P17_OUT)){
            write_stmt();
        } else{
            throw tokenDesconhecido();
        }
    }

    private void write_stmt() throws Exception{
        if(tagToken.match(Tag.P17_OUT)){
            eat(Tag.P17_OUT);
            eat(Tag.RESTO7_ESCREVER);
            writable();
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void writable() throws Exception{
        if(tagToken.match(Tag.RESTO1_IDENTIFICADOR)
        || tagToken.match(Tag.RESTO3_INTEGER_CONST)
        || tagToken.match(Tag.RESTO4_FLOAT_CONST)
        || tagToken.match(Tag.RESTO5_CHAR_CONST)
        || tagToken.match(Tag.PONT2_ABRE_PAR)
        || tagToken.match(Tag.OP6_DIFERENTE)
        || tagToken.match(Tag.OP8_MENOS)){
          simple_expr();
        } else if (tagToken.match(Tag.RESTO2_LITERAL)){
            eat(Tag.RESTO2_LITERAL);
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void read_stmt() throws Exception{
        if(tagToken.match(Tag.P16_IN)){
            eat(Tag.P16_IN);
            eat(Tag.RESTO6_LER);
            eat(Tag.RESTO1_IDENTIFICADOR);
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void repeat_stmt() throws Exception{
        if(tagToken.match(Tag.P12_REPEAT)){
            eat(Tag.P12_REPEAT);
            stmt_list();
            stmt_suffix();
        }
        else{
            throw tokenDesconhecido();
        }

    }

    private void stmt_suffix() throws Exception{
        if(tagToken.match(Tag.P13_UNTIL)) {
            eat(Tag.P13_UNTIL);
            condition();
        }
        else {
            throw tokenDesconhecido();
        }
    }

    private void while_stmt() throws Exception{
        if(tagToken.match(Tag.P14_WHILE)) {
           stmt_prefix();
           stmt_list();
           eat(Tag.P18_END_FINAL);
        }
        else {
            throw tokenDesconhecido();
        }
    }

    private void stmt_prefix() throws Exception{
        if(tagToken.match(Tag.P14_WHILE)) {
            eat(Tag.P14_WHILE);
            condition();
            eat(Tag.P15_DO);
        }
        else {
            throw tokenDesconhecido();
        }
    }

    private void condition() throws Exception{
        if(tagToken.match(Tag.RESTO1_IDENTIFICADOR)
        || tagToken.match(Tag.RESTO3_INTEGER_CONST)
        || tagToken.match(Tag.RESTO4_FLOAT_CONST)
        || tagToken.match(Tag.RESTO5_CHAR_CONST)
        || tagToken.match(Tag.PONT2_ABRE_PAR)
        || tagToken.match(Tag.OP6_DIFERENTE)
        || tagToken.match(Tag.OP8_MENOS)){
            expression();
        } 
        else{
            throw tokenDesconhecido();
        }
    }

    private void expression() throws Exception{
        if(tagToken.match(Tag.RESTO1_IDENTIFICADOR)
        || tagToken.match(Tag.RESTO3_INTEGER_CONST)
        || tagToken.match(Tag.RESTO4_FLOAT_CONST)
        || tagToken.match(Tag.RESTO5_CHAR_CONST)
        || tagToken.match(Tag.PONT2_ABRE_PAR)
        || tagToken.match(Tag.OP6_DIFERENTE)
        || tagToken.match(Tag.OP8_MENOS)){
            simple_expr();
            expression_novo();
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void expression_novo() throws Exception{
        if(tagToken.match(Tag.OP1_IGUAL_IGUAL)
        || tagToken.match(Tag.OP2_MAIOR_QUE)
        || tagToken.match(Tag.OP3_MAIOR_OU_IGUAL)
        || tagToken.match(Tag.OP4_MENOR_QUE)
        || tagToken.match(Tag.OP5_MENOR_OU_IGUAL)
        || tagToken.match(Tag.OP6_DIFERENTE)){
            relop();
            simple_expr();
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void relop() throws Exception{
        if(tagToken.match(Tag.OP1_IGUAL_IGUAL)){
            eat(Tag.OP1_IGUAL_IGUAL);
        } else if(tagToken.match(Tag.OP2_MAIOR_QUE)){
            eat(Tag.OP2_MAIOR_QUE);
        } else if(tagToken.match(Tag.OP3_MAIOR_OU_IGUAL)){
            eat(Tag.OP3_MAIOR_OU_IGUAL);
        } else if(tagToken.match(Tag.OP4_MENOR_QUE)){
            eat(Tag.OP4_MENOR_QUE);
        } else if(tagToken.match(Tag.OP5_MENOR_OU_IGUAL)){
            eat(Tag.OP5_MENOR_OU_IGUAL);
        } else if(tagToken.match(Tag.OP6_DIFERENTE)){
            eat(Tag.OP6_DIFERENTE);
        } else {
            throw tokenDesconhecido();
        }
    }

    private void if_stmt() throws Exception{
        if(tagToken.match(Tag.P9_IF)) {
            eat(Tag.P9_IF);
            condition();
            eat(Tag.P10_THEN);
            stmt_list();
            if_stmt_novo();
        }
        else {
            throw tokenDesconhecido();
        }
    }

    private void if_stmt_novo() throws Exception{
        if(tagToken.match(Tag.P18_END_FINAL)) {
            eat(Tag.P18_END_FINAL);
        } else if(tagToken.match(Tag.P11_ELSE)){
            eat(Tag.P11_ELSE);
            stmt_list();
            eat(Tag.P18_END_FINAL);
        }
        else {
            throw tokenDesconhecido();
        }
    }

    private void assign_stmt() throws Exception{
        if(tagToken.match(Tag.RESTO1_IDENTIFICADOR)) {
            eat(Tag.RESTO1_IDENTIFICADOR);
            eat(Tag.OP13_IGUAL);
            simple_expr();
        }
        else {
            throw tokenDesconhecido();
        }
    }

    private void simple_expr() throws Exception{
        if(tagToken.match(Tag.RESTO1_IDENTIFICADOR)
                || tagToken.match(Tag.RESTO3_INTEGER_CONST)
                || tagToken.match(Tag.RESTO4_FLOAT_CONST)
                || tagToken.match(Tag.RESTO5_CHAR_CONST)
                || tagToken.match(Tag.PONT2_ABRE_PAR)
                || tagToken.match(Tag.OP6_DIFERENTE)
                || tagToken.match(Tag.OP8_MENOS)){
            simple_expr_novo();
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void simple_expr_novo() throws Exception{
        if(tagToken.match(Tag.OP8_MENOS)
        || tagToken.match(Tag.OP7_MAIS)
        || tagToken.match(Tag.OP9_OU)){
            addop();
            term();
            simple_expr_novo();
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void addop() throws Exception{
        if(tagToken.match(Tag.OP7_MAIS)){
            eat(Tag.OP7_MAIS);
        } else if(tagToken.match(Tag.OP8_MENOS)){
            eat(Tag.OP8_MENOS);
        } else if(tagToken.match(Tag.OP9_OU)){
            eat(Tag.OP9_OU);
        }
        else {
            throw tokenDesconhecido();
        }
    }

    private void term() throws Exception{
        if(tagToken.match(Tag.RESTO1_IDENTIFICADOR)
                || tagToken.match(Tag.RESTO3_INTEGER_CONST)
                || tagToken.match(Tag.RESTO4_FLOAT_CONST)
                || tagToken.match(Tag.RESTO5_CHAR_CONST)
                || tagToken.match(Tag.PONT2_ABRE_PAR)
                || tagToken.match(Tag.OP6_DIFERENTE)
                || tagToken.match(Tag.OP8_MENOS)){
            factor_a();
            term_novo();
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void term_novo() throws Exception{
        if(tagToken.match(Tag.OP10_VEZES)
                || tagToken.match(Tag.OP11_DIVISAO)
                || tagToken.match(Tag.OP12_E)){
            mulop();
            factor_a();
            term_novo();
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void mulop() throws Exception{
        if(tagToken.match(Tag.OP10_VEZES)){
            eat(Tag.OP10_VEZES);
        } else if(tagToken.match(Tag.OP11_DIVISAO)){
            eat(Tag.OP11_DIVISAO);
        } else if(tagToken.match(Tag.OP12_E)){
            eat(Tag.OP12_E);
        }
        else {
            throw tokenDesconhecido();
        }
    }

    private void factor_a() throws Exception{
        if(tagToken.match(Tag.RESTO1_IDENTIFICADOR)
                || tagToken.match(Tag.RESTO3_INTEGER_CONST)
                || tagToken.match(Tag.RESTO4_FLOAT_CONST)
                || tagToken.match(Tag.RESTO5_CHAR_CONST)
                || tagToken.match(Tag.PONT2_ABRE_PAR)){
            factor();
        } else if(tagToken.match(Tag.OP6_DIFERENTE)){
            eat(Tag.OP6_DIFERENTE);
            factor();
        } else if(tagToken.match(Tag.OP8_MENOS)){
            eat(Tag.OP8_MENOS);
            factor();
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void factor() throws Exception{
        if(tagToken.match(Tag.RESTO1_IDENTIFICADOR)){
            eat(Tag.RESTO1_IDENTIFICADOR);
        } else if(tagToken.match(Tag.RESTO3_INTEGER_CONST)
        || tagToken.match(Tag.RESTO4_FLOAT_CONST)
        || tagToken.match(Tag.RESTO5_CHAR_CONST)){
            constant();
        } else if(tagToken.match(Tag.PONT2_ABRE_PAR)){
            eat(Tag.PONT2_ABRE_PAR);
            expression();
            eat(Tag.PONT3_FECHA_PAR);
        }
        else{
            throw tokenDesconhecido();
        }
    }

    private void constant() throws Exception{
        if(tagToken.match(Tag.RESTO3_INTEGER_CONST)){
            eat(Tag.RESTO3_INTEGER_CONST);
        } else if(tagToken.match(Tag.RESTO4_FLOAT_CONST)){
            eat(Tag.RESTO4_FLOAT_CONST);
        } else if(tagToken.match(Tag.RESTO5_CHAR_CONST)){
            eat(Tag.RESTO5_CHAR_CONST);
        }
        else{
            throw tokenDesconhecido();
        }
    }

}
