package compilador.src.analisador_lexico;

public class Literal extends Token{
    public final String value;
    public Literal(Tag tag, String value) {
        super(Tag.RESTO2_LITERAL);
        this.value = value;
    }
    public String toString(){
        return "" + value;
    }
}
