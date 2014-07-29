/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.cli;

import com.powerhive.meterservice.MeterServiceConfiguration;
import com.powerhive.meterservice.core.Template;
import com.google.common.base.Optional;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class RenderCommand.
 *
 * @author shermes641
 */
public class RenderCommand extends ConfiguredCommand<MeterServiceConfiguration> {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RenderCommand.class);

    /**
     * Instantiates a new render command.
     */
    public RenderCommand() {
        super("render", "Render the template data to console");
    }

    /* (non-Javadoc)
     * @see io.dropwizard.cli.ConfiguredCommand#configure(net.sourceforge.argparse4j.inf.Subparser)
     */
    @Override
    /**
     * 
     */
    public void configure(Subparser subparser) {
        super.configure(subparser);
        subparser.addArgument("-i", "--include-default")
                 .action(Arguments.storeTrue())
                 .dest("include-default")
                 .help("Also render the template with the default name");
        subparser.addArgument("names").nargs("*");
    }

    /* (non-Javadoc)
     * @see io.dropwizard.cli.ConfiguredCommand#run(io.dropwizard.setup.Bootstrap, net.sourceforge.argparse4j.inf.Namespace, io.dropwizard.Configuration)
     */
    @Override
    /**
     * 
     */
    protected void run(Bootstrap<MeterServiceConfiguration> bootstrap,
                       Namespace namespace,
                       MeterServiceConfiguration configuration) throws Exception {
        final Template template = configuration.buildTemplate();

        if (namespace.getBoolean("include-default")) {
            LOGGER.info("DEFAULT => {}", template.render(Optional.<String>absent()));
        }

        for (String name : namespace.<String>getList("names")) {
            for (int i = 0; i < 1000; i++) {
                LOGGER.info("{} => {}", name, template.render(Optional.of(name)));
                Thread.sleep(1000);
            }
        }
    }
}
