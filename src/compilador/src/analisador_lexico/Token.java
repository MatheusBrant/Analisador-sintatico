package compilador.src.analisador_lexico;

public class Token {

        public final Tag tagToken; //constante que representa o token
        public Token (Tag tag){
            tagToken = tag;
        }
        public String toString(){
            return "" + tagToken;
        }
        public boolean match(Tag tagName) {
            return this.tagToken == tagName;
        }
}
