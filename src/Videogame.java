/**
 * Created by 47989768s on 16/01/17.
 */
public class Videogame {

    private int id;
    private String title;
    private int players;
    private int price;
    private int company;

    public Videogame() {}
    public Videogame(String title_, int players_, int price_, int company_) {
        this.title = title_;
        this.players = players_;
        this.price = price_;
        this.company = company_;
    }
    public int getId() {
        return id;
    }
    public void setId( int id ) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }
}
