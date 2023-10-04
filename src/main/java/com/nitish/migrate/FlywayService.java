package com.nitish.migrate;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.flywaydb.core.api.output.MigrateResult;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class FlywayService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FlywayService.class);

    @Autowired
    DataSource dataSource;

    public void validateFlywayDb(DataSource dataSource) {
        LOGGER.info("Flyway validations started.......");
        ClassicConfiguration classicConfiguration = new ClassicConfiguration();
        classicConfiguration.setDataSource(dataSource);
        classicConfiguration.setLocationsAsStrings("classpath:db/migration");
        Flyway flyway = new Flyway(classicConfiguration);
        flyway.validate();
        LOGGER.info("Flyway validations completed.......");

    }

    public void repairFlywayDb(DataSource dataSource) {
        LOGGER.info("Flyway repair started.......");
        ClassicConfiguration classicConfiguration = new ClassicConfiguration();
        classicConfiguration.setDataSource(dataSource);
        classicConfiguration.setLocationsAsStrings("classpath:db/migration");
        Flyway flyway = new Flyway(classicConfiguration);
        flyway.repair();
        LOGGER.info("Flyway repair completed.......");
    }

    public void migrateDb(DataSource dataSource) {
        LOGGER.info("Flyway migrations started.......");
        ClassicConfiguration classicConfiguration = new ClassicConfiguration();
        classicConfiguration.setDataSource(dataSource);
        classicConfiguration.setLocationsAsStrings("classpath:db/migration");
        Flyway flyway = new Flyway(classicConfiguration);
        MigrateResult migrateResult = flyway.migrate();
        LOGGER.info("Successfully applied {} migrations", migrateResult.migrationsExecuted);
        LOGGER.info("Flyway migrations completed.......");
    }


}
