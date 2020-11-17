package compilador.src.analisador_lexico;

public enum Tag {

    // palavras reservadas
    P1_PROGRAM,
    P2_IS,
    P3_DECLARE,
    P4_INIT,
    P5_END,
    P6_INT,
    P7_FLOAT,
    P8_CHAR,
    P9_IF,
    P10_THEN,
    P11_ELSE,
    P12_REPEAT,
    P13_UNTIL,
    P14_WHILE,
    P15_DO,
    P16_IN,
    P17_OUT,
    P18_END_FINAL,

    //operadores

    OP1_IGUAL_IGUAL,
    OP2_MAIOR_QUE,
    OP3_MAIOR_OU_IGUAL,
    OP4_MENOR_QUE,
    OP5_MENOR_OU_IGUAL,
    OP6_DIFERENTE,
    OP7_MAIS,
    OP8_MENOS,
    OP9_OU,
    OP10_VEZES,
    OP11_DIVISAO,
    OP12_E,
    OP13_IGUAL,
    OP14_NEGACAO,

    //pontuacao

    PONT1_PONTO,
    PONT2_ABRE_PAR,
    PONT3_FECHA_PAR,
    PONT4_VIRGULA,
    PONT5_PONTO_E_VIRGULA,
    PONT6_DOIS_PONTOS,

    //resto
    RESTO1_IDENTIFICADOR,
    RESTO2_LITERAL,
    RESTO3_INTEGER_CONST,
    RESTO4_FLOAT_CONST,
    RESTO5_CHAR_CONST,
    RESTO6_LER,
    RESTO7_ESCREVER,
    RESTO8_TOKEN_INVALIDO,
    RESTO9_FIM_ARQUIVO;
}