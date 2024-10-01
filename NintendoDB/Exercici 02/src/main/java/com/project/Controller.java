package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URISyntaxException;

public class Controller {

    @FXML
    private ComboBox<String> comboBoxCategory;

    @FXML
    private ListView<String> listViewItems;

    @FXML
    private ImageView gameImage;

    @FXML
    private Label gameTitle;

    @FXML
    private Text gameDescription;

    private JSONArray gamesArray, charactersArray, consolesArray;

    private ObservableList<String> itemsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        comboBoxCategory.setItems(FXCollections.observableArrayList("Jocs", "Personatges", "Consoles"));
        comboBoxCategory.getSelectionModel().selectFirst();

        loadGamesData();
        loadCharactersData();
        loadConsolesData();
    
        comboBoxCategory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        });
    
        // Listener para seleccionar un ítem en el ListView
        listViewItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String selectedCategory = comboBoxCategory.getSelectionModel().getSelectedItem();
    
                JSONArray selectedArray = getJSONArrayForCategory(selectedCategory);
    
                if (selectedArray != null && selectedArray.length() > 0) {
                    displayItemDetails(newValue, selectedArray);
                }
            }
        });
        updateListView("Jocs");
    }
    
    

    private void updateListView(String category) {
        itemsList.clear();
        JSONArray selectedArray = getJSONArrayForCategory(category);
    
        for (int i = 0; i < selectedArray.length(); i++) {
            JSONObject item = selectedArray.getJSONObject(i);
            String itemName = item.optString("nom", null);
    
            if (itemName != null) {
                itemsList.add(itemName);
            }
        }
    
        listViewItems.setItems(itemsList);
        gameImage.setImage(null);
        gameTitle.setText("");
        gameDescription.setText("");
    }
    

    private void displayItemDetails(String itemName, JSONArray selectedArray) {
    
        for (int i = 0; i < selectedArray.length(); i++) {
            JSONObject item = selectedArray.getJSONObject(i);
            String name = item.optString("nom", null);
    
            if (name != null && name.equals(itemName)) {
                gameTitle.setText(name);
    
                // Obtener la categoría seleccionada
                String selectedCategory = comboBoxCategory.getSelectionModel().getSelectedItem();
                
                // Mostrar detalles específicos para Consoles
                if ("Consoles".equals(selectedCategory)) {
                    String releaseDate = item.optString("data", "Data no disponible");
                    String processor = item.optString("procesador", "Procesador no disponible");
                    String color = item.optString("color", "Color no disponible");
                    int unitsSold = item.optInt("venudes", 0);
    
                    gameDescription.setText(
                        "Data de Lançament: " + releaseDate + "\n" +
                        "Processador: " + processor + "\n" +
                        "Color: " + color + "\n" +
                        "Unitats venudes: " + unitsSold
                    );
    
                // Mostrar detalles específicos para Personatges
                } else if ("Personatges".equals(selectedCategory)) {
                    String color = item.optString("color", "Color no disponible");
                    String gameName = item.optString("nom_del_videojoc", "Nom del videojoc no disponible");
    
                    gameDescription.setText(
                        "Color: " + color + "\n" +
                        "Videojoc: " + gameName
                    );
    
                // Mostrar la descripción de los juegos
                } else {
                    String description = item.optString("descripcio", "Descripció no disponible");
                    gameDescription.setText(description);
                }
    
                // Cargar la imagen si está disponible
                if (item.has("imatge")) {
                    String imageFile = item.getString("imatge");
                    String imagePath = "/assets/images/" + imageFile;
                    Image image = new Image(getClass().getResourceAsStream(imagePath));
                    gameImage.setImage(image);
                }
    
                break;
            }
        }
    }
    
    
    // Devuelve el JSONArray correspondiente a la categoría seleccionada
    private JSONArray getJSONArrayForCategory(String category) {
        switch (category) {
            case "Personatges":
                return charactersArray;
            case "Consoles":
                return consolesArray;
            default:
                return gamesArray;
        }
    }

    private void loadGamesData() {
        try {
            String jsonPath = "/assets/data/jocs.json";
            String content = new String(Files.readAllBytes(Paths.get(getClass().getResource(jsonPath).toURI())));
            gamesArray = new JSONArray(content);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    

    private void loadCharactersData() {
        try {
            String jsonPath = "/assets/data/personatges.json";
            String content = new String(Files.readAllBytes(Paths.get(getClass().getResource(jsonPath).toURI())));
            charactersArray = new JSONArray(content);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void loadConsolesData() {
        try {
            String jsonPath = "/assets/data/consoles.json";
            String content = new String(Files.readAllBytes(Paths.get(getClass().getResource(jsonPath).toURI())));
            consolesArray = new JSONArray(content);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
