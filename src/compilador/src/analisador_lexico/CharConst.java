package compilador.src.analisador_lexico;

public class CharConst extends Token{
    public final char value;
    public CharConst(Tag tag, char value) {
        super(Tag.RESTO5_CHAR_CONST);
        this.value = value;
    }
    public String toString(){
        return "" + value;
    }
}
