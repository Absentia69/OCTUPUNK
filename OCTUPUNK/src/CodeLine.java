import java.util.LinkedList;
import java.util.Random;

public class CodeLine<E> {
    private final LinkedList<Registre<E>> registers;
    private final Commande cmd;
    private final Label<E> lbl;
    private final long adresse;

    public CodeLine(Commande cmd){
        this.cmd = cmd;
        this.registers = null;
        this.lbl = null;
        this.adresse = new Random().nextLong(3000, 10000);
    }

    public CodeLine(Commande cmd, LinkedList<Registre<E>> registers){
        this.cmd = cmd;
        this.registers = registers;
        this.lbl = null;
        this.adresse = new Random().nextLong(3000, 10000);
    }
    public CodeLine(Commande cmd, Label<E> lbl){
        this.cmd = cmd;
        this.lbl = lbl;
        this.registers = null;
        this.adresse = new Random().nextLong(3000, 10000);
    }

    public CodeLine(Commande cmd, LinkedList<Registre<E>> registers, Label<E> lbl){
        this.cmd = cmd;
        this.registers = registers;
        this.lbl = lbl;        
        this.adresse = new Random().nextLong(3000, 10000);
    }

    public LinkedList<Registre<E>> getRegisters(){
        return new LinkedList<Registre<E>>(this.registers);
    }

    public Commande getCommande(){
        return this.cmd;
    }

    public Label<E> getLabel(){
        return this.lbl;
    }

    public long getAdresse(){
        return this.adresse;
    }

    public String printRegisters(){
        String txt ="";
        for (Registre<E> r : registers) {
            txt+= "R" + r.getRegistreNumber() + ", ";
        }
        txt = txt.substring(0, txt.length() - 2);
        return txt;
    }

    @Override
    public String toString() {
        if (getCommande() == Commande.HALT) {
            return "@" + getAdresse() + " " + getCommande();
        }
        if (getLabel() == null) {
            return "@" + getAdresse() + " " + getCommande() + " " + printRegisters();
        }
        if (getRegisters() == null) {
            return "@" + getAdresse() + " " + getCommande() + " " + getLabel().getLabelName();
        }
        if (getCommande() == Commande.ST) {
            return "@" + getAdresse() + " " + getCommande() + " " + printRegisters() + ", " + getLabel().getLabelName();
        }
        return "@" + getAdresse() + " " + getCommande() + " " + printRegisters() + ", " + getLabel().getLabelName();
    }

    @SuppressWarnings("unchecked")
    public void execute(){
        switch (getCommande()) {
            case ADD:
                if (registers.size() != 3) {
                    throw new IllegalStateException();
                }
                for (Registre<E> r : registers) {
                    if (r.getRegistreContent() == null || !(r.getRegistreContent() instanceof Integer)){
                        throw new IllegalArgumentException();
                    }
                }
                Integer sum = ((Integer) registers.get(1).getRegistreContent()) + ((Integer) registers.get(2).getRegistreContent()); 
                registers.get(0).setRegistreContent((E) sum);
                break;
            case NOT:
                if (registers.size() != 1) {
                    throw new IllegalStateException();
                }
                if (registers.get(0) == null || !(registers.get(0).getRegistreContent() instanceof Integer)){
                    throw new IllegalArgumentException();
                }
                Integer not = (Integer) registers.get(0).getRegistreContent()*-1;
                registers.get(0).setRegistreContent((E) not);
                break;
            case AND:
                if (registers.size() != 3) {
                    throw new IllegalStateException();
                }
                for (Registre<E> r : registers) {
                    if (r.getRegistreContent() == null || !(r.getRegistreContent() instanceof Integer)){
                        throw new IllegalArgumentException();
                    }
                }
                Integer and = ((Integer) registers.get(1).getRegistreContent()) & ((Integer) registers.get(2).getRegistreContent()); 
                registers.get(0).setRegistreContent((E) and);
                break;
            case LD:
                if (!(lbl instanceof Label)) {
                    throw new IllegalArgumentException();
                }
                if (registers.size() != 1) {
                    throw new IllegalStateException();
                }
                registers.get(0).setRegistreContent(lbl.getLabelValue());               
                break;
            case ST:
                if (!(lbl instanceof Label)) {
                    throw new IllegalArgumentException();
                }
                if (registers.size() != 1) {
                    throw new IllegalStateException();
                }
                lbl.setLabelValue(registers.get(0).getRegistreContent());              
                break;
            case HALT:
                System.out.println("Program Halted");
                System.exit(0);
                break;      
            default:
                break;
        }
    }

    public static void main(String[] args) {
        Registre<Integer> R0 = new Registre<Integer>(0, 4);
        Registre<Integer> R1 = new Registre<Integer>(1, 12);
        Registre<Integer> R2 = new Registre<Integer>(2, 14);
        LinkedList<Registre<Integer>> l = new LinkedList<Registre<Integer>>();
        l.add(R0);
        l.add(R1);
        l.add(R2);
        System.out.println(R0);
        System.out.println(R1);
        System.out.println(R2);
        CodeLine<Integer> c = new CodeLine<Integer>(Commande.ADD, l);
        CodeLine<Integer> c0 = new CodeLine<Integer>(Commande.AND, l);
        System.out.println(c);
        c.execute();
        System.out.println(R0);
        System.out.println(c0);
        c0.execute();
        System.out.println(R0);
        l.remove();
        l.remove();
        CodeLine<Integer> c1 = new CodeLine<Integer>(Commande.NOT, l);
        System.out.println(c1);
        c1.execute();
        System.out.println(R2);
        Label<Integer> y = new Label<Integer>("y",74);
        System.out.println(y);
        CodeLine<Integer> c2 = new CodeLine<Integer>(Commande.LD, l, y);
        System.out.println(c2);
        c2.execute();
        System.out.println(l.get(0));
        Label<Integer> z = new Label<Integer>("z",15);
        CodeLine<Integer> c3 = new CodeLine<Integer>(Commande.ST, l, z);
        System.out.println(c3);
        c3.execute();
        System.out.println(z);
        CodeLine<Integer> c4 = new CodeLine<Integer>(Commande.HALT);
        System.out.println(c4);
        c4.execute();
        System.out.println("hahahahahahahah");
    }
}
