package interface_adapter.home;

import interface_adapter.ViewModel;

/**
 * View model for the Home View.
 */
public class HomeViewModel extends ViewModel<HomeState> {
    public HomeViewModel() {
        super("Home");
        setState(new HomeState());
    }
}
