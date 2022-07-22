package minesweeper;

import java.io.*;

public class Settings implements Serializable {
    private final int rowCount;
    private final int columnCount;
    private final int mineCount;
    private static final String SETTING_FILE = System.getProperty("user.home") + System.getProperty("file.separator" + "minesweeper.settings");

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public int getMineCount() {
        return mineCount;
    }

    public static final Settings BEGINNER = new Settings(9, 9, 10);
    public static final Settings INTERMEDIATE = new Settings(16, 16, 40);
    public static final Settings EXPERT = new Settings(16, 30, 99);


    public void save() {
        ObjectOutputStream oos = null;  //vytvorime novy objekt ale neinicializujeme ho
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SETTING_FILE));  //vytvori sa objektovy outpustream ktory
            //je subor  SETTING_FILE
            oos.writeObject(this);  //zapise sa settings do objektu - preco this?  hodnoty tohto objektu, zapisuje do suboru ako object
        } catch (IOException e) {
            System.out.println("nepodarilo sa zapisat settings do objektu");
        } finally {
            if (oos != null) {  //nakonci sa vykona test ak nie je outputstream null a je tam nieco zapisane tak ho uzavrieme
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static Settings load() {  //vraciame objekt typu Settings
        ObjectInputStream ois = null;  //na zaciatok ho iba vytvorime ale neinicializujeme
        try{
            ois = new ObjectInputStream(new FileInputStream(SETTING_FILE));  //vyskusame nacitat SETTING_FILE a ulozit do objctinputstream objektu
            Settings s = (Settings) ois.readObject();  //pretypujeme na objekt Settings - preco este .readObject()?, nacitame zo suboru, nacitany je Object a my to musime pretypovat na typ ktory musime vediet musi byt zhodny s tym co tam predtym islo
            return s;
        } catch (IOException e){
            System.out.println("nepodarilo sa otvoorit settings subor, nastavujem BEGINNER uroven");  //ak sa nepodari otvorit subor
        } catch(ClassNotFoundException e){
            System.out.println("nepodarilo sa precitat settings subor, nastavujem BEGINAEr uroven");  //ak sa nepodari precitat subor
        } finally{
            if (ois!=null){
                try{
                    ois.close();
                } catch (IOException e){

                }
            }
        }
        return BEGINNER;  //vrati beginner ak sa vyhodi vynimka
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Settings)) {  //ak je nie je objekt instanciou triedy tak vrat false
            return false;
        }
        Settings s = (Settings) obj;  //vytovirme premennu s ktoru pretypujeme z Object na Settings
        return s.rowCount == rowCount && s.columnCount == columnCount && s.mineCount == mineCount;  //vratime true ak su podmienky splnene
    }

    @Override
    public int hashCode() {
        return rowCount * columnCount * mineCount;
    }

    @Override
    public String toString() {
        return "Settings ["+rowCount +","+columnCount+","+mineCount+"]";
    }

    public Settings(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;


    }
}
