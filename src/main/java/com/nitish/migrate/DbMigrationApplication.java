package com.nitish.migrate;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

/**
 * Program Arguments
 * -m to modify and apply the migrations
 * -v to verify if all the migrations are upto date of not
 * -r to repair and remove migrations which were applied correctly. These migrations are deleted completely
 *      from schema_version. So, if some part of that migration file was applied and some wasn't then make sure that the
 *      migration that was applied is removed. Once that is done, then use -m option again to apply the migration
 *
 *
 *      RUN this before anything else:
 */
@SpringBootApplication
public class DbMigrationApplication {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DbMigrationApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DbMigrationApplication.class, args);
        FlywayService flywayService = ctx.getBean(FlywayService.class);
        DataSource dataSource = ctx.getBean(DataSource.class);
        Actions action = Actions.MIGRATE;
        if (args.length != 1) {
            ctx.close();
            System.exit(0);
        } else {
            if (args[0].equals("m")) {
                action = Actions.MIGRATE;
            } else if (args[0].equals("r")) {
                action = Actions.REPAIR;
            } else if (args[0].equals("v")) {
                action = Actions.VALIDATE;
            } else {
                ctx.close();
                System.exit(0);
            }
        }
        if (Actions.MIGRATE.equals(action)) {
            flywayService.migrateDb(dataSource);
        }
        if (Actions.REPAIR.equals(action)) {
            flywayService.repairFlywayDb(dataSource);
        }
        if (Actions.VALIDATE.equals(action)) {
            flywayService.validateFlywayDb(dataSource);
        }
        ctx.close();
        System.exit(0);
    }

}
