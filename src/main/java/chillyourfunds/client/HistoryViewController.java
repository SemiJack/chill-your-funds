package chillyourfunds.client;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.TilePane;

public class HistoryViewController {

    @FXML
    private SplitPane splitPane;
    @FXML
    private TilePane tilePane;

    @FXML
    public TilePane getTilePane(){
        return tilePane;
    }


}
