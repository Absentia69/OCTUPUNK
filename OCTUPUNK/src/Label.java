import java.util.Random;

public class Label<E> {
    
    private final String name;
    private E value;
    private long adresse;

    public Label(String name){
        this.name = name;
        this.value = null;
        this.adresse = new Random().nextLong(3000, 10000);
    }

    public Label(String name, E value){
        this(name);
        this.value = value;
    }

    public Label(String name, E value, long adresse){
        this(name,value);
        this.adresse = adresse;
    }

    public String getLabelName(){
        return this.name;
    }

    public E getLabelValue(){
        return this.value;
    }

    public long getLabelAdresse(){
        return this.adresse;
    }

    public void setLabelValue(E newValue){
        this.value = newValue;
    }

    public void setLabelAdresse(long newAdresse){
        this.adresse = newAdresse;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Label)) {
            return false;
        }
        Label<?> l = (Label<?>) o;
        return this.getLabelAdresse() == l.getLabelAdresse() &&
                this.getLabelName().equals(l.getLabelName()) &&
                this.getLabelValue() == l.getLabelValue();
    }

    @Override
    public String toString() {
        return "Label " + getLabelName() + "@" + getLabelAdresse() + ":" + getLabelValue();
    }
}
