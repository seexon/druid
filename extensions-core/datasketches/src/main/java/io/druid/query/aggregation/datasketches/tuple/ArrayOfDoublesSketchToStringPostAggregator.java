/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.query.aggregation.datasketches.tuple;

import java.util.Comparator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yahoo.sketches.tuple.ArrayOfDoublesSketch;

import io.druid.java.util.common.IAE;
import io.druid.query.aggregation.AggregatorUtil;
import io.druid.query.aggregation.PostAggregator;
import io.druid.query.cache.CacheKeyBuilder;

/**
 * Returns a human-readable summary of a given {@link ArrayOfDoublesSketch}.
 * This is a string returned by toString() method of the sketch.
 * This can be useful for debugging.
 */
public class ArrayOfDoublesSketchToStringPostAggregator extends ArrayOfDoublesSketchUnaryPostAggregator
{

  @JsonCreator
  public ArrayOfDoublesSketchToStringPostAggregator(
      @JsonProperty("name") final String name,
      @JsonProperty("field") final PostAggregator field
  )
  {
    super(name, field);
  }

  @Override
  public String compute(final Map<String, Object> combinedAggregators)
  {
    final ArrayOfDoublesSketch sketch = (ArrayOfDoublesSketch) getField().compute(combinedAggregators);
    return sketch.toString();
  }

  @Override
  public Comparator<String> getComparator()
  {
    throw new IAE("Comparing sketch summaries is not supported");
  }

  @Override
  public byte[] getCacheKey()
  {
    return new CacheKeyBuilder(AggregatorUtil.ARRAY_OF_DOUBLES_SKETCH_TO_STRING_CACHE_TYPE_ID)
        .appendCacheable(getField())
        .build();
  }

}
