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

    public Button listAll;
    @FXML
    private Button borrador;

    @FXML
    public ListView<Videogame> videogamesLV;

    ManageVideogame manager;

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

        Videogame v = new Videogame(title, players, price);
        if(manager.exists(v)) {
            JOptionPane.showMessageDialog(null, "Ya existe un videojuego con este nombre.");
            return;
        }

        manager.addVideogame(title, players, price);
        titlePlayers.setText("");
        titlePrice.setText("");
        titleName.setText("");
    }

    public void onListAll(ActionEvent actionEvent) {

        List<Videogame> videogames = manager.listVideogames();
        ObservableList<Videogame> videogamesObservable = FXCollections.observableArrayList ();

        for(int i = 0; i < videogames.size(); ++i) {
            videogamesObservable.add(videogames.get(i));
        }

        videogamesLV.setItems(videogamesObservable);

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

    }

    public void onDelete(ActionEvent actionEvent) {

        Videogame v = videogamesLV.getSelectionModel().getSelectedItem();
        int id = v.getId();
        manager.deleteVideogame(id);

    }
}
