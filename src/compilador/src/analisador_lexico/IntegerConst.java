package compilador.src.analisador_lexico;

public class IntegerConst extends Token{
    public final int value;
    public IntegerConst(Tag tag, int value) {
        super(Tag.RESTO3_INTEGER_CONST);
        this.value = value;
    }
    public String toString(){
        return "" + value;
    }
}
