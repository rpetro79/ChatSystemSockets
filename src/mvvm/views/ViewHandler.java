package mvvm.views;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mvvm.Model;
import mvvm.viewModels.ViewModelHandler;

import java.io.IOException;

public class ViewHandler {
//    private LoginView logInView;
//    private SignUpView signUpView;
//    private ChatView chatView;

    private Stage stage;
    private ViewModelHandler viewModelHandler;

    public ViewHandler(Stage stage, Model model) {
        this.viewModelHandler = new ViewModelHandler(model, this);
        this.stage = stage;
    }

    public void openView(String viewToOpen)
    {
        Scene scene = null;
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        if("logInView".equals(viewToOpen))
        {
            try {
                loader.setLocation(getClass().getResource("../../fxmlFiles/LogInView.fxml"));
                root = loader.load();
                LoginView loginView = loader.getController();
                loginView.init(viewModelHandler.getLogInViewModel());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else if("signUpView".equals(viewToOpen))
        {
            try {
                loader.setLocation(getClass().getResource("../../fxmlFiles/signUpView.fxml"));
                root = loader.load();
                SignUpView signUpView = loader.getController();
                signUpView.init(viewModelHandler.getSignUpViewModel());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        else if("chatView".equals(viewToOpen))
        {
            try {
                loader.setLocation(getClass().getResource("../../fxmlFiles/chatView.fxml"));
                root = loader.load();
                ChatView chatView = loader.getController();
                chatView.init(viewModelHandler.getChatViewModel());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        stage.setTitle("Heyyy");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
