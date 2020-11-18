package compilador.src.analisador_lexico;

import java.io.IOException;

public class Word extends Token {

    private String lexema = "";

    public Word(String s,Tag tag) {
        super(tag);
        this.lexema = s;
    }

    public static final Word program = new Word("program",Tag.P1_PROGRAM);
    public static final Word is = new Word("is",Tag.P2_IS);
    public static final Word declare = new Word("declare",Tag.P3_DECLARE);
    public static final Word init = new Word("begin",Tag.P4_INIT);
    public static final Word end = new Word("end",Tag.P5_END);
    public static final Word intVar = new Word("int",Tag.P6_INT);
    public static final Word floatVar = new Word("float",Tag.P7_FLOAT);
    public static final Word charVar = new Word("char",Tag.P8_CHAR);
    public static final Word ifOp = new Word("if",Tag.P9_IF);
    public static final Word then = new Word("then",Tag.P10_THEN);
    public static final Word elseOp = new Word("else",Tag.P11_ELSE);
    public static final Word repeat = new Word("repeat",Tag.P12_REPEAT);
    public static final Word until = new Word("until",Tag.P13_UNTIL);
    public static final Word whileOp = new Word("while",Tag.P14_WHILE);
    public static final Word doOp = new Word("do",Tag.P15_DO);
    public static final Word in = new Word("in",Tag.P16_IN);
    public static final Word out = new Word("out",Tag.P17_OUT);
    public static final Word end_final = new Word("end",Tag.P18_END_FINAL);

    public static final Word igual_igual = new Word("==",Tag.OP1_IGUAL_IGUAL);
    public static final Word maior_que = new Word(">",Tag.OP2_MAIOR_QUE);
    public static final Word maior_ou_igual = new Word(">=",Tag.OP3_MAIOR_OU_IGUAL);
    public static final Word menor_que = new Word("<",Tag.OP4_MENOR_QUE);
    public static final Word menor_ou_igual = new Word("<=",Tag.OP5_MENOR_OU_IGUAL);
    public static final Word diferente = new Word("!=",Tag.OP6_DIFERENTE);
    public static final Word mais = new Word("+",Tag.OP7_MAIS);
    public static final Word menos = new Word("-",Tag.OP8_MENOS);
    public static final Word ou = new Word("||",Tag.OP9_OU);
    public static final Word vezes = new Word("*",Tag.OP10_VEZES);
    public static final Word divisao = new Word("/",Tag.OP11_DIVISAO);
    public static final Word e = new Word("&&",Tag.OP12_E);
    public static final Word igual = new Word("=",Tag.OP13_IGUAL);
    public static final Word negacao = new Word("!",Tag.OP14_NEGACAO);

    public static final Word ponto = new Word(".",Tag.PONT1_PONTO);
    public static final Word abre_par = new Word("(",Tag.PONT2_ABRE_PAR);
    public static final Word fecha_par = new Word(")",Tag.PONT3_FECHA_PAR);
    public static final Word virgula = new Word(",",Tag.PONT4_VIRGULA);
    public static final Word ponto_e_virgula = new Word(";",Tag.PONT5_PONTO_E_VIRGULA);
    public static final Word dois_pontos = new Word(":",Tag.PONT6_DOIS_PONTOS);

    public static final Word ler = new Word("<<",Tag.RESTO6_LER);
    public static final Word escrever = new Word(">>",Tag.RESTO7_ESCREVER);
    public static final Word invalido = new Word("Token Inválido",Tag.RESTO8_TOKEN_INVALIDO);
    public static final Word fim_do_arquivo = new Word("Fim do Arquivo",Tag.RESTO9_FIM_ARQUIVO);

    public String toString(){
        return "" + lexema;
    }

    public String getLexema() {
        return lexema;
    }
}
