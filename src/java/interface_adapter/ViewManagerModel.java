package interface_adapter;

/**
 * View Manager Model Class. State represents the name of the active View.
 */
public class ViewManagerModel extends ViewModel<String>{
    public ViewManagerModel() {
        super("view manager");
        this.setState("");
    }
}
