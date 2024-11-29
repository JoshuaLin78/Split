package interface_adapter.home;

import interface_adapter.ViewModel;

public class HomeViewModel extends ViewModel<HomeState> {
    public HomeViewModel() {
        super("Home");
        setState(new HomeState());
    }
}
