
import il.ac.hit.Model.costManagerException;
import il.ac.hit.Model.DBConnection;
import il.ac.hit.Model.IModel;
import il.ac.hit.View.IView;
import il.ac.hit.View.MainPage;
import il.ac.hit.ViewModel.IViewModel;
import il.ac.hit.ViewModel.ViewModel;

import javax.swing.*;

public class Application {
    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //creating the application components
                IModel model = null;
                try {
                    model = new DBConnection();
                }  catch (costManagerException e) {
                    e.printStackTrace();
                }
                IView view = (IView) new MainPage();
                IViewModel vm = null;

                    vm = new ViewModel();



                //connecting the components with each other
                view.setViewModel(vm);
                vm.setModel(model);
                vm.setView(view);
            }
        });

    }
}
