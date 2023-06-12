module br.igorms.crudapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;


    opens br.igorms.crudapp to javafx.fxml;
    exports br.igorms.crudapp;
}