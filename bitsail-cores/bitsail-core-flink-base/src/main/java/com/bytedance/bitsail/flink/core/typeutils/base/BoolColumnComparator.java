/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Original Files: apache/flink(https://github.com/apache/flink)
 * Copyright: Copyright 2014-2022 The Apache Software Foundation
 * SPDX-License-Identifier: Apache License 2.0
 *
 * This file may have been modified by ByteDance Ltd. and/or its affiliates.
 */

package com.bytedance.bitsail.flink.core.typeutils.base;

import com.bytedance.bitsail.common.column.BooleanColumn;

import org.apache.flink.api.common.typeutils.TypeComparator;
import org.apache.flink.core.memory.DataInputView;
import org.apache.flink.core.memory.MemorySegment;

import java.io.IOException;

/**
 * @desc:
 */
@Deprecated
public class BoolColumnComparator extends ColumnTypeComparator<BooleanColumn> {

  private static final long serialVersionUID = 1L;

  public BoolColumnComparator(boolean ascending) {
    super(ascending);
  }

  @Override
  public int compareSerialized(DataInputView firstSource, DataInputView secondSource)
      throws IOException {
    final int fs = firstSource.readBoolean() ? 1 : 0;
    final int ss = secondSource.readBoolean() ? 1 : 0;
    int comp = fs - ss;
    return ascendingComparison ? comp : -comp;
  }

  @Override
  public boolean supportsNormalizedKey() {
    return true;
  }

  @Override
  public int getNormalizeKeyLen() {
    return 1;
  }

  @Override
  public boolean isNormalizedKeyPrefixOnly(int keyBytes) {
    return keyBytes < 1;
  }

  @Override
  public void putNormalizedKey(BooleanColumn record, MemorySegment target, int offset, int numBytes) {
    Boolean val = record.asBoolean();

    if (numBytes > 0) {
      target.put(offset, (byte) (val.booleanValue() ? 1 : 0));

      for (offset = offset + 1; numBytes > 1; numBytes--) {
        target.put(offset++, (byte) 0);
      }
    }
  }

  @Override
  public TypeComparator<BooleanColumn> duplicate() {
    return new BoolColumnComparator(ascendingComparison);
  }
}
