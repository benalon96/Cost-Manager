package il.ac.hit.View;

import il.ac.hit.ViewModel.IViewModel;


public interface IView {

    //public void displayPieChart(Map map);
    public void setViewModel(IViewModel vm);
    public void showMessage(String text);
    public void refreshMethod();

    //..
}
