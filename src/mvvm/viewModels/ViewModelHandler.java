package mvvm.viewModels;

import mvvm.Model;
import mvvm.views.ViewHandler;

public class ViewModelHandler {
    private LogInViewModel logInViewModel;
    private SignUpViewModel signUpViewModel;
    private ChatViewModel chatViewModel;

    public ViewModelHandler(Model model, ViewHandler viewHandler) {
        logInViewModel = new LogInViewModel(model, viewHandler);
        signUpViewModel = new SignUpViewModel(model, viewHandler);
        chatViewModel = new ChatViewModel(model, viewHandler);
    }

    public LogInViewModel getLogInViewModel() {
        return logInViewModel;
    }

    public SignUpViewModel getSignUpViewModel() {
        return signUpViewModel;
    }

    public ChatViewModel getChatViewModel() {
        return chatViewModel;
    }
}
