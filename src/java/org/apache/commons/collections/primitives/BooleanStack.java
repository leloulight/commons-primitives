/*
 *   Copyright 2004 The Apache Software Foundation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.apache.commons.collections.primitives ;


import java.util.EmptyStackException ;

import org.apache.commons.collections.primitives.ArrayBooleanList;


/**
 * A primitive boolean based Stack.  The underlying backing store is an
 * ArrayBooleanList where the front of the list is the bottom of the stack
 * and the tail of the list is the top of the stack.
 *
 * @author <a href="mailto:directory-dev@incubator.apache.org">
 * Apache Directory Project</a>
 * @version $Rev: 9968 $
 */
public class BooleanStack
{
    /** the underlying dynamic primitive backing store */
    private ArrayBooleanList list = new ArrayBooleanList() ;


    /**
     * Creates an empty primitive stack.
     */
    public BooleanStack()
    {
    }


    /**
     * Creates a stack prepopulating it with values.
     *
     * @param bits the array to add
     */
    public BooleanStack( boolean[] bits )
    {
        for ( int ii = 0; ii < bits.length; ii++ )
        {    
            list.add( bits[ii] ) ;
        }
    }
    

    /**
     * Tests if this stack is empty.
     * 
     * @return true if and only if this stack is empty; false otherwise
     */
    public boolean empty()
    {
        return list.isEmpty() ;
    }

    
    /**
     * Looks at the top of this stack without removing it.
     * 
     * @return the value at the top of this stack
     * @throws java.util.EmptyStackException if this stack is empty
     */
    public boolean peek()
    {
        if ( list.isEmpty() )
        {
            throw new EmptyStackException() ;
        }
        
        return list.get( list.size() - 1 ) ;
    }

    
    /**
     * Return the n'th boolean down the stack, where 0 is the top element and
     * [size()-1] is the bottom element.
     *
     * @param n the element index
     * @return the element at the index
     * @throws EmptyStackException if the stack is empty
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public boolean peek( int n )
    {
        if ( list.isEmpty() )
        {
            throw new EmptyStackException() ;
        }

        return list.get( list.size() - n - 1 ) ;
    }


    /**
     * Removes the value at the top of this stack and returns it.
     * 
     * @return value at the top of this stack
     * @throws java.util.EmptyStackException if this stack is empty
     */
    public boolean pop()
    {
        if ( list.isEmpty() )
        {
            throw new EmptyStackException() ;
        }
        
        return list.removeElementAt( list.size() - 1 ) ;
    }

    
    /**
     * Pushes a value onto the top of this stack.
     * 
     * @param item the value to push onto this stack
     * @return the item argument for call chaining
     */
    public boolean push( boolean item )
    {
        list.add( item ) ;
        return item ;
    }
    

    /**
     * Returns the 1-based position where a value is on this stack. If the value
     * occurs as an item in this stack, this method returns the distance from 
     * the top of the stack of the occurrence nearest the top of the stack; the 
     * topmost item on the stack is considered to be at distance 1. 
     * 
     * @param item the value to search for from the top down
     * @return the 1-based position from the top of the stack where the int is 
     *  located; the return value -1 indicates that the int is not on the stack
     */
    public int search( boolean item )
    {
        for ( int ii = list.size() - 1; ii >= 0; ii-- )
        {
            if ( list.get( ii ) == item )
            {
                return list.size() - ii ;
            }
        }
        
        
        return -1 ;
    }
    
    
    /**
     * Gets items from the stack where the index is zero based and the top of
     * the stack is at an index of size()-1 with the bottom of the stack at an
     * index of 0.
     * 
     * @param index the index into the stack treated as a list
     * @return the value at the index
     */
    public boolean get( int index )
    {
        return list.get( index ) ;
    }
    
    
    /**
     * Gets the size of this stack.
     * 
     * @return the size of this stack
     */
    public int size()
    {
        return list.size() ;
    }
    

    /**
     * Empties the contents of the stack.
     */
    public void clear()
    {
        list.clear() ;
    }
}
