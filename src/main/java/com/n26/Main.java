package com.n26;

import com.n26.controller.AggregateController;
import com.n26.controller.TransactionController;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by pratyushverma on 9/15/16.
 */
public class Main extends Application<AppConfiguration> {
    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    @Override
    public String getName() {
        return "n29";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(AppConfiguration configuration,
                    Environment environment) {
        environment.jersey().setUrlPattern("/transactionservice/*");
        environment.jersey().register(new AggregateController());
        environment.jersey().register(new TransactionController());
    }

}
