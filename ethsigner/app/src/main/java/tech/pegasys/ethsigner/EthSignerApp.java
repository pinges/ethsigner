/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package tech.pegasys.ethsigner;

import picocli.CommandLine.RunLast;
import tech.pegasys.ethsigner.core.RunnerBuilder;
import tech.pegasys.ethsigner.core.signing.TransactionSigner;
import tech.pegasys.ethsigner.core.signing.fileBased.FileBasedSignerHelper;
import tech.pegasys.ethsigner.core.signing.hashicorp.HashicorpSignerHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class EthSignerApp {
  private static final int SUCCESS_EXIT_CODE = 0;
  private static final int ERROR_EXIT_CODE = 1;

  private static final Logger LOG = LogManager.getLogger();

  public static void main(final String... args) {
    final CommandLineConfig config = new CommandLineConfig(System.out);

    config.parse(new RunLast(), args);
  }
}
