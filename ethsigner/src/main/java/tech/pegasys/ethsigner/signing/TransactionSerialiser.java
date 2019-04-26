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
package tech.pegasys.ethsigner.signing;

import static tech.pegasys.ethsigner.signing.web3j.TransactionEncoder.createEip155SignatureData;
import static tech.pegasys.ethsigner.signing.web3j.TransactionEncoder.encode;

import tech.pegasys.ethsigner.signing.web3j.TransactionEncoder.TransmittableSignature;

import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

public class TransactionSerialiser {

  private final TransactionSigner signer;
  private long chainId;

  public TransactionSerialiser(final TransactionSigner signer, final long chainId) {
    this.signer = signer;
    this.chainId = chainId;
  }

  public String serialise(final RawTransaction rawTransaction) {
    final byte[] bytesToSign = encode(rawTransaction, chainId);

    final SignatureData signature = signer.sign(bytesToSign);

    final TransmittableSignature eip155Signature = createEip155SignatureData(signature, chainId);

    final byte[] serialisedBytes = encode(rawTransaction, eip155Signature);
    return Numeric.toHexString(serialisedBytes);
  }

  public String getAddress() {
    return signer.getAddress();
  }
}