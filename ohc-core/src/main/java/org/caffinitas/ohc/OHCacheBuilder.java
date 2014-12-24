/*
 *      Copyright (C) 2014 Robert Stupp, Koeln, Germany, robert-stupp.de
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.caffinitas.ohc;

import java.lang.reflect.InvocationTargetException;

import org.caffinitas.ohc.linked.OHCacheImpl;

public class OHCacheBuilder<K, V>
{
    private int segmentCount;
    private int hashTableSize = 8192;
    private int bucketLength = 8;
    private long capacity = 64L * 1024L * 1024L;
    private CacheSerializer<K> keySerializer;
    private CacheSerializer<V> valueSerializer;
    private float loadFactor = .75f;
    private long maxEntrySize;
    private Class<? extends OHCache> type = OHCacheImpl.class;

    private OHCacheBuilder()
    {
        segmentCount = roundUpToPowerOf2(Runtime.getRuntime().availableProcessors() * 2, 1 << 30);
    }

    static int roundUpToPowerOf2(int number, int max)
    {
        return number >= max
               ? max
               : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
    }

    public static <K, V> OHCacheBuilder<K, V> newBuilder()
    {
        return new OHCacheBuilder<>();
    }

    public OHCache<K, V> build()
    {
        try
        {
            return type.getDeclaredConstructor(OHCacheBuilder.class).newInstance(this);
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Class<? extends OHCache> getType()
    {
        return type;
    }

    public OHCacheBuilder<K, V> type(Class<? extends OHCache> type)
    {
        this.type = type;
        return this;
    }

    public int getHashTableSize()
    {
        return hashTableSize;
    }

    public OHCacheBuilder<K, V> hashTableSize(int hashTableSize)
    {
        this.hashTableSize = hashTableSize;
        return this;
    }

    public int getBucketLength()
    {
        return bucketLength;
    }

    public OHCacheBuilder<K, V> setBucketLength(int bucketLength)
    {
        this.bucketLength = bucketLength;
        return this;
    }

    public long getCapacity()
    {
        return capacity;
    }

    public OHCacheBuilder<K, V> capacity(long capacity)
    {
        this.capacity = capacity;
        return this;
    }

    public CacheSerializer<K> getKeySerializer()
    {
        return keySerializer;
    }

    public OHCacheBuilder<K, V> keySerializer(CacheSerializer<K> keySerializer)
    {
        this.keySerializer = keySerializer;
        return this;
    }

    public CacheSerializer<V> getValueSerializer()
    {
        return valueSerializer;
    }

    public OHCacheBuilder<K, V> valueSerializer(CacheSerializer<V> valueSerializer)
    {
        this.valueSerializer = valueSerializer;
        return this;
    }

    public int getSegmentCount()
    {
        return segmentCount;
    }

    public OHCacheBuilder<K, V> segmentCount(int segmentCount)
    {
        this.segmentCount = segmentCount;
        return this;
    }

    public float getLoadFactor()
    {
        return loadFactor;
    }

    public OHCacheBuilder<K, V> loadFactor(float loadFactor)
    {
        this.loadFactor = loadFactor;
        return this;
    }

    public long getMaxEntrySize()
    {
        return maxEntrySize;
    }

    public OHCacheBuilder<K, V> maxEntrySize(long maxEntrySize)
    {
        this.maxEntrySize = maxEntrySize;
        return this;
    }
}
