package compilador.src.analisador_lexico;

public class Identificador extends Token{
    public final String lexema;
    public Identificador(Tag tag, String lexema) {
        super(Tag.RESTO1_IDENTIFICADOR);
        String lexama = null;
        this.lexema = lexama;
    }
    public String toString(){
        return "" + lexema;
    }
}
