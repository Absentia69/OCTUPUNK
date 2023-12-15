import java.util.Random;

public class Registre<E> {


    private final int number; //Register number 0-7
    private E content; //content of the register
    private long adresse; //@ of the register


    public Registre(int number){
        if (number < 0 && number > 8) {
            throw new IllegalArgumentException();
        }
        this.number=number;
        content = null;
        adresse = new Random().nextLong(3000, 10000);
    }

    public Registre(int n, E content){
        this(n);
        this.content=content;
    }

    public Registre(int n, E content, long adresse){
        this(n, content);
        this.adresse = adresse;
    }

    public int getRegistreNumber(){
        return this.number;
    }

    public E getRegistreContent(){
        return this.content;
    }

    public long getRegistreAdresse(){
        return this.adresse;
    }

    public void setRegistreContent(E newContent){
        this.content = newContent;
    }
    
    public void setRegistreAdresse(long newAdresse){
        this.adresse = newAdresse;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Registre)) {
            return false;
        }
        Registre<?> r = (Registre<?>) o;
        return this.getRegistreAdresse() == r.getRegistreAdresse() &&
                this.getRegistreNumber() == r.getRegistreNumber() &&
                this.getRegistreContent().equals(r.getRegistreContent());
    }

    @Override 
    public int hashCode(){
        return getRegistreNumber() + (int) getRegistreAdresse() + getRegistreContent().hashCode();
    }

    @Override
    public String toString(){
        return "Registre R" + getRegistreNumber() + "@" + getRegistreAdresse() + ":" + getRegistreContent();
    }
    public static void main(String[] args) {
        Registre<String> R0 = new Registre<String>(2, "Hello");
        System.out.println(R0);
        R0.setRegistreContent("Coucou");
        System.out.println(R0);
    }
}
