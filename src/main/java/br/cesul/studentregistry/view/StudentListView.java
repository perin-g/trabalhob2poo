package br.cesul.studentregistry.view;

import br.cesul.studentregistry.model.Student;
import br.cesul.studentregistry.viewmodel.StudentListViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StudentListView {
    @FXML private TableView<Student> table;
    @FXML private TableColumn<Student, String> colName;
    @FXML private TableColumn<Student, String> colEmail;
    @FXML private TableColumn<Student, Number> colGrade;
    @FXML private TableColumn<Student, Boolean> colActive;
    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private Spinner<Double> spnGrade;
    @FXML private Button btnAdd;
    @FXML private Button btnToggle;
    @FXML private Button btnUpdate;
    @FXML private ProgressIndicator progress;
    @FXML private Label lblError;

    private StudentListViewModel viewModel;

    public void setViewModel(StudentListViewModel vm) {
        viewModel = vm;

        // TableView observa 'items' da VM
        table.setItems(viewModel.getItems());
        progress.visibleProperty().bind(viewModel.loadingProperty());
        // Toda vez que a propriedade String do erro da VM for alterada o componente label da tela mostrará o erro.
        lblError.textProperty().bind(viewModel.errorProperty());
        colName.setCellValueFactory(cell -> Bindings.createStringBinding(cell.getValue()::getName));
        colEmail.setCellValueFactory(cell -> Bindings.createStringBinding(cell.getValue()::getEmail));
        colGrade.setCellValueFactory(cell -> Bindings.createObjectBinding(cell.getValue()::getGrade));
        colActive.setCellValueFactory(cell -> Bindings.createObjectBinding(cell.getValue()::isActive));
        spnGrade.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10, 7.0, 0.5));
    }
    @FXML
    private void onAdd() {
        viewModel.addNew(txtName.getText(), txtEmail.getText(), spnGrade.getValue());
    }

    @FXML
    private void onToggle() {
        var selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            viewModel.toggleFlag(selected.getId());
        }
    }

    @FXML
    private void onUpdate() {
        var selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            var newGrade = spnGrade.getValue();
            viewModel.updateField(selected.getId(), newGrade);
        }
    }
}
