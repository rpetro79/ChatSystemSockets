package mvvm.views;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import mvvm.viewModels.ChatViewModel;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class ChatView {
    @FXML
    private ScrollPane messagesScrollPane;
    @FXML
    private TextArea myMessage;
    @FXML
    private Button sendButton;
    @FXML
    private Button logOutButton;
    @FXML
    private VBox vBox;
    @FXML
    private ListView users;

    private StringProperty username;
//    ArrayList<Label> messages;

    private ChatViewModel viewModel;

    public void init(ChatViewModel vm)
    {
        viewModel = vm;
        username = new SimpleStringProperty();
        username.bindBidirectional(viewModel.userNameProperty());
        myMessage.textProperty().bindBidirectional(viewModel.messageProperty());
        messagesScrollPane = new ScrollPane();

        users.setItems(viewModel.getUserList());

        viewModel.addListener("newMessage", this::newMessage);
        viewModel.addListener("listUpdate", this :: updateList);
        myMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER))
                    send();
            }
        });
        messagesScrollPane.vvalueProperty().bind(vBox.heightProperty());
        myMessage.setWrapText(true);
        vBox.setSpacing(10);
    }

    public void updateList(PropertyChangeEvent evt)
    {
        Platform.runLater(() -> {
            users.setItems((ObservableList) evt.getNewValue());
        });
    }

    public void send()
    {
        viewModel.send();
    }

    public void logOut()
    {
        viewModel.logOut();
    }

    public void newMessage(PropertyChangeEvent evt)
    {
        String mess = (String) evt.getNewValue();
        char c = mess.charAt(0);
        mess = mess.substring(1);
        int i = 0;
        if(c == '1')
        {
            while(mess.length() > 50)
            {
                Label lbl;
                HBox hbox = new HBox();
                i = 50;
                while(mess.charAt(i) != ' ' && i > 35)
                    --i;
                lbl = new Label(mess.substring(0, i));
                lbl.setStyle("-fx-background-color:  #021C1E; -fx-text-fill: #b5e61d; -fx-border-radius: 100; -fx-padding: 8;");
                hbox.getChildren().add(lbl);
                hbox.setAlignment(Pos.BASELINE_RIGHT);
                Platform.runLater(() -> {
                    lbl.setLineSpacing(2);
                    vBox.getChildren().add(hbox);
                });
                mess = mess.substring(i);
            }
            if(mess.length() > 0)
            {
                Label lbl;
                HBox hbox = new HBox();
                lbl = new Label(mess);
                lbl.setStyle("-fx-background-color:  #021C1E; -fx-text-fill: #b5e61d; -fx-border-radius: 100; -fx-padding: 8;");
                hbox.getChildren().add(lbl);
                hbox.setAlignment(Pos.BASELINE_RIGHT);
                Platform.runLater(() -> {
                    lbl.setLineSpacing(2);
                    vBox.getChildren().add(hbox);
                });
            }
        }
        else {
            int l = 50 - username.getValue().length();
            while(mess.length() > l) {
                Label lbl;
                HBox hbox = new HBox();
                i = l;
                while (mess.charAt(i) != ' ' && i > l-20)
                    --i;
                if (i == l-20) {
                    lbl = new Label(username.getValue() + ": " + mess.substring(0, l));
                    lbl.setStyle("-fx-background-color: #b5e61d ; -fx-text-fill: #021C1E; -fx-border-radius: 100; -fx-padding: 8;");
                    hbox.getChildren().add(lbl);
                    hbox.setAlignment(Pos.BASELINE_LEFT);
                    Platform.runLater(() -> {
                        lbl.setLineSpacing(2);
                        vBox.getChildren().add(hbox);
                    });
                    mess = mess.substring(l);
                } else {
                    lbl = new Label(username.getValue() + ": " + mess.substring(0, i));
                    lbl.setStyle("-fx-background-color: #b5e61d ; -fx-text-fill: #021C1E; -fx-border-radius: 100; -fx-padding: 8;");
                    hbox.getChildren().add(lbl);
                    hbox.setAlignment(Pos.BASELINE_LEFT);
                    Platform.runLater(() -> {
                        lbl.setLineSpacing(2);
                        vBox.getChildren().add(hbox);
                    });
                    mess = mess.substring(i);
                }
            }
            if(mess.length() > 0)
            {
                Label lbl;
                HBox hbox = new HBox();
                lbl = new Label(mess);
                lbl.setStyle("-fx-background-color: #b5e61d ; -fx-text-fill: #021C1E; -fx-border-radius: 100; -fx-padding: 8;");
                hbox.getChildren().add(lbl);
                hbox.setAlignment(Pos.BASELINE_LEFT);
                Platform.runLater(() -> {
                    lbl.setLineSpacing(2);
                    vBox.getChildren().add(hbox);
                });

            }
        }




       /* while(mess.length() - username.getValue().length() > 70)
        {
            Label lbl;
            HBox hbox = new HBox();
            if(c == '1')
            {
                i = 70;
                while(mess.charAt(i) != ' ' && i > 55)
                    --i;
                lbl = new Label(mess.substring(0, i));
                lbl.setStyle("-fx-background-color:  #021C1E; -fx-text-fill: #b5e61d; -fx-border-radius: 100; -fx-padding: 8;");
                hbox.getChildren().add(lbl);
                hbox.setAlignment(Pos.BASELINE_RIGHT);
            }
            else {
                i = 70;
                while(mess.charAt(i) != ' ' && i > 55)
                    --i;
                lbl = new Label(username.getValue() + ": " + mess.substring(0, i));
                lbl.setStyle("-fx-background-color: #b5e61d ; -fx-text-fill: #021C1E; -fx-border-radius: 100; -fx-padding: 8;");
                hbox.getChildren().add(lbl);
                hbox.setAlignment(Pos.BASELINE_LEFT);
        }
            Platform.runLater(() -> {
                lbl.setLineSpacing(2);
                vBox.getChildren().add(hbox);
            });
            mess = mess.substring(i);
        }
        if(mess.length() > 0)
        {
            Label lbl;
            HBox hbox = new HBox();
            if(c == '1')
            {
                lbl = new Label(mess);
                lbl.setStyle("-fx-background-color:  #021C1E; -fx-text-fill: #b5e61d; -fx-border-radius: 100; -fx-padding: 8;");
                hbox.getChildren().add(lbl);
                hbox.setAlignment(Pos.BASELINE_RIGHT);
            }
            else {
                lbl = new Label(username.getValue() + ": " + mess);
                lbl.setStyle("-fx-background-color: #b5e61d ; -fx-text-fill: #021C1E; -fx-border-radius: 100; -fx-padding: 8;");
                hbox.getChildren().add(lbl);
                hbox.setAlignment(Pos.BASELINE_LEFT);
            }
            Platform.runLater(() -> {
                vBox.setSpacing(10);
                lbl.setLineSpacing(2);
                vBox.getChildren().add(hbox);
            });
        }*/
    }
}
