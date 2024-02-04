module com.example.cis255project5vincenthsiao {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cis255project5vincenthsiao to javafx.fxml;
    exports com.example.cis255project5vincenthsiao;
}