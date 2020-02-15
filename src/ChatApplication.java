import Client.Client;
import javafx.application.Application;
import javafx.stage.Stage;
import mvvm.Model;
import mvvm.viewModels.ViewModelHandler;
import mvvm.views.ViewHandler;

public class ChatApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        Client client = new Client(model);
        ViewHandler viewHandler = new ViewHandler(primaryStage, model);
        viewHandler.openView("logInView");
    }
}
