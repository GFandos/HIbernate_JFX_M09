/**
 * Created by 47989768s on 30/01/17.
 */
public class Companies {

    private int id;
    private String name;

    public Companies(String newName) {
        name = newName;
    }

    public Companies(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
