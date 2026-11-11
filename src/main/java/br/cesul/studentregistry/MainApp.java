package br.cesul.studentregistry;

import br.cesul.studentregistry.config.MongoConfig;
import br.cesul.studentregistry.repository.StudentRepository;
import br.cesul.studentregistry.viewmodel.StudentListViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        var repository = new StudentRepository(MongoConfig.database(), "students");
        var viewModel = new StudentListViewModel(repository);

        var loader = new FXMLLoader(getClass().getResource("/StudentView.fxml"));
        Parent root = loader.load();

        var conroller = loader.getController();
        conroller.getClass().getMethod("setViewModel", StudentListViewModel.class).invoke(conroller, viewModel);

        primaryStage.setTitle("StudentRegistry");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        viewModel.loadAll();
    }

    public static void main( String[] args ) {
        launch(args);
    }
}
