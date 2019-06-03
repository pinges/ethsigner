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
package tech.pegasys.ethsigner.core.signing.fileBased;

import java.nio.file.Path;
import tech.pegasys.ethsigner.core.signing.CredentialTransactionSigner;
import tech.pegasys.ethsigner.core.signing.TransactionSigner;

import java.io.IOException;
import java.nio.file.Files;

import com.google.common.base.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

public class FileBasedSignerHelper {

  private static final Logger LOG = LogManager.getLogger();

  public static TransactionSigner getSigner(final Path passwordFile, final Path keyfile) {
    String password;
    try {
      password = readPasswordFromFile(passwordFile);
    } catch (IOException e) {
      LOG.error(
          "Error when reading the password from file using the following path:\n {}.",
          passwordFile,
          e);
      return null;
    }
    Credentials credentials;
    try {
      credentials = WalletUtils.loadCredentials(password, keyfile.toFile());
    } catch (IOException e) {
      LOG.error(
          "Error when reading key file for the file based signer using the following path:\n {}.",
          keyfile,
          e);
      return null;
    } catch (CipherException e) {
      LOG.error(
          "Error when decrypting key file ({}) using supplied password file {}.",
          keyfile,
          passwordFile,
          e);
      return null;
    }
    return new CredentialTransactionSigner(credentials);
  }

  private static String readPasswordFromFile(final Path passwordFile)
      throws IOException {
    byte[] fileContent = Files.readAllBytes(passwordFile);
    return new String(fileContent, Charsets.UTF_8);
  }
}
