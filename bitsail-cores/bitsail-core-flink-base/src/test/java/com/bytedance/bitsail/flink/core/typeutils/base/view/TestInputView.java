/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.bytedance.bitsail.flink.core.typeutils.base.view;

import org.apache.flink.core.memory.DataInputView;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class TestInputView extends DataInputStream implements DataInputView {

  public TestInputView(byte[] data) {
    super(new ByteArrayInputStream(data));
  }

  @Override
  public void skipBytesToRead(int numBytes) throws IOException {
    while (numBytes > 0) {
      int skipped = skipBytes(numBytes);
      numBytes -= skipped;
    }
  }
}