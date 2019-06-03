package tech.pegasys.ethsigner;

import java.io.PrintStream;
import java.util.List;
import org.apache.logging.log4j.Level;
import picocli.CommandLine;
import picocli.CommandLine.AbstractParseResultHandler;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParameterException;

@Command(
    description = "This command runs the EthSigner.",
    abbreviateSynopsis = true,
    name = "ethsigner",
    mixinStandardHelpOptions = true,
    versionProvider = VersionProvider.class,
    header = "Usage:",
    synopsisHeading = "%n",
    descriptionHeading = "%nDescription:%n%n",
    optionListHeading = "%nOptions:%n",
    footerHeading = "%n",
    footer = "EthSigner is licensed under the Apache License 2.0")
public class CommandlineParser {

  private CommandLine commandLine;
  protected final PrintStream output;

  public CommandlineParser(PrintStream output) {
    this.output = output;
  }

  public boolean parse(final AbstractParseResultHandler<List<Object>> resultHandler,
      final String... args) {

    commandLine = new CommandLine(this);
    commandLine.setCaseInsensitiveEnumValuesAllowed(true);
    commandLine.registerConverter(Level.class, Level::valueOf);

    HashicorpSignerCommand hashicorpSignerBasedConfig = new HashicorpSignerCommand(output);
    commandLine.addSubcommand(HashicorpSignerCommand.COMMAND_NAME, hashicorpSignerBasedConfig);

    FileBasedSignerCommand fileBasedSignerConfig = new FileBasedSignerCommand();
    commandLine.addSubcommand(FileBasedSignerCommand.COMMAND_NAME, fileBasedSignerConfig);

    // Must manually show the usage/version info, as per the design of picocli
    // (https://picocli.info/#_printing_help_automatically)
    try {
      commandLine.parseWithHandlers(resultHandler, null, args);
    } catch (ParameterException ex) {
      //handleParseException(ex);
      return false;
    }

    if (commandLine.isUsageHelpRequested()) {
      commandLine.usage(output);
      return false;
    } else if (commandLine.isVersionHelpRequested()) {
      commandLine.printVersionHelp(output);
      return false;
    }
    return true;
  }
/*
  public void handleParseException(final ParameterException ex) {
    if (logLevel != null && Level.DEBUG.isMoreSpecificThan(logLevel)) {
      ex.printStackTrace(output);
    } else {
      output.println(ex.getMessage());
    }
    if (!CommandLine.UnmatchedArgumentException.printSuggestions(ex, output)) {
      ex.getCommandLine().usage(output, Ansi.AUTO);
    }
  }*/

}
