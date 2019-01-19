package com.tinyerp.gateway.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Utility class for Collections
 */
@SuppressWarnings("unused")
public final class CollectionsUtil
{
    /**
     * validates a collection and return empty collection if collection is null.
     *
     * @return empty collection or the original collection
     */
    public static <T> Collection<T> safe(Collection<T> collection )
    {
        return collection == null ? Collections.EMPTY_LIST : collection;
    }

    public static <T> Collection<T> asSafeCollection(Collection<T> unsafeCollection)
    {
        return unsafeCollection != null ? ImmutableList.copyOf(unsafeCollection) : Collections.emptyList();
    }

    public static <T> Set<T> asSafeSet(Set<T> unsafeSet)
    {
        return unsafeSet != null ? ImmutableSet.copyOf(unsafeSet) : Collections.emptySet();
    }

    public static <T> List<T> asSafeList(List<T> unsafeList)
    {
        return unsafeList != null ? ImmutableList.copyOf(unsafeList) : Collections.emptyList();
    }

    public static boolean isEmpty( Collection<?> collection )
    {
        if( collection == null || collection.isEmpty() ){
            return true;
        }
        return false;
    }
}
