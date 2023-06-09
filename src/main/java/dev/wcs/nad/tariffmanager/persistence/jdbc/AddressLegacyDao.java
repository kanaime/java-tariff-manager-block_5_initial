package dev.wcs.nad.tariffmanager.persistence.jdbc;

import dev.wcs.nad.tariffmanager.persistence.entity.Address;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class AddressLegacyDao {

    private final DataSource dataSource;

    // DataSource is configured by Spring in application.properties and injected
    // during Context setup.
    public AddressLegacyDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Address> getByIdJava7Syntax(long id) {
        // Challenge: Add the retrieval of the Address ResultSet and the Mapping to an
        // instance of Address here.
        try (Connection connection = dataSource.getConnection();

                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ADDRESS WHERE ID=?")) {
            stmt.setLong(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {

                    Address adresse = new Address();
                    adresse.setId(resultSet.getLong("ID"));
                    return Optional.of(adresse);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Optional.empty();
    }

}
