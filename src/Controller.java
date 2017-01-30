import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.util.List;

public class Controller {


    public TextField titleName;
    public TextField titlePlayers;
    public TextField titlePrice;
    public TextField companyName;
    public TextField companyId;

    public Button listAll;
    @FXML
    private Button borrador;

    @FXML
    public ListView<Videogame> videogamesLV;
    public ListView<Companies> companyLV;

    ManageVideogame manager;
    ObservableList<Videogame> videogamesObservable;
    ObservableList<Companies> companiesObservable;

    public Controller(){
        manager = new ManageVideogame();
    }

    public void OnAddVideogames(ActionEvent actionEvent) {

        String title = "";
        int players = 0;
        int price = 0;

        System.out.println("titleName = " + titleName.getText());
        if(!titleName.getText().isEmpty()) {
            title = titleName.getText();

        } else {
            JOptionPane.showMessageDialog(null, "Título no puede ser un campo vacio.");
            return;
        }
        System.out.println("titlePLayers = " + titlePlayers.getText());
        if(!titlePlayers.getText().isEmpty()) {
            players = Integer.parseInt(titlePlayers.getText());

        } else {
            JOptionPane.showMessageDialog(null, "Número de jugadores no puede ser un campo vacio.");
            return;
        }

        System.out.println("titlePrice = " + titlePrice.getText());
        if(!titlePrice.getText().isEmpty()) {
            price = Integer.parseInt(titlePrice.getText());

        } else {
            JOptionPane.showMessageDialog(null, "Precio no puede ser un campo vacio.");
            return;
        }

        int compan = 0;
        if(!companyId.getText().isEmpty()) {
            compan = Integer.parseInt(companyId.getText());

        } else {
            JOptionPane.showMessageDialog(null, "Compañía no puede ser un campo vacio.");
            return;
        }

        Videogame v = new Videogame(title, players, price, compan);
        if(manager.exists(v)) {
            JOptionPane.showMessageDialog(null, "Ya existe un videojuego con este nombre.");
            return;
        }

        manager.addVideogame(title, players, price, compan);
        titlePlayers.setText("");
        titlePrice.setText("");
        titleName.setText("");
        companyId.setText("");

        this.listAll();
    }

    public void onListAll(ActionEvent actionEvent) {

        this.listAll();

    }

    public void listAll(){
        this.getObservableVideogameList();

        videogamesLV.setItems(videogamesObservable);
    }

    public void getObservableVideogameList(){
        List<Videogame> videogames = manager.listVideogames();
        videogamesObservable = FXCollections.observableArrayList ();

        for(int i = 0; i < videogames.size(); ++i) {
            videogamesObservable.add(videogames.get(i));
        }

    }

    public void initialize() {

        videogamesLV.setCellFactory((list) -> {
            return new ListCell<Videogame>() {
                @Override
                public void updateItem(Videogame item, boolean empty) {

                    super.updateItem(item, empty);
                        if(!empty) setText(item.getTitle());
                }
            };
        });

        videogamesLV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println("ListView Selection Changed (selected: " + newValue.toString() + ")");
                    borrador.setVisible(true);

                }
        );

        companyLV.setCellFactory((list) -> {
            return new ListCell<Companies>() {
                @Override
                public void updateItem(Companies item, boolean empty) {

                    super.updateItem(item, empty);
                    if(!empty) {
                        setText(item.getName());
//                        setText(String.valueOf(item.getId()));
                    }
                }

            };
        });

        companyLV.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println("ListView Selection Changed (selected: " + newValue.toString() + ")");
                    borrador.setVisible(true);

                }
        );

    }

    public void onDelete(ActionEvent actionEvent) {

        Videogame v = videogamesLV.getSelectionModel().getSelectedItem();
        int index = videogamesLV.getSelectionModel().getSelectedIndex();
        int id = v.getId();


        final int newSelectedIdx =
                (index == videogamesLV.getItems().size() - 1)
                        ? index - 1
                        : index;

        videogamesLV.getItems().remove(index);
        videogamesLV.getSelectionModel().select(newSelectedIdx);
        videogamesLV.getItems().clear();

        manager.deleteVideogame(id);

        this.getObservableVideogameList();
        videogamesLV.setItems(videogamesObservable);

        this.listAll();

    }

    public void onAddCompany(ActionEvent actionEvent) {

        String name;

        if(!companyName.getText().isEmpty()) {
            name = titleName.getText();

        } else {
            JOptionPane.showMessageDialog(null, "Nombre no puede ser un campo vacio.");
            return;
        }

        Companies c = new Companies(name);
        manager.addCompany(name);

        this.listAllCompanies();


    }

    public void listAllCompanies(){
        this.getObservableCompaniesList();

        companyLV.setItems(companiesObservable);
    }

    public void getObservableCompaniesList(){
        List<Companies> companiess = manager.listCompanies();
        companiesObservable = FXCollections.observableArrayList ();

        for(int i = 0; i < companiess.size(); ++i) {
            companiesObservable.add(companiess.get(i));
        }

    }
}
