/*
 * Copyright 2003-2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections.primitives.adapters;

import java.util.Iterator;

import org.apache.commons.collections.primitives.ShortIterator;

/**
 * Adapts a {@link java.lang.Number Number}-valued 
 * {@link Iterator Iterator} 
 * to the {@link ShortIterator ShortIterator} 
 * interface.
 * <p />
 * This implementation delegates most methods
 * to the provided {@link Iterator Iterator} 
 * implementation in the "obvious" way.
 *
 * @since Commons Primitives 1.0
 * @version $Revision$ $Date$
 * @author Rodney Waldhoff 
 */
public class IteratorShortIterator implements ShortIterator {
    
    /**
     * Create an {@link ShortIterator ShortIterator} wrapping
     * the specified {@link Iterator Iterator}.  When
     * the given <i>iterator</i> is <code>null</code>,
     * returns <code>null</code>.
     * 
     * @param iterator the (possibly <code>null</code>) 
     *        {@link Iterator Iterator} to wrap
     * @return an {@link ShortIterator ShortIterator} wrapping the given 
     *         <i>iterator</i>, or <code>null</code> when <i>iterator</i> is
     *         <code>null</code>.
     */
    public static ShortIterator wrap(Iterator iterator) {
        return null == iterator ? null : new IteratorShortIterator(iterator);
    }
   
    /**
     * Creates an {@link ShortIterator ShortIterator} wrapping
     * the specified {@link Iterator Iterator}.
     * @see #wrap
     */
    public IteratorShortIterator(Iterator iterator) {
        _iterator = iterator;
    }
    
    public boolean hasNext() {
        return _iterator.hasNext();
    }
    
    public short next() {
        return ((Number)(_iterator.next())).shortValue();
    }
    
    public void remove() {
        _iterator.remove();
    }
    
    private Iterator _iterator = null;

}
