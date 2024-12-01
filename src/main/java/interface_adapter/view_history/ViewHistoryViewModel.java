package interface_adapter.view_history;

import interface_adapter.ViewModel;

public class ViewHistoryViewModel extends ViewModel<ViewHistoryState> {
    public ViewHistoryViewModel() {
        super("View History");
        setState(new ViewHistoryState());
    }
}

