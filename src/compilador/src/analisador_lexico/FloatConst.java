package compilador.src.analisador_lexico;

public class FloatConst extends Token{
    public final float value;
    public FloatConst(Tag tag, float value) {
        super(Tag.RESTO4_FLOAT_CONST);
        this.value = value;
    }
    public String toString(){
        return "" + value;
    }
}
